package spec.concordion.ext.timestampFormatter;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class TimestampFormatter {
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value);
    }
    
    public String getFooterStart() throws Exception {
        String footerText = new TestRig()
            .withFixture(this)
            .processFragment("<p concordion:execute='sleep(#TEXT)'>2000</p>").getFooterText();
        return footerText.substring(0, footerText.indexOf("on") + 2);
    }
    
    public void sleep(int millis) throws Exception {
        Thread.sleep(millis);
    }
}
