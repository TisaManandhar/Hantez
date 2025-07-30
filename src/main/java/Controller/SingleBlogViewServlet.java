package Controller;

import java.io.*;

import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Models.BlogPost;
import Utils.DbConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import  jakarta.servlet.annotation.WebServlet;
@WebServlet("/singleBlog")
public class SingleBlogViewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int postId = Integer.parseInt(request.getParameter("post_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String action = request.getParameter("action");

        try (Connection conn = DbConnection.getConnection()) {
            if ("like".equals(action)) {
                // Avoid duplicate likes
                PreparedStatement checkLike = conn.prepareStatement("SELECT * FROM Likes WHERE user_id = ? AND post_id = ?");
                checkLike.setInt(1, userId);
                checkLike.setInt(2, postId);
                ResultSet rs = checkLike.executeQuery();
                if (!rs.next()) {
                    PreparedStatement insertLike = conn.prepareStatement("INSERT INTO Likes (user_id, post_id) VALUES (?, ?)");
                    insertLike.setInt(1, userId);
                    insertLike.setInt(2, postId);
                    insertLike.executeUpdate();
                }
            } else if ("comment".equals(action)) {
                String comment = request.getParameter("comment");
                if (comment != null && !comment.trim().isEmpty()) {
                    PreparedStatement insertComment = conn.prepareStatement("INSERT INTO Comments (user_id, post_id, comment_text) VALUES (?, ?, ?)");
                    insertComment.setInt(1, userId);
                    insertComment.setInt(2, postId);
                    insertComment.setString(3, comment);
                    insertComment.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // After action, redirect to GET for updated data
        response.sendRedirect("singleBlog?post_id=" + postId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int postId = Integer.parseInt(request.getParameter("post_id"));
        BlogPost post = null;
        int totalLikes = 0;
        int totalComments = 0;
        List<String> tags = new ArrayList<>();
        List<String[]> comments = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()) {
            // Fetch post
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BlogPosts WHERE post_id = ?");
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                post = new BlogPost(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("image_url"),
                    rs.getTimestamp("published_date")
                );
            }

            // Likes
            PreparedStatement likeStmt = conn.prepareStatement("SELECT COUNT(*) FROM Likes WHERE post_id = ?");
            likeStmt.setInt(1, postId);
            ResultSet rsLikes = likeStmt.executeQuery();
            if (rsLikes.next()) {
                totalLikes = rsLikes.getInt(1);
            }

            // Comments
            PreparedStatement commentCountStmt = conn.prepareStatement("SELECT COUNT(*) FROM Comments WHERE post_id = ?");
            commentCountStmt.setInt(1, postId);
            ResultSet rsComments = commentCountStmt.executeQuery();
            if (rsComments.next()) {
                totalComments = rsComments.getInt(1);
            }

            // Fetch tags
            PreparedStatement tagStmt = conn.prepareStatement(
                "SELECT t.tag_name FROM Tags t JOIN PostTags pt ON t.tag_id = pt.tag_id WHERE pt.post_id = ?");
            tagStmt.setInt(1, postId);
            ResultSet rsTags = tagStmt.executeQuery();
            while (rsTags.next()) {
                tags.add(rsTags.getString("tag_name"));
            }

            // Fetch comments (optional - with user names)
            PreparedStatement commentStmt = conn.prepareStatement(
                "SELECT u.name, c.comment_text, c.comment_date FROM Comments c JOIN Users u ON c.user_id = u.user_id WHERE c.post_id = ? ORDER BY c.comment_date DESC");
            commentStmt.setInt(1, postId);
            ResultSet rsCommentList = commentStmt.executeQuery();
            while (rsCommentList.next()) {
                comments.add(new String[] {
                    rsCommentList.getString("name"),
                    rsCommentList.getString("comment_text"),
                    rsCommentList.getTimestamp("comment_date").toString()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("post", post);
        request.setAttribute("likes", totalLikes);
        request.setAttribute("commentsCount", totalComments);
        request.setAttribute("tags", tags);
        request.setAttribute("commentList", comments);

        RequestDispatcher rd = request.getRequestDispatcher("pages/singleblog.jsp");
        rd.forward(request, response);
    }
}
