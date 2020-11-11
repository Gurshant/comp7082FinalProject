package com.example.comp7082finalproject;

public class Counter {
    private String title;
    private int count;

    public Counter(String t, int c){
        this.title=t;
        this.count =c;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
