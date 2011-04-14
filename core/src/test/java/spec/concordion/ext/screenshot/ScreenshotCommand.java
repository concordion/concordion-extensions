package spec.concordion.ext.screenshot;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.FileOutputStreamer;
import test.concordion.TestRig;
import test.concordion.ext.screenshot.DummyScreenshotFactory;

@RunWith(ConcordionRunner.class)
public class ScreenshotCommand {
    
    public static final String SPEC_NAME = "/" + ScreenshotCommand.class.getName().replace(".java", ".html").replaceAll("\\.","/");
    public static int testRunNumber = 1;  // since we want to run the test rig multiple times for a single spec 
    
    @Before 
    public void installExtension() {
        System.setProperty("concordion.extensions", 
                DummyScreenshotFactory.class.getName() + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public String render(String fragment, String namespacePrefix, String namespace) throws Exception {
        return new TestRig()
            .withNamespaceDeclaration(namespacePrefix, namespace)
            .withFixture(this)
            .withOutputStreamer(new FileOutputStreamer())
            .processFragment(fragment, SPEC_NAME + testRunNumber++)
            .getOutputFragmentXML();
    }
}
