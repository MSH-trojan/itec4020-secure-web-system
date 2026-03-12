<%@ page contentType="text/html;charset=UTF-8" %>

<%-- 
    index.jsp
    This is the login page of the secure web system.
    Users must enter their credentials here before accessing protected pages.
--%>

<!DOCTYPE html>
<html>

<head>

    <%-- Character encoding for proper text display --%>
    <meta charset="UTF-8" />

    <%-- CSS files for page styling --%>
    <link rel="stylesheet" href="css/reset.css" />   <%-- resets browser default styles --%>
    <link rel="stylesheet" href="css/login.css" />   <%-- main login page design --%>
    <link rel="stylesheet" href="css/credit.css" />  <%-- footer credit styling --%>

    <title>Login Page</title>
</head>

<body>

    <%-- Visual overlay effect used in the login design --%>
    <div class="overlay"></div>

    <div class="page-wrapper">

        <%-- Main welcome message displayed on login page --%>
        <h1 class="welcome-header">Welcome to Group 4 Project</h1>
        <h1 class="welcome-header">a secure website</h1>

        <%-- Container holding the login form --%>
        <div class="container">            

            <%-- 
                Login form
                Sends POST request to LoginServlet mapped at /login
                request.getContextPath() ensures correct path regardless of deployment location
            --%>
            <form method="post" action="<%= request.getContextPath() %>/login">

                <%-- Username input field --%>
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required />

                <%-- Password input field (masked) --%>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required />

                <%-- Login button --%>
                <input type="submit" value="Login" />
            </form>
        </div>

        <%-- Footer credit for the project authors --%>
        <div class="credit">Created by Group 4</div>

    </div>

    <%-- 
        JavaScript security scripts
        These scripts attempt to prevent copying, dragging, saving images,
        and printing page content to protect the website's resources.
    --%>
    <script src="js/disableSelection.js"></script>
    <script src="js/disableSavingImg.js"></script>
    <script src="js/disableDragging.js"></script>
    <script src="js/disablePrint.js"></script>

</body>
</html>