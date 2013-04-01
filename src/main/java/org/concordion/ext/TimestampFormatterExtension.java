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
import org.concordion.ext.footer.TimestampFormattingSpecificationListener;

/**
 * Formats the footer of the Concordion output HTML to show the time in hours, minutes and seconds rather than milliseconds.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "org.concordion.ext.TimestampFormatterExtension");
 * </pre>
 */
public class TimestampFormatterExtension implements ConcordionExtension {

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withSpecificationProcessingListener(new TimestampFormattingSpecificationListener());
    }
}
