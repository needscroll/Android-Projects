package com.example.assignment3jaw160230;

import java.util.Comparator;

public class Contact implements Comparator<Contact> {

    public String fname;
    public String lname;
    public String phone;
    public String bday;
    public String create_day;

    public Contact()
    {
        fname = "";
        lname = "";
        phone = "";
        bday = "";
        create_day = "";
    }

    public Contact(String fname, String lname, String phone, String bday, String create_day) {
        this.fname = fname;
        this.lname = lname;
        this.phone = phone;
        this.bday = bday;
        this.create_day = create_day;
    }

    //no longer used?
    public String[] get_array()
    {
        String[] result = new String[5];
        result[0] = fname;
        result[1] = lname;
        result[2] = phone;
        result[3] = bday;
        result[4] = create_day;
        return result;
    }

    //determines if the contact is a blank contact
    public boolean isBlank()
    {
        String[] data = get_array();
        for (String field: data)
        {
            if (field.length() > 0)
            {
                return false;
            }
        }
        return true;
    }

    public String toString()
    {
        return fname + ":" + lname + ":" + phone + ":" + bday + ":" + create_day;
    }

    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.get_array()[0].compareTo(o2.get_array()[0]);
    }
}
