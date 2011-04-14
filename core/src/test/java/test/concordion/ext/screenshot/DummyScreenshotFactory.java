package test.concordion.ext.screenshot;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.ScreenshotExtension;

public class DummyScreenshotFactory implements ConcordionExtensionFactory {
    
    @Override
    public ConcordionExtension createExtension() {
        ScreenshotExtension extension = new ScreenshotExtension();
        extension.setScreenshotTaker(new DummyScreenshotTaker());
        return extension;
    }
}
