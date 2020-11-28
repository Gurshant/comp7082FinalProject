package com.example.comp7082finalproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.comp7082finalproject.Presenter.CounterPresenter;
import com.example.comp7082finalproject.Presenter.DatabaseHelper;
import com.example.comp7082finalproject.R;
import com.example.comp7082finalproject.model.Counter;

public class ShowFragment extends Fragment {

    Counter c;
    TextView count;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final DatabaseHelper dbHelper = new DatabaseHelper( getActivity());
        view.findViewById(R.id.button_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ShowFragment.this)
                        .navigate(R.id.action_ShowFragment_to_ListFragment);
            }
        });

        TextView title = view.findViewById(R.id.textView_title);
        count = view.findViewById(R.id.textView_count);
        try {
            c = CounterPresenter.getCounter(dbHelper, getArguments().getInt("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
        if(c==null){
            count.setText("-1");
        }else {
            count.setText(String.valueOf(c.getCount()));
            title.setText(c.getTitle());
        }

        view.findViewById(R.id.button_subtract).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c.setCount(c.getCount()-1);
                count.setText(String.valueOf( CounterPresenter.currentCountAfterUpdate(c, dbHelper) ) );
            }
        });
        view.findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setCount(c.getCount()+1);
                count.setText(String.valueOf( CounterPresenter.currentCountAfterUpdate(c, dbHelper) ) );
            }
        });
        view.findViewById(R.id.button_stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putInt("id", c.getId());
                NavHostFragment.findNavController(ShowFragment.this)
                        .navigate(R.id.action_ShowFragment_to_StatsFragment, args);
            }
        });

    }


}