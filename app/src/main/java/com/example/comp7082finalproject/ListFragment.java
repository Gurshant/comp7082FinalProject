package com.example.comp7082finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Counter> counters;
    DatabaseHelper dbHelper;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DatabaseHelper( getActivity());

        getCounters();

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListFragment.this).navigate(R.id.action_ListFragment_to_ShowFragment);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListFragment.this).navigate(R.id.action_ListFragment_to_NewFragment);
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new Adapter(counters);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putInt("id", counters.get(position).getId());
                NavHostFragment.findNavController(ListFragment.this).navigate(R.id.action_ListFragment_to_ShowFragment, args);
//                counters.get(position).setCount(20);
//                mAdapter.notifyItemChanged(position);
            }
        });

    }
    public void getCounters(){
        counters = new ArrayList<>();
        counters = dbHelper.indexCounters();
    }

}