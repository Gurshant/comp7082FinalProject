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
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ShowFragment.this)
                        .navigate(R.id.action_ShowFragment_to_ListFragment);
            }
        });
        try {
            int id = getArguments().getInt("id");
            count = view.findViewById(R.id.textView_count);
            count.setText(String.valueOf(id));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}