<%@ include file="includes/header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>

<header>
    <h1>Welcome to Your Profile</h1>
</header>

<h2>User Profile</h2>

<div class="profile-container">
    <img src="${pageContext.request.contextPath}/${profile_picture}" alt="Profile Picture" width="100" height="100">
    <h3>Name: ${name}</h3>
    <p><strong>Email:</strong> ${email}</p>
    <p><strong>Bio:</strong> ${bio}</p>
    <p><strong>Role:</strong> ${role}</p>

    <button id="editProfileBtn" class="edit-button">Edit Profile</button>
    <a href="/Hantez/fetchBlogServlet" class="back-button">Back to Home</a>
</div>

<!-- Modal -->
<div id="editProfileModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Edit Profile</h2>
        <form action="updateProfile" method="POST" enctype="multipart/form-data">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="${name}" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="${email}" required>

            <label for="bio">Bio:</label>
            <textarea id="bio" name="bio">${bio}</textarea>

            <label for="profilePic">Profile Picture:</label>
            <input type="file" id="profilePic" name="profilePic">

            <button type="submit" class="submit-btn">Save Changes</button>
        </form>
    </div>
</div>

<script>
    const modal = document.getElementById("editProfileModal");
    const btn = document.getElementById("editProfileBtn");
    const span = document.querySelector(".close");

    btn.onclick = () => modal.style.display = "block";
    span.onclick = () => modal.style.display = "none";
    window.onclick = (event) => {
        if (event.target === modal) modal.style.display = "none";
    };
</script>

</body>
</html>
