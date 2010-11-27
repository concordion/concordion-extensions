package org.concordion.ext.screenshot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.concordion.ext.ScreenshotTaker;

/**
 * Takes screenshots using {@link java.awt.Robot}.
 */
public class RobotScreenshotTaker implements ScreenshotTaker {

    private static final String FILE_TYPE = "jpg";

    @Override
    public int writeScreenshotTo(OutputStream outputStream) throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        BufferedImage image = getImage(screenSize);
        ImageIO.write(image, FILE_TYPE, outputStream);
        return (int) screenSize.getWidth();
    }

    public BufferedImage getImage(Dimension size) {
        try {
            return new Robot().createScreenCapture(new Rectangle(0, 0, (int) size.getWidth(), (int) size.getHeight()));
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getFileExtension() {
        return FILE_TYPE;
    }
}
