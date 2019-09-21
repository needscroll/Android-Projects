package com.example.assignment3jaw160230;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static String TABLENAME = "CONTACTS";
    public static String COL1 = "ID";
    public static String COL2 = "FNAME";
    public static String COL3 = "LNAME";
    public static String COL4 = "PHONE";
    public static String COL5 = "BDAY";
    public static String COL6 = "CDATE";
    String CREATETABLE = "CREATE TABLE " + TABLENAME + "(" +
            COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " VARCHAR(20), " +
            COL3 + " VARCHAR(20), " +
            COL4 + " VARCHAR(20), " +
            COL5 + " VARCHAR(20), " +
            COL6 + " VARCHAR(20));";

    public static String DROPTABLE = "DROP TABLE IF EXISTS " + TABLENAME;
    public static String SELECTALL = "SELECT * FROM " + TABLENAME + ";";
    public static String DELETE = "DELETE FROM " + TABLENAME;

    public DBHelper(Context context) {
        super(context, TABLENAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROPTABLE);
        onCreate(db);
    }

    //adds a single point of data to a column
    public boolean addData(String column, String data)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put(column, data);

        long result = db.insert(TABLENAME, null, contentvalues);

        if (result == -1)
        {
            return false;
        }

        return true;
    }

    //adds an entire contact
    public boolean addContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();

        String[] columns = getFields();
        String[] data = contact.get_array();

        for(int i = 0; i < columns.length; i++)
        {
            contentvalues.put(columns[i], data[i]);
        }

        long result = db.insert(TABLENAME, null, contentvalues);

        if (result == -1)
        {
            return false;
        }

        return true;
    }

    //edits contact by deleting the old and replacing it
    public boolean editContact(Contact oldContact, Contact newContact)
    {
        deleteContact(oldContact);
        boolean create_new = addContact(newContact);
        return create_new;
    }

    //deletes a contact
    public void deleteContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Contact> list = readDatabase();
        ArrayList<Contact> finalList = new ArrayList<Contact>();
        String line = contact.toString();

        for (int i = 0; i < list.size(); i++)
        {
            if (!line.contains(list.get(i).toString()))
            {
                finalList.add(list.get(i));
            }
        }

        wipeTable();

        for (int i = 0; i < finalList.size(); i++)
        {
            addContact(finalList.get(i));
        }
    }

    //reads the database and returns the data in it
    public ArrayList<Contact> readDatabase()
    {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(SELECTALL, null);

        if (cursor.moveToFirst())
        {
            do {
                Contact contact = new Contact();
                contact.fname = cursor.getString(cursor.getColumnIndex(COL2));
                contact.lname = cursor.getString(cursor.getColumnIndex(COL3));
                contact.phone = cursor.getString(cursor.getColumnIndex(COL4));
                contact.bday = cursor.getString(cursor.getColumnIndex(COL5));
                contact.create_day = cursor.getString(cursor.getColumnIndex(COL6));
                contacts.add(contact);
            } while (cursor.moveToNext());
        }

        return contacts;
    }

    //wipes the table
    public void wipeTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DELETE);
    }

    //finds the index of a contact
    private int findIndex(Contact contact)
    {
        ArrayList<Contact> list = readDatabase();
        int key = -1;
        String line = contact.toString();

        for (int i = 0; i < list.size(); i++)
        {
            if (line.contains(list.get(i).toString()))
            {
                key = i;
            }
        }
        //Cursor cursor = db.query(TABLENAME, COL1, );
        //db
        return key;
    }

    private String[] getColumns()
    {
        String[] columns = {COL1, COL2, COL3, COL4, COL5, COL6};
        return columns;
    }

    private String[] getFields()
    {
        String[] columns = {COL2, COL3, COL4, COL5, COL6};
        return columns;
    }
}
