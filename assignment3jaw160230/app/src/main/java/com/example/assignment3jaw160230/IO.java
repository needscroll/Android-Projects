package com.example.assignment3jaw160230;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class IO extends AsyncTask<String, Void, String> {

    private Context contextRef;
    private View rootview;
    String filename = "contactlist14.txt";
    private File dir;
    ArrayList<Contact> contactlist = new ArrayList<Contact>();
    public String debug = "default debug";
    boolean sort = false;

    public IO(Context context, View rootview) {
        this.contextRef = context;
        this.rootview = rootview;
    }

    //logic to tell which command to do from the main activity
    @Override
    protected String doInBackground(String... strings) {
        String command = strings[0];
        String[] parts = command.split(";");
        debug = command;

        if (command.contains("add"))
        {
            String data = parts[1];
            write_line(data);
            debug = "adding";
        }
        if (command.contains("edit"))
        {
            String old_data = parts[1];
            String new_data = parts[2];
            edit_contact_list(old_data, new_data);
            debug = "editing";
        }
        if (command.contains("delete"))
        {
            String toDelete = parts[1];
            delete_from_list(toDelete);
            debug = "deleteing";
        }
        if (command.contains("resort"))
        {
            sort = true;
        }

        return null;
    }

    //====================================

    //reads from the contactlist.txt file and returns the lines in a string array
    public String[] read()
    {
        ArrayList<String> input = new ArrayList<String>();
        try
        {
            dir = new File(contextRef.getFilesDir(), filename);
            if (dir.exists())
            {
                Scanner contacts = new Scanner(dir);
                while (contacts.hasNextLine())
                {
                    input.add(contacts.nextLine());
                }
                contacts.close();
            }
        }catch (Exception ex)
        {
            //debug("read file contents failure");
            debug = "test read failure";
        }
        return input.toArray(new String[1]);
    }

    //writes a line to the end of the txt file
    //does this by reading in the entire file, then writing it back plus the additional line
    public void write_line(String string)
    {
        String[] current = read();

        try
        {
            dir = new File(contextRef.getFilesDir(), filename);
            PrintWriter writer = new PrintWriter(dir);

            for (int i = 0; i < current.length; i++)
            {
                String line = current[i];
                if (line != null)
                {
                    writer.println(line);
                }
            }
            writer.println(string);
            writer.close();
        }catch (Exception ex)
        {
            debug = "test write failure";
        }
    }

    //=====================
    //reads from the contact list and returns a valid list of contact objects from the data in the list
    public ArrayList<Contact> read_contact_list()
    {
        ArrayList<Contact> result = new ArrayList<Contact>();
        String[] current_data = read();

        for (int i = 0; i < current_data.length; i++)
        {
            String line = current_data[i];
            if (line != null)
            {
                String[] split = line.split(":");
                if (split.length >= 5)
                {
                    result.add(new Contact(split[0], split[1], split[2], split[3], split[4]));
                }
            }
        }
        return result;
    }

    //deletes a line from the file if one matches the string
    //does this by reading in all the data, then writing all but that string back to the file
    public void delete_from_list(String string)
    {
        String[] current_data = read();
        try
        {
            dir = new File(contextRef.getFilesDir(), filename);
            PrintWriter writer = new PrintWriter(dir);

            for (int i = 0; i < current_data.length; i++)
            {
                String line = current_data[i];
                if (line != null && !line.contains(string))
                {
                    writer.println(line);
                }
            }
            writer.close();
        }catch (Exception ex)
        {
            //debug2("write_contact_list failure");
        }
    }

    //edits the contact list by reading in the data then writing it all back with the changes made
    public void edit_contact_list(String old_contact, String new_contact)
    {
        String[] current_data = read();
        try
        {
            dir = new File(contextRef.getFilesDir(), filename);
            PrintWriter writer = new PrintWriter(dir);

            for (int i = 0; i < current_data.length; i++)
            {
                String line = current_data[i];
                if (line != null && !line.contains(old_contact))
                {
                    writer.println(line);
                }
            }
            writer.println(new_contact);
            writer.close();
        }catch (Exception ex)
        {
            //debug2("write_contact_list failure");
        }
    }

    private ArrayList<Contact> sort_list(ArrayList<Contact> list)
    {
        ArrayList<Contact> result = list;
        Collections.sort(result, new Contact());
        return result;
    }

    //on completion, refreshes the list to update the contactlist view list
    @Override
    protected void onPostExecute(String s) {
        contactlist = read_contact_list();
        if (sort)
        {
            contactlist = sort_list(contactlist);
        }
        ListView listview = rootview.findViewById(R.id.list);
        PersonListAdapter adapter = new PersonListAdapter(contextRef, R.layout.scrollinglist, contactlist);
        listview.setAdapter(adapter);
    }
}
