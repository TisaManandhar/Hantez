package Controller;

import java.io.*;
import java.sql.*;

import Utils.DbConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/fetchUserDetails")
public class FetchUserDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Get user ID from session
            HttpSession session = request.getSession(false);
            
            // Check if session exists, otherwise redirect to login
            if (session == null) {
                response.sendRedirect("pages/login.jsp?error=notloggedin");
                return;
            }

            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("pages/login.jsp?error=notloggedin");
                return;
            }

            // Connect to DB and fetch user details
            Connection conn = DbConnection.getConnection();
            String sql = "SELECT name, email, profile_picture, bio, role FROM Users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String profilePic = rs.getString("profile_picture");
                String bio = rs.getString("bio");
                String role = rs.getString("role");

                // Set user details in request scope
                request.setAttribute("name", name);
                request.setAttribute("email", email);
                request.setAttribute("profile_picture", profilePic);
                request.setAttribute("bio", bio);
                request.setAttribute("role", role);

                // Forward to user profile page (e.g., userProfile.jsp)
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/profile.jsp");
                dispatcher.forward(request, response);
            } else {
                out.println("<h3>Error: User details not found!</h3>");
            }

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            out.println("<h3>Error: " + ex.getMessage() + "</h3>");
        }
    }
}
