package com.example.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {

    private Logger logger;

    //Här skapar vi en Arraylist
    ArrayList<Blog> blogArray;

    //En räknare som alltid räknas uppåt när en ny blog läggs till + datum
    int latestBlogID;

    public BlogService() {
        blogArray = new ArrayList<>();
        latestBlogID = 0;
        logger = LoggerFactory.getLogger(BlogController.class);
    }

    //Listing all blogs
    public ArrayList<Blog> getBlogs() {

        logger.info("Listan av alla blogginlägg skickas nu till klienten");
        return blogArray;
    }

    //Create a blog
    public Blog createMyBlog(Blog newBlog) {

        latestBlogID++;
        newBlog.setId(latestBlogID);
        blogArray.add(newBlog);

        logger.info("Lade till en blogginlägg med titel: " + newBlog.getTitle() + ". Content: " + newBlog.getText() + ". Datum: " + newBlog.getDate());

        return newBlog;

    }


    //Update a blog
    public Blog updateBlogByID(int id) {

        Blog fetchedBlog = getBlogByID(id);
        specificBlog(id);

        return fetchedBlog;
    }


    public Blog specificBlog(int id) {

        Blog fetchedBlog = getBlogByID(id);

        return fetchedBlog;
    }

    //tillhör view a specific blog by ID
    public Blog getBlogByID(int id) {

        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.get(i);
            }
        }
        return null;
    }

    public Blog deleteBlogByID(int id) {
        Blog fetchedBlog = getBlogByID(id);

        return fetchedBlog;
    }

    //tillhör Ta bort blogg med ID
    public Blog deleteBlogById(int id) {

        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.remove(i);

            }
        }
        return new Blog();
    }

    //Ta bort allt i listan
    public void clearList() {
        blogArray.clear();
        logger.info("Alla blogginlägg i listan är nu borttagna");
    }


}
