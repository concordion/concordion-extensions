/*
 * Copyright (c) 2010 Two Ten Consulting Limited, New Zealand 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.concordion.ext;

import org.concordion.api.Element;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.listener.ThrowableCaughtEvent;
import org.concordion.api.listener.ThrowableCaughtListener;
import org.concordion.ext.translator.MessageTranslator;

/**
 * Translates exception messages in the Concordion output.
 * <p>
 * The extension must be installed using a custom extension factory, since it requires a custom {@link MessageTranslator} class.
 * <p>
 * An example factory that adds the Concordion namespace is:
 * <pre>
 * package sample.translator;
 * 
 * import org.concordion.api.extension.ConcordionExtension;
 * import org.concordion.api.extension.ConcordionExtensionFactory;
 * import org.concordion.ext.TranslatorExtension;
 * import org.concordion.ext.translator.MessageTranslator;
 * 
 * public class FrenchTranslatorExtensionFactory implements ConcordionExtensionFactory, MessageTranslator {
 *
 * @Override
 * public ConcordionExtension createExtension() {
 *     return new TranslatorExtension(this);
 *  }
 *  
 *  @Override
 *  public String translate(String originalMessage) {
 *      return originalMessage.replace("RuntimeException: Unable to connect to", "Impossible de se connecter à");
 *  }
 * </pre>
 * To install this example extension factory:
 * <pre>
 *       System.setProperty("concordion.extensions", "sample.translator.FrenchTranslatorExtensionFactory");
 * </pre>
 */
public class TranslatorExtension implements ConcordionExtension, ThrowableCaughtListener {

    private final MessageTranslator messageTranslator;

    public TranslatorExtension(MessageTranslator messageTranslator) {
        this.messageTranslator = messageTranslator;
    }

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withThrowableListener(this);
    }

    @Override
    public void throwableCaught(ThrowableCaughtEvent event) {
        Element[] childSpans = event.getElement().getDescendantElements("span");
        for (Element span : childSpans) {
            if ("exceptionMessage".equals(span.getAttributeValue("class"))) {
                Element replacementExceptionMessage = new Element("span")
                    .addAttribute("class", "exceptionMessage")
                    .appendText(translateMessage(span.getText()));
                span.appendSister(replacementExceptionMessage);
                event.getElement().removeChild(span);
            }
        }
    }

    private String translateMessage(String originalMessage) {
        return messageTranslator.translate(originalMessage);
    }
}
