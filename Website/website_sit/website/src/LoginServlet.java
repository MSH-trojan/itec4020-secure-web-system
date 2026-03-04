import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    private static final String FAKE_USER = "Group4";
    private static final String FAKE_PASS = "Group4"; // keep same if you want
    private static final String USER_ATTR = "userId";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        username = (username == null) ? "" : username.trim();
        password = (password == null) ? "" : password.trim();

        boolean ok = FAKE_USER.equals(username) && FAKE_PASS.equals(password);

        if (ok) {
            session.setAttribute(USER_ATTR, FAKE_USER);
            response.sendRedirect("aboutme");
        } else {
            session.removeAttribute(USER_ATTR);
            response.sendRedirect("index.jsp?error=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("index.jsp");
    }
}
