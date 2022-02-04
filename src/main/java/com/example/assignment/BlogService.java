package com.example.assignment;


import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {

    //Här skapar vi en Arraylist
    ArrayList<Blog> blogArray;

    //En räknare som alltid räknas uppåt när en ny blog läggs till + datum
    int latestBlogID;

    public BlogService() {
        blogArray = new ArrayList<>();
        latestBlogID = 0;
    }

    //Listing all blogs
    public ArrayList<Blog> getBlogs() {

        System.out.println("Listan av alla blogginlägg skickas nu till klienten");

        return blogArray;
    }

    //Create a blog
    public Blog createMyBlog(Blog newBlog) {

        latestBlogID++;
        newBlog.setId(latestBlogID);
        blogArray.add(newBlog);
        return newBlog;
    }


    //Update a blog
    public Blog updateBlogByID(int id) {

        Blog fetchedBlog = getBlogByID(id);
        specificBlog(id);

        return fetchedBlog;
    }


    public Blog specificBlog(int id) {
        System.out.println("Hämtar blogg med ID nummer " + id);
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
        System.out.println("Blogg med id nummer: " + id + " raderat");
        return new Blog();
    }

    //Ta bort allt i listan
    public void clearList() {
        blogArray.clear();
        System.out.println("Alla blogginlägg i listan är nu borttagna");
    }


}
