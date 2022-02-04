package com.example.assignment;

//Importerat paket
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/v1/blogs")
public class BlogController {
    private BlogService blogService; //Skapandet av service och controller

    //Skapar en konstruktor (Dependency Injection)

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
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
            System.out.println("Lade till en blogginlägg med titel: " + newBlog.getTitle() + ". Content: " + newBlog.getText() + ". Datum: " + newBlog.getDate());
            return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
        }
    }

    //Metod för att lista alla blogginlägg
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Blog>> listAllBlogs() {

       ArrayList<Blog> newBlog = blogService.getBlogs();

       return new ResponseEntity<>(newBlog, HttpStatus.OK);

    }

    //CRUD - Read
    //Metod för att lista en specifik blogginlägg
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    private ResponseEntity<Blog> specificBlog(@PathVariable("id") int id) {

        Blog fetchedBlog = blogService.specificBlog(id);

        if (fetchedBlog == null) { // b) let's say we dont find the blog we want
            System.out.println("Blogginlägget finns inte");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // b) then we can send another response saying its not found
        }

        return new ResponseEntity<>(fetchedBlog, HttpStatus.OK); ///c ) if found, then we send a message saying OK.
    }
    /*istället för att returnera en blogginlägg direkt, så kan man istället säga att man returnerar en responseEntity
    av typen Blog.
   */


    //TODO: Ta bort detta
    //Denna metod hör ihop med ovan metoden för att hämta ID.
   /* private Blog getBlogByID(int id) {
        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.get(i);
            }
        }
        return null;
    }

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
            System.out.println("Blogginlägget finns inte eller har raderats");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("Hämtar blogg med id nummer " + id);
        return new ResponseEntity(blogService.deleteBlogById(id), HttpStatus.OK);
    }
    //Först hämtar den fram inlägget man vill ta bort, sem måste man klicka igen på send via Insomnia för att
    // få bort just den inlägget.


    // TODO: Ta bort detta
  /*  private Blog deleteBlogById(int id) {
        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.remove(i);
            }
        }
        System.out.println("Blogg med id nummer: " + id + " raderat");
        return new Blog();
       }
   */



    //Metod för att ändra/uppdatera något i listan

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public ResponseEntity<Blog> listBlogs(@PathVariable("id") int id, @RequestBody Blog blogChanges) {

        Blog fetchedBlog = blogService.updateBlogByID(id);

        if(fetchedBlog == null) {
            System.out.println("Blogginlägget finns inte eller har raderats");
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


        System.out.println("Blog " + id + " has been updated");
        return new ResponseEntity<>(fetchedBlog, HttpStatus.OK);

    }
}