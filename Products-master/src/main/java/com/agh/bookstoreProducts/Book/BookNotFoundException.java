package com.agh.bookstoreProducts.Book;

public class BookNotFoundException extends RuntimeException {
    BookNotFoundException(Long id){
        super("Book "+id+" not found. Error 404.");
    }
}
