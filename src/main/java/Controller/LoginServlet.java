package Controller;

import java.io.*;
import java.sql.*;
import Utils.DbConnection;
import Utils.PasswordHashing;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/loginServlet", "/login", "/signin"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT * FROM Users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                if (PasswordHashing.verifyPassword(password, storedHashedPassword)) {
                    int userId = rs.getInt("user_id");
                    String userName = rs.getString("name");
                    String userRole = rs.getString("role");
                    String imageUrl = rs.getString("profile_picture");

                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);
                    session.setAttribute("userName", userName);
                    session.setAttribute("userRole", userRole);
                    session.setAttribute("imageUrl", imageUrl);

                    if ("Admin".equalsIgnoreCase(userRole)) {
                        response.sendRedirect("/Hantez/admin/dashboard");
                    } else {
                        response.sendRedirect("fetchBlogServlet");
                    }

                } else {
                    // Wrong password
                    response.sendRedirect("pages/login.jsp?msg=Invalid password");
                }
            } else {
                // Email not found
                response.sendRedirect("pages/login.jsp?msg=User not found");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/login.jsp?msg=Something went wrong, please try again later");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to login page with optional message
        String msg = request.getParameter("msg");
        if (msg == null) {
            msg = "";
        }
        response.sendRedirect("pages/login.jsp?msg=" + msg);
    }
}
