package Controller;



import Models.BlogPost;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Utils.DbConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import  jakarta.servlet.annotation.WebServlet;
@WebServlet(urlPatterns = {"/fetchBlogServlet", "/getBlogs", "", "/loadBlogs","/home"})
public class FetchBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filterAuthor = request.getParameter("author");
        String filterDate = request.getParameter("date");

        List<BlogPost> blogList = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM blogposts WHERE 1=1");
            if (filterAuthor != null && !filterAuthor.trim().isEmpty()) {
                sql.append(" AND user_id = ?");
            }
            if (filterDate != null && !filterDate.trim().isEmpty()) {
                sql.append(" AND DATE(published_date) = ?");
            }

            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            int index = 1;
            if (filterAuthor != null && !filterAuthor.trim().isEmpty()) {
                stmt.setInt(index++, Integer.parseInt(filterAuthor));
            }
            if (filterDate != null && !filterDate.trim().isEmpty()) {
                stmt.setDate(index++, Date.valueOf(filterDate));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                BlogPost blog = new BlogPost(
                    rs.getInt("post_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("image_url"),
                    rs.getTimestamp("published_date")
                );
                blogList.add(blog);
            }

            System.out.print(blogList);
            request.setAttribute("blogs", blogList);
            RequestDispatcher rd = request.getRequestDispatcher("pages/home.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Still logs to server console
            response.setContentType("text/html");
            response.getWriter().println("<h3>Database error: " + e.getMessage() + "</h3>");
        }

    }
}
