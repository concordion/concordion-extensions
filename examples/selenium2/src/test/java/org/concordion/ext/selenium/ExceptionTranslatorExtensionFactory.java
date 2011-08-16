package org.concordion.ext.selenium;

import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.TranslatorExtension;
import org.concordion.ext.translator.MessageTranslator;

public class ExceptionTranslatorExtensionFactory implements ConcordionExtensionFactory {

    @Override
    public ConcordionExtension createExtension() {
        return new TranslatorExtension(new MessageTranslator() {
            
            @Override
            public String translate(String originalMessage) {
                int webDriverDebugInfoStart = originalMessage.indexOf("Build info:");
                if (webDriverDebugInfoStart > 0) {
                    return originalMessage.substring(0, webDriverDebugInfoStart);
                }
                return originalMessage;
            }
        });
    }
}