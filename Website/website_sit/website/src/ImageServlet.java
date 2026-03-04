import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ImageServlet extends HttpServlet {

    private static final String BASE_DIR = "/home/itec4020grp4/website/private_images/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String img = request.getParameter("img");
        if (img == null || img.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing img parameter");
            return;
        }

        img = img.trim();

        // Block path traversal
        if (img.contains("..") || img.contains("/") || img.contains("\\") ) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image name");
            return;
        }

        File file = new File(BASE_DIR + img);
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Content type (png is your case)
        if (img.toLowerCase().endsWith(".png")) response.setContentType("image/png");
        else if (img.toLowerCase().endsWith(".jpg") || img.toLowerCase().endsWith(".jpeg")) response.setContentType("image/jpeg");
        else if (img.toLowerCase().endsWith(".gif")) response.setContentType("image/gif");
        else response.setContentType("application/octet-stream");

        // No-cache (helps prevent local caching)
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Stream file
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}
