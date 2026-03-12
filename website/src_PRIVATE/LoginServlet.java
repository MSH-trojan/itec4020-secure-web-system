import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/*
    LoginServlet.java

    This servlet handles user authentication for the secure website.
    It receives login credentials submitted from the login form (index.jsp)
    and verifies them against predefined values.

    If authentication succeeds:
        - A session attribute is created to identify the logged-in user.
        - The user is redirected to the protected "About Me" page.

    If authentication fails:
        - Any existing login session attribute is removed.
        - The user is redirected back to the login page with an error indicator.
*/

public class LoginServlet extends HttpServlet {

    /*
        Predefined username and password used for demonstration purposes.

        In a real-world system, credentials would normally be stored
        in a secure database with hashed passwords.
    */
    private static final String FAKE_USER = "Group4";
    private static final String FAKE_PASS = "Group4";

    /*
        Name of the session attribute used to identify authenticated users.
    */
    private static final String USER_ATTR = "userId";

    /*
        Handles POST requests generated when the login form is submitted.
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
            Create or retrieve the current HTTP session.
            If no session exists, a new one will be created.
        */
        HttpSession session = request.getSession();

        /*
            Retrieve username and password from the submitted login form.
        */
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        /*
            Normalize input values to avoid null pointer errors.
            Also removes leading and trailing spaces.
        */
        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        /*
            Validate credentials by comparing them with the predefined values.
        */
        boolean ok = FAKE_USER.equals(username) && FAKE_PASS.equals(password);

        /*
            If credentials are correct, mark the user as authenticated.
        */
        if (ok) {

            /*
                Store the authenticated user identifier in the session.
                Other servlets will check this value to allow access.
            */
            session.setAttribute(USER_ATTR, FAKE_USER);

            /*
                Redirect the user to the protected About Me page.
            */
            response.sendRedirect(request.getContextPath() + "/aboutme");

        } else {

            /*
                Remove any existing login attribute to ensure the user
                is not considered authenticated.
            */
            session.removeAttribute(USER_ATTR);

            /*
                Redirect the user back to the login page with an error flag.
                The login page can detect this parameter and display an error message.
            */
            response.sendRedirect(request.getContextPath() + "/index.jsp?error=1");
        }
    }

    /*
        If someone tries to access the servlet directly using a GET request,
        redirect them to the login page instead.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.sendRedirect("index.jsp");
    }
}