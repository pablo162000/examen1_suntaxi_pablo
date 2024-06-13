package com.distribuida.servicios;

import com.distribuida.db.Book;

import java.util.List;

public interface ServicioBook {
    Book findById(Integer id);
    List<Book> findAll();
    void insertarBook(Book b);
    void actualizarBook(Book b);
    void borrarBook(Integer id);
}