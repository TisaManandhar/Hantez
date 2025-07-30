<%@ page import="java.util.List" %>
<%@ page import="Models.BlogPost" %> <!-- Replace with actual package -->
<%@ include file="../includes/sidebar_admin.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Blogs</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/manage_blogs.css">
</head>
<body>
    <div class="container">
        <h2 class="page-title">Manage Blogs</h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) {
        %>
            <div class="alert-message error"><%= error %></div>
        <%
            }
        %>

        <!-- Blog Table -->
        <table class="data-table">
            <thead>
                <tr>
                    <th>Image</th>
                    <th>Title</th>
                    <th>Content</th>
                    <th>Likes</th>
                    <th>Comments</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<BlogPost> blogs = (List<BlogPost>) request.getAttribute("blogs");
                    if (blogs != null) {
                        for (BlogPost blog : blogs) {
                %>
                    <tr>
                        <td>
                            <img src="<%= request.getContextPath() + "/" + blog.getImageUrl() %>" class="blog-thumbnail" alt="Blog Image">
                        </td>
                        <td><%= blog.getTitle() %></td>
                        <td><%= blog.getContent() %></td>
                        <td><%= blog.getTotalLikes() %></td>
                        <td><%= blog.getTotalComments() %></td>
                        <td class="action-buttons">
                            <a href="viewBlogDetails?post_id=<%= blog.getPostId() %>" class="btn btn-view">View</a>
                            <a href="deleteBlog?post_id=<%= blog.getPostId() %>" class="btn btn-delete"
                               onclick="return confirm('Are you sure you want to delete this blog?')">Delete</a>
                        </td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="6">No blogs available.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
