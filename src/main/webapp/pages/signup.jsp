<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Hantez Food Blog</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f3f4f9;
            position: relative;
        }
        .signup-container {
            background-color: #e6e6fa;
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
        #preview {
            width: 100px;
            height: 100px;
            object-fit: cover;
            border-radius: 50%;
            display: none;
            margin-bottom: 15px;
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
    <div class="signup-container col-md-4">
        <!-- Top Logo -->
        <img src="/Hantez/resources/images/logo.png" alt="Hantez Logo Top" class="logo-top">

        <h3 class="text-center mb-4">Create an Account</h3>
        <form method="POST" action="../signupServlet" enctype="multipart/form-data">
            <div class="mb-3 text-center">
                <img id="preview" src="#" alt="Profile Preview">
            </div>
            <div class="mb-3">
                <label for="profilePic" class="form-label">Profile Picture</label>
                <input type="file" class="form-control" id="profilePic" name="profilePic" accept="image/*" required>
            </div>
            <div class="mb-3">
                <label for="name" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Enter full name" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
            </div>
            <div class="mb-3">
                <label for="confirm_password" class="form-label">Confirm Password</label>
                <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Confirm password" required>
            </div>
            <button type="submit" class="btn btn-lavender w-100">Sign Up</button>
        </form>
        <p class="text-center mt-3">Already have an account? <a href="login.jsp" class="text-decoration-none">Login</a></p>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- Image Preview Script -->
<script>
    const profileInput = document.getElementById('profilePic');
    const previewImg = document.getElementById('preview');

    profileInput.addEventListener('change', function() {
        const file = this.files[0];
        if (file) {
            previewImg.style.display = "block";
            const reader = new FileReader();
            reader.onload = function(e) {
                previewImg.setAttribute('src', e.target.result);
            }
            reader.readAsDataURL(file);
        } else {
            previewImg.style.display = "none";
        }
    });
</script>

</body>
</html>
