package com.example.bass.instagramclient;

import java.util.ArrayList;

/**
 * Created by bass on 2015/7/29.
 */
public class InstagramPhoto {
    public String username;
    public String profilePicture;
    public long createdTime;

    public String caption;
    public String imageUrl;
    public int imageHeight;
    public int likesCount;

    public int commentsCount;
    public ArrayList<InstagramComment> comments;

    public InstagramPhoto() {
        comments = new ArrayList<>();
    }
}
