package com.example.comp7082finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Counter> counters;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            count = itemView.findViewById(R.id.count);
        }
    }
    public Adapter(ArrayList<Counter> c){
        counters = c;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.counter_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Counter counter = counters.get(position);
        holder.title.setText(counter.getTitle());
        holder.count.setText(String.valueOf(counter.getCount()));
    }

    @Override
    public int getItemCount() {
        return counters.size();
    }
}
