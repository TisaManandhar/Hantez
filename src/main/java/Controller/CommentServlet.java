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

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            // Not logged in, redirect to login
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int postId = Integer.parseInt(request.getParameter("post_id"));
            String commentText = request.getParameter("comment_text");
            System.out.println(postId);
            System.out.println(commentText);
            System.out.println(userId);
            System.out.print("Got errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr1");


            if (commentText != null && !commentText.trim().isEmpty()) {
                saveComment(userId, postId, commentText);
                session.setAttribute("message", "Comment posted successfully!");
                System.out.print("Got errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr2");

            } else {
                session.setAttribute("error", "Comment cannot be empty.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Failed to post comment.");
            System.out.print("Got errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr3");

        }

        response.sendRedirect("/Hantez/singleBlog?post_id=" + request.getParameter("post_id"));
    }

    private void saveComment(int userId, int postId, String commentText) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn =  DbConnection.getConnection();

            String sql = "INSERT INTO comments (user_id, post_id, comment_text) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, postId);
            stmt.setString(3, commentText);

            // Store comment_date as a string in the format 'YYYY-MM-DD HH:MM:SS'
            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
           // stmt.setString(4, currentTime);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Got errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrreeeeee");
        } finally {
           // DbConnection.close(stmt, conn);
        }
    }
}
