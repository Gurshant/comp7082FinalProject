package com.example.comp7082finalproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import static com.example.comp7082finalproject.WidgetActivity.COUNT_TEXT;
import static com.example.comp7082finalproject.WidgetActivity.ID_TEXT;
import static com.example.comp7082finalproject.WidgetActivity.TITLE_TEXT;
import static com.example.comp7082finalproject.NewFragment.SHARED_PREF;

public class CounterAppWidgetProvider extends AppWidgetProvider {
    DatabaseHelper dbHelper;
    public static final String ADD ="add";
    public static final String SUBTRACT ="sub";
    Counter c;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);

        for(int appWidgetId: appWidgetIds){

            RemoteViews views  = new RemoteViews(context.getPackageName(),R.layout.counter_widget);
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF,context.MODE_PRIVATE);

            int id =prefs.getInt(ID_TEXT +appWidgetId, 0);

            try {
                dbHelper = new DatabaseHelper(context);
                c = dbHelper.selectCounter(id);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (c != null) {
                views.setTextViewText(R.id.textView_widget_count,"" + c.getCount());
            }
            String title = prefs.getString(TITLE_TEXT + appWidgetId, "title");

            Intent intent =  new Intent(context, CounterAppWidgetProvider.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            intent.setAction(SUBTRACT);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.btn_widget_sub, pendingIntent);
//            views.setOnClickPendingIntent(R.id.btn_widget_add, getPendingSelfIntent(context, add));
            views.setCharSequence(R.id.textView_widget_title, "setText", title);
//            views.setCharSequence(R.id.textView_widget_count, "setText", ""+count);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
        RemoteViews views  = new RemoteViews(context.getPackageName(),R.layout.counter_widget);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF,context.MODE_PRIVATE);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        int id =prefs.getInt(ID_TEXT +appWidgetId, 0);

        if(SUBTRACT.equals(intent.getAction())){
            try {
                dbHelper = new DatabaseHelper(context);
                c = dbHelper.selectCounter(id);
                int count = c.getCount();

                if (c != null) {
                    dbHelper.updateCounter(c.getId(), count-1);
                    count--;
                    views.setTextViewText(R.id.textView_widget_count,"" + count);
                    AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Log.d("","subtract");
        }else if(ADD.equals(intent.getAction())){
            Log.d("","add");
        }
    }

    protected  PendingIntent getPendingSelfIntent(Context context, String action){
//        Intent intent =  new Intent(context, CounterAppWidgetProvider.class);
//        intent.setAction(action);
//        return PendingIntent.getBroadcast(context,0,intent,0);
        return null;
    }
}
