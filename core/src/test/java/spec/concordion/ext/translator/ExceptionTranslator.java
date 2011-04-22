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
package spec.concordion.ext.translator;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class ExceptionTranslator {

    private String exceptionMessage;

    @Before
    public void setupEmbedExtension() {
        System.setProperty("concordion.extensions", EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public void setSystemProperty(String name, String value) {
        System.setProperty(name, value + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    
    public void makeConnection() {
        throw new RuntimeException(exceptionMessage);
    }
    
    public String render(String fragment) throws Exception {
        return new TestRig()
            .withFixture(this)
            .processFragment(fragment)
            .getOutputFragmentXML();
    }
    
    public String getExceptionMessage(String fragment) {
        return new TestRig()
        .withFixture(this)
        .processFragment(fragment)
        .getExceptionMessage();
    }

    public String getStackTraceMessage(String fragment) {
        return new TestRig()
        .withFixture(this)
        .processFragment(fragment)
        .getStackTraceMessage().replace("org.concordion.internal.InvalidExpressionException: ", "");
    }
}
