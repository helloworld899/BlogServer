package com.example.assignment;

//Importerat paket

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
    public Blog getMyBlog(@RequestBody Blog blog) {
        latestBlogID++;
        blog.setId(latestBlogID);
        blogArray.add(blog);
        System.out.println("Lade till en blogginlägg med titel: " + blog.getTitle() + blog.getText() + " Datum: " + blog.getDate());
        return blog;
    }

    //Metod för att lista alla blogginlägg
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ArrayList<Blog> listAllBlogs() {
        System.out.println("Listan av alla blogginlägg skickas nu till klienten");
        return blogArray;
    }

    //Metod för att lista en specifik blogginlägg
    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    private Blog specificBlog(@PathVariable("id") int id) {
        System.out.println("Hämtar blogg med ID nummer " + id);
        return getBlogByID(id);
    }

    //Denna metod hör ihop med ovan metoden för att hämta ID.
    private Blog getBlogByID(int id) {
        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.get(i);
            }
        }
        return new Blog();
    }


    //Metod för att radera allt i listan
    @RequestMapping(value = "clear", method = RequestMethod.DELETE)
    public void clearAllBlogs() {
        blogArray.clear();
        System.out.println("Alla blogginlägg i listan är nu borttagna");
    }

    //Metod för att radera en specifik blogginlägg   | ...här tittade jag på metoden "lista alla blogginlägg"
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    private Blog deleteBlogByID(@PathVariable("id") int id) {
        System.out.println("Hämtar blogg med id nummer " + id);
        return deleteBlogById(id);
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
    public Blog listBlogs(@PathVariable("id") int id, @RequestBody Blog blogChanges) {
        System.out.println("Getting movie with id " + id);
        Blog blogToUpdate = getBlogByID(id);

        if (blogChanges.getText() != null) {
            blogToUpdate.setText(blogChanges.getText());
        }

        if (blogChanges.getTitle() != null) {
            blogToUpdate.setTitle(blogChanges.getTitle());
        }

        if (blogChanges.getDate() != null) {
            blogToUpdate.setDate(blogChanges.getDate());
        }

        specificBlog(id);

        return blogToUpdate;
    }
}