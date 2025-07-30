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

@jakarta.servlet.annotation.WebServlet("/createBlogServlet")
@jakarta.servlet.annotation.MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 1024 * 1024 * 5,     // 5MB
    maxRequestSize = 1024 * 1024 * 10  // 10MB
)
public class CreateBlogServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Get user ID from session
            HttpSession session = request.getSession(false);
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("pages/login.jsp?error=whileaddingblog");
                return;
            }

            // Retrieve blog form data
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Part imagePart = request.getPart("image");

            if (title == null || content == null || imagePart == null) {
                out.println("<h3>Error: All fields are required!</h3>");
                return;
            }

            // File handling
            String uploadPath = "G:/EclipsServlets/Hantez/src/main/webapp/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();

            String originalFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
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

            imagePart.write(file.getAbsolutePath());
            String imageUrl = "uploads/" + fileName;

            // Current date/time
            String publishedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Save to DB
            Connection conn = DbConnection.getConnection();
            String sql = "INSERT INTO BlogPosts (user_id, title, content, image_url, published_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, content);
            stmt.setString(4, imageUrl);
            stmt.setString(5, publishedDate);

            int result = stmt.executeUpdate();

            if (result > 0) {
                response.sendRedirect("fetchBlogServlet");
            } else {
                out.println("<h3>Failed to save blog post.</h3>");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
