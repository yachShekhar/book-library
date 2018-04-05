package com.csy.booklibrary.service;

import com.csy.booklibrary.data.Book;
import com.csy.booklibrary.data.Catalogue;

import java.util.Set;

public interface BookLibraryService {
    public Catalogue catalogue();

    public Book addBook(Book book);

    public Set<Book> findBooks(String string);

    public Set<Book> listBooks();




}
