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
package org.concordion.ext;

import java.awt.Robot;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.ext.screenshot.ScreenshotEmbedder;

/**
 * Adds screenshots to the Concordion output, typically when failures or exceptions occur. The screenshot is displayed when you hover over the relevant element.
 * Clicking on the element will open the image in the current browser window.
 * <p>
 * It can also be used as a command, to explicitly include screenshots in the output. 
 * <p>
 * By default, the screenshot will be of the full visible screen.  This can be overridden using a custom {@link ScreenshotTaker}.
 */
public class ScreenshotExtension implements ConcordionExtension {

    public static final String COMMAND_NAME = "screenshot";
    public static final String EXTENSION_NAMESPACE = "urn:concordion-extensions:2010";

    private ScreenshotEmbedder extension = new ScreenshotEmbedder();
    
    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withSpecificationProcessingListener(extension);
        concordionExtender.withBuildListener(extension);
        concordionExtender.withAssertEqualsListener(extension);
        concordionExtender.withAssertTrueListener(extension);
        concordionExtender.withAssertFalseListener(extension);
        concordionExtender.withThrowableListener(extension);
        concordionExtender.withCommand(EXTENSION_NAMESPACE, COMMAND_NAME, extension);
        concordionExtender.withEmbeddedCSS(extension.getCSS());
        concordionExtender.withEmbeddedJavaScript(extension.getJavaScript());
    }

    /**
     * Set a custom screenshot taker. If not set, the extension will default to using {@link Robot}
     * which will take a shot of the full visible screen.
     * 
     * @param screenshotTaker
     */
    public ScreenshotExtension setScreenshotTaker(ScreenshotTaker screenshotTaker) {
        extension.setScreenshotTaker(screenshotTaker);
        return this;
    }

    /**
     * Sets the maximum width that will be used for display of the images in the output.
     */
    public ScreenshotExtension setMaxWidth(int maxWidth) {
        extension.setMaxWidth(maxWidth);
        return this;
    }

    /** 
     * Sets whether screenshots will be embedded in the output when assertions fail. Defaults to <b><code>true</code></b>.
     * @param takeShot <code>true</code> to take screenshots on assertion failures, <code>false</code> to not take screenshots.
     */
    public ScreenshotExtension setScreenshotOnAssertionFailure(boolean takeShot) {
        extension.setScreenshotOnAssertionFailure(takeShot);
        return this;
    }

    /** 
     * Sets whether screenshots will be embedded in the output when assertions pass. Defaults to <b><code>false</code></b>.
     * @param takeShot <code>true</code> to take screenshots on assertion success, <code>false</code> to not take screenshots.
     */
    public ScreenshotExtension setScreenshotOnAssertionSuccess(boolean takeShot) {
        extension.setScreenshotOnAssertionSuccess(takeShot);
        return this;
    }

    /** 
     * Sets whether screenshots will be embedded in the output when uncaught Throwables occur in the test. Defaults to <b><code>true</code></b>.
     * @param takeShot <code>true</code> to take screenshots on uncaught Throwables, <code>false</code> to not take screenshots.
     */
    public ScreenshotExtension setScreenshotOnThrowable(boolean takeShot) {
        extension.setScreenshotOnThrowable(takeShot);
        return this;
    }
}
