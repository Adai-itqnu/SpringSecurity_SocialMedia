<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.login-container {
	background: white;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	text-align: center;
	width: 350px;
}

h2 {
	margin-bottom: 20px;
	color: #333;
}

.input-group {
	margin-bottom: 15px;
	text-align: left;
}

.input-group label {
	display: block;
	margin-bottom: 5px;
	color: #555;
}

.input-group input {
	width: 94%;
	padding: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
	font-size: 16px;
}
.signup-link {
    display: block;
    margin-top: 20px; /* Khoảng cách đủ thoáng */
    color: #28a745;
    text-decoration: none;
    font-weight: bold;
}

.signup-link:hover {
    text-decoration: underline;
}

.btn-login {
	background-color: #007bff;
	color: white;
	border: none;
	padding: 10px 15px;
	width: 100%;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
}

.btn-login:hover {
	background-color: #0056b3;
}

.error-message {
	color: red;
	margin-top: 10px;
}
</style>

<script>
        async function login(event) {
            event.preventDefault(); // Ngăn chặn hành động submit mặc định
            
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            const response = await fetch("/simple-social-network/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username: username, password: password })
            });

            if (response.ok) {
                window.location.href = "/simple-social-network/home";
            } else {
                alert("Đăng nhập thất bại. Vui lòng kiểm tra lại tài khoản.");
            }
        }
    </script>
</head>
<body>
	<div class="login-container">
		<h2>Login</h2>
		<form onsubmit="login(event)">
			<div class="input-group">
				<label for="username">Username:</label> <input type="text"
					id="username" name="username" required>
			</div>
			<div class="input-group">
				<label for="password">Password:</label> <input type="password"
					id="password" name="password" required>
			</div>
			<button type="submit" class="btn-login">Login</button>
		</form>
		
		<a class="signup-link" href="/simple-social-network/auth/signup">Don't have an account? Sign up here</a>
	</div>
</body>
</html>
