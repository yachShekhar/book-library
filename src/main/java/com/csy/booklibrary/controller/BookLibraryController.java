package com.csy.booklibrary.controller;

import com.csy.booklibrary.data.Book;
import com.csy.booklibrary.data.Catalogue;
import com.csy.booklibrary.service.BookLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping(path = "/v1")
public class BookLibraryController {

    @Autowired
    private BookLibraryService bookLibraryService;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome to book library", HttpStatus.OK);
    }

    @GetMapping("/catalogue/list")
    public ResponseEntity<Catalogue> getCatalogue(){
        return new ResponseEntity<>(bookLibraryService.catalogue(), HttpStatus.OK);
    }

    @GetMapping("/book/search/{searchPrefix}")
    public ResponseEntity<Set<Book>> searchBooks(@PathVariable("searchPrefix") String searchPrefix){
        return new ResponseEntity<Set<Book>>(bookLibraryService.findBooks(searchPrefix), HttpStatus.OK);
    }

    @GetMapping("/book/list")
    public ResponseEntity<Set<Book>> listAllBooks(){
        return new ResponseEntity<Set<Book>>(bookLibraryService.listBooks(), HttpStatus.OK);
    }

    @PutMapping("/book/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
       return new ResponseEntity<Book>(bookLibraryService.addBook(book), HttpStatus.ACCEPTED);
    }




}
