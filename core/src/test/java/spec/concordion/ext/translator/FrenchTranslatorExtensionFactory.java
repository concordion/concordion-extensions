package spec.concordion.ext.translator;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.TranslatorExtension;
import org.concordion.ext.translator.MessageTranslator;

public class FrenchTranslatorExtensionFactory implements ConcordionExtensionFactory, MessageTranslator {
    
    @Override
    public ConcordionExtension createExtension() {
        return new TranslatorExtension(this);
    }

    @Override
    public String translate(String originalMessage) {
        return originalMessage.replace("RuntimeException: Unable to connect to", "Impossible de se connecter à");
    }
}