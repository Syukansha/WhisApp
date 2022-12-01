package com.sample.whisapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CryptoProject";
    private static final String TABLE_USER = "users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MESSAGE = "message";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_NAME + " TEXT,"+ COLUMN_PASSWORD + " TEXT,"+ COLUMN_EMAIL + " TEXT," + COLUMN_MESSAGE + " TEXT"+")";
        db.execSQL(CREATE_ITEM_TABLE);

    }
    public void open()
    {
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public boolean Login(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE name=? AND password=?", new String[]{username, password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public List<Users> getAllUsers() {
        List<Users> contactList = new ArrayList<Users>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setName(cursor.getString(1));
                users.setPassword(cursor.getString(2));
                users.setEmail(cursor.getString(3));
                users.setMessage(cursor.getString(4));
                // Adding contact to list
                contactList.add(users);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    Users getUser(String username,String password) {
        List<Users> contactList = new ArrayList();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER+" WHERE name=? AND password=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{username, password});
        if (cursor != null)
            cursor.moveToFirst();

        Users users = new Users(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));

        return users;
    }

    public boolean checkUser(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE name=?", new String[]{name});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return false;
            }
        }
        return true;
    }
    void addUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName()); // Name
        values.put(COLUMN_PASSWORD, user.getPassword()); //Password
        values.put(COLUMN_EMAIL, user.getEmail());// Email

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    void sendMessage(String rec, String message, receivers r){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("message", r.getMessage());// Message
        db.update(TABLE_USER, values, "email" + " = ?",
                new String[] {r.getEmail()});
        db.close(); // Closing database connection

    }
}
