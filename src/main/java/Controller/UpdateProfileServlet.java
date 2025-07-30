package Controller;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import Utils.DbConnection;

@WebServlet("/updateProfile")
@MultipartConfig // Important for file upload!
public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get session and user ID
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("pages/login.jsp?error=notloggedin");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");

        // Get form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio"); // ✅ now included

        // Handle profile picture upload
        Part profilePicPart = request.getPart("profilePic");
        String profilePicFileName = null;
        if (profilePicPart != null && profilePicPart.getSize() > 0) {
            String uploadDir = getServletContext().getRealPath("/uploads");
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            profilePicFileName = userId + "_" + profilePicPart.getSubmittedFileName();
            profilePicPart.write(uploadDir + File.separator + profilePicFileName);
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DbConnection.getConnection();
            String sql = "UPDATE Users SET name = ?, email = ?, bio = ?, profile_picture = ? WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, bio);
            if (profilePicFileName != null) {
                stmt.setString(4, "uploads/"+profilePicFileName);
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }
            stmt.setInt(5, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                session.setAttribute("imageUrl", profilePicFileName);

                response.sendRedirect("fetchUserDetails"); // redirect to a servlet that loads user data
            } else {
                request.setAttribute("errorMessage", "Failed to update profile.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/profile.jsp");
                dispatcher.forward(request, response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/profile.jsp");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
