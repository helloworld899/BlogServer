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


    //Metod för att skapa en blog
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Blog> createMyBlog(@RequestBody Blog blog) {

        Blog newBlog = blogService.createMyBlog(blog);


        if (blog.getTitle() == "") {  // Ifall man försöker skapa en tom titel i klienten
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (blog.getText() == "") {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ifall man försöker skapa en tom text i klienten osv...
        }
        if (blog.getDate() == "") {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } else {
            //logger.info("Lade till en blogginlägg med titel: " + newBlog.getTitle() + ". Content: " + newBlog.getText() + ". Datum: " + newBlog.getDate());
           return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
        }
    }

    //Metod för att lista alla blogginlägg
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Blog>> listAllBlogs() {

       ArrayList<Blog> newBlog = blogService.getBlogs();

      // logger.info("Listan av alla blogginlägg skickas nu till klienten");
       return new ResponseEntity<>(newBlog, HttpStatus.OK);

    }

    //CRUD - Read
    //Metod för att lista en specifik blogginlägg
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    private ResponseEntity<Blog> specificBlog(@PathVariable("id") int id) {

        Blog fetchedBlog = blogService.specificBlog(id);

        if (fetchedBlog == null) { // b) let's say we dont find the blog we want
            logger.warn("The Blog you want does not exist or has been deleted");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // b) then we can send another response saying its not found
        }
        logger.info("Hämtar blogg med ID nummer " + id);
        return new ResponseEntity<>(fetchedBlog, HttpStatus.OK); ///c ) if found, then we send a message saying OK.
    }
    /*istället för att returnera en blogginlägg direkt, så kan man istället säga att man returnerar en responseEntity
    av typen Blog.
   */



    //Metod för att radera allt i listan
    @RequestMapping(value = "clear", method = RequestMethod.DELETE)
    public void clearAllBlogs() {
       blogService.clearList();
    }

    //Metod för att radera en specifik blogginlägg   | ...här tittade jag på metoden "lista alla blogginlägg"
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    private ResponseEntity <Blog> deleteBlogByID(@PathVariable("id") int id) {

        Blog fetchedBlog = blogService.deleteBlogByID(id);

       // Blog fetchedBlog = getBlogByID(id); //skapar nytt objekt för att hitta ID nummer
        if(fetchedBlog == null) {
            logger.error("Blogginlägget finns inte eller har raderats");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Blogg med id nummer: " + id + " raderat");
        return new ResponseEntity(blogService.deleteBlogById(id), HttpStatus.OK);
    }
    //Först hämtar den fram inlägget man vill ta bort, sem måste man klicka igen på send via Insomnia för att
    // få bort just den inlägget.


    //Metod för att ändra/uppdatera något i listan
    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public ResponseEntity<Blog> listBlogs(@PathVariable("id") int id, @RequestBody Blog blogChanges) {

        Blog fetchedBlog = blogService.updateBlogByID(id);

        if(fetchedBlog == null) {
            logger.error("Blogginlägget finns inte eller har raderats");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (blogChanges.getText() != null) {
            fetchedBlog.setText(blogChanges.getText());
        }

        if (blogChanges.getTitle() != null) {
            fetchedBlog.setTitle(blogChanges.getTitle());
        }

        if (blogChanges.getDate() != null) {
            fetchedBlog.setDate(blogChanges.getDate());
        }

        logger.info("Blog " + id + " has been updated");
        return new ResponseEntity<>(fetchedBlog, HttpStatus.OK);

    }
}