package com.example.moviecatalog;

public class Movie {
    private int id;
    private String title;
    private String genre;
    private float rating;
    private int year;
    private String description;

    public Movie(int id, String title, String genre, float rating, int year, String description) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.description = description;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}