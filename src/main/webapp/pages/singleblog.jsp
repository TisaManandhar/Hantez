<%@ page import="Models.BlogPost, java.util.List" %>
<%@ include file="includes/header.jsp" %>

<%
    BlogPost post = (BlogPost) request.getAttribute("post");
    Integer likes = (Integer) request.getAttribute("likes");
    Integer commentsCount = (Integer) request.getAttribute("commentsCount");
    List<String> tags = (List<String>) request.getAttribute("tags");
    List<String[]> commentList = (List<String[]>) request.getAttribute("commentList");
    Boolean userLiked = (Boolean) request.getAttribute("userLiked");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= post != null ? post.getTitle() : "Blog Details" %></title>
    <link rel="stylesheet" href="<%= contextPath %>/css/single-blog.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>

<div class="main-container">
    <div class="breadcrumb">
        <a href="<%= contextPath %>/fetchBlogServlet">Home</a> &gt; Blog Details
    </div>

    <% if (post != null) { %>
        <div class="blog-card">
            <img src="<%= contextPath + "/" + post.getImageUrl() %>" alt="Blog Image" class="blog-image">
            <div class="blog-content">
                <h1><%= post.getTitle() %></h1>
                <p class="author">By <%= post.getUserId() %> | <%= post.getPublishedDate() %></p>
                <p class="description"><%= post.getContent() %></p>

                <% if (tags != null && !tags.isEmpty()) { %>
                    <div class="tags">
                        <% for (String tag : tags) { %>
                            <span class="tag"><%= tag %></span>
                        <% } %>
                    </div>
                <% } %>

                <div class="like-section">
                    <form action="<%= contextPath %>/blog" method="post">
                        <input type="hidden" name="action" value="like">
                        <input type="hidden" name="id" value="<%= post.getPostId() %>">
                        <button type="submit" class="like-btn">
                            <% if (userLiked != null && userLiked) { %>
                                <i class="fas fa-heart liked"></i>
                            <% } else { %>
                                <i class="far fa-heart"></i>
                            <% } %>
                            <span><%= likes %> Likes</span>
                        </button>
                    </form>
                </div>

                <div class="comment-section">
                    <h3>Comments (<%= commentsCount %>)</h3>
                    <% if (commentList != null && !commentList.isEmpty()) {
                        for (String[] comment : commentList) { %>
                            <div class="comment">
                                <strong><%= comment[0] %>:</strong> <%= comment[1] %>
                            </div>
                        <% }
                    } else { %>
                        <p>No comments yet.</p>
                    <% } %>
                </div>

                <form action="comment" method="post" class="comment-form">
                    <input type="hidden" name="action" value="comment">
                    <input type="hidden" name="post_id" value="<%= post.getPostId() %>">
                    <textarea name="comment_text" placeholder="Add a comment..." required></textarea>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </div>
    <% } else { %>
        <p>Post not found.</p>
    <% } %>
</div>

</body>
</html>
