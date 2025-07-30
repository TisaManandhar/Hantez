package Controller;

import java.io.*;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.BlogDAO;
import Models.BlogPost;
import Utils.DbConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import  jakarta.servlet.annotation.WebServlet;
@WebServlet("/manageRecipes")
public class ManageRecipesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        int user = Integer.parseInt(session.getAttribute("userId").toString()) ;

        if (user==0) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        BlogDAO blogDAO = new BlogDAO();
        List<BlogPost> userRecipes = blogDAO.getBlogsByUserId(user);
        System.out.print("USer Recep");
        System.out.println(userRecipes);

        request.setAttribute("recipes", userRecipes);
        request.getRequestDispatcher("pages/manage_recipes.jsp").forward(request, response);
    }
}
