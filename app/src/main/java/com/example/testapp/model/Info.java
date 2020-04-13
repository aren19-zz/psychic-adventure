package com.example.testapp.model;

public class Info {

    int pages;
    String next;

    public Info(int pages, String next, String prev) {
        this.pages = pages;
        this.next = next;
        this.prev = prev;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    String prev;
}
