package com.example.comp7082finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finalProj.db";
    private static final String TABLE_NAME = "counter";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_COUNT = "count";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
                 COLUMN_ID + " INTEGER PRIMARY KEY," +
                 COLUMN_TITLE + " TEXT," +
                 COLUMN_COUNT+ " INTEGER)";
         db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public int createCounter(String title){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_COUNT, 0);
            long newRowId = db.insert(TABLE_NAME, null, values);
            db.close();
            return (int) newRowId;
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
    public ArrayList<Counter> indexCounters(){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
            ArrayList<Counter> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Counter counter = new Counter();
                    counter.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    counter.setCount(cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)));
                    counter.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                    list.add(counter);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.close();
            return list;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Counter selectCounter(int id){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] projection = {
                    COLUMN_ID,
                    COLUMN_TITLE,
                    COLUMN_COUNT
            };
            String selection = COLUMN_ID + " = ?";
            String[] selectionArgs = {""+id};

            Cursor cursor = db.query(
                    TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                Counter counter = new Counter();
                counter.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                counter.setCount(cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT)));
                counter.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                db.close();
                return counter;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCounter(int id, int count){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            //what to update with
            ContentValues values = new ContentValues();
            values.put(COLUMN_COUNT, count);

            // Which row to update, based on the title
            String selection = COLUMN_ID + " LIKE ?";
            String[] selectionArgs = {""+id};

            db.update(TABLE_NAME,values,selection,selectionArgs);
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
