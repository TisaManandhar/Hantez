package Controller;



import DAO.UserDAO;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Utils.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/admin/deleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userRole") == null || !"Admin".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("blog.jsp?error=unauthorized");
            return;
        }

        String userIdParam = request.getParameter("user_id");

        if (userIdParam != null) {
            try {
                int userId = Integer.parseInt(userIdParam);

                UserDAO userDAO = new UserDAO();
                boolean deleted = userDAO.deleteUserById(userId);

                if (deleted) {
                    response.sendRedirect("manageUsers");
                } else {
                    response.sendRedirect("manageUsers?error=delete_failed");
                }

            } catch (NumberFormatException e) {
                response.sendRedirect("manageUsers?error=invalid_id");
            }
        } else {
            response.sendRedirect("manageUsers?error=missing_id");
        }
    }
}
