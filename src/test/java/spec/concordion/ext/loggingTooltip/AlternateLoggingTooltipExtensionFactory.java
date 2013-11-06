package spec.concordion.ext.loggingTooltip;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.LoggingTooltipExtension;
import org.concordion.ext.logging.LogMessenger;

public class AlternateLoggingTooltipExtensionFactory implements ConcordionExtensionFactory {

    public static String logMessage;

    @Override
    public ConcordionExtension createExtension() {
        return new LoggingTooltipExtension(new LogMessenger() {
            @Override
            public String getNewLogMessages() {
                return "[]: " + logMessage;
            }
        });
    }
}
