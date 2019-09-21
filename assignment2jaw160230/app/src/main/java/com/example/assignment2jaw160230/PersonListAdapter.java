package com.example.assignment2jaw160230;

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
public class PersonListAdapter extends ArrayAdapter<Item> {
    private static final String TAG = "listviewlayout";
    private Context mContext;
    int resource;

    public PersonListAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        mContext = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String a = getItem(position).a;
        String b = getItem(position).b;
        String c = getItem(position).c;
        String d = getItem(position).d;
        String e = getItem(position).e;
        String f = getItem(position).f;
        String g = getItem(position).g;


        Item item = new Item(a, b, c, d, e, f, g);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(resource, parent, false);

        TextView view1 = (TextView) convertView.findViewById(R.id.FirstText);
        TextView view2 = (TextView) convertView.findViewById(R.id.SecondText);
        TextView view3 = (TextView) convertView.findViewById(R.id.ThirdText);
        TextView view4 = (TextView) convertView.findViewById(R.id.FourthText);
        TextView view5 = (TextView) convertView.findViewById(R.id.FifthText);
        TextView view6 = (TextView) convertView.findViewById(R.id.SixthText);
        TextView view7 = (TextView) convertView.findViewById(R.id.SeventhText);


        view1.setText(a);
        view2.setText(b);
        view3.setText(c);
        view4.setText(d);
        view5.setText(e);
        view6.setText(f);
        view7.setText(g);
        return convertView;
    }
}
