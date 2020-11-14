package com.example.comp7082finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.ArrayList;

import static com.example.comp7082finalproject.CounterAppWidgetProvider.ADD;
import static com.example.comp7082finalproject.CounterAppWidgetProvider.SUBTRACT;

public class WidgetActivity extends AppCompatActivity {
    ArrayList<Counter> counters;
    DatabaseHelper dbHelper;

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String SHARED_PREFS = "prefs";
    public static final String TITLE_TEXT = "titleText";
    public static final String COUNT_TEXT = "countText";
    public static final String ID_TEXT = "idText";
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        dbHelper = new DatabaseHelper(this);
        getCounters();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adapter(counters);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putInt("id", counters.get(position).getId());
                confirmConfiguration(position);
            }
        });

        Intent configIntent = getIntent();
        Bundle extras = configIntent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED, resultValue);

        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }


    }
    public void getCounters(){
        counters = new ArrayList<>();
        counters = dbHelper.indexCounters();
    }

    public void confirmConfiguration(int position) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        Intent intent = new Intent(this, CounterAppWidgetProvider.class);
        intent.setAction(SUBTRACT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.counter_widget);

        Counter c = counters.get(position);
        views.setOnClickPendingIntent(R.id.btn_widget_sub, pendingIntent);

        views.setCharSequence(R.id.textView_widget_count, "setText", c.getCount()+"");
        views.setCharSequence(R.id.textView_widget_title, "setText", c.getTitle());

//        intent.setAction(ADD);
//        views.setOnClickPendingIntent(R.id.textView_widget_title, pendingIntent);
        views.setOnClickPendingIntent(R.id.btn_widget_sub, pendingIntent);


        appWidgetManager.updateAppWidget(appWidgetId, views);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TITLE_TEXT + appWidgetId, c.getTitle());
        editor.putInt(COUNT_TEXT + appWidgetId, c.getCount());
        editor.putInt(ID_TEXT + appWidgetId, c.getId());
        editor.apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}