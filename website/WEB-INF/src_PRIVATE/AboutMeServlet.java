import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ImageHelper;

/*
    AboutMeServlet.java

    This servlet generates the protected "About Me" page of the secure website.
    It is only accessible to authenticated users who have a valid session.

    Main responsibilities:
    - Check whether the user is logged in
    - Prevent unauthorized access
    - Disable browser caching of protected content
    - Add extra security-related HTTP headers
    - Display the protected image stored in WEB-INF
    - Output the About Me page dynamically in HTML
*/
public class AboutMeServlet extends HttpServlet {

    /*
        Session attribute name used to store the logged-in user identity.
        If this attribute does not exist in the session, the user is treated as unauthenticated.
    */
    private static final String USER_ATTR = "userId";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
            Get the existing session without creating a new one.
            If no session exists, request.getSession(false) returns null.
        */
        HttpSession session = request.getSession(false);

        /*
            Retrieve the user object from the session if the session exists.
            If there is no session, userObj becomes null.
        */
        Object userObj = (session == null) ? null : session.getAttribute(USER_ATTR);

        /*
            If the user is not logged in, redirect back to the login page.
            This prevents unauthorized users from accessing protected content.
        */
        if (userObj == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        /*
            Disable browser caching.

            This prevents the browser from storing the protected page,
            which helps stop users from seeing secure content again
            through the browser back button after logout.
        */
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        /*
            Add extra security headers.

            X-Content-Type-Options: nosniff
            Prevents MIME-type sniffing by the browser.

            X-Frame-Options: DENY
            Prevents the page from being embedded in a frame or iframe,
            reducing clickjacking risk.
        */
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        /*
            Set the response type as HTML with UTF-8 encoding.
        */
        response.setContentType("text/html;charset=UTF-8");

        /*
            PrintWriter is used to send dynamically generated HTML to the browser.
        */
        PrintWriter out = response.getWriter();

        /*
            Convert the session user object into a string for display.
        */
        String username = String.valueOf(userObj);

        /*
            Start building the HTML page.
        */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        /*
            Get the context path of the web application.
            This helps build correct paths regardless of deployment location.
        */
        String ctx = request.getContextPath();

        /*
            Link the anti-print stylesheet and anti-print JavaScript
            using the context path.
        */
        out.println("<link rel='stylesheet' href='" + ctx + "/css/preventPrint.css'>");
        out.println("<script src='" + ctx + "/js/disablePrint.js'></script>");

        /*
            Standard page metadata and linked CSS files.
        */
        out.println("<meta charset='UTF-8'>");
        out.println("<title>About Me</title>");
        out.println("<link rel=\"stylesheet\" href=\"css/reset.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/useDefaultCursor.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/preventPrint.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/credit.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/profile.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/aboutme.css\">");
        out.println("</head>");

        out.println("<body>");

        /*
            Title bar section showing page heading and a personalized welcome message.
            escapeHtml() is used to safely display the username.
        */
        out.println("<div class='titlebar'>");
        out.println("<div class='pagetitle'>Navigation bar</div>");
        out.println("<div class='welcome'>Welcome, " + escapeHtml(username) + "</div>");
        out.println("</div>");

        /*
            Navigation bar with links to protected pages and logout.
        */
        out.println("<div class='nav'>");
        out.println("<a href='aboutme'>About Me</a>");
        out.println("<a href='courses'>Courses</a>");
        out.println("<div class='logout'><a href='logout'>Logout</a></div>");
        out.println("</div>");

        /*
            Main page content container.
        */
        out.println("<div class='content'>");
        out.println("<div class='contentbox' style='width: 600px; height: 400px;'>");

        out.println("<div class='profile-box'>");

        /*
            Load the protected YorkU logo from the WEB-INF/images folder.

            The ImageHelper converts the image file into a Base64 string,
            which is then embedded directly into the HTML <img> tag.

            This avoids exposing the real file path to the browser.
        */
        String yorkuLogo = ImageHelper.encodeImage(this, "/WEB-INF/images/Yorku_Logo.png");

        /*
            If the image was loaded successfully, display it.
            Otherwise, show a fallback message.
        */
        if (yorkuLogo != null && !yorkuLogo.isEmpty()) {
            out.println("<img alt='YorkU Logo' style='max-width:180px;' src='data:image/png;base64," + yorkuLogo + "' />");
        } else {
            out.println("<p><i>YorkU logo not found.</i></p>");
        }

        /*
            Main About Me content shown on the page.
        */
        out.println("<h1>About Me</h1>");
        out.println("<h3>ITEC 4020 </h3>");
        out.println("<p>Internet Client-Server Systems</p>");
        out.println("</div>"); // end profile-box

        out.println("</div>"); // end contentbox
        out.println("</div>"); // end content

        /*
            Footer note describing the security protections applied to the website.
        */
        out.println("<div class='credit'>Security measures: Copying and printing for text and image are both disabled.</div>");

        /*
            JavaScript security scripts.
            These reduce client-side copying, image saving, dragging, and printing actions.
        */
        out.println("<script src='js/disableSelection.js'></script>");
        out.println("<script src='js/disableSavingImg.js'></script>");
        out.println("<script src='js/disableDragging.js'></script>");
        out.println("<script src='js/disablePrint.js'></script>");

        out.println("</body>");
        out.println("</html>");
    }

    /*
        escapeHtml()

        This helper method safely escapes special HTML characters
        before displaying user-controlled content such as usernames.

        This helps prevent HTML injection or broken page layout.
    */
    private static String escapeHtml(String s) {
        if (s == null) return "";

        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}