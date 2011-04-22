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
package test.concordion;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;


/**
 * Spies on java.util.logging messages. 
 */
public class StubLogger {

    public ByteArrayOutputStream baos;
    public StreamHandler streamHandler;

    public StubLogger() {
        baos = new ByteArrayOutputStream(10000);
        streamHandler = new StreamHandler(baos, new SimpleFormatter());
        streamHandler.setLevel(Level.ALL);
        Logger logger = Logger.getLogger("");
        logger.addHandler(streamHandler);
    }

    /**
     * Returns any new java.util.logging messages since this method was last called, 
     * or since this object was constructed if this method has not yet been called.
     */
    public String getNewLogMessages() {
        streamHandler.flush();
        String text = baos.toString();
        baos.reset();
        return text;
    }
}
