package com.example.assignment;


import java.time.LocalDate;

    //Här skapar vi attributer
    public class Blog {
        private int id;
        private LocalDate date;
        private String text;

    //Konstruktorer

    public Blog() {}

    public Blog(int id,
                LocalDate date,
                String text) {
        this.id = id;
        this.date = date;
        this.text = text;
    }

    public Blog(LocalDate date,
                String text) {
        this.date = date;
        this.text = text;
    }

        //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
                ", date=" + date +
                ", text='" + text + '\'' +
                '}';
    }
}