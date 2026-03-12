import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/*
    ImageServlet.java

    This servlet securely serves image files stored inside the protected
    WEB-INF/images directory.

    The browser cannot directly access files inside WEB-INF, so this servlet
    acts as a controlled gateway that validates requests before returning
    image content.

    Security features implemented:
    - Authentication check (user must be logged in)
    - Path traversal protection
    - Cache prevention
    - Controlled MIME type handling
*/

public class ImageServlet extends HttpServlet {

    /*
        Session attribute used to identify logged-in users.
        If this attribute does not exist, the user is considered unauthenticated.
    */
    private static final String USER_ATTR = "userId";

    /*
        Directory containing protected images.
        Because this directory is inside WEB-INF, it cannot be accessed directly
        from the browser.
    */
    private static final String IMG_DIR = "/WEB-INF/images/"; // protected

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
            Verify that the user is authenticated before allowing access
            to protected images.
        */
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(USER_ATTR) == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        /*
            Retrieve the requested image name from query parameters.

            The servlet accepts either:
            ?img=filename
            or
            ?name=filename
        */
        String img = request.getParameter("img");

        if (img == null || img.trim().isEmpty()) {
            img = request.getParameter("name");
        }

        /*
            If no image parameter is provided, return a bad request error.
        */
        if (img == null || img.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing img parameter");
            return;
        }

        img = img.trim();

        /*
            Path traversal protection.

            This prevents attackers from attempting to access files outside
            the intended directory using paths such as:
            ../../etc/passwd
        */
        if (img.contains("..") || img.contains("/") || img.contains("\\")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image name");
            return;
        }

        /*
            Disable caching to prevent browsers from storing protected images.
        */
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        /*
            Determine the appropriate MIME type based on file extension.
            This ensures the browser interprets the image correctly.
        */
        String lower = img.toLowerCase();

        if (lower.endsWith(".png"))
            response.setContentType("image/png");
        else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg"))
            response.setContentType("image/jpeg");
        else if (lower.endsWith(".gif"))
            response.setContentType("image/gif");
        else
            response.setContentType("application/octet-stream");

        /*
            Construct the full path of the image inside the protected directory.
        */
        String resourcePath = IMG_DIR + img;

        /*
            Load the image file from the server using ServletContext.
            This allows access to resources inside WEB-INF.
        */
        InputStream in = getServletContext().getResourceAsStream(resourcePath);

        /*
            If the image file does not exist, return a 404 error.
        */
        if (in == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /*
            Output stream used to send binary image data to the client.
        */
        OutputStream out = response.getOutputStream();

        try {

            /*
                Buffer used to efficiently transfer image data.
            */
            byte[] buffer = new byte[4096];
            int len;

            /*
                Read the image file and write it to the response output stream.
            */
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }

        } finally {

            /*
                Ensure the input stream is closed to release resources.
            */
            try {
                in.close();
            } catch (Exception ignored) {}
        }
    }
}