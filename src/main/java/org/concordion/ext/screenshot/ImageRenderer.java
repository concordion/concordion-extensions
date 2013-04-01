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
package org.concordion.ext.screenshot;

import org.concordion.api.Element;

public class ImageRenderer {

    public static final String CSS = "\n" +
        ".screenshot {\n" +
        "  position:absolute;\n" +
        "  visibility:hidden;\n" +
        "  border:solid 1px black;\n" +
        "  z-index:30;\n" +
        "}\n";

    public static final String script = "\n" +
        "function show(id) {\n" +
        "  document.getElementById(id).style.visibility = 'visible';\n" +
        "}\n" +
        "\n" +
        "function hide(id) {\n" +
        "  document.getElementById(id).style.visibility = 'hidden';\n" +
        "}\n" + 
        "function showScreenshotOn(e) {\n" + 
        "  var targ;\n" + 
        "  if (!e) var e = window.event;\n" + 
        "  if (e.target) targ = e.target\n" + 
        "  else if (e.srcElement) targ = e.srcElement\n" + 
        "  if (targ.nodeType == 3) // defeat Safari bug\n" + 
        "    targ = targ.parentNode;\n" + 
        "  if (targ.nodeName == 'P')\n" + 
        "    targ = targ.parentNode;\n" + 
        "  return (targ.className.indexOf('stackTrace') != 0);\n" + 
        "}";

    private final boolean hidden;
    private final int maxWidth;
    
    public ImageRenderer(boolean hidden, int maxWidth) {
        this.hidden = hidden;
        this.maxWidth = maxWidth;
    }

    void addImageToElement(Element element, String imageName, int imageWidth) {
        Element a = new Element("a");
        a.addAttribute("href", imageName);
        Element img = new Element("img");
        img.setId(imageName);
        img.addAttribute("src", imageName);
        img.addAttribute("width", Integer.toString(Math.min(maxWidth, imageWidth)));
        
        if (hidden) {
            img.addStyleClass("screenshot");
            Element hoverElement = element;  // the element that must be hovered over to show screenshot
            if ("td".equals(element.getLocalName())) {
                Element[] childElements = element.getChildElements();
                if (childElements.length > 0 && "span".equals(childElements[0].getLocalName())) { 
                    hoverElement = childElements[0];  // add hover to child span so that it co-exists nicely with log annotator extension on tables
                }
            }
            hoverElement.addAttribute("onMouseOver", "if (showScreenshotOn(event)) {show('" + imageName + "');this.style.cursor='pointer'}");
            hoverElement.addAttribute("onMouseOut",  "hide('" + imageName + "');this.style.cursor='default'");
            hoverElement.addAttribute("onClick",     "if (showScreenshotOn(event)) {hide('" + imageName + "');window.location.href='" + imageName + "'}");
        }
    
        element.appendChild(a);
        a.appendChild(img);
    }

}
