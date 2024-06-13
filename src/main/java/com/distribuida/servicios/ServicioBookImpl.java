package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
@ApplicationScoped

public class ServicioBookImpl implements ServicioBook {
    @Inject
    EntityManager em;

    @Override
    public Book findById(Integer id) {
        return em.find(Book.class,id);
    }

    @Override
    public List<Book> findAll() {
        return em
                .createQuery("select b from Book b ", Book.class).getResultList();
    }

    @Override
    public void insertarBook(Book b) {
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
    }

    @Override
    public void actualizarBook(Book b) {
        em.getTransaction().begin();
        em.merge(b);
        em.getTransaction().commit();
    }

    @Override
    public void borrarBook(Integer id) {
        Book b = this.findById(id);
        em.getTransaction().begin();
        em.remove(b);
        em.getTransaction().commit();
    }
}
