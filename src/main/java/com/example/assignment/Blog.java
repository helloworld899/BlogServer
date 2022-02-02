package com.example.assignment;


//Här skapar vi attributer
public class Blog {
    private int id;
    private String title;
    private String date;
    private String text;

    //Konstruktorer

    public Blog() {
    }

    public Blog(int id,
                String date,
                String title,
                String text) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.text = text;
    }

    public Blog(String date,
                String title,
                String text) {
        this.date = date;
        this.title = title;
        this.text = text;
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}