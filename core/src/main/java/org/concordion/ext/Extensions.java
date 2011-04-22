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

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;

/**
 * Installs all of the extensions in this package.
 * <p>To add all of the extensions, set the system property <code>concordion.extensions</code> to <code>org.concordion.ext.Extensions</code>.
 * This is typically set in the &#064;BeforeClass of a base test case:</p>
 * <pre>
 *   &#064;BeforeClass
 *   public static void addExtensions() {
 *       System.setProperty("concordion.extensions", "org.concordion.ext.Extensions");
 *   }
 * </pre>
 * <p>The extensions may also be installed individually and have a number of customisation options. See the Javadoc of each extension for details.</p>
 * <b>NOTE:</b> only use this class if you want the default configuration of each extension.  If you wish to customise, you will need to
 * specify each extension, or extension factory, in a comma-separated list to the <code>concordion.extensions</code> property. 
 */
public class Extensions implements ConcordionExtension {

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        new LoggingTooltipExtension().addTo(concordionExtender);
        new ScreenshotExtension().addTo(concordionExtender);
        new TimestampFormatterExtension().addTo(concordionExtender);
        new EmbedExtension().addTo(concordionExtender);
    }
}
