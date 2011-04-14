package test.concordion;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.EmbedExtension;
import org.concordion.ext.ScreenshotExtension;
import org.concordion.internal.ConcordionBuilder;

public class EmbedExtensionWithConcordionNamespaceFactory implements ConcordionExtensionFactory {
    @Override
    public ConcordionExtension createExtension() {
        EmbedExtension extension = new EmbedExtension();
        extension.withNamespace("concordion", ConcordionBuilder.NAMESPACE_CONCORDION_2007);
        extension.withNamespace("cx", ScreenshotExtension.EXTENSION_NAMESPACE);
        return extension;
    }
}
