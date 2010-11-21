package nz.co.twoten.concordion.screenshot;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import nz.co.twoten.concordion.ScreenshotTaker;

/**
 * Takes screenshots using {@link java.awt.Robot}.
 * <p> 
 * This code was derived from Mark Derricutt's <a href="http://github.com/talios/concordion-examples/blob/master/src/test/java/com/talios/ScreenshotCommand.java">ScreenshotCommand</a>.
 */
public class RobotScreenshotTaker implements ScreenshotTaker {

    private static final String FILE_TYPE = "jpg";

    @Override
    public int writeScreenshotTo(OutputStream outputStream) throws IOException {
        Dimension screenSize = getScreenSize();
        BufferedImage image = getImage(screenSize);
        ImageIO.write(image, FILE_TYPE, outputStream);
        return (int) screenSize.getWidth();
    }

    public BufferedImage getImage(Dimension dim) {
        Robot rbt;
        try {
            rbt = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        BufferedImage background = rbt.createScreenCapture(new Rectangle(0, 0, (int) dim.getWidth(), (int) dim.getHeight()));
        return background;
    }

    private Dimension getScreenSize() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        return dim;
    }

    @Override
    public String getFileExtension() {
        return FILE_TYPE;
    }
}
