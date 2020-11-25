package com.example.comp7082finalproject.Presenter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.comp7082finalproject.R;
import com.example.comp7082finalproject.View.MainActivity;
import com.example.comp7082finalproject.model.Counter;

import static com.example.comp7082finalproject.View.WidgetActivity.ID_TEXT;
import static com.example.comp7082finalproject.View.WidgetActivity.TITLE_TEXT;
import static com.example.comp7082finalproject.View.NewFragment.SHARED_PREF;

public class CounterAppWidgetProvider extends AppWidgetProvider {
    DatabaseHelper dbHelper;
    public static final String ADD ="add";
    public static final String SUBTRACT ="sub";
    Counter c;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);

        for(int appWidgetId: appWidgetIds){

            RemoteViews views  = new RemoteViews(context.getPackageName(), R.layout.counter_widget);
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);

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
            views.setOnClickPendingIntent(R.id.btn_widget_add, getPendingSelfIntent(context, ADD, appWidgetId));
            views.setOnClickPendingIntent(R.id.btn_widget_sub, getPendingSelfIntent(context, SUBTRACT, appWidgetId));
            views.setCharSequence(R.id.textView_widget_title, "setText", title);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(context,0,intent, 0);
            views.setOnClickPendingIntent(R.id.textView_widget_title, pi);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
    @Override
    public void onReceive(Context context, Intent intent){
        super.onReceive(context,intent);
        RemoteViews views  = new RemoteViews(context.getPackageName(),R.layout.counter_widget);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF,Context.MODE_PRIVATE);
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        int id =prefs.getInt(ID_TEXT +appWidgetId, 0);

        if(SUBTRACT.equals(intent.getAction())){
            try {
                dbHelper = new DatabaseHelper(context);
                c = dbHelper.selectCounter(id);
                if (c != null) {
                    c.setCount(c.getCount()-1);
                    updateCount(context, views,appWidgetId );
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Log.d("","subtract");
        }else if(ADD.equals(intent.getAction())){
            try {
                dbHelper = new DatabaseHelper(context);
                c = dbHelper.selectCounter(id);
                if (c != null) {
                    c.setCount(c.getCount()+1);
                    updateCount(context,views,appWidgetId);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            Log.d("","add");
        }
    }

    protected  PendingIntent getPendingSelfIntent(Context context, String action, int appWidgetId){
        Intent intent =  new Intent(context, CounterAppWidgetProvider.class);
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void updateCount(Context context, RemoteViews views,  int appWidgetId) {
        dbHelper.updateCounter(c.getId(), c.getCount());
        dbHelper.createChange(c.getId(), c.getCount());
        views.setTextViewText(R.id.textView_widget_count, "" + c.getCount());
        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, views);
    }
}
