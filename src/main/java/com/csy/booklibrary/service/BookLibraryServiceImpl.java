package com.csy.booklibrary.service;

import com.csy.booklibrary.core.BookLibraryIndex;
import com.csy.booklibrary.core.Trie;
import com.csy.booklibrary.data.Attribute;
import com.csy.booklibrary.data.Book;
import com.csy.booklibrary.data.Catalogue;
import com.csy.booklibrary.data.Genre;
import com.csy.booklibrary.util.Constant;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookLibraryServiceImpl implements BookLibraryService{
    @Getter
    private Catalogue catalogue = new Catalogue();
    @Getter
    private Set<Book> books = new HashSet<>();

    @Autowired BookLibraryIndex bookLibraryIndex;
    @Autowired Trie trie;

    @Override public Catalogue catalogue(){
        return getCatalogue();
    }

    @Override public Book addBook(Book book){
        books.add(book);
        book.getAuthors().stream().forEach(a->{
            catalogue.getAuthors().add(a);
            bookLibraryIndex.put(newAttribute(Constant.AUTHOR_ATTRIBUTE_KEY, a), book);
            trie.insert(a);
        });

        catalogue.getAuthorNationality().add(book.getAuthorCountry());
        for(Genre genre : book.getGenres()){
            bookLibraryIndex.put(newAttribute(Constant.GENRE_ATTRIBUTE_KEY, genre.name().toLowerCase()), book);
            trie.insert(genre.name().toLowerCase());
        }
        bookLibraryIndex.put(newAttribute(Constant.TITLE_ATTRIBUTE_KEY, book.getTitle()), book);
        trie.insert(book.getTitle());
        return book;
    }

    @Override public Set<Book> findBooks(String searchPrefix) {
        searchPrefix = searchPrefix != null ? searchPrefix.toLowerCase() : "";
        List<String> keys = trie.findCompletions(searchPrefix);
        Set<Book> books = new HashSet<>();
        for(String key : keys){
            Set<Book> authorBooks = bookLibraryIndex.get(newAttribute(Constant.AUTHOR_ATTRIBUTE_KEY, key));
            if(authorBooks != null) {
                books.addAll(authorBooks);
            }
            Set<Book> genreBooks = bookLibraryIndex.get(newAttribute(Constant.GENRE_ATTRIBUTE_KEY, key));
            if(genreBooks != null) {
                books.addAll(genreBooks);
            }

            Set<Book> titleBooks = bookLibraryIndex.get(newAttribute(Constant.TITLE_ATTRIBUTE_KEY, key));
            if(titleBooks != null) {
                books.addAll(titleBooks);
            }
        }
        return books;
    }

    @Override public Set<Book> listBooks() {
        return books;
    }

    private Attribute newAttribute(String key, Object value){
        return new Attribute(key, value);
    }
}
