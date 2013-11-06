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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.concordion.api.Resource;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.ext.logging.JavaUtilLogMessenger;
import org.concordion.ext.logging.LogMessageTooltipWriter;
import org.concordion.ext.logging.LogMessenger;
import org.concordion.ext.tooltip.TooltipRenderer;

/**
 * Annotates the Concordion HTML output with logging information captured using a LogMessenger.
 * <p>
 * <h4>Default Configuration</h4>
 * By default, this extension will capture all output from the root logger and disable console logging of the root logger.
 * <p>
 * <h4>Custom Configuration</h4>
 * The extension can be customised using the custom constructor. The logging can be restricted to named loggers,
 * and by logging levels. The output of logging to the console can also be enabled.
 * <p>
 * <h4>Custom Log Framework</h4>
 * An alternate LogMessenger implementation can be provided.  The default uses java.util.logging.
 * <p>
 *
 * Thanks to Trent Richardson for the <a href="http://trentrichardson.com/examples/csstooltips/">CSS Tooltip</a> implementation.
 */
public class LoggingTooltipExtension implements ConcordionExtension {

    private static final String TOOLTIP_CSS_SOURCE_PATH = "/org/concordion/ext/resource/tooltip.css";
    private static final Resource TOOLTIP_CSS_TARGET_RESOURCE = new Resource("/tooltip.css");

    private static final Resource BUBBLE_FILLER_IMAGE_RESOURCE = new Resource("/image/bubble_filler.gif");
    private static final String BUBBLE_FILLER_RESOURCE_PATH = "/org/concordion/ext/resource/bubble_filler.gif";
    private static final Resource BUBBLE_IMAGE_RESOURCE = new Resource("/image/bubble.gif");
    private static final String BUBBLE_RESOURCE_PATH = "/org/concordion/ext/resource/bubble.gif";
    private static final Resource INFO_IMAGE_RESOURCE = new Resource("/image/info16.png");
    private static final String INFO_RESOURCE_PATH = "/org/concordion/ext/resource/i16.png";

    private final LogMessenger logMessenger;

    /**
     * Default constructor that logs output from all java.util.loggers, with a {@link Level} of <code>INFO</code> or higher, and disables the console output of the root logger.
     */
    public LoggingTooltipExtension() {
        this("", Level.INFO, false);
    }

    /**
     * Implementation that permits a custom LogMessenger.
     * @param messenger
     */
    public LoggingTooltipExtension(LogMessenger messenger) {
        this.logMessenger = messenger;
    }

    /**
     * Custom constructor.
     * Uses a JavaUtilLogMessenger logger.
     *
     * @param loggerNames a comma separated list of the names of loggers whose output is to be shown in the Concordion output. An empty string indicates the root logger.
     * @param loggingLevel the logging {@link Level} for the handler that writes to the Concordion output. Log messages of this level and
     * higher will be output.  Note that the associated {@link Logger}s must also have an appropriate logging level set.
     * @param displayRootConsoleLogging <code>false</code> to remove console output for the root logger, <code>true</code> to show the console output
     */
    public LoggingTooltipExtension(String loggerNames, Level loggingLevel, boolean displayRootConsoleLogging) {
        this.logMessenger = new JavaUtilLogMessenger(loggerNames, loggingLevel, displayRootConsoleLogging);
    }

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        LogMessageTooltipWriter extension = new LogMessageTooltipWriter(new TooltipRenderer(INFO_IMAGE_RESOURCE), logMessenger);

        concordionExtender.withExecuteListener(extension).withAssertEqualsListener(extension).withAssertTrueListener(extension)
                .withAssertFalseListener(extension).withVerifyRowsListener(extension).withThrowableListener(extension);
        concordionExtender.withSpecificationProcessingListener(extension);
        concordionExtender.withLinkedCSS(TOOLTIP_CSS_SOURCE_PATH, TOOLTIP_CSS_TARGET_RESOURCE);
        concordionExtender.withResource(BUBBLE_RESOURCE_PATH, BUBBLE_IMAGE_RESOURCE);
        concordionExtender.withResource(BUBBLE_FILLER_RESOURCE_PATH, BUBBLE_FILLER_IMAGE_RESOURCE);
        concordionExtender.withResource(INFO_RESOURCE_PATH, INFO_IMAGE_RESOURCE);
    }

}
