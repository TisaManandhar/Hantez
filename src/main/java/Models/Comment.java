package Models;

public class Comment {
    private int commentId;
    private int userId;
    private int postId;
    private String commentText;
    private java.sql.Timestamp commentDate;

    public Comment() {}

    public Comment(int commentId, int userId, int postId, String commentText, java.sql.Timestamp commentDate) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public int getCommentId() { return commentId; }
    public void setCommentId(int commentId) { this.commentId = commentId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public String getCommentText() { return commentText; }
    public void setCommentText(String commentText) { this.commentText = commentText; }
    public java.sql.Timestamp getCommentDate() { return commentDate; }
    public void setCommentDate(java.sql.Timestamp commentDate) { this.commentDate = commentDate; }

    @Override
    public String toString() {
        return "Comment [commentId=" + commentId + ", userId=" + userId + ", postId=" + postId + "]";
    }
}
