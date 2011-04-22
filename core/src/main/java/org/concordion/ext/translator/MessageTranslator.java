package org.concordion.ext.translator;

public interface MessageTranslator {
    /**
     * Translates the original message into a new message.
     * @param originalMessage
     * @return the new message
     */
    String translate(String originalMessage);
}
