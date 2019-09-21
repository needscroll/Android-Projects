package com.example.assignment1jaw160230;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
this app creates 5 text views with various fruits on the screen.
clicking on a fruit makes it disapear
when all fruits are gone, a view fruit order text will appear
clicking on the text launches a second activity where the fruits are listed in the order they are clicked
 */
public class FruitBeGone extends AppCompatActivity {

    ArrayList<String> gone_order = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //this calls the saved state
        setContentView(R.layout.activity_main); //sets this as main screen
        Toolbar toolbar = findViewById(R.id.toolbar); //makes toolbar, not used in this app
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab); //makes fab, not used in this app
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

    public void make_gone(View view)
    {
        //this sets the clicked on item invisible
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.INVISIBLE);

            //gets name of fruit from view
            String fruitname = ((TextView)view).getText().toString();
            gone_order.add(fruitname);

            //checks if lsat fruit is now gone
            if (gone_order.size() == 5) {

                TextView list = findViewById(R.id.viewlist);
                list.setVisibility(View.VISIBLE);

            }
        }
    }

    public void show_order(View view)
    {
        //if view list is visible, starts new intent to view fruit list
        if (view.getVisibility() == View.VISIBLE)
        {
            Intent intent = new Intent(this, FruitList.class);
            intent.putStringArrayListExtra("fruit order", gone_order);
            startActivity(intent);
        }
    }
}
