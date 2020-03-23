package com.blogspot.pavankreddy.indiasnewsapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract
{
    public static final String AUTHORITY = "com.blogspot.pavankreddy.indianewsapp.CONTENT_PROVIDER";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final class DatabaseEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "fav_news_articles";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final String COLOUMN_ARTICLE_TITLE = "news_title";
        public static final String COLOUMN_ARTICLE_DESC = "news_desc";
        public static final String COLOUMN_ARTICLE_IMAGE_LINK="image_link";
        public static final String COLOUMN_ARTICLE_URL_LINK = "article_url";

    }
}
