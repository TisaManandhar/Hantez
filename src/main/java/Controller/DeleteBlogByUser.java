package Controller;

import DAO.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@jakarta.servlet.annotation.WebServlet(urlPatterns = {"/deleteRecipe", "/admin/deleteBlog"})
public class DeleteBlogByUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleDelete(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleDelete(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String postIdParam = request.getParameter("post_id");

        try {
            int postId = Integer.parseInt(postIdParam);

            BlogDAO blogDAO = new BlogDAO();
            boolean deleted = blogDAO.deleteBlogByBlogId(postId);

            if (deleted) {
                // Redirect based on origin path
                if (request.getRequestURI().contains("/admin/")) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageBlogs?message=Blog+deleted+successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/manageRecipes?message=Blog+deleted+successfully");
                }
            } else {
                if (request.getRequestURI().contains("/admin/")) {
                    response.sendRedirect(request.getContextPath() + "/admin/manageBlogs?error=Unable+to+delete+blog");
                } else {
                    response.sendRedirect(request.getContextPath() + "/manageRecipes?error=Unable+to+delete+blog");
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manageRecipes?error=Invalid+Blog+ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/manageRecipes?error=Unexpected+error+occurred");
        }
    }
}
