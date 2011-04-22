package spec.concordion.ext.loggingTooltip;

import org.concordion.api.Resource;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class LoggingTooltipResources {
    
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value);
    }
    
    public boolean hasCopiedResource(String resourceName) throws Exception {
        TestRig testRig = new TestRig();
        testRig
            .withFixture(this)
            .withSourceFilter("/org/concordion/ext/resource")
            .processFragment("");
        return testRig.hasCopiedResource(new Resource("/image/" + resourceName));
    }
}
