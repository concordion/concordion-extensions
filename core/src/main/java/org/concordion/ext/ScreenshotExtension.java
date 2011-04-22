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
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.screenshot.ScreenshotEmbedder;

/**
 * Adds screenshots to the Concordion output, typically when failures or exceptions occur. The screenshot is displayed when you hover over the relevant element.
 * Clicking on the element will open the image in the current browser window.
 * <p>
 * It can also be used as a command, to explicitly include screenshots in the output. 
 * <h4>Default Configuration</h4>
 * By default, this extension will add screenshots to the output whenever an assertion fails, or an uncaught Throwable occurs in the test.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "org.concordion.ext.ScreenshotExtension");
 * </pre>
 * <h4>Custom Configuration</h4>
 * The extension can be customised using an {@link ConcordionExtensionFactory} to create and modify the extension.  
 * <p>
 * An example factory:
 * <pre>
 * package carbon;
 * 
 * import org.concordion.ext.ScreenshotExtension;
 * import org.concordion.ext.ScreenshotTaker;
 * import org.concordion.ext.selenium.SeleniumScreenshotTaker;
 * 
 * import org.concordion.api.extension.ConcordionExtension;
 * import org.concordion.api.extension.ConcordionExtensionFactory;
 * 
 * public class ScreenshotExtensionFactory implements ConcordionExtensionFactory {
 * 
 *     private static ScreenshotExtension extension;
 *     private static ScreenshotTaker screenshotTaker = new SeleniumScreenshotTaker();
 * 
 *     &#064;Override
 *     public ConcordionExtension createExtension() {
 *         extension = new ScreenshotExtension();
 *         extension.setScreenshotTaker(screenshotTaker);
 *         extension.setMaxWidth(400);
 *         return extension;
 *     }
 * 
 *     public static void setScreenshotOnAssertSuccess() {
 *         extension.setScreenshotOnAssertionSuccess(true);
 *     }
 * 
 *     public static void setScreenshotOnThrowable(boolean takeShot) {
 *         extension.setScreenshotOnThrowable(takeShot);
 *     }
 * 
 *     public static ScreenshotTaker getScreenshotTaker() {
 *         return screenshotTaker;
 *     }
 * }
 * </pre>
 * To install this example extension factory:
 * <pre>
 *       System.setProperty("concordion.extensions", "carbon.ScreenshotExtensionFactory");
 * </pre>
 * 
 * <h4>Using as a Command</h4>
 * To explicitly include a screenshot in your output, add an attribute named <code>screenshot</code> using the namespace
 * <code>"urn:concordion-extensions:2010"</code> to your Concordion HTML. For example: 
 * <pre>
 * &lt;html xmlns:concordion="http://www.concordion.org/2007/concordion" xmlns:ext="urn:concordion-extensions:2010"&gt;
 * ....
 * &lt;div ext:screenshot=""/&gt;
 * ...
 * </pre> 
 * By default, the screenshot is embedded in the output HTML. If you'd rather have it linked, set the value of the <code>screenshot</code> attribute to <code>linked</code>, for example:
 * <pre>
 * &lt;p&gt;See &lt;span ext:screenshot="linked" style="text-decoration: underline;"&gt;this screen&lt;/span&gt;&lt;/p&gt;
 * </pre>
 * 
 * <h4>The Screenshot Taker</h4>
 * By default, the screenshot will be of the full visible screen.  This can be overridden using a custom {@link ScreenshotTaker}.  For example, 
 * the SeleniumScreenshotTaker in the above examples ensures that only the browser window is captured, that the full browser page is captured and that it is 
 * captured regardless of whether the browser window is currently displayed.
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
    public void setScreenshotTaker(ScreenshotTaker screenshotTaker) {
        extension.setScreenshotTaker(screenshotTaker);
    }

    /**
     * Sets the maximum width that will be used for display of the images in the output.
     */
    public void setMaxWidth(int maxWidth) {
        extension.setMaxWidth(maxWidth);
    }

    /** 
     * Sets whether screenshots will be embedded in the output when assertions fail. Defaults to <b><code>true</code></b>.
     * @param takeShot <code>true</code> to take screenshots on assertion failures, <code>false</code> to not take screenshots.
     */
    public void setScreenshotOnAssertionFailure(boolean takeShot) {
        extension.setScreenshotOnAssertionFailure(takeShot);
    }

    /** 
     * Sets whether screenshots will be embedded in the output when assertions pass. Defaults to <b><code>false</code></b>.
     * @param takeShot <code>true</code> to take screenshots on assertion success, <code>false</code> to not take screenshots.
     */
    public void setScreenshotOnAssertionSuccess(boolean takeShot) {
        extension.setScreenshotOnAssertionSuccess(takeShot);
    }

    /** 
     * Sets whether screenshots will be embedded in the output when uncaught Throwables occur in the test. Defaults to <b><code>true</code></b>.
     * @param takeShot <code>true</code> to take screenshots on uncaught Throwables, <code>false</code> to not take screenshots.
     */
    public void setScreenshotOnThrowable(boolean takeShot) {
        extension.setScreenshotOnThrowable(takeShot);
    }
}
