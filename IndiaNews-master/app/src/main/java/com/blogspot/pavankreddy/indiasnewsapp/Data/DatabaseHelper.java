package com.blogspot.pavankreddy.indiasnewsapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "favourite_news.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE "  + DatabaseContract.DatabaseEntry.TABLE_NAME + " (" +
                DatabaseContract.DatabaseEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_TITLE + " TEXT, " +
                DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_DESC    + " TEXT,"+
                DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_URL_LINK    + " TEXT,"+
                DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_IMAGE_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            db.execSQL("DROP TABLE "+DatabaseContract.DatabaseEntry.TABLE_NAME);
            onCreate(db);
    }
}
