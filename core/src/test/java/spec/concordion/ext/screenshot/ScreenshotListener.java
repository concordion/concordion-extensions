package spec.concordion.ext.screenshot;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.FileOutputStreamer;
import test.concordion.TestRig;
import test.concordion.ext.screenshot.DummyScreenshotFactory;

@RunWith(ConcordionRunner.class)
public class ScreenshotListener {
    
    private static final String STACK_TRACE_START_TAG = "<span class=\"stackTraceEntry\">";
    private static final String STACK_TRACE_END_TAG = "</span>";
    
    public static final String SPEC_NAME = "/" + ScreenshotListener.class.getName().replace(".java", ".html").replaceAll("\\.","/");
    public String acronym;
    
    @Before 
    public void installExtension() {
        System.setProperty("concordion.extensions", 
                DummyScreenshotFactory.class.getName() + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public String renderAsFailure(String fragment, String acronym) throws Exception {
        this.acronym = acronym;
        return new TestRig()
            .withFixture(this)
            .withOutputStreamer(new FileOutputStreamer())
            .processFragment(fragment, SPEC_NAME)
            .getOutputFragmentXML();
    }

    public int divideByZero() throws Exception {
        return 1 / 0;
    }

    public String removeStackTraces(String htmlSnippet) {
        String firstBit = htmlSnippet.substring(0, htmlSnippet.indexOf(STACK_TRACE_START_TAG));
        int lastStackTraceStart = htmlSnippet.lastIndexOf(STACK_TRACE_START_TAG);
        int lastStackTraceEnd = htmlSnippet.indexOf(STACK_TRACE_END_TAG, lastStackTraceStart);
        String lastBit = htmlSnippet.substring(lastStackTraceEnd + 1);
        return firstBit + "\n...\n" + lastBit;
    }
}
