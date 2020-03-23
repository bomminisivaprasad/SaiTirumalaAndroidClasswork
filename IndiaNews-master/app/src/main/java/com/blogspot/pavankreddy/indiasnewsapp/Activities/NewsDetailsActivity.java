package com.blogspot.pavankreddy.indiasnewsapp.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.pavankreddy.indiasnewsapp.Data.DatabaseContract;
import com.blogspot.pavankreddy.indiasnewsapp.HomeScreenWidget.NewAppWidget;
import com.blogspot.pavankreddy.indiasnewsapp.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blogspot.pavankreddy.indiasnewsapp.Data.DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_TITLE;
import static com.blogspot.pavankreddy.indiasnewsapp.Data.DatabaseContract.DatabaseEntry.CONTENT_URI;

public class NewsDetailsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    public static final String TITLE_KEY = "TITLE";
    public static final String DESC_KEY = "DESC";
    public static final String IMAGE_LINK_KEY = "IMAGE_LINK";
    public static final String NEWS_LINK_KEY = "NEWS_LINK";

    @BindView(R.id.image_of_news_article)
    ImageView imageView;
    @BindView(R.id.news_article_title)
    TextView news_title;
    @BindView(R.id.news_description)
    TextView news_description;

    String news_url;
    FloatingActionButton fab;
    String title;
    String desc;
    String image_link;
    boolean status;
    int id = 0;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ButterKnife.bind(this);
        Intent widget_intent = new Intent(this, NewAppWidget.class);
        widget_intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), NewAppWidget.class));
        widget_intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(widget_intent);

        imageView = findViewById(R.id.image_of_news_article);
        news_title = findViewById(R.id.news_article_title);
        news_description = findViewById(R.id.news_description);

        Intent intent = getIntent();
        title = intent.getStringExtra(TITLE_KEY);
        setTitle(title);
        desc = intent.getStringExtra(DESC_KEY);
        image_link = intent.getStringExtra(IMAGE_LINK_KEY);
        news_url = intent.getStringExtra(NEWS_LINK_KEY);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Glide.with(this).load(image_link).into(imageView);
        news_title.setText(title);
        news_description.setText(desc);



        status = checkifNewsInDatabase(title);
        if(status)
        {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        else
        {
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkifNewsInDatabase(title))
                {
                    int i = deleteNewsArticle(title);
                    if(i>0)
                    {
                        fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }

                }
                else
                {
                    boolean i = addNewsToDatabase();
                    if(i)
                    {
                        fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                }
            }
        });
    }

    private boolean checkifNewsInDatabase(String title) {
        boolean present = false;
        Cursor cursor = getContentResolver().query(CONTENT_URI,null,null,null,null);
        if(cursor!=null && cursor.moveToFirst())
        {
            do
            {
                if(title.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(COLOUMN_ARTICLE_TITLE))))
                {
                    present = true;
                    id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.DatabaseEntry._ID));
                }

            }
            while(cursor.moveToNext());
        }
        return present;
    }
    public void loadWebView(View view)
    {
        Intent intent = new Intent(this,NewsUrlLoaderActivity.class);
        intent.putExtra(NewsUrlLoaderActivity.LINK_KEY,news_url);
        startActivity(intent);
    }



    private boolean addNewsToDatabase()
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_TITLE,title);
        contentValues.put(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_DESC,desc);
        contentValues.put(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_IMAGE_LINK,image_link);
        contentValues.put(DatabaseContract.DatabaseEntry.COLOUMN_ARTICLE_URL_LINK,news_url);
        Uri uri = getContentResolver().insert(DatabaseContract.DatabaseEntry.CONTENT_URI,contentValues);
        if(uri!=null)
        {
            Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private int deleteNewsArticle(String title)
    {
        if(id!=0)
        {
            Uri uri = DatabaseContract.DatabaseEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(title).build();
            int a = getContentResolver().delete(uri, null, null);
            if(a>0)
            {
                Toast.makeText(this, "Row "+a+" Deleted", Toast.LENGTH_SHORT).show();
                return a;
            }

        }
        return 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void speakText(View view)
    {
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    private void speakOut()
    {
        if(desc!=null)
            tts.speak(desc, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void stopSpeaking(View view)
    {
        tts.stop();
    }
}
