package com.example.assignment3jaw160230;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/*
overrides ArrayAdapter for multiple columns through getView()
finds the textviews in the listview and places the split downloaded strings into each textview
 */
public class PersonListAdapter extends ArrayAdapter<Contact> {
    private static final String TAG = "listviewlayout";
    private Context mContext;
    int resource;

    public PersonListAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String fname = getItem(position).fname;
        String lname = getItem(position).lname;
        String phone = getItem(position).phone;
        String bday = getItem(position).bday;
        String create_day = getItem(position).create_day;

        Contact contact = new Contact(fname, lname, phone, bday, create_day);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView view1 = (TextView) convertView.findViewById(R.id.FirstText);
        TextView view2 = (TextView) convertView.findViewById(R.id.SecondText);
        TextView view3 = (TextView) convertView.findViewById(R.id.ThirdText);
        TextView view4 = (TextView) convertView.findViewById(R.id.FourthText);
        TextView view5 = (TextView) convertView.findViewById(R.id.FifthText);


        view1.setText(fname);
        view2.setText(lname);
        view3.setText(phone);
        view4.setText(bday);
        view5.setText(create_day);
        return convertView;
    }
}
