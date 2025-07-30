package Models;

public class PostCategory {
    private int postId;
    private int categoryId;

    public PostCategory() {}

    public PostCategory(int postId, int categoryId) {
        this.postId = postId;
        this.categoryId = categoryId;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    @Override
    public String toString() {
        return "PostCategory [postId=" + postId + ", categoryId=" + categoryId + "]";
    }
}
