package spec.concordion.ext.screenshot;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.ScreenshotExtension;

import test.concordion.ext.screenshot.DummyScreenshotTaker;

public class ScreenshotExtensionFactory implements ConcordionExtensionFactory {
    @Override
    public ConcordionExtension createExtension() {
        ScreenshotExtension extension = new ScreenshotExtension();
        extension.setScreenshotOnAssertionSuccess(true);
        extension.setMaxWidth(400);
        extension.setScreenshotTaker(new DummyScreenshotTaker());  // Not needed for the example, but ensures screenshots of my system aren't published. 
        return extension;
    }
}