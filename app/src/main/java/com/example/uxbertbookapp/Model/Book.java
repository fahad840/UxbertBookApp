package com.example.uxbertbookapp.Model;

//Model of the book which contain book name aurthor and pages.

public class Book {
    int id;
    String name;
    String aurthor;
    int pages;
    String status;
    int notifiable;

    //Empty Constructor
    public Book() {
    }

    //Constructor with data.
    public Book(int id, String name, String aurthor, int pages, String status, int notifiable) {
        this.id = id;
        this.name = name;
        this.aurthor = aurthor;
        this.pages = pages;
        this.status = status;
        this.notifiable = notifiable;
    }
    //Constructor with data.

    public Book(String name, String aurthor, int pages, String status, int notifiable) {
        this.name = name;
        this.aurthor = aurthor;
        this.pages = pages;
        this.status = status;
        this.notifiable = notifiable;
    }

    //getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAurthor() {
        return aurthor;
    }

    public void setAurthor(String aurthor) {
        this.aurthor = aurthor;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNotifiable() {
        return notifiable;
    }

    public void setNotifiable(int notifiable) {
        this.notifiable = notifiable;
    }
}
