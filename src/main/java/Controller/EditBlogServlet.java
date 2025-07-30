package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Utils.DbConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/editRecipe")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,   // 1MB
    maxFileSize = 1024 * 1024 * 5,     // 5MB
    maxRequestSize = 1024 * 1024 * 10  // 10MB
)
public class EditBlogServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            HttpSession session = request.getSession(false);
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("pages/login.jsp?error=whileeditingblog");
                return;
            }

            int postId = Integer.parseInt(request.getParameter("post_id"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            Part imagePart = request.getPart("image_file");

            String imageUrl = null;

            if (imagePart != null && imagePart.getSize() > 0) {
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
                imageUrl = "uploads/" + fileName;
            }

            Connection conn = DbConnection.getConnection();

            String sql;
            PreparedStatement stmt;

            if (imageUrl != null) {
                sql = "UPDATE BlogPosts SET title = ?, content = ?, image_url = ?, published_date = ? WHERE post_id = ? AND user_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, content);
                stmt.setString(3, imageUrl);
                stmt.setString(4, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                stmt.setInt(5, postId);
                stmt.setInt(6, userId);
            } else {
                sql = "UPDATE BlogPosts SET title = ?, content = ?, published_date = ? WHERE post_id = ? AND user_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, content);
                stmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                stmt.setInt(4, postId);
                stmt.setInt(5, userId);
            }

            int result = stmt.executeUpdate();

            if (result > 0) {
                response.sendRedirect("fetchBlogServlet");
            } else {
                out.println("<h3>Failed to update blog post.</h3>");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
