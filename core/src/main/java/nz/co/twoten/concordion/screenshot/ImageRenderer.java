package nz.co.twoten.concordion.screenshot;

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
        "}\n";

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
            hoverElement.addAttribute("onMouseOver", "show('" + imageName + "');this.style.cursor='pointer'");
            hoverElement.addAttribute("onMouseOut",  "hide('" + imageName + "');this.style.cursor='default'");
            hoverElement.addAttribute("onClick",     "hide('" + imageName + "');window.location.href='" + imageName + "';return false");
        }
    
        element.appendChild(a);
        a.appendChild(img);
    }

}
