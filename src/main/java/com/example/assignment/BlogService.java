package com.example.assignment;

//Importerad packet

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {

    //Användning av loggning
    private Logger logger;

    //Här skapar vi en Arraylist
    ArrayList<Blog> blogArray;

    //En räknare som alltid räknas uppåt när en ny blog läggs till + datum
    int latestBlogID;

    public BlogService() {
        blogArray = new ArrayList<>();
        latestBlogID = 0;
        logger = LoggerFactory.getLogger(BlogService.class);
    }

    //Listar alla blogginlägg
    public ArrayList<Blog> getBlogs() {

        logger.info("List of all blog entries are now sent to the Client");
        return blogArray;
    }

    //Skapar ett blogginlägg
    public Blog createMyBlog(Blog newBlog) {

        // Ökar ID:t med 1 innan vi lägger in bloggen
        // I verkligheten senare så görs detta automatiskt av en databas
        latestBlogID++;
        newBlog.setId(latestBlogID);
        blogArray.add(newBlog);

        return newBlog;

    }


    // Tillhör updateBlogByID metoden i BlogController
    public Blog updateBlog(int id) {

        // Blog fetchedBlog = getBlogByID(id); --- fetchedBlog är redundant
        /* specificBlog(id); ---Här skulle vi också kunna bara anropa metoden specifikBlog(id) inne här
        och returnera specificBlog(id) */
        return getBlogByID(id);
    }

    // Tillhör specificBlog metoden i BlogController
    public Blog specificBlog(int id) {

        Blog fetchedBlog = getBlogByID(id);

        return fetchedBlog;
    }


    public Blog getBlogByID(int id) {

        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.get(i);
            }
        }
        return null;
    }

    public Blog deleteBlog(int id) {
        Blog fetchedBlog = getBlogByID(id);  // Skapar nytt objekt för att hitta ID numret m.h.a. metoden getBlogByID

        return fetchedBlog;
    }

    // Tillhör deleteBlogByID metoden
    // Metod för att ta bort självaste ID numret
    public Blog deleteBlogById(int id) {

        for (int i = 0; i < blogArray.size(); i++) {
            Blog currentBlog = blogArray.get(i);
            if (currentBlog.getId() == id) {
                return blogArray.remove(i);

            }
        }
        return new Blog();
    }

    // Ta bort allt i listan
    public void clearList() {
        blogArray.clear();
        logger.info("All blog entries in the list are now deleted");
    }
}
