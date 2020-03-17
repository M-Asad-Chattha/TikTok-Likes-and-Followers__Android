package com.asadchattha.tiktoklikesandfollowers.Model;

public class Post {
    String key;
    private String url;
    private String profileURL;
    private String userKey;
    private String postType;
    private String numberOfViews;
    private String viewsLimit;
    private String status;

    /*Getter Methods*/
    public Post() {

    }

    public Post(String key, String url,
                String profileURL, String userKey,
                String postType, String numberOfViews,
                String viewsLimit, String status) {
        this.key = key;
        this.url = url;
        this.profileURL = profileURL;
        this.userKey = userKey;
        this.postType = postType;
        this.numberOfViews = numberOfViews;
        this.viewsLimit = viewsLimit;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(String numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public String getViewsLimit() {
        return viewsLimit;
    }

    public void setViewsLimit(String viewsLimit) {
        this.viewsLimit = viewsLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
