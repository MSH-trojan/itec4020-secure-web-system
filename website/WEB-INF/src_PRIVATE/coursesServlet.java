import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
    coursesServlet.java

    This servlet generates the protected "Courses" page of the secure website.
    Only authenticated users with a valid session are allowed to access this page.

    Main responsibilities:
    - Verify that the user is logged in
    - Prevent browser caching of protected content
    - Apply security-related HTTP headers
    - Display a list of courses dynamically using HTML
*/
public class coursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
            Retrieve the current session without creating a new one.
            If no session exists, it returns null.
        */
        HttpSession session = request.getSession(false);

        /*
            If the user is not logged in (no session or missing userId),
            redirect them to the login page.
        */
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        /*
            Disable caching to prevent the browser from storing
            protected content. This reduces the risk of users
            viewing secure pages through the browser back button
            after logout.
        */
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        /*
            Additional security headers.

            X-Content-Type-Options: nosniff
            Prevents the browser from guessing the MIME type.

            X-Frame-Options: DENY
            Prevents the page from being embedded inside frames,
            protecting against clickjacking attacks.
        */
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        /*
            Set response content type to HTML with UTF-8 encoding.
        */
        response.setContentType("text/html;charset=UTF-8");

        /*
            PrintWriter is used to output HTML content to the browser.
        */
        PrintWriter out = response.getWriter();

        /*
            Retrieve the username from the session for display.
        */
        String username = String.valueOf(session.getAttribute("userId"));

        /*
            Begin constructing the HTML page.
        */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        /*
            Get the application context path.
            This ensures resource links remain correct regardless of deployment location.
        */
        String ctx = request.getContextPath();

        /*
            Include CSS and JavaScript related to print protection.
        */
        out.println("<link rel='stylesheet' href='" + ctx + "/css/preventPrint.css'>");
        out.println("<script src='" + ctx + "/js/disablePrint.js'></script>");

        /*
            Standard page metadata and linked stylesheets.
        */
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Course</title>");
        out.println("<link rel=\"stylesheet\" href=\"css/reset.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/useDefaultCursor.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/preventPrint.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/credit.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/profile.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/courses.css\">");
        out.println("</head>");

        out.println("<body>");

        /*
            Page title bar displaying navigation title and user greeting.
            The username is sanitized before display.
        */
        out.println("<div class='titlebar'>");
        out.println("<div class='pagetitle'>Navigation bar</div>");
        out.println("<div class='welcome'>Welcome, " + escapeHtml(username) + "</div>");
        out.println("</div>");

        /*
            Navigation menu allowing users to move between pages.
        */
        out.println("<div class='nav'>");
        out.println("<a href='aboutme'>About Me</a>");
        out.println("<a href='courses'>Courses</a>");
        out.println("<div class='logout'><a href='logout'>Logout</a></div>");
        out.println("</div>");

        /*
            Main content section for the course table.
        */
        out.println("<div class='content'>");
        out.println("<div class='contentbox' style='width: 600px; height: 500px;'>");

        /*
            Course table listing several IT courses and their semesters.
        */
        out.println("<div class=\"course-section\">");
        out.println("<div class=\"course-section-head\"><h2>Courses</h2></div>");
        out.println("<table class=\"table\">");
        out.println("<tr><th>Title</th><th>Semester</th></tr>");
        out.println("<tr><td>ITEC 1000</td><td>1st Semester</td></tr>");
        out.println("<tr><td>ITEC 3030</td><td>3rd Semester</td></tr>");
        out.println("<tr><td>ITEC 3040</td><td>3rd Semester</td></tr>");
        out.println("<tr><td>ITEC 2620</td><td>2nd Semester</td></tr>");
        out.println("</table>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        /*
            Footer message describing applied security protections.
        */
        out.println("<div class='credit'>Security measures: Copying and printing for text is disabled.</div>");

        /*
            JavaScript scripts to reduce copying, saving, and dragging actions.
        */
        out.println("<script src='js/disableSelection.js'></script>");
        out.println("<script src='js/disableSavingImg.js'></script>");
        out.println("<script src='js/disableDragging.js'></script>");

        out.println("</body>");
        out.println("</html>");
    }

    /*
        escapeHtml()

        This helper function sanitizes text before displaying it in HTML.
        It replaces special characters with their safe HTML equivalents
        to prevent page layout issues or HTML injection.
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