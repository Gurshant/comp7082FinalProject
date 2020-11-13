package com.example.comp7082finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class ShowFragment extends Fragment {
    Button btnSub, btnAdd;
    TextView title, count;
    DatabaseHelper dbHelper;
    Counter c;
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

        dbHelper = new DatabaseHelper( getActivity());

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ShowFragment.this)
                        .navigate(R.id.action_ShowFragment_to_ListFragment);
            }
        });

        title = view.findViewById(R.id.textView_title);
        count = view.findViewById(R.id.textView_count);

        try {
            int id = getArguments().getInt("id");
            c =dbHelper.selectCounter(id);
            if(c==null){
                title.setText("ERROR");
                count.setText("-1");

            }else {
                count.setText(String.valueOf(c.getCount()));
                title.setText(c.getTitle());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        btnSub = view.findViewById(R.id.button_subtract);
        btnAdd = view.findViewById(R.id.button_add);

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setCount(c.getCount()-1);
                updateView();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.setCount(c.getCount()+1);
                updateView();
            }
        });

    }
    public void updateView(){
        dbHelper.updateCounter(c.getId(),c.getCount());
        count.setText(""+c.getCount());
    }
}