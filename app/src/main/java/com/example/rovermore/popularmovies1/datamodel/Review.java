package com.example.rovermore.popularmovies1.datamodel;

public class Review {

    private String author;
    private String content;

    public Review(String author, String content){
        this.author=author;
        this.content=content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
