import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ContactServlet extends HttpServlet {

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
        out.println("<title>Contact</title>");
        out.println("<link rel=\"stylesheet\" href=\"css/reset.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/useDefaultCursor.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/preventPrint.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/credit.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/profile.css\">");
        out.println("<link rel=\"stylesheet\" href=\"css/contact.css\">");
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
        out.println("<div class='contentbox' style='width: 600px; height: 550px;'>");

        out.println("<div class=\"container\">");
        out.println("<form action=\"\" method=\"post\">");

        out.println("<label for=\"firstName\">First Name</label>");
        out.println("<input type=\"text\" id=\"firstName\" name=\"firstname\" placeholder=\"Enter your first name\">");

        out.println("<label for=\"lastName\">Last Name</label>");
        out.println("<input type=\"text\" id=\"lastName\" name=\"lastname\" placeholder=\"Enter your last name\">");

        out.println("<label for=\"emailAdd\">Email Address</label>");
        out.println("<input type=\"text\" id=\"emailAdd\" name=\"emailAdd\" placeholder=\"Enter your email address\">");

        out.println("<label for=\"phoneNumber\">Phone Number</label>");
        out.println("<input type=\"text\" id=\"phoneNumber\" name=\"phoneNumber\" placeholder=\"Enter your phone number\">");

        out.println("<label for=\"subject\">Subject</label>");
        out.println("<textarea id=\"subject\" name=\"subject\" placeholder=\"Write something..\" style=\"height:200px\"></textarea>");

        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
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
