<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hantez</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/header.css">
</head>
<body>
    <nav class="navbar">
        <a class="navbar-brand" href="<%= request.getContextPath() %>/fetchBlogServlet">
            <img src="<%= request.getContextPath() %>/resources/images/logo.png" alt="Logo"> Hantez
        </a>

        <input type="checkbox" id="menu-toggle">
        <label for="menu-toggle" class="menu-icon">&#9776;</label>

        <div class="navbar-menu">
            <ul class="navbar-nav">
                <li><a href="<%= request.getContextPath() %>/fetchBlogServlet">Recipes</a></li>

                <%
                    String userName = (String) session.getAttribute("userName");
                    String userRole = (String) session.getAttribute("userRole");
                    if (userName != null) {
                %>
                    <li><a href="<%= request.getContextPath() %>/manageRecipes">Manage Recipes</a></li>
                    <li><a href="<%= request.getContextPath() %>/pages/create_blog.jsp">Post Recipes</a></li>
                    <% if ("Admin".equalsIgnoreCase(userRole)) { %>
                        <li><a href="<%= request.getContextPath() %>/admin/dashboard">Admin Panel</a></li>
                    <% } %>
                <% } %>
            </ul>

            <ul class="navbar-nav right">
                <%
                    String profilePic = (String) session.getAttribute("imageUrl");
                    if (userName != null) {
                %>
                    <li class="dropdown">
                        <a href="#" class="profile-link">
                            <img src="<%= request.getContextPath() %>/<%= profilePic != null ? profilePic : "resources/images/default-profile.png" %>" class="profile-img" alt="Profile">
                            <%= userName %>
                        </a>
                        <ul class="dropdown-content">
                            <li><a href="<%= request.getContextPath() %>/fetchUserDetails">Profile</a></li>
                            <li><a href="<%= request.getContextPath() %>/logout">Logout</a></li>
                        </ul>
                    </li>
                <% } else { %>
                    <li><a href="<%= request.getContextPath() %>/pages/login.jsp">Login</a></li>
                    <li><a href="<%= request.getContextPath() %>/pages/register.jsp">Signup</a></li>
                <% } %>
            </ul>
        </div>
    </nav>
</body>
</html>
