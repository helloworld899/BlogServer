package com.example.assignment;

//Importerat paket

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/v1/blogs")
public class BlogController {

    private Logger logger;


    private BlogService blogService; //Skapandet av service och controller

    //Skapar en konstruktor (Dependency Injection)
    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;

        logger = LoggerFactory.getLogger(BlogController.class);
    }


    // CRUD - Create
    // Går även att skapa en ny blog genom att skicka det här i JSON-format i bodyn av vår HTTP-request i Insomnia.
    // Adressen det skickas till då är http://127.0.0.1:8080/api/v1/blogs/create
    // Metod nedan för att skapa en blog
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Blog> createMyBlog(@RequestBody Blog blog) {

        Blog newBlog = blogService.createMyBlog(blog);


        if (blog.getTitle() == "") {  // Ifall man försöker skapa en tom titel i klienten
            logger.error("Titel is empty for blog ID: " + newBlog.getId() + ", try write something");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //så ska man få en BAD REQUEST med 404 som HTTP-status
        }

        if (blog.getText() == "") {    // Ifall man försöker skapa en tom text i klienten
            logger.error("No Content for blog ID: " + newBlog.getId() + ", try write something");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (blog.getDate() == "") {   // Ifall man försöker skapa en tom datum i klienten
            logger.error("Pick a date for blog ID: " + newBlog.getId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } else {
            logger.info("Added a blog entry with Title: " + newBlog.getTitle() + ". Content: " + newBlog.getText() + ". Date: " + newBlog.getDate());
            return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
        }
    }

    // CRUD - Read
    // Metod för att lista alla blogginlägg
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Blog>> listAllBlogs() {

        ArrayList<Blog> newBlog = blogService.getBlogs();

        return new ResponseEntity<>(newBlog, HttpStatus.OK);
    }

    // CRUD - Read
    // Metod för att lista en specifik blogginlägg
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    private ResponseEntity<Blog> specificBlog(@PathVariable("id") int id) {

        Blog fetchedBlog = blogService.specificBlog(id);

        if (fetchedBlog == null) {              // a) låt oss säga att vi inte hittar det blogginlägg vi vill ha...
            logger.warn("The blog you want does not exist or has been deleted");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // b) då kan vi skicka annan response som säger "NOT FOUND"...
        }

        logger.info("Getting blog entry with ID: " + id);
        return new ResponseEntity<>(fetchedBlog, HttpStatus.OK); // c) men om inlägget hittas, då skickar vi ett annat response-meddelande med "OK".
    }
    /*istället för att returnera en blogginlägg direkt, så kan man istället säga att man returnerar en responseEntity
    av typen Blog.
   */


    // CRUD - Delete
    // Metod för att radera allt i listan
    @RequestMapping(value = "clear", method = RequestMethod.DELETE)
    public void clearAllBlogs() {
        blogService.clearList();
    }

    //CRUD - Delete
    //Metod för att radera en specifik blogginlägg   | ...här tittade jag på metoden "lista alla blogginlägg"
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    private ResponseEntity<Blog> deleteBlogByID(@PathVariable("id") int id) {

        Blog fetchedBlog = blogService.deleteBlog(id);

        if (fetchedBlog == null) {
            logger.error("The blog you want does not exist or has been deleted");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Blog entry with ID: " + id + " has been deleted");
        return new ResponseEntity(blogService.deleteBlogById(id), HttpStatus.OK);
    }
    // Först hämtar den fram inlägget man vill ta bort, sem måste man klicka igen på send via Insomnia för att
    // få bort just den inlägget.


    // CRUD - Update
    // Metod för att ändra/uppdatera något i listan
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public ResponseEntity<Blog> updateBlogByID(@PathVariable("id") int id, @RequestBody Blog blogChanges) {

        Blog fetchedBlog = blogService.updateBlog(id);

        if (fetchedBlog == null) {
            logger.error("The blog you want does not exist or has been deleted");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (blogChanges.getTitle() != null) {
            fetchedBlog.setTitle(blogChanges.getTitle());
        }

        if (blogChanges.getText() != null) {
            fetchedBlog.setText(blogChanges.getText());
        }

        if (blogChanges.getDate() != null) {
            fetchedBlog.setDate(blogChanges.getDate());
        }

        logger.info("Blog entry with ID: " + id + " has been updated");
        return new ResponseEntity<>(fetchedBlog, HttpStatus.CREATED);

    }
}