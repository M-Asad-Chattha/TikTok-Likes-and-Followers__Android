package com.asadchattha.tiktoklikesandfollowers.Model;

public class Post {
    private String url;
    private String profileURL;
    private String userKey;
    private String postType;
    private String numberOfViews;
    private String viewsLimit;
    private String status;

    /*Getter Methods*/
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
