package util;

/*
    ImageHelper.java

    This utility class provides a helper method for loading an image
    from the protected WEB-INF directory and converting it into a
    Base64-encoded string.

    The encoded image can then be embedded directly inside HTML
    using a data URI (data:image/...;base64,...). This prevents
    direct browser access to image files and improves security.
*/

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

public class ImageHelper {

    /*
        Private constructor prevents this class from being instantiated.

        Since this class only contains static helper methods,
        there is no reason to create objects of ImageHelper.
    */
    private ImageHelper() {}

    /*
        encodeImage()

        This method reads an image file from the server and converts it
        into a Base64 string.

        Parameters:
        servlet → the servlet requesting the image
        path → the path of the image inside the web application

        Returns:
        A Base64 encoded string representing the image.
    */
    public static String encodeImage(HttpServlet servlet, String path) throws IOException {

        /*
            Get the ServletContext from the servlet.
            This allows access to application resources such as files.
        */
        ServletContext context = servlet.getServletContext();

        /*
            Open the image file as an InputStream.
            getResourceAsStream allows access to files inside WEB-INF,
            which cannot be accessed directly by the browser.
        */
        InputStream inputStream = context.getResourceAsStream(path);

        /*
            If the file does not exist, return an empty string
            to prevent application errors.
        */
        if (inputStream == null) {
            return ""; // path not found
        }

        /*
            ByteArrayOutputStream is used to store the binary image data
            while reading the file.
        */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        /*
            Buffer used to read the file in chunks for efficiency.
        */
        byte[] buffer = new byte[4096];
        int bytesRead;

        /*
            Read the image file chunk by chunk and store it in memory.
        */
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        /*
            Close the input stream after reading is complete.
        */
        inputStream.close();

        /*
            Convert the image byte data into a Base64 string.
            This allows the image to be embedded directly in HTML
            using the "data:image/png;base64,..." format.
        */
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}