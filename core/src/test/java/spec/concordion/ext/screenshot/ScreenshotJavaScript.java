package spec.concordion.ext.screenshot;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import test.concordion.FileOutputStreamer;
import test.concordion.ProcessingResult;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class ScreenshotJavaScript {
    
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value);
    }
    
    public boolean hasJavaScriptFunction(String functionName) throws Exception {
        ProcessingResult result = new TestRig()
            .withFixture(this)
            .withOutputStreamer(new FileOutputStreamer())
            .processFragment("");
        return result.hasJavaScriptFunction(functionName);
    }
}
