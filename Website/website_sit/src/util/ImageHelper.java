package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class ImageHelper {

    private ImageHelper() {}

    public static String encodeImage(HttpServlet servlet, String path) throws IOException {
        ServletContext context = servlet.getServletContext();
        InputStream inputStream = context.getResourceAsStream(path);

        if (inputStream == null) {
            return ""; // path not found
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();

        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}
