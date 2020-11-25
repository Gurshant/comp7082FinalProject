package com.example.comp7082finalproject.model;


public class CounterChange {

    private int id;
    private int counterId;
    private int newValue;
    private String time;

    public CounterChange(int id, int counterId, int newValue, String time){
        this.id = id;
        this.counterId = counterId;
        this.newValue =newValue;
        this.time = time;
    }
    public CounterChange(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCounterId() {
        return counterId;
    }

    public void setCounterId(int counterId) {
        this.counterId = counterId;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
