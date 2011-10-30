package org.concordion.ext.selenium;

import org.concordion.ext.translator.MessageTranslator;

public final class SeleniumExceptionMessageTranslator implements MessageTranslator {
    @Override
    public String translate(String originalMessage) {
        int webDriverDebugInfoStart = originalMessage.indexOf("Build info:");
        if (webDriverDebugInfoStart > 0) {
            return originalMessage.substring(0, webDriverDebugInfoStart);
        }
        return originalMessage;
    }
}