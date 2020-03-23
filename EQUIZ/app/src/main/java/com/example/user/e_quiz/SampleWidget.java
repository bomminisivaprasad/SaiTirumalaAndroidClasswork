package com.example.user.e_quiz;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SampleWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            SharedPreferences sharedPreferences = context.getSharedPreferences(MyScoreActivity.name, Context.MODE_PRIVATE);
            String title = JavaActivity.user.getEmail();
            String languageF = "languagef";
            String no_data = "NO DATA";
            String s = sharedPreferences.getString(languageF, no_data);
            String OP = "operators";
            String s1 = sharedPreferences.getString(OP, no_data);
            String dec = "declarative";
            String s2 = sharedPreferences.getString(dec, no_data);
            String ass = "assertions";
            String s3 = sharedPreferences.getString(ass, no_data);
            String excep = "exceptions";
            String s4 = sharedPreferences.getString(excep, no_data);
            String IClass = "innerclass";
            String s5 = sharedPreferences.getString(IClass, no_data);
            String gar = "gc";
            String s6 = sharedPreferences.getString(gar, no_data);
            String javalang = "javalang";
            String s7 = sharedPreferences.getString(javalang, no_data);
            String flow = "flowcontrol";
            String s8 = sharedPreferences.getString(flow, no_data);
            String OC = "oc";
            String s9 = sharedPreferences.getString(OC, no_data);
            String t = "threads";
            String s10 = sharedPreferences.getString(t, no_data);
            String overall = "overallque";
            String s11 = sharedPreferences.getString(overall, no_data);


            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sample_widget);
            String mi = "My Id : ";
            remoteViews.setTextViewText(R.id.appwidget_textTitle, mi + title);
            String lf = "Language Fundamentals : ";
            remoteViews.setTextViewText(R.id.appwidget_text, lf + s);
            String oa = "Operators And Assignments : ";
            remoteViews.setTextViewText(R.id.appwidget_text1, oa + s1);
            String da = "Declarations And AccessControl : ";
            remoteViews.setTextViewText(R.id.appwidget_text2, da + s2);
            String as = "Assertions : ";
            remoteViews.setTextViewText(R.id.appwidget_text3, as + s3);
            String ex = "Exceptions : ";
            remoteViews.setTextViewText(R.id.appwidget_text4, ex + s4);
            String ic = "Inner Classes : ";
            remoteViews.setTextViewText(R.id.appwidget_text5, ic + s5);
            String gc = "Garbage Collections : ";
            remoteViews.setTextViewText(R.id.appwidget_text6, gc + s6);
            String jl = "java.lang Class : ";
            remoteViews.setTextViewText(R.id.appwidget_text7, jl + s7);
            String fc = "Flow Control : ";
            remoteViews.setTextViewText(R.id.appwidget_text8, fc + s8);
            String lf1 = "LanguageFundamentals : ";
            remoteViews.setTextViewText(R.id.appwidget_text, lf1 + s);
            String oc1 = "Objects And Collections : ";
            remoteViews.setTextViewText(R.id.appwidget_text9, oc1 + s9);
            String tH1 = "Threads : ";
            remoteViews.setTextViewText(R.id.appwidget_text10, tH1 + s10);
            String oq = "Overall Questions : ";
            remoteViews.setTextViewText(R.id.appwidget_text11, oq + s11);


            Intent intent = new Intent(context, MyScoreActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widgetLinear, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }
    }


}

