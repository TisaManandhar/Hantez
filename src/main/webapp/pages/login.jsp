<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Hantez Food Blog</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f3f4f9; /* Light lavender background */
            position: relative;
        }
        .login-container {
            background-color: #e6e6fa; /* Lavender background */
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
            position: relative;
            z-index: 1;
        }
        .btn-lavender {
            background-color: #dda0dd;
            color: white;
        }
        .btn-lavender:hover {
            background-color: #ba55d3;
        }
        .logo-background {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            opacity: 0.03;
            max-width: 500px;
            z-index: 0;
            pointer-events: none;
        }
        .logo-top {
            display: block;
            margin: 0 auto 20px auto;
            max-width: 120px;
        }
    </style>
</head>
<body>

<!-- Background Logo -->
<img src="/Hantez/resources/images/logo.png" alt="Hantez Logo Background" class="logo-background">

<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="login-container col-md-4">
        <!-- Top Logo -->
        <img src="/Hantez/resources/images/logo.png" alt="Hantez Logo Top" class="logo-top">

        <h3 class="text-center mb-4">Login to Your Account</h3>
        <%
    String msg = request.getParameter("msg");
    if (msg != null && !msg.trim().isEmpty()) {
%>
    <div class="alert alert-danger text-center" role="alert">
        <%= msg %>
    </div>
<%
    }
%>
        <form method="POST" action="../loginServlet">
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
            </div>
            <button type="submit" class="btn btn-lavender w-100">Login</button>
        </form>
        <p class="text-center mt-3">Don't have an account? <a href="signup.jsp" class="text-decoration-none">Sign up</a></p>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
