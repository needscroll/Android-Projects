package com.example.textviewbugtesting;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

        TextView wa = findViewById(R.id.myid);
        wa.setText("literal string");
        Download1 d = new Download1();
        d.execute("");
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

    //class for downloading
    private class Download1 extends AsyncTask<String, Integer, String>
    {

        @Override
        protected void onPreExecute() { //starts process dialog

        }

        //downloads the file from the internet and reads line by line into a stringbuilder and returns the stringbuilder
        @Override
        protected String doInBackground(String... address) {

            try {



                String line = "";
                if (1 == 2)
                {

                }
                else
                {
                    TextView wa = findViewById(R.id.myid);
                    wa.setText("literal strin1111111111111g");
                    return "a,a,a,a,a,a,a\n";
                }
            } catch (Exception e) {
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

        }
    }
}
