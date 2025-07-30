<%@ include file="includes/header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="Models.BlogPost" %>

<%
List<BlogPost> recipes = (List<BlogPost>) request.getAttribute("recipes");
String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Recipes</title>
    <link rel="stylesheet" type="text/css" href="<%= contextPath %>/css/my_recipes.css">
    <style>
        .modal {
            display: none;
            position: fixed;
            z-index: 9999;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .modal.open {
            display: block;
            opacity: 1;
        }

        .modal-content {
            background-color: #fff;
            margin: 10% auto;
            padding: 30px;
            border-radius: 8px;
            width: 90%;
            max-width: 500px;
            position: relative;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            transform: translateY(-20px);
            opacity: 0;
            transition: all 0.3s ease;
        }

        .modal.open .modal-content {
            transform: translateY(0);
            opacity: 1;
        }

        .close {
            position: absolute;
            right: 15px;
            top: 10px;
            font-size: 24px;
            font-weight: bold;
            color: #666;
            cursor: pointer;
        }

        .close:hover {
            color: #000;
        }

        .modal-content label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
        }

        .modal-content input[type="text"],
        .modal-content textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .modal-content button[type="submit"] {
            background-color: #4a90e2;
            color: white;
            border: none;
            padding: 12px 24px;
            border-radius: 4px;
            cursor: pointer;
        }

        .modal-content button[type="submit"]:hover {
            background-color: #357abD;
        }

        #editImagePreview {
            display: block;
            max-width: 100%;
            max-height: 150px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="page-title">My Recipes</h2>

    <% if (recipes == null || recipes.isEmpty()) { %>
        <div class="no-recipes-container">
            <p class="no-recipes-message">You haven't added any recipes yet.</p>
            <a href="<%= contextPath %>/pages/create_blog.jsp" class="btn btn-primary">Add Your First Recipe</a>
        </div>
    <% } else { %>
        <div class="recipe-grid">
            <% for (BlogPost recipe : recipes) {
                String imgUrl = (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty())
                        ? recipe.getImageUrl()
                        : "images/default-recipe.jpg";
                String shortContent = recipe.getContent().length() > 150
                        ? recipe.getContent().substring(0, 150) + "..."
                        : recipe.getContent();
            %>
                <div class="recipe-card">
                    <div class="card">
                        <img src="<%= contextPath + "/" + imgUrl %>" class="card-image" alt="<%= recipe.getTitle() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= recipe.getTitle() %></h5>
                            <p class="card-text"><%= shortContent %></p>
                        </div>
                        <div class="card-footer">
                            <a href="<%= contextPath %>/singleBlog?post_id=<%= recipe.getPostId() %>" class="btn btn-view">View</a>
                            <button class="btn btn-edit"
                                    onclick="openEditModal('<%= recipe.getPostId() %>', 
                                                           '<%= recipe.getTitle().replace("'", "\\'").replace("\"", "\\\"") %>',
                                                           `<%= recipe.getContent().replace("'", "\\'").replace("\"", "\\\"").replaceAll("\n", "\\n") %>`,
                                                           '<%= contextPath + "/" + imgUrl %>')">
                                Edit
                            </button>
                            <form action="<%= contextPath %>/deleteRecipe" method="POST" style="display:inline;">
                                <input type="hidden" name="post_id" value="<%= recipe.getPostId() %>">
                                <button type="submit" class="btn btn-delete" onclick="return confirm('Are you sure you want to delete this recipe?')">Delete</button>
                            </form>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    <% } %>
</div>

<!-- Modal for editing a recipe -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeEditModal()">&times;</span>
        <h2>Edit Recipe</h2>
        <form action="<%= contextPath %>/editRecipe" method="POST" enctype="multipart/form-data">
            <input type="hidden" id="editPostId" name="post_id">

            <label>Title:</label>
            <input type="text" id="editTitle" name="title" required>

            <label>Content:</label>
            <textarea id="editContent" name="content" rows="6" required></textarea>

            <label>Current Image:</label>
            <div>
                <img id="editImagePreview" src="" alt="Recipe Image">
            </div>

            <label>Replace Image:</label>
            <input type="file" id="editImageFile" name="image_file" accept="image/*">

            <button type="submit" class="btn btn-primary">Update</button>
        </form>
    </div>
</div>

<script>
function openEditModal(postId, title, content, imageUrl) {
    document.getElementById('editPostId').value = postId;
    document.getElementById('editTitle').value = title;
    document.getElementById('editContent').value = content.replace(/\\n/g, "\n");
    document.getElementById('editImagePreview').src = imageUrl || 'images/default-recipe.jpg';
    document.getElementById('editModal').classList.add('open');
}

function closeEditModal() {
    document.getElementById('editModal').classList.remove('open');
}
</script>

</body>
</html>
