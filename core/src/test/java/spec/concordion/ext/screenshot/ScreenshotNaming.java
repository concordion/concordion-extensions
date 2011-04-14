package spec.concordion.ext.screenshot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.FileOutputStreamer;
import test.concordion.TestRig;
import test.concordion.ext.screenshot.DummyScreenshotFactory;

@RunWith(ConcordionRunner.class)
public class ScreenshotNaming {
    
    public static final String SPEC_NAME = "/" + ScreenshotNaming.class.getName().replace(".java", ".html").replaceAll("\\.","/");
    public static final String TEXT_BEFORE_IMAGE_NAME = "if (showScreenshotOn(event)) {show('";
    public String acronym;
    private FileOutputStreamer streamer;

    @Before 
    public void installExtension() {
        System.setProperty("concordion.extensions", 
                DummyScreenshotFactory.class.getName() + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public ScreenshotNaming() {
        streamer = new FileOutputStreamer();
        
    }
    
    public String render(String fragment, String acronym) throws Exception {
        this.acronym = acronym;
        return new TestRig()
            .withFixture(this)
            .withOutputStreamer(streamer)
            .processFragment(fragment, SPEC_NAME)
            .getOutputFragmentXML();
    }

    public String renderUsingFixtureNamed(String fragment, String fixtureName) throws Exception {
        return new TestRig()
            .withFixture(this)
            .withOutputStreamer(streamer)
            .processFragment(fragment, fixtureName)
            .getOutputFragmentXML();
    }
    
    public List<String> getResourceNamesOutput() {
        return streamer.getResourcesOutput();
    }
    
    public List<ImageResult> listScreenshotImages(String folder, String fragment) {
        ArrayList<ImageResult> list = new ArrayList<ImageResult>();
        int pos = 0;
        while ((pos=fragment.indexOf(TEXT_BEFORE_IMAGE_NAME, pos)) != -1) {
            ImageResult result = new ImageResult();
            result.imageName = fragment.substring(pos + TEXT_BEFORE_IMAGE_NAME.length(), fragment.indexOf("')", pos));
            pos++;
            File file = new File(new File(streamer.getBaseOutputDir(), folder), result.imageName);
            System.out.println("looking for " + file.toString());
            result.storedOnDisk = file.exists();
            list.add(result);
        }
        return list;
    }
    
    public static class ImageResult {
        public String imageName;
        public boolean storedOnDisk;
    }
}
