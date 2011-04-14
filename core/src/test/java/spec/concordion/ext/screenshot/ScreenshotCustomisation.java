package spec.concordion.ext.screenshot;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import test.concordion.FileOutputStreamer;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class ScreenshotCustomisation {
    public String acronym;
    
    public void setSystemProperty(String key, String value) {
        System.setProperty(key, value);
    }

    public String render(String fragment, String acronym) throws Exception {
        this.acronym = acronym;
        return new TestRig()
            .withFixture(this)
            .withOutputStreamer(new FileOutputStreamer())
            .processFragment(fragment)
            .getOutputFragmentXML();
    }
}
