package com.example.comp7082finalproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.comp7082finalproject.Presenter.CounterPresenter;
import com.example.comp7082finalproject.Presenter.DatabaseHelper;
import com.example.comp7082finalproject.R;
import com.example.comp7082finalproject.model.Counter;
import com.example.comp7082finalproject.model.CounterChange;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatsFragment extends Fragment {
    Counter c;
    TextView title, count, minVal, maxVal, lastUpdated, dailyTotal;

    DatabaseHelper dbHelper;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper( getActivity());
        title = view.findViewById(R.id.textViewTitle);
        count = view.findViewById(R.id.textViewCount);
        minVal = view.findViewById(R.id.textViewMinVal);
        maxVal = view.findViewById(R.id.textViewMaxVal);
        lastUpdated = view.findViewById(R.id.textViewLastUpdateVal);
        dailyTotal = view.findViewById(R.id.textViewDailyTotalVal);

        ArrayList<CounterChange> list;

        try {
            int id = getArguments().getInt("id");
            c =dbHelper.selectCounter(id);
            list = dbHelper.indexChanges(id);

            if(c!=null){
                title.setText(c.getTitle());
                Map<String,Number> values = CounterPresenter.values(list, c);

                count.setText(String.valueOf(values.get("count")));
                minVal.setText(String.valueOf(values.get("minVal")));
                maxVal.setText(String.valueOf(values.get("maxVal")));
                dailyTotal.setText(String.valueOf(values.get("dailyChange")));
                lastUpdated.setText(list.get(list.size()-1).getTime());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}