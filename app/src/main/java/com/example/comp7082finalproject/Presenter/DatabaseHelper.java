package com.example.comp7082finalproject.Presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comp7082finalproject.model.Counter;
import com.example.comp7082finalproject.model.CounterChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finalProj.db";
    private static final String TABLE_NAME_COUNTER = "counter";
    private static final String TABLE_NAME_CHANGE = "counter_change";


    private static final String COUNTER_ID = "id";
    private static final String COUNTER_TITLE = "title";
    private static final String COUNTER_COUNT = "count";

    private static final String CHANGE_ID = "id";
    private static final String CHANGE_COUNTER_ID = "counter_id";
    private static final String CHANGE_NEW_COUNT = "new_count";
    private static final String CHANGE_TIME = "last_updated";

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

         String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME_COUNTER + " (" +
                 COUNTER_ID + " INTEGER PRIMARY KEY," +
                 COUNTER_TITLE + " TEXT," +
                 COUNTER_COUNT + " INTEGER)";
         db.execSQL(SQL_CREATE_ENTRIES);

        String SQL_CREATE_COUNTER_CHANGE = "CREATE TABLE " + TABLE_NAME_CHANGE + " (" +
                CHANGE_ID + " INTEGER PRIMARY KEY," +
                CHANGE_COUNTER_ID + " INTEGER," +
                CHANGE_TIME + " DATETIME," +
                CHANGE_NEW_COUNT + " INTEGER)";
        db.execSQL(SQL_CREATE_COUNTER_CHANGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME_COUNTER;
        db.execSQL(SQL_DELETE_ENTRIES);

        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME_CHANGE;
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);
    }

    public int createCounter(String title){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(COUNTER_TITLE, title);
            values.put(COUNTER_COUNT, 0);
            long newRowId = db.insert(TABLE_NAME_COUNTER, null, values);
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
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME_COUNTER, null);
            ArrayList<Counter> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    Counter counter = new Counter();
                    counter.setId(cursor.getInt(cursor.getColumnIndex(COUNTER_ID)));
                    counter.setCount(cursor.getInt(cursor.getColumnIndex(COUNTER_COUNT)));
                    counter.setTitle(cursor.getString(cursor.getColumnIndex(COUNTER_TITLE)));
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
                    COUNTER_ID,
                    COUNTER_TITLE,
                    COUNTER_COUNT
            };
            String selection = COUNTER_ID + " = ?";
            String[] selectionArgs = {""+id};
            Cursor cursor = db.query(
                    TABLE_NAME_COUNTER,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,
                    null
            );

            if (cursor.moveToFirst()) {
                Counter counter = new Counter();
                counter.setId(cursor.getInt(cursor.getColumnIndex(COUNTER_ID)));
                counter.setCount(cursor.getInt(cursor.getColumnIndex(COUNTER_COUNT)));
                counter.setTitle(cursor.getString(cursor.getColumnIndex(COUNTER_TITLE)));
                db.close();
                cursor.close();
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
            values.put(COUNTER_COUNT, count);

            // Which row to update, based on the title
            String selection = COUNTER_ID + " LIKE ?";
            String[] selectionArgs = {""+id};

            db.update(TABLE_NAME_COUNTER,values,selection,selectionArgs);
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void createChange(int counterId, int count){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(CHANGE_COUNTER_ID, counterId);
            values.put(CHANGE_NEW_COUNT, count);
            getDateTime();
            values.put(CHANGE_TIME, getDateTime());
            db.insert(TABLE_NAME_CHANGE, null, values);
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<CounterChange> indexChanges(int counterId){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String[] projection = {
                    CHANGE_ID,
                    CHANGE_COUNTER_ID,
                    CHANGE_NEW_COUNT,
                    CHANGE_TIME
            };
            String selection = CHANGE_COUNTER_ID + " = ?";
            String[] selectionArgs = {""+counterId};
            Cursor cursor = db.query(
                    TABLE_NAME_CHANGE,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,
                    CHANGE_TIME +" ASC"
            );

            ArrayList<CounterChange> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    CounterChange counterChanges = new CounterChange();
                    counterChanges.setId(cursor.getInt(cursor.getColumnIndex(CHANGE_ID)));
                    counterChanges.setCounterId(cursor.getInt(cursor.getColumnIndex(CHANGE_COUNTER_ID)));
                    counterChanges.setNewValue(cursor.getInt(cursor.getColumnIndex(CHANGE_NEW_COUNT)));
                    counterChanges.setTime(cursor.getString(cursor.getColumnIndex(CHANGE_TIME)));
                    list.add(counterChanges);
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
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
