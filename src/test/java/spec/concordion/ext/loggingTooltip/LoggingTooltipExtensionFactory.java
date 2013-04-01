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