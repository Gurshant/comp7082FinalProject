package com.example.comp7082finalproject;

public class Counter {
    private int id;
    private String title;
    private int count;

    public Counter(int id, String t, int c){
        this.id = id;
        this.title=t;
        this.count =c;
    }
    public Counter(){
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
