package com.csy.booklibrary.data;

import lombok.Data;

import java.util.List;

@Data
public class Book {
    private String id;
    private String name;
    private long soldCount;
    private String publisher;
    private String authorCountry;
    private String imageUrl;
    private String title;
    private List<String> authors;
    private List<Genre> genres;
}
