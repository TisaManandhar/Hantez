package Controller;

import java.io.*;



import DAO.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import  jakarta.servlet.annotation.WebServlet;
@WebServlet("/blog")
public class LikeBlogServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        // Check if user is logged in
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            // Not logged in, redirect to login page or show error
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        if ("like".equals(action)) {
            try {
                int postId = Integer.parseInt(request.getParameter("id"));

                // Call your DAO or service method to toggle like
                boolean success = BlogDAO.toggleLike(postId, userId);

                // Optionally store feedback message
                if (success) {
                    session.setAttribute("message", "You liked the post!");
                } else {
                    session.setAttribute("message", "You unliked the post!");
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Failed to process like.");
            }

            // Redirect back to the same blog post page
            response.sendRedirect(request.getContextPath() + "/singleBlog?post_id=" + request.getParameter("id"));
        }

        // You could also handle "comment" action here if needed
    }
}
