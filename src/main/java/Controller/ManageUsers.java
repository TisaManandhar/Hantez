package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Models.User;
import Utils.DbConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import  jakarta.servlet.annotation.WebServlet;
@WebServlet("/admin/manageUsers")
public class ManageUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session
        HttpSession session = request.getSession(false);
        System.out.print(session.getAttribute("userRole"));
        if (session == null || session.getAttribute("userRole") == null) {
            response.sendRedirect("../pages/blog.jsp?error=unauthorized");
            return;
        }

        String role = (String) session.getAttribute("userRole");

        if (!"Admin".equals(role)) {
            response.sendRedirect("../pages/blog.jsp?error=unauthorized");
            return;
        }

        // Fetch users from DB
        List<User> users = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setJoinDate(rs.getTimestamp("join_date"));
                u.setProfilePicture(rs.getString("profile_picture"));
                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("users", users);
        request.getRequestDispatcher("..//pages/admin/manage_users.jsp").forward(request, response);
    }
}
