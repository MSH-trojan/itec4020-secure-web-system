import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SkillsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = String.valueOf(session.getAttribute("userId"));

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Skills</title>");
        out.println("<link rel=\"stylesheet\" href=\"css/reset.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/useDefaultCursor.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/preventPrint.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/credit.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/profile.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/skills.css\">");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='titlebar'>");
        out.println("<div class='pagetitle'>My ePortfolio</div>");
        out.println("<div class='welcome'>Welcome, " + escapeHtml(username) + "</div>");
        out.println("</div>");

        out.println("<div class='nav'>");
        out.println("<a href='aboutme'>About Me</a>");
        out.println("<a href='skills'>Skills</a>");
        out.println("<a href='contact'>Contact</a>");
        out.println("<div class='logout'><a href='logout'>Logout</a></div>");
        out.println("</div>");

        out.println("<div class='content'>");
        out.println("<div class='contentbox' style='width: 600px; height: 500px;'>");

        out.println("<div class=\"skill-section\">");
        out.println("<div class=\"skill-section-head\"><h2>Skills</h2></div>");
        out.println("<table class=\"table\">");
        out.println("<tr><th>Language</th><th>Experience</th></tr>");
        out.println("<tr><td>Java</td><td>3 years</td></tr>");
        out.println("<tr><td>HTML</td><td>3 years</td></tr>");
        out.println("<tr><td>CSS</td><td>2 years</td></tr>");
        out.println("<tr><td>JavaScript</td><td>2 years</td></tr>");
        out.println("<tr><td>Visual Basic</td><td>5 years</td></tr>");
        out.println("<tr><td>Python</td><td>2 months</td></tr>");
        out.println("</table>");

        out.println("<div class=\"skill-par2\">");
        out.println("<p class=\"paragraph\">I have experience in a few programming languages, including Java, HTML, CSS, JavaScript, Visual Basic, and Python. The table above shows how long I have been working with each language. I learned Visual Basic and Python in high school, and Java, HTML, CSS, and JavaScript at university.</p>");
        out.println("</div>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='credit'>Copying and Printing is disabled for this page</div>");

        out.println("<script src='js/disableSelection.js'></script>");
        out.println("<script src='js/disableSavingImg.js'></script>");
        out.println("<script src='js/disableDragging.js'></script>");

        out.println("</body>");
        out.println("</html>");
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
