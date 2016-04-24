package com.example.nabil.finalproject.Models;

/**
 * Created by Nabil on 4/14/2016.
 */
public class Review
{
    private String author;
    private String content;
    private String link;

    public String getLink()
    {
        return link;
    }

    public void setLink(String link
    ) {
        this.link = link;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
