package com.example.assignment3jaw160230;

import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
james wei
cs 4301.002
this app acts as a basic contact manager storing the name, phonenumber, and dates of a contact
 */
public class MainActivity extends AppCompatActivity implements SensorListener {

    List<Contact> contactlist = new ArrayList<Contact>();
    String filename = "contactlist10.txt";
    private File dir;
    private ViewGroup viewGroup;

    SensorManager sensorm = null;
    long lastUpdate;
    float x1 = 0;
    float y1 = 0;
    float z1 = 0;
    private final float SHAKE_THRESHOLD = 800;

    /*
    on create, initializes the contactlist through the io async activity and sets the onclick listener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorm.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        lastUpdate = System.currentTimeMillis();


        IO io = getIOTask();
        io.execute("");

        final ListView listview = findViewById(R.id.list);
        PersonListAdapter adapter = new PersonListAdapter(MainActivity.this, R.layout.scrollinglist, contactlist);
        listview.setAdapter(adapter);
        //create_contact();
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit_contact(view);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.addcontact)
        {
            debug("power strip button pressed");
            create_contact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    on click, reads in the information from the view and sends it to the second activity for editing
     */
    public void edit_contact(View view)
    {
        ArrayList<String> data = new ArrayList<String>();
        TextView view1 = view.findViewById(R.id.FirstText);
        TextView view2 = view.findViewById(R.id.SecondText);
        TextView view3 = view.findViewById(R.id.ThirdText);
        TextView view4 = view.findViewById(R.id.FourthText);
        TextView view5 = view.findViewById(R.id.FifthText);
        data.add(view1.getText().toString());
        data.add(view2.getText().toString());
        data.add(view3.getText().toString());
        data.add(view4.getText().toString());
        data.add(view5.getText().toString());

        Intent edit_intent = new Intent(this, ContactDetails.class);
        edit_intent.putStringArrayListExtra("old data", data);
        startActivityForResult(edit_intent, 999);
    }

    /*
    on click, creates a new blank contact and sends the information to the second activity
    when the second activity adds a new person, it will override the blank contact created
     */
    public void create_contact()
    {
        ArrayList<String> old_data = new ArrayList<String>();
        Contact a = new Contact();
        contactlist.add(a);

        for (int i = 0; i < 5; i++)
        {
            old_data.add("");
        }

        Intent edit_intent = new Intent(this, ContactDetails.class);
        edit_intent.putExtra("create new", true);
        edit_intent.putStringArrayListExtra("old data", old_data);
        startActivityForResult(edit_intent, 999);
    }

    //updates the list, no longer used because of the IO async task
    private void update_list()
    {
        ListView listview = findViewById(R.id.list);
        PersonListAdapter adapter = new PersonListAdapter(MainActivity.this, R.layout.scrollinglist, contactlist);
        listview.setAdapter(adapter);
    }

    /*
    the four following functions debug the app
     */
    public void debug(String custom_string)
    {
        TextView a = findViewById(R.id.debug);
        a.setText(custom_string);
    }

    public void debug(int num)
    {
        TextView a = findViewById(R.id.debug);
        a.setText(Integer.toString(num));
    }

    public void debug2(String custom_string)
    {
        TextView a = findViewById(R.id.debug2);
        a.setText(custom_string);
    }

    public void debug2(int num)
    {
        TextView a = findViewById(R.id.debug2);
        a.setText(Integer.toString(num));
    }

    /*
    on return from the second activity, it checks a few booleans to see what happens and then does things accordingly
    function reads old data in order to know what contact to overwrite or delete
    all file IO is done through the IO async task
    finally, the contact list is read again to refresh and update the list
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //debug("second activity ended");
        if (requestCode == 999 && resultCode == RESULT_OK && data != null)
        {
            ArrayList<String> new_data = data.getStringArrayListExtra("new data");
            ArrayList<String> old_data = data.getStringArrayListExtra("old data");
            boolean deleted = data.getBooleanExtra("deleted", false);
            boolean create_new = data.getBooleanExtra("create new", false);
            String command = "";
            IO io = getIOTask();

            if (old_data != null)
            {
                if (deleted)
                {

                    Contact toDelete = new Contact(old_data.get(0), old_data.get(1), old_data.get(2), old_data.get(3), old_data.get(4));

                    command += "delete;";
                    command += toDelete.toString();
                    io.execute(command);
                    //delete_from_list(toDelete.toString());
                }
                else
                {
                    Contact old_contact = new Contact(old_data.get(0), old_data.get(1), old_data.get(2), old_data.get(3), old_data.get(4));
                    Contact new_contact = new Contact(new_data.get(0), new_data.get(1), new_data.get(2), new_data.get(3), new_data.get(4));
                    if (old_contact.isBlank())
                    {
                        command += "add;";
                        command += new_contact.toString();
                        io.execute(command);
                        //write_line(new_contact.toString());
                    }
                    else
                    {
                        command += "edit;";
                        command += old_contact.toString() + ";";
                        command += new_contact.toString();
                        io.execute(command);
                        //edit_contact_list(old_contact.toString(), new_contact.toString());
                    }
                }
            }
        }
        IO io = getIOTask();
        io.execute("");
    }

    //returns an IO async task
    private IO getIOTask()
    {
        viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        IO io = new IO(this, viewGroup);
        return io;
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float x = values[SensorManager.DATA_X];
                float y = values[SensorManager.DATA_Y];
                float z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - x1 - y1 - z1) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    IO io = getIOTask();
                    io.execute("resort");

                    /*
                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                    debug2("sensor");*/
                }
                x1 = x;
                y1 = y;
                z1 = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
