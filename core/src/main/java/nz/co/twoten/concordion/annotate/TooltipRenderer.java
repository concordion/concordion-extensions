package nz.co.twoten.concordion.annotate;

import org.concordion.api.Element;
import org.concordion.api.Resource;

public class TooltipRenderer {

    private long buttonId = 0;
    private final Resource infoImageResource;
    
    public TooltipRenderer(Resource infoImageResource) {
        this.infoImageResource = infoImageResource;
    }

    public void hoverText(Resource resource, Element element, String text) {
        buttonId++;
        
        if (element.getLocalName().equals("table")) {
            Element tr = element.getFirstChildElement("tr");
            Element th = new Element("th"); 
            tr.appendChild(th);
            th.appendChild(tooltip(resource, text));
        } else if (element.getLocalName().equals("tr")) {
            Element td = new Element("td"); 
            element.appendChild(td);
            td.appendChild(tooltip(resource, text));
        } else if (element.getLocalName().equals("td")) {
            Element span = new Element("span");
            element.moveChildrenTo(span);
            element.moveAttributesTo(span);
            element.appendChild(span);
            element.appendChild(tooltip(resource, text));
        } else {
            element.appendNonBreakingSpace();
            element.appendSister(tooltip(resource, text));
        }
    }
    
   private Element tooltip(Resource resource, String text) {
        Element tt = new Element("a").addAttribute("href", "#").addStyleClass("tt");
        tt.appendChild(new Element("img").addAttribute("src", resource.getRelativePath(infoImageResource)).addAttribute("alt", "i").appendNonBreakingSpace());
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
