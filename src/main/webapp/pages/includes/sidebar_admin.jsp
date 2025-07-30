<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/sidebar.css"></head>
    
</head>
<body>

<div class="sidebar">
    <h4 class="sidebar-title">Admin Panel</h4>
    <a href="../admin/dashboard">Dashboard</a>
    <a href="../admin/manageUsers">Manage Users</a>
    <a href="../admin/manageBlogs">Manage Blogs</a>
    <a href="logout.jsp">Logout</a>
</div>

<div class="content">
    <h2>Welcome to the Admin Dashboard</h2>
    <p>Select a feature from the sidebar to manage the platform.</p>
</div>

</body>
</html>
