<%@ page import="Models.BlogPost" %>
<%@ page import="java.util.List" %>
<%@ include file="includes/header.jsp" %> <!-- Optional: your nav/header file -->

<!DOCTYPE html>
<html>
<head>
    <title>Blog Posts</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/blog_posts.css">
</head>
<body>

<div class="container">

    <h2 class="page-title">Blog Posts</h2>

    <!-- Filter Form -->
    <form action="fetchBlogServlet" method="get" class="filter-form">
        <div class="form-group">
            <label for="author">Author ID:</label>
            <input type="text" name="author" id="author" placeholder="e.g., 1">
        </div>

        <div class="form-group">
            <label for="date">Date:</label>
            <input type="date" name="date" id="date">
        </div>

        <button type="submit" class="btn-primary">Filter</button>
    </form>

    <div class="blog-grid">
        <%
        List<BlogPost> blogs = (List<BlogPost>) request.getAttribute("blogs");
        if (blogs != null && !blogs.isEmpty()) {
            for (BlogPost blog : blogs) {
        %>
        <div class="blog-card-container">
            <a href="singleBlog?post_id=<%= blog.getPostId() %>" class="blog-link">
                <div class="blog-card">
                    <img src="<%= request.getContextPath() + "/"+blog.getImageUrl() %>"
                        class="blog-image"
                        alt="Blog Image">
                    <div class="blog-content">
                        <h5 class="blog-title"><%= blog.getTitle() %></h5>
                        <p class="blog-excerpt">
                            <%= blog.getContent().length() > 100 ? blog.getContent().substring(0, 100) + "..." : blog.getContent() %>
                        </p>
                    </div>
                    <div class="blog-footer">
                        Author ID: <%= blog.getUserId() %> <br>
                        Date: <%= blog.getPublishedDate().toString().substring(0, 10) %>
                    </div>
                </div>
            </a>
        </div>
        <%
            }
        } else {
        %>
        <div class="no-blogs-container">
            <div class="alert-message warning">No blog posts found.</div>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>