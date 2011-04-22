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
package org.concordion.ext.embed;

import nu.xom.Document;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.internal.XMLParser;
import org.concordion.internal.command.EchoCommand;
import org.concordion.internal.util.Check;

/**
 * Embeds HTML fragments in the Concordion output.
 * <p>
 * When embedding HTML, it is often necessary to add namespace declarations to the Concordion output.  
 * The {@link #withNamespace(String, String)} method registers a namespace declaration. When the embed command
 * is executed for an element, it creates a wrapper element that contains the namespace declarations around
 * the element that the command is being executed for.
 *
 * @see {@link EmbedExtension) for details of configuring and using this command
 * @see The {@link EchoCommand} is similar, except that it adds text rather than HTML to the output.
 */
public class EmbedCommand extends AbstractCommand {

    private String namespaceDeclaration = "";
    
    /**
     * Registers a namespace declaration.
     * 
     * @param prefix the prefix to use for the namespace
     * @param namespace the namespace
     */
    public void withNamespace(String prefix, String namespace) {
        String decl;
        if (prefix == null || prefix.length() == 0) {
            decl = String.format("xmlns='%s'", namespace);
        } else {
            decl = String.format("xmlns:%s='%s'", prefix, namespace);
        }
        if (namespaceDeclaration.length() > 0) {
            namespaceDeclaration += " ";
        }
        namespaceDeclaration += decl;
    }

    @Override
    public void verify(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), "Nesting commands inside an 'embed' is not supported");
        
        Object result = evaluator.evaluate(commandCall.getExpression());

        Element element = commandCall.getElement();
        if (result != null) {
            Element rootElement = getRootElement(result.toString());
            rootElement.moveChildrenTo(element);
        } else {
            Element child = new Element("em");
            child.appendText("null");
            element.appendChild(child);
        }
    }
    
    private Document getXOMDocument(String xml) {
        try {
            return XMLParser.parse(String.format("<wrapper %s>%s</wrapper>", namespaceDeclaration, xml));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse resultant XML document", e);
        }
    }
    
    private Element getRootElement(String xml) {
        return new Element(getXOMDocument(xml).getRootElement());
    }
}
