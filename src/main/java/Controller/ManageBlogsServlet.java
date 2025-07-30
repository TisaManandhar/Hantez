package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.BlogDAO;
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
@WebServlet("/admin/manageBlogs")
public class ManageBlogsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"Admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("blog.jsp?error=unauthorized");
            return;
        }

        BlogDAO blogDAO = new BlogDAO();
        List<BlogPost> blogs = blogDAO.getAllBlogsWithStats(); // Fetch blogs with likes/comments
        
        System.out.print(blogs);

        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("..//pages/admin/manage_blogs.jsp").forward(request, response);
    }
}
