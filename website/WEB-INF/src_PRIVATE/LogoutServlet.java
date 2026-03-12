import java.io.IOException;
import javax.servlet.http.*;

/*
    LogoutServlet.java

    This servlet handles the logout functionality of the secure website.
    It terminates the user session and redirects the user back to the login page.

    Once the session is invalidated, all session attributes (including userId)
    are removed, preventing access to protected resources.
*/

public class LogoutServlet extends HttpServlet {

    /*
        Handles GET requests when the user clicks the logout link.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*
            Retrieve the current session without creating a new one.
        */
        HttpSession session = request.getSession(false);

        /*
            If a session exists, invalidate it.
            This removes all stored session attributes and effectively logs the user out.
        */
        if (session != null) {
            session.invalidate();
        }

        /*
            Apply cache prevention headers to ensure that previously viewed
            protected pages cannot be accessed through the browser's back button.
        */
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        /*
            Redirect the user back to the login page.
        */
        response.sendRedirect("index.jsp");
    }

    /*
        Handle POST requests by redirecting them to the same logout process.
        This ensures logout works consistently regardless of HTTP method.
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}