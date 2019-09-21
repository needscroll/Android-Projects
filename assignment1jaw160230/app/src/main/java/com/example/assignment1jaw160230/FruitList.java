package com.example.assignment1jaw160230;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FruitList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_list);
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


        //gets fruit order list from last intent
        TextView fruitlist = findViewById(R.id.fruitlist);
        Intent intent = getIntent();
        ArrayList<String> list = intent.getStringArrayListExtra("fruit order");

        //if list exists, displays list
        if (list != null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                fruitlist.setText(fruitlist.getText().toString() + list.get(i) + "\n");
            }
        }
    }
}
