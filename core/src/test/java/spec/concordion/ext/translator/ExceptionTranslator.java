package spec.concordion.ext.translator;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class ExceptionTranslator {

    private String exceptionMessage;

    @Before
    public void setupEmbedExtension() {
        System.setProperty("concordion.extensions", EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    
    public void makeConnection() {
        throw new RuntimeException(exceptionMessage);
    }
    
    public String render(String fragment) throws Exception {
        return new TestRig()
            .withFixture(this)
            .processFragment(fragment)
            .getOutputFragmentXML();
    }
    
    public String getExceptionMessage(String fragment) {
        return new TestRig()
        .withFixture(this)
        .processFragment(fragment)
        .getExceptionMessage();
    }

    public String getStackTraceMessage(String fragment) {
        return new TestRig()
        .withFixture(this)
        .processFragment(fragment)
        .getStackTraceMessage().replace("org.concordion.internal.InvalidExpressionException: ", "");
    }
}
