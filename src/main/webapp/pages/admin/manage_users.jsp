<%@ page import="Models.User" %>
<%@ page import="java.util.List" %>
<%@ include file="../includes/sidebar_admin.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/manage_users.css">
</head>
<body>
    <div class="container">
        <div class="card">
            <div class="card-header">
                <h3>Manage Users</h3>
            </div>
            <div class="card-body">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Profile</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Role</th>
                            <th>Joined</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                        %>
                        <tr>
                            <td>
                                <img src="<%= request.getContextPath() + "/" + user.getProfilePicture() %>"
                                     alt="Profile" class="profile-pic">
                            </td>
                            <td><%= user.getName() %></td>
                            <td><%= user.getEmail() %></td>
                            <td><%= user.getRole() %></td>
                            <td><%= user.getJoinDate().toString().substring(0, 10) %></td>
                            <td>
                                <form method="post" action="deleteUserServlet" onsubmit="return confirm('Delete this user?');">
                                    <input type="hidden" name="user_id" value="<%= user.getUserId() %>">
                                    <button type="submit" class="btn-delete">Delete</button>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="6" class="no-data">No users found.</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>