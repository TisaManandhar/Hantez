<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ include file="includes/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Blog Post</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/create_blog.css">
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h2 class="form-title">Create a New Blog Post</h2>
            <form action="/Hantez/createBlogServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="title">Blog Title</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>

                <div class="form-group">
                    <label for="content">Content</label>
                    <textarea class="form-control" id="content" name="content" rows="6" required></textarea>
                </div>

                <div class="form-group">
                    <label for="image">Upload Image</label>
                    <input type="file" class="form-file" id="image" name="image" onchange="previewImage(event)">
                    <img id="imagePreview" alt="Image Preview">
                </div>

                <div class="form-group">
                    <label for="date">Published Date</label>
                    <input type="date" class="form-control" id="date" name="published_date" required>
                </div>

                <button type="submit" class="btn-purple">Publish Blog</button>
            </form>
        </div>
    </div>

    <script>
        function previewImage(event) {
            const reader = new FileReader();
            reader.onload = function(){
                const output = document.getElementById('imagePreview');
                output.src = reader.result;
                output.style.display = 'block';
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    </script>
</body>
</html>