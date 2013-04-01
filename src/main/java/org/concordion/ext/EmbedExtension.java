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

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.ext.embed.EmbedCommand;

/**
 * Embeds HTML snippets in the Concordion output.
 */
public class EmbedExtension implements ConcordionExtension {
    
    public static final String EXTENSION_NAMESPACE = "urn:concordion-extensions:2010";
    public static final String COMMAND_NAME = "embed";
    
    private EmbedCommand embedCommand = new EmbedCommand();

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withCommand(EXTENSION_NAMESPACE, COMMAND_NAME, embedCommand);
    }

    /**
     * Declares additional namespaces that are present in the HTML snippet.
     * If the HTML fragment includes elements or attributes with a namespace prefix, the additional namespaces must be declared, both in the HTML specification, 
     * and using this method.
     * 
     * @param prefix the prefix assigned to the namespace
     * @param namespace the corresponding namespace
     * @return this
     */
    public EmbedExtension withNamespace(String prefix, String namespace) {
        embedCommand.withNamespace(prefix, namespace);
        return this;
    }
}
