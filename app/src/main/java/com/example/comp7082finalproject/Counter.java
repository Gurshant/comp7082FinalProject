package com.example.comp7082finalproject;

public class Counter {
    private String title;
    private int counter;

    public Counter(String t, int c){
        this.title=t;
        this.counter=c;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
}
