package com.softarena.tiktoklikesandfollowers.Model;

public class PostViewer {
    private String userId;

    public PostViewer() {
    }

    public PostViewer(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
