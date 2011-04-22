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
package org.concordion.ext.footer;

import org.concordion.api.Element;
import org.concordion.api.listener.SpecificationProcessingEvent;
import org.concordion.api.listener.SpecificationProcessingListener;

public class TimestampFormattingSpecificationListener implements SpecificationProcessingListener {
    
    TimestampFormatter formatter = new TimestampFormatter();

    @Override
    public void beforeProcessingSpecification(SpecificationProcessingEvent event) {
    }

    @Override
    public void afterProcessingSpecification(SpecificationProcessingEvent event) {
        Element body = event.getRootElement().getFirstChildElement("body");
        
        if (body != null) {
            Element[] divs = body.getChildElements("div");
            for (Element div : divs) {
                if ("footer".equals(div.getAttributeValue("class"))) {
                    Element timeDiv = div.getFirstChildElement("div");
                    String timeTaken = timeDiv.getText();
                    div.removeChild(timeDiv);
                    
                    Element newTimeDiv = new Element("div");
                    newTimeDiv.addStyleClass("testTime");
                    newTimeDiv.appendText(formatter.reformat(timeTaken));
                    div.appendChild(newTimeDiv);
                }
            }
        }
    }
}
