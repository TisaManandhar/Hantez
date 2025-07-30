<%@ include file="../includes/sidebar_admin.jsp" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/admin_dashboard.css">
</head>
<body>
    <div class="container">
        <h2 class="dashboard-title">Admin Dashboard</h2>

        <div class="stats-container">
            <div class="stat-card primary">
                <div class="stat-content">Total Posts: <strong>${totalPosts}</strong></div>
            </div>
            <div class="stat-card success">
                <div class="stat-content">Total Users: <strong>${totalUsers}</strong></div>
            </div>
            <div class="stat-card warning">
                <div class="stat-content">Total Likes: <strong>${totalLikes}</strong></div>
            </div>
            <div class="stat-card danger">
                <div class="stat-content">Total Comments: <strong>${totalComments}</strong></div>
            </div>
        </div>

        <h4 class="section-title">Top Liked Posts</h4>
        <table class="data-table">
            <thead>
                <tr>
                    <th>Post ID</th>
                    <th>Title</th>
                    <th>Likes</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="post" items="${mostLikedPosts}">
                    <tr>
                        <td>${post.postId}</td>
                        <td>${post.title}</td>
                        <td>${post.totalLikes}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>