package nz.co.twoten.concordion;

import java.util.logging.Level;
import java.util.logging.Logger;

import nz.co.twoten.concordion.annotate.TooltipRenderingListener;

import org.concordion.api.Resource;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;

/**
 * Annotates the Concordion HTML output with logging information captured using java.util.logging. See <a href="http://tutansblog.blogspot.com/2010/09/whats-happening-in-my-acceptance-tests.html">this blog entry</a> for details and screenshots.
 * <p>
 * <h4>Default Configuration</h4>
 * By default, this extension will capture all output from the root logger and disable console logging of the root logger.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "nz.co.twoten.concordion.LoggingTooltipExtension");
 * </pre>
 * <h4>Custom Configuration</h4>
 * The extension can be customised using an {@link ConcordionExtensionFactory} to call the custom constructor. The logging can be restricted to named loggers,
 * and by logging levels. The output of logging to the console can also be enabled.
 * <p>
 * An example factory:
 * <pre>
 * package carbon;
 * 
 * import java.util.logging.Level;
 * import nz.co.twoten.concordion.LoggingTooltipExtension;
 * import org.concordion.api.extension.ConcordionExtension;
 * import org.concordion.api.extension.ConcordionExtensionFactory;
 * 
 * public class LoggingTooltipExtensionFactory implements ConcordionExtensionFactory {
 *     &#064;Override
 *     public ConcordionExtension createExtension() {
 *         String loggers = "carbon.CarbonCalculatorTest, selenium.events";
 *         return new LoggingTooltipExtension(loggers, Level.FINE, false);
 *     }
 * }
 * </pre>
 * To install this example extension factory:
 * <pre>
 *       System.setProperty("concordion.extensions", "carbon.LoggingTooltipExtensionFactory");
 * </pre>
 * <p>
 * Thanks to Trent Richardson for the <a href="http://trentrichardson.com/examples/csstooltips/">CSS Tooltip</a> implementation.
 */
public class LoggingTooltipExtension implements ConcordionExtension {

    private static final String TOOLTIP_CSS_SOURCE_PATH = "/nz/co/twoten/resource/tooltip.css";
    private static final Resource TOOLTIP_CSS_TARGET_RESOURCE = new Resource("/tooltip.css");

    private static final Resource BUBBLE_FILLER_IMAGE_RESOURCE = new Resource("/image/bubble_filler.gif");
    private static final String BUBBLE_FILLER_RESOURCE_PATH = "/nz/co/twoten/resource/bubble_filler.gif";
    private static final Resource BUBBLE_IMAGE_RESOURCE = new Resource("/image/bubble.gif");
    private static final String BUBBLE_RESOURCE_PATH = "/nz/co/twoten/resource/bubble.gif";
    private static final Resource INFO_IMAGE_RESOURCE = new Resource("/image/info16.png");
    private static final String INFO_RESOURCE_PATH = "/nz/co/twoten/resource/i16.png";
    
    private final String loggerNames;
    private final boolean displayRootConsoleLogging;
    private final Level loggingLevel;

    /**
     * Default constructor that logs output from all loggers, with a {@link Level} of <code>INFO</code> or higher, and disables the console output of the root logger.
     */
    public LoggingTooltipExtension() {
        this("", Level.INFO, false);
    }

    /**
     * Custom constructor. 
  
     * @param loggerNames a comma separated list of the names of loggers whose output is to be shown in the Concordion output. An empty string indicates the root logger.
     * @param loggingLevel the logging {@link Level} for the handler that writes to the Concordion output. Log messages of this level and
     * higher will be output.  Note that the associated {@link Logger}s must also have an appropriate logging level set.
     * @param displayRootConsoleLogging <code>false</code> to remove console output for the root logger, <code>true</code> to show the console output
     */
    public LoggingTooltipExtension(String loggerNames, Level loggingLevel, boolean displayRootConsoleLogging) {
        this.loggerNames = loggerNames;
        this.loggingLevel = loggingLevel;
        this.displayRootConsoleLogging = displayRootConsoleLogging;
    }

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        TooltipRenderingListener extension = createExtension();

        concordionExtender.withExecuteListener(extension).withAssertEqualsListener(extension).withAssertTrueListener(extension)
                .withAssertFalseListener(extension).withVerifyRowsListener(extension).withThrowableListener(extension);
        concordionExtender.withSpecificationProcessingListener(extension);
        concordionExtender.withLinkedCSS(TOOLTIP_CSS_SOURCE_PATH, TOOLTIP_CSS_TARGET_RESOURCE);
        concordionExtender.withResource(BUBBLE_RESOURCE_PATH, BUBBLE_IMAGE_RESOURCE);
        concordionExtender.withResource(BUBBLE_FILLER_RESOURCE_PATH, BUBBLE_FILLER_IMAGE_RESOURCE);
        concordionExtender.withResource(INFO_RESOURCE_PATH, INFO_IMAGE_RESOURCE);
    }

    private TooltipRenderingListener createExtension() {
        return new TooltipRenderingListener(INFO_IMAGE_RESOURCE, loggerNames, loggingLevel, displayRootConsoleLogging);
    }
}
