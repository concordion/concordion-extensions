package spec.concordion.ext.loggingTooltip;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import test.concordion.FileOutputStreamer;
import test.concordion.ProcessingResult;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class LoggingTooltipCss {
    
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value);
    }
    
    public boolean hasCSSDeclaration(String cssFilename) throws Exception {
        ProcessingResult result = new TestRig()
            .withFixture(this)
            .withOutputStreamer(new FileOutputStreamer())
            .withSourceFilter("/org/concordion/ext/resource")
            .processFragment("");
        return result.hasCSSDeclaration(cssFilename);
    }
}
