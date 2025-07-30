package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Utils.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import DAO.BlogDAO;
import DAO.UserDAO;
import Models.BlogPost;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@jakarta.servlet.annotation.WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        UserDAO userDAO = new UserDAO();

        Map<String, Object> stats = blogDAO.getBlogStatistics();
        int totalUsers = userDAO.getTotalUsers();

        request.setAttribute("totalPosts", stats.get("totalPosts"));
        request.setAttribute("totalLikes", stats.get("totalLikes"));
        request.setAttribute("totalComments", stats.get("totalComments"));
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("mostLikedPosts", stats.get("mostLikedPosts"));

        request.getRequestDispatcher("..//pages/admin/admin_dashboard.jsp").forward(request, response);
    }
}
