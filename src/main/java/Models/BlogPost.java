package Models;

import java.sql.Timestamp;
import java.util.Objects;

public class BlogPost {
    private int postId;
    private int userId;
    private String title;
    private String content;
    private String imageUrl;
    private Timestamp publishedDate;
    private int totalLikes;
    private int totalComments;

    public BlogPost() {
        // Default constructor
    }

    public BlogPost(int postId, int userId, String title, String content, 
                   String imageUrl, Timestamp publishedDate) {
        this.postId = postId;
        this.userId = userId;
        this.setTitle(title);
        this.setContent(content);
        this.setImageUrl(imageUrl);
        this.publishedDate = publishedDate;
    }

    // Getters and Setters with basic validation
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { 
        this.title = title != null ? title.trim() : "";
    }

    public String getContent() { return content; }
    public void setContent(String content) { 
        this.content = content != null ? content.trim() : "";
    }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { 
        this.imageUrl = imageUrl != null ? imageUrl.trim() : "";
    }

    public Timestamp getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Timestamp publishedDate) { 
        this.publishedDate = publishedDate;
    }

    public int getTotalLikes() { return totalLikes; }
    public void setTotalLikes(int totalLikes) { 
        this.totalLikes = Math.max(totalLikes, 0);
    }

    public int getTotalComments() { return totalComments; }
    public void setTotalComments(int totalComments) { 
        this.totalComments = Math.max(totalComments, 0);
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + (content != null ? content.substring(0, Math.min(content.length(), 50)) + "..." : "null") +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishedDate=" + publishedDate +
                ", totalLikes=" + totalLikes +
                ", totalComments=" + totalComments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogPost blogPost = (BlogPost) o;
        return postId == blogPost.postId &&
                userId == blogPost.userId &&
                Objects.equals(title, blogPost.title) &&
                Objects.equals(content, blogPost.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, userId, title, content);
    }
}