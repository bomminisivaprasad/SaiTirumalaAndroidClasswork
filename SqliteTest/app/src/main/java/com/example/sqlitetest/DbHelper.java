package com.example.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final  String TABLE_NAME = "student";
    public static final String DB_NAME = "sten";
    public static final String COL_0 = "id";
    public static final String COL_1 = "name";
    public static final String COL_2 = "mobile";
    public static final String QUERY = "create table if not exists student(id integer primary key autoincrement,name varchar(200),mobile varchar(10));";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table "+TABLE_NAME);
        onCreate(db);

    }

    public long insertData(ContentValues values){
        SQLiteDatabase db = getWritableDatabase();
        long i = db.insert(TABLE_NAME,null,values);
        return i;

    }

    public Cursor getData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("Select *from "+TABLE_NAME,null);
        return c;

    }


}
