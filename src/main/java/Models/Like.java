package Models;

public class Like {
    private int likeId;
    private int userId;
    private int postId;
    private java.sql.Timestamp likeTimestamp;

    public Like() {}

    public Like(int likeId, int userId, int postId, java.sql.Timestamp likeTimestamp) {
        this.likeId = likeId;
        this.userId = userId;
        this.postId = postId;
        this.likeTimestamp = likeTimestamp;
    }

    public int getLikeId() { return likeId; }
    public void setLikeId(int likeId) { this.likeId = likeId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public java.sql.Timestamp getLikeTimestamp() { return likeTimestamp; }
    public void setLikeTimestamp(java.sql.Timestamp likeTimestamp) { this.likeTimestamp = likeTimestamp; }

    @Override
    public String toString() {
        return "Like [likeId=" + likeId + ", userId=" + userId + ", postId=" + postId + "]";
    }
}
