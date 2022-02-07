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

    //Listing all blogs
    public ArrayList<Blog> getBlogs() {

        logger.info("List of all blog entries are now sent to the Client");
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
        Blog fetchedBlog = getBlogByID(id);  //skapar nytt objekt för att hitta ID nummer

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
        logger.info("All blog entries in the list are now deleted");
    }
}
