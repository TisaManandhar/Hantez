package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import Utils.DbConnection;
import Utils.PasswordHashing;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(urlPatterns = {"/signupServlet", "/signup", "/register"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 1024 * 1024 * 5,     // 5MB
    maxRequestSize = 1024 * 1024 * 10  // 10MB
)
public class SignupServlet extends HttpServlet {

    // Handle GET request: redirect to register.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("pages/signup.jsp");
    }

    // Handle POST request: registration logic
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Retrieve form data
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirm_password");
            Part filePart = request.getPart("profilePic");

            // Validate input
            if (name == null || email == null || password == null || confirmPassword == null || filePart == null
                    || name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                response.sendRedirect("pages/register.jsp?msg=All+fields+are+required");
                return;
            }

            if (!password.equals(confirmPassword)) {
                response.sendRedirect("pages/register.jsp?msg=Passwords+do+not+match");
                return;
            }

            // Hash the password
            String hashedPassword = PasswordHashing.hashPassword(password);

            // Upload path (adjust for deployment)
            String uploadPath = "G:/EclipsServlets/Hantez/src/main/webapp/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            // Get filename and resolve conflict
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String baseName = originalFileName;
            String extension = "";
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex > 0) {
                baseName = originalFileName.substring(0, dotIndex);
                extension = originalFileName.substring(dotIndex);
            }

            String fileName = originalFileName;
            File file = new File(uploadPath, fileName);
            int count = 1;
            while (file.exists()) {
                fileName = baseName + "_" + count + extension;
                file = new File(uploadPath, fileName);
                count++;
            }

            // Save the file
            filePart.write(file.getAbsolutePath());
            String profilePicPath = "uploads/" + fileName;

            // Save to DB
            Connection conn = DbConnection.getConnection();
            String sql = "INSERT INTO Users (name, email, password, profile_picture, bio, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, profilePicPath);
            stmt.setString(5, "");
            stmt.setString(6, "User");

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.sendRedirect("pages/login.jsp?msg=Registration+successful!+Please+login.");
            } else {
                response.sendRedirect("pages/register.jsp?msg=Something+went+wrong,+please+try+again");
            }

            conn.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("pages/signup.jsp?msg=" + java.net.URLEncoder.encode("Error: " + ex.getMessage(), "UTF-8"));
        }
    }
}
