package com.distribuida;

import com.distribuida.db.Book;
import com.distribuida.servicios.ServicioBook;
import io.helidon.webserver.WebServer;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;

import java.util.List;
import com.google.gson.Gson;


public class Main {
    private static ContainerLifecycle lifecycle = null;

    public static void main(String[] args)  {
        lifecycle = WebBeansContext.currentInstance().getService(ContainerLifecycle.class);
        lifecycle.startApplication(null);
        ServicioBook servicioBook = CDI.current().select(ServicioBook.class).get();

        WebServer server = WebServer.builder()
                .port(8080)
                .routing(builder -> builder
                        .post("/books", (req, res) -> {
                            Gson gson=new Gson();
                            String body = req.content().as(String.class);
                            Book book1 = gson.fromJson(body, Book.class);
                            servicioBook.insertarBook(book1);
                            res.send("Se inserto correctamente");
                        })
                        .put("/books/{id}",(req, res) -> {
                            Gson gson=new Gson();
                            String body = req.content().as(String.class);
                            Book book1 = gson.fromJson(body, Book.class);
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            book1.setId(id);
                            servicioBook.actualizarBook(book1);
                            res.send("Se inserto correctamente el registro" + id);
                        })
                        .delete("/books/{id}",(req, res) -> {
                            Integer id = Integer.valueOf(req.path().pathParameters().get("id"));
                            servicioBook.borrarBook(id);
                            res.send("Se elimino el registro:" + id);
                        })
                        .get("/books", (req, res) -> {
                            List<Book> books = servicioBook.findAll();
                            String response = new Gson().toJson(books);
                            res.send(response);
                        })
                        .get("/books/{id}",(req, res) -> {
                            Book b1=servicioBook.findById(Integer.valueOf(req.path().pathParameters().get("id")));
                            res.send(new Gson().toJson(b1));
                        })
                )
                .build();

        server.start();
        servicioBook.findAll().stream().forEach(System.out::println);
        lifecycle.stopApplication(null);
    }
}