<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="css/reset.css" />
    <link rel="stylesheet" href="css/login.css" />
    <link rel="stylesheet" href="css/credit.css" />
    <title>Login Page</title>
</head>

<body>

    <div class="overlay"></div>

    <div class="page-wrapper">
        <h1 class="welcome-header">Welcome to Group 4 Project</h1>

        <div class="container">
            <form action="login" method="post">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required />

                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />

                <input type="submit" value="Login" />
            </form>
        </div>

        <div class="credit">Created by Group 4</div>
    </div>

</body>
</html>
