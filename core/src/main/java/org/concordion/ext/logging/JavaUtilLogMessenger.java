package org.concordion.ext.logging;

import java.io.ByteArrayOutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.concordion.ext.jul.formatter.TimeAndMessageFormatter;

/**
 * Configures java<!-- -->.util.<!-- -->.logging to store new messages for subsequent retrieval.
 */
public class JavaUtilLogMessenger implements LogMessenger {
    private final ByteArrayOutputStream baos;
    private final StreamHandler streamHandler;

    /**
     * Configures a {@link Handler} to store new messages. The logging level   
     * @param loggerNames
     * @param loggingLevel
     * @param displayRootConsoleLogging
     */
    public JavaUtilLogMessenger(String loggerNames, Level loggingLevel, boolean displayRootConsoleLogging) {
        baos = new ByteArrayOutputStream(4096);
        streamHandler = new StreamHandler(baos, new TimeAndMessageFormatter());
        streamHandler.setLevel(loggingLevel);
        for (String loggerName : loggerNames.split(",")) {
            Logger logger = Logger.getLogger(loggerName.trim());
            logger.addHandler(streamHandler);
        }
        if (!displayRootConsoleLogging) {
            removeRootConsoleHandler();
        }
    }

    private void removeRootConsoleHandler() {
        Logger logger = Logger.getLogger("");
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            if (handler instanceof ConsoleHandler) {
                System.out.println("LoggingTooltipExtension: removing root console logging handler");
                logger.removeHandler(handler);
            }
        }
    }

    @Override
    public String getNewLogMessages() {
        streamHandler.flush();
        String text = baos.toString();
        baos.reset();
        return text;
    }
}