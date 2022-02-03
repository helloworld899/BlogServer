package com.example.assignment;

//Importerat paket

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/v1/blogs")
public class BlogController {

    //Här skapar vi en Arraylist
    ArrayList<Blog> blogArray;

    //En räknare som alltid räknas uppåt när en ny blog läggs till + datum
    int latestBlogID;

    public BlogController() {
        blogArray = new ArrayList<>();
        latestBlogID = 0;
    }


    //Metod för att skapa en blog
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ResponseEntity<Blog> CreateMyBlog(@RequestBody Blog blog) {

        if (blog.getTitle() == "") {  // Ifall man försöker skapa en tom titel i klienten
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (blog.getText() == "") {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ifall man försöker skapa en tom text i klienten osv...
        }
        if (blog.getDate() == "") {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } else {

            latestBlogID++;
            blog.setId(latestBlogID);
            blogArray.add(blog);
            System.out.println("Lade till en blogginlägg med titel: " + blog.getTitle() + ". Content: " + blog.getText() + ". Datum: " + blog.getDate());
            return new ResponseEntity<Blog>(blog, HttpStatus.CREATED);
        }
    }

    //Metod för att lista alla blogginlägg
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<Blog>> listAllBlogs() {

        System.out.println("Listan av alla blogginlägg skickas nu till klienten");
        return new ResponseEntity<>(blogArray, HttpStatus.OK);
    }

    //CRUD - Read
    //Metod för att lista en specifik blogginlägg
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    private ResponseEntity<Blog> specificBlog(@PathVariable("id") int id) {
        System.out.println("Hämtar blogg med ID nummer " + id);

        Blog fetchedBlog = getBlogByID(id); // a) here we try to find the blog

        if (fetchedBlog == null) { // b) let's say we dont find the blog we want
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // b) then we can send another response saying its not found
        }

        return new ResponseEntity<Blog>(fetchedBlog, HttpStatus.OK); ///c ) if found, then we send a message saying OK.
    }
    /*istället för att returnera en blogginlägg direkt, så kan man istället säga att man returnerar en responseEntity
    av typen Blog.
   */

    //Denna metod hör ihop med ovan metoden för att hämta ID.
    private Blog getBlogByID(int id) {
        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.get(i);
            }
        }
        return null;
    }


    //Metod för att radera allt i listan
    @RequestMapping(value = "clear", method = RequestMethod.DELETE)
    public void clearAllBlogs() {
        blogArray.clear();
        System.out.println("Alla blogginlägg i listan är nu borttagna");
    }

    //Metod för att radera en specifik blogginlägg   | ...här tittade jag på metoden "lista alla blogginlägg"
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    private ResponseEntity <Blog> deleteBlogByID(@PathVariable("id") int id) {

        Blog fetchedBlog = getBlogByID(id); //skapar nytt objekt för att hitta ID nummer
        if(fetchedBlog == null) {
            System.out.println("Blogginlägget finns inte eller har raderats");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        System.out.println("Hämtar blogg med id nummer " + id);
        return new ResponseEntity<>(deleteBlogById(id), HttpStatus.OK);
    }  //Först hämtar den fram inlägget man vill ta bort, sem måste man klicka igen på send via Insomnia för att
    // få bort just den inlägget.

    private Blog deleteBlogById(int id) {
        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.remove(i);
            }
        }
        System.out.println("Blogg med id nummer: " + id + " raderat");
        return new Blog();
    }


    //Metod för att ändra/uppdatera något i listan

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public ResponseEntity<Blog> listBlogs(@PathVariable("id") int id, @RequestBody Blog blogChanges) {
        System.out.println("Getting movie with id " + id);


        Blog fetchedBlog = getBlogByID(id);

        if(fetchedBlog == null) {
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

        specificBlog(id);

        return new ResponseEntity<Blog>(fetchedBlog, HttpStatus.OK);
    }
}