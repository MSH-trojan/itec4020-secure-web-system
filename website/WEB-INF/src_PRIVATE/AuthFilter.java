import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/*
    AuthFilter.java

    This filter intercepts incoming HTTP requests before they reach the servlets.
    Its purpose is to enforce authentication by checking whether the user
    has a valid session.

    If the user is not logged in, the request is redirected to the login page.
    If the user is authenticated, the request is allowed to continue.

    The filter is applied to all URLs (configured in web.xml with /*).
*/

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        /*
            Cast the generic ServletRequest and ServletResponse
            objects into HTTP-specific request/response objects.
        */
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        /*
            Get the full request URI.
            Example:
            /itec4020grp4/aboutme
        */
        String path = req.getRequestURI();

        /*
            Get the context path of the web application.
            Example:
            /itec4020grp4
        */
        String ctx = req.getContextPath();

        /*
            Allow access to public resources without authentication.

            These include:
            - login page
            - login servlet
            - static resources (CSS, JS, images)

            Without this exception, the filter would block everything,
            including the login page itself.
        */
        boolean allowed =
                path.equals(ctx + "/") ||
                path.equals(ctx + "/index.jsp") ||
                path.equals(ctx + "/login") ||       // LoginServlet
                path.endsWith(".css") ||
                path.endsWith(".js")  ||
                path.endsWith(".png") ||
                path.endsWith(".jpg") ||
                path.endsWith(".jpeg")||
                path.endsWith(".gif");

        /*
            If the requested resource is allowed publicly,
            pass the request directly to the next component
            (servlet or resource).
        */
        if (allowed) {
            chain.doFilter(request, response);
            return;
        }

        /*
            Get the current session without creating a new one.
        */
        HttpSession session = req.getSession(false);

        /*
            Check whether the user is logged in.
            The login servlet stores the user identity
            in the session attribute "userId".
        */
        boolean loggedIn = (session != null && session.getAttribute("userId") != null);

        /*
            If the user is not authenticated,
            redirect them to the login page.
        */
        if (!loggedIn) {
            res.sendRedirect(ctx + "/index.jsp");
            return;
        }

        /*
            If authentication is valid,
            allow the request to continue normally.
        */
        chain.doFilter(request, response);
    }
}