package nz.co.twoten.concordion;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Takes shots of the system under test. 
 */
public interface ScreenshotTaker {

    /**
     * Take a shot and write to the given output stream.  
     * 
     *  @return the width of the screenshot
     *  @throws IOException if an I/O error occurs writing the screenshot to the stream
     *  @throws ScreenshotUnavailableException if unable to take a screenshot 
     */
    int writeScreenshotTo(OutputStream outputStream) throws IOException;

    /**
     * Returns the filename extension that should be used for images taken by this object.
     */
    String getFileExtension();
}