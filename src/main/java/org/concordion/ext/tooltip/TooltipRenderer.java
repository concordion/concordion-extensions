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
package org.concordion.ext.tooltip;

import org.concordion.api.Element;
import org.concordion.api.Resource;

/**
 * Renders tooltips in the Concordion output. The tooltip will display text when hovered over. 
 */
public class TooltipRenderer {

    private long buttonId = 0;
    private final Resource tooltipImageResource;
    
    /**
     * Constructor.
     * 
     * @param tooltipImageResource the image to use for the tooltip icon
     */
    public TooltipRenderer(Resource tooltipImageResource) {
        this.tooltipImageResource = tooltipImageResource;
    }

    /**
     * Adds a tooltip with the given text to the given element of the given resource.
     * 
     * If the element is:
     * <ul>
     * <li>a table (<code>&lt;table&gt;</code>), the tooltip will be added to a new table header column at the end of the first row.</li>
     * <li>a table row (<code>&lt;tr&gt;</code>), the tooltip will be added to a new table column at the end of the row.</li>
     * <li>a table detail column (<code>&lt;td&gt;</code>), the existing elements in the column will be wrapped in a new span, with the tooltip appended.</li>
     * <li>otherwise the tooltip will be appended as a sibling.</li>
     * </ul>
     * 
     * @param resource the output resource 
     * @param element the element that the tooltip is to be added to
     * @param text the text to include in the tooltip
     */
    public void renderTooltip(Resource resource, Element element, String text) {
        buttonId++;
        
        Element tooltip = createTooltip(resource.getRelativePath(tooltipImageResource), text);
        if (element.getLocalName().equals("table")) {
            Element tr = element.getFirstDescendantNamed("tr");
            Element th = new Element("th"); 
            tr.appendChild(th);
            th.appendChild(tooltip);
        } else if (element.getLocalName().equals("tr")) {
            Element td = new Element("td"); 
            element.appendChild(td);
            td.appendChild(tooltip);
        } else if (element.getLocalName().equals("td")) {
            Element span = new Element("span");
            element.moveChildrenTo(span);
            element.moveAttributesTo(span);
            element.appendChild(span);
            element.appendChild(tooltip);
        } else {
            element.appendNonBreakingSpace();
            element.appendSister(tooltip);
        }
    }
    
   private Element createTooltip(String relativePathToImage, String text) {
        Element tt = new Element("a").addAttribute("href", "#").addStyleClass("tt");
        tt.appendChild(new Element("img").addAttribute("src", relativePathToImage).addAttribute("alt", "i").appendNonBreakingSpace());
        Element tooltip = new Element("span").addStyleClass("tooltip");
        tt.appendChild(tooltip);
        tooltip.appendChild(new Element("span").addStyleClass("top").appendText(""));
        Element textChild = new Element("span").addStyleClass("middle");
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            textChild.appendText(line).appendChild(new Element("br"));
        }
        tooltip.appendChild(textChild);
        tooltip.appendChild(new Element("span").addStyleClass("bottom").appendText(""));
        
        return tt;
    }
}
