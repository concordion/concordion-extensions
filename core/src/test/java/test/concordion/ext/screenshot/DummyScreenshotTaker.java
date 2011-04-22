/*
 * Copyright (c) 2010 Two Ten Consulting Limited, New Zealand 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.concordion.ext.screenshot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.concordion.ext.ScreenshotTaker;

/**
 * Returns the contents of a static file, so that screenshots of my system don't inadvertently make it onto the net :-)
 */
public final class DummyScreenshotTaker implements ScreenshotTaker {
    private static final String IMAGE_PATH = "image/details.jpg";
    static final BufferedImage IMAGE;

    static {
        try {
            InputStream imageStream = DummyScreenshotFactory.class.getClassLoader().getResourceAsStream(IMAGE_PATH);
            if (imageStream == null) {
                throw new RuntimeException(String.format("Unable to find IMAGE '%s' on classpath", IMAGE_PATH));
            }
            IMAGE = ImageIO.read(imageStream);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create DummyScreenshotFactory", e);
        }
    }
    
    @Override
    public int writeScreenshotTo(OutputStream outputStream) throws IOException {
        ImageIO.write(IMAGE, getFileExtension(), outputStream);
        return IMAGE.getWidth();
    }

    @Override
    public String getFileExtension() {
        return "jpg";
    }
}