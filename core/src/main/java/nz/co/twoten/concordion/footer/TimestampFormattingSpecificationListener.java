package nz.co.twoten.concordion.footer;

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
