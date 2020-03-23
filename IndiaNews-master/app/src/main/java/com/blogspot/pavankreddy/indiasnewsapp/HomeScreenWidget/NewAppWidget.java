package com.blogspot.pavankreddy.indiasnewsapp.HomeScreenWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.blogspot.pavankreddy.indiasnewsapp.Activities.MainActivity;
import com.blogspot.pavankreddy.indiasnewsapp.Activities.NewsDetailsActivity;
import com.blogspot.pavankreddy.indiasnewsapp.Adapter.RecyclerViewAdapter;
import com.blogspot.pavankreddy.indiasnewsapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider
{
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId)
    {
        String title = null,desc = null,image_link = null,news_url = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences(RecyclerViewAdapter.SHARED_PREFERENCE_FILE_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences!=null)
        {
            title = sharedPreferences.getString(NewsDetailsActivity.TITLE_KEY,context.getString(R.string.not_found_data));
            news_url = sharedPreferences.getString(NewsDetailsActivity.NEWS_LINK_KEY,context.getString(R.string.open_app_for_details));
        }
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.widget_news_title, "\n\n"+title);
        views.setTextViewText(R.id.news_link, "\n\n"+news_url);

        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_host,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

