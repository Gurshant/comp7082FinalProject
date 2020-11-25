package com.example.comp7082finalproject.Presenter;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.comp7082finalproject.R;
import com.example.comp7082finalproject.model.Counter;
import com.example.comp7082finalproject.model.CounterChange;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.comp7082finalproject.Presenter.CounterAppWidgetProvider.ADD;
import static com.example.comp7082finalproject.Presenter.CounterAppWidgetProvider.SUBTRACT;

public class CounterPresenter {
    public static int currentCountAfterUpdate(Counter c, DatabaseHelper dbHelper){
        try {
            dbHelper.updateCounter(c.getId(), c.getCount());
            dbHelper.createChange(c.getId(), c.getCount());
            return c.getCount();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public static Map<String,Number> values(ArrayList<CounterChange> list, Counter c){
        int dailyChange =0;
        int lastVal;
        int min=list.get(0).getNewValue();
        int max=list.get(0).getNewValue();
        Map<String, Number> values = new HashMap<>();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        for (int i = 1; i < list.size(); i++) {
            if(list.get(i).getNewValue() < min){
                min = list.get(i).getNewValue();
            }else if(list.get(i).getNewValue() > max){
                max = list.get(i).getNewValue();
            }
            try {
                String updateCountDate = sdf.format(sdf.parse(list.get(i).getTime()));
                lastVal = list.get(i - 1).getNewValue();
                if (today.equals(updateCountDate)) {
                    int currentVal = list.get(i).getNewValue();
                    if (currentVal > lastVal) {
                        dailyChange++;
                    } else if (currentVal < lastVal) {
                        dailyChange--;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }
        }
        values.put("count", c.getCount());
        values.put("minVal", min);
        values.put("maxVal", max);
        values.put("dailyChange", dailyChange);
        return values;
    }


    public static ArrayList<Counter> getCounters( DatabaseHelper dbHelper){
        ArrayList<Counter> counters = dbHelper.indexCounters();
        return counters;
    }

    public static PendingIntent getPendingSelfIntent(Context context, String action, int appWidgetId){
        Intent intent =  new Intent(context, CounterAppWidgetProvider.class);
        intent.setAction(action);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
