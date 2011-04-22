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