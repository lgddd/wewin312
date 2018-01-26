package com.wewin.entity;

public class FileURL {
    int id;
    String value;

    public FileURL(){

    }
    public  FileURL(int id,String value){
        this.id = id;
        this.value =value;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
