package com.example.android.newsflash;

public class TechNews {
    private String title;
    private String author;

    private String date;
    private String url;


    public TechNews(String title,String author,String date,String url) {

        this.title = title;
        this.author = author;
        this.date = date;
        this.url = url;
    }
    public TechNews (String title, String date, String url){
        this.title = title;
        this.url  = url;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }



    public boolean hasAuthor(){
        return author != null;
    }
}
