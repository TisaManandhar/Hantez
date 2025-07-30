package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.BlogPost;
import Utils.DbConnection;

public class BlogDAO {

	public List<BlogPost> getAllBlogsWithStats() {
	    List<BlogPost> blogs = new ArrayList<>();
	    String sql = "SELECT bp.*, " +
	                 "(SELECT COUNT(*) FROM Likes WHERE post_id = bp.post_id) AS total_likes, " +
	                 "(SELECT COUNT(*) FROM Comments WHERE post_id = bp.post_id) AS total_comments " +
	                 "FROM BlogPosts bp ORDER BY bp.published_date DESC";
	    System.out.print(sql);
	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            BlogPost blog = new BlogPost(
	                rs.getInt("post_id"),
	                rs.getInt("user_id"),
	                rs.getString("title"),
	                rs.getString("content"),
	                rs.getString("image_url"),
	                rs.getTimestamp("published_date")
	            );

	            // Set totalLikes and totalComments separately
	            blog.setTotalLikes(rs.getInt("total_likes"));
	            blog.setTotalComments(rs.getInt("total_comments"));

	            blogs.add(blog);
	        }
	    } catch (Exception e) {
	    	
	        e.printStackTrace();
	    }

	    return blogs;
	}
	
	public Map<String, Object> getBlogStatistics() {
	    Map<String, Object> stats = new HashMap<>();

	    String sql = "SELECT " +
	                 "  (SELECT COUNT(*) FROM BlogPosts) AS totalPosts, " +
	                 "  (SELECT COUNT(*) FROM Likes) AS totalLikes, " +
	                 "  (SELECT COUNT(*) FROM Comments) AS totalComments";

	    String topLikedSql = "SELECT post_id, title, COUNT(*) AS likeCount " +
	                         "FROM BlogPosts bp JOIN Likes l ON bp.post_id = l.post_id " +
	                         "GROUP BY bp.post_id " +
	                         "ORDER BY likeCount DESC LIMIT 3";

	    try (Connection conn = DbConnection.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        if (rs.next()) {
	            stats.put("totalPosts", rs.getInt("totalPosts"));
	            stats.put("totalLikes", rs.getInt("totalLikes"));
	            stats.put("totalComments", rs.getInt("totalComments"));
	        }

	        List<BlogPost> topPosts = new ArrayList<>();
	        try (PreparedStatement topStmt = conn.prepareStatement(topLikedSql);
	             ResultSet topRs = topStmt.executeQuery()) {
	            while (topRs.next()) {
	                BlogPost post = new BlogPost();
	                post.setPostId(topRs.getInt("post_id"));
	                post.setTitle(topRs.getString("title"));
	                post.setTotalLikes(topRs.getInt("likeCount"));
	                topPosts.add(post);
	            }
	        }

	        stats.put("mostLikedPosts", topPosts);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return stats;
	}
	
	public List<BlogPost> getBlogsByUserId(int userId) {
	    List<BlogPost> blogs = new ArrayList<>();
	    String sql = "SELECT * FROM BlogPosts WHERE user_id = ? ORDER BY published_date DESC";

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogPost blog = new BlogPost();
	            blog.setPostId(rs.getInt("post_id"));
	            blog.setUserId(rs.getInt("user_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setContent(rs.getString("content"));
	            blog.setImageUrl(rs.getString("image_url"));
	            blog.setPublishedDate(rs.getTimestamp("published_date"));
	            blogs.add(blog);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println("Blog fetchinggggg");
	    System.out.println(userId);
	    System.out.println(blogs);

	    

	    return blogs;
	}
	public List<BlogPost> getAllBlogs() {
	    List<BlogPost> blogs = new ArrayList<>();
	    String sql = "SELECT * FROM BlogPosts ORDER BY published_date DESC";

	    try (Connection conn = DbConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogPost blog = new BlogPost();
	            blog.setPostId(rs.getInt("post_id"));
	            blog.setUserId(rs.getInt("user_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setContent(rs.getString("content"));
	            blog.setImageUrl(rs.getString("image_url"));
	            blog.setPublishedDate(rs.getTimestamp("published_date"));
	            blogs.add(blog);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    System.out.println("Blog fetchinggggg");
	    System.out.println(blogs);

	    

	    return blogs;
	}
	
	 public static boolean toggleLike(int postId, int userId) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {
	            conn = DbConnection.getConnection();

	            // Check if like already exists
	            stmt = conn.prepareStatement("SELECT * FROM likes WHERE post_id = ? AND user_id = ?");
	            stmt.setInt(1, postId);
	            stmt.setInt(2, userId);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                // Already liked — unlike it
	                stmt = conn.prepareStatement("DELETE FROM likes WHERE post_id = ? AND user_id = ?");
	                stmt.setInt(1, postId);
	                stmt.setInt(2, userId);
	                stmt.executeUpdate();
	                return false; // Unliked
	            } else {
	                // Not liked yet — like it
	                stmt = conn.prepareStatement("INSERT INTO likes(post_id, user_id) VALUES (?, ?)");
	                stmt.setInt(1, postId);
	                stmt.setInt(2, userId);
	                stmt.executeUpdate();
	                return true; // Liked
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	        	//DbConnection(rs, stmt, conn);
	        }
	        return false;
	    }
	 
	 public boolean deleteBlogByBlogId(int blogId) {
		    String deleteLikesSql = "DELETE FROM Likes WHERE post_id = ?";
		    String deleteCommentsSql = "DELETE FROM Comments WHERE post_id = ?";
		    String deleteBlogSql = "DELETE FROM BlogPosts WHERE post_id = ?";

		    try (Connection conn = DbConnection.getConnection();
		         PreparedStatement deleteLikesStmt = conn.prepareStatement(deleteLikesSql);
		         PreparedStatement deleteCommentsStmt = conn.prepareStatement(deleteCommentsSql);
		         PreparedStatement deleteBlogStmt = conn.prepareStatement(deleteBlogSql)) {

		        // Delete related likes
		        deleteLikesStmt.setInt(1, blogId);
		        deleteLikesStmt.executeUpdate();

		        // Delete related comments
		        deleteCommentsStmt.setInt(1, blogId);
		        deleteCommentsStmt.executeUpdate();

		        // Delete the blog post
		        deleteBlogStmt.setInt(1, blogId);
		        int rowsAffected = deleteBlogStmt.executeUpdate();

		        return rowsAffected > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}




}
