package com.example.comp7082finalproject;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NewFragment extends Fragment {
    DatabaseHelper dbHelper;
    EditText et_title;

    public static final String SHARED_PREF = "prefs";
    private int appWidgetID = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper( getActivity());
        et_title=view.findViewById(R.id.editTextTitle);

        view.findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                int id =dbHelper.createCounter(et_title.getText().toString());
                args.putInt("id", id);
                NavHostFragment.findNavController(NewFragment.this).navigate(R.id.action_NewFragment_to_ShowFragment, args);
            }
        });

        Intent configIntent =getActivity().getIntent();
        Bundle extras = configIntent.getExtras();
        if(extras!=null){
            appWidgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        Intent resultVal = new Intent();
        resultVal.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetID);
        getActivity().setResult(getActivity().RESULT_CANCELED,resultVal);

    }
}