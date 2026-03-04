import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.ImageHelper;

public class AboutMeServlet extends HttpServlet {

    private static final String USER_ATTR = "userId";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Object userObj = (session == null) ? null : session.getAttribute(USER_ATTR);

        if (userObj == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Disable caching (prevents back-button showing secured content after logout)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Optional: extra hardening headers (fine for A2)
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = String.valueOf(userObj);
	out.println("<div style='color:red;font-weight:bold;'>BUILD MARKER: 2026-03-04-0001</div>");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
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

        // title bar
        out.println("<div class='titlebar'>");
        out.println("<div class='pagetitle'>My ePortfolio</div>");
        out.println("<div class='welcome'>Welcome, " + escapeHtml(username) + "</div>");
        out.println("</div>");

        // nav
        out.println("<div class='nav'>");
        out.println("<a href='aboutme'>About Me</a>");
        out.println("<a href='skills'>Skills</a>");
        out.println("<a href='contact'>Contact</a>");
        out.println("<div class='logout'><a href='logout'>Logout</a></div>");
        out.println("</div>");

        // content
        out.println("<div class='content'>");
        out.println("<div class='contentbox' style='width: 600px; height: 400px;'>");

        out.println("<div class='profile-box'>");

        // Protected image from WEB-INF
	out.println("<h3 style='color:blue'>AboutMeServlet is running (build test)</h3>");
	out.println("<hr>");
	out.println("<p><b>DEBUG realPath(/):</b> " + getServletContext().getRealPath("/") + "</p>");
	out.println("<p><b>DEBUG realPath(/WEB-INF/images/Yorku_Logo.png):</b> " 
	    + getServletContext().getRealPath("/WEB-INF/images/Yorku_Logo.png") + "</p>");
	out.println("<p><b>DEBUG resource(/WEB-INF/images/Yorku_Logo.png):</b> " 
	    + getServletContext().getResource("/WEB-INF/images/Yorku_Logo.png") + "</p>");
	out.println("<hr>");

	String yorkuLogo = ImageHelper.encodeImage(this, "/WEB-INF/images/Yorku_Logo.png");
	out.println("<p>DEBUG Yorku base64 length: " + (yorkuLogo == null ? "null" : yorkuLogo.length()) + "</p>");
		if (yorkuLogo != null && !yorkuLogo.isEmpty()) {
    			out.println("<img alt='YorkU Logo' style='max-width:180px;' src='data:image/png;base64," + yorkuLogo + "' />");
		} else {
    			out.println("<p><i>YorkU logo not found.</i></p>");
		}
        out.println("<h1>Tomcat Admin</h1>");
        out.println("<h2>Student</h2>");
        out.println("<p>B.A. Information Technology</p>");
        out.println("</div>"); // profile-box

        out.println("</div>"); // contentbox
        out.println("</div>"); // content

        // credit
        out.println("<div class='credit'>Copying and Printing is disabled for this page</div>");

        // scripts must be inside body
        out.println("<script src='js/disableSelection.js'></script>");
        out.println("<script src='js/disableSavingImg.js'></script>");
        out.println("<script src='js/disableDragging.js'></script>");

        out.println("</body>");
        out.println("</html>");
    }

    // Simple HTML escape (prevents breaking HTML if username contains special chars)
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
