package spec.concordion.ext.loggingTooltip;

import java.util.logging.Level;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.LoggingTooltipExtension;

public class LoggingTooltipExtensionFactory implements ConcordionExtensionFactory {
    
    public static String loggers;
    public static Level logLevel;
    
    @Override
    public ConcordionExtension createExtension() {
        LoggingTooltipExtension extension = new LoggingTooltipExtension(loggers, logLevel, false);
        return extension;
    }
}