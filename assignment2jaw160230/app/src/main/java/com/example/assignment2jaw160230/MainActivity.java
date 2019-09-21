package com.example.assignment2jaw160230;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/*
downloads a text file from the internet and displays it in a listview with multiple columns
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void display_data(View view)
    {
        //the base url, user input is concatnated onto this url to find the right file
        String short_url = "http://utdallas.edu/~jxc064000/2017Spring/";

        EditText edittext = findViewById(R.id.editText2);
        short_url += edittext.getText().toString().toUpperCase() + ".txt";

        Download1 download = new Download1();
        download.execute(short_url);
    }

    //class for downloading
    private class Download1 extends AsyncTask<String, Integer, String>
    {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() { //starts process dialog
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "Progress",
                    "Downloading");
        }

        //downloads the file from the internet and reads line by line into a stringbuilder and returns the stringbuilder
        @Override
        protected String doInBackground(String... address) {
            String result = "";
            HttpURLConnection connection = null;


            try {
                URL url = new URL(address[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(1000);
                //connection.setRequestProperty(HTTP.USER_AGENT, "myapp");
                connection.connect();

                String line = "";
                if (connection.getResponseCode() == 200)
                {
                    int length = connection.getContentLength();
                    InputStream stream = connection.getInputStream();
                    InputStreamReader inputreader = new InputStreamReader(stream, "UTF-8");

                    BufferedReader reader = new BufferedReader(inputreader);
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; (line = reader.readLine()) != null;i++)
                    {
                        builder.append(line).append("\n");
                        publishProgress((int)(i/length));
                    }
                    reader.close();
                    inputreader.close();
                    stream.close();
                    connection.disconnect();
                    return builder.toString();
                }
                else
                {
                    /* WHY DOES THIS NOT WORK??!!??!?
                    TextView error = findViewById(R.id.viewtext);
                    String error_message = "this is an error message";
                    error.setText(error_message); //why doesn't this work?
                    return "bad connection code: " + Integer.toString(connection.getResponseCode());
                    */
                    return Integer.toString(connection.getResponseCode());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "bad,address";
        }

        protected void onProgressUpdate(String... text) {

        }

        //clears button and edit text, splits downloaded string and puts it into the listview
        @Override
        protected void onPostExecute(String result)
        {
            ListView listview = findViewById(R.id.list);
            String[] resultArray = result.split("\n");
            ArrayList<Item> list = new ArrayList<Item>();

            for (String s: resultArray)
            {
                String[] split = s.split(",");
                if (split.length == 7)
                {
                    Item row = new Item(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
                    list.add(row);
                }
            }

            //ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
            //listview.setAdapter(itemsAdapter);

            PersonListAdapter adapter = new PersonListAdapter(MainActivity.this, R.layout.listviewlayout, list);
            listview.setAdapter(adapter);

            Button button = findViewById(R.id.button2);
            button.setVisibility(View.GONE);

            EditText edittext = findViewById(R.id.editText2);
            edittext.setVisibility(View.GONE);


            if (result.matches("\\d+(?:\\.\\d+)?"))
            {
                TextView error = findViewById(R.id.viewtext);
                String error_message = "Error message: " + result;
                error.setText(error_message); //why doesn't this work?
            }

            progressDialog.cancel();
        }
    }
}
