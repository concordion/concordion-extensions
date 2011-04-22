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
package spec.concordion.ext.loggingTooltip;

import java.util.logging.Logger;

import org.concordion.ext.LoggingTooltipExtension;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.EmbedExtensionWithConcordionNamespaceFactory;
import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class LoggingTooltip {
    
    private static final Logger logger = Logger.getLogger(LoggingTooltip.class.getName());
    
    @Before 
    public void installExtension() {
        System.setProperty("concordion.extensions", 
                LoggingTooltipExtension.class.getName() + ", " + EmbedExtensionWithConcordionNamespaceFactory.class.getName());
    }
    
    public String render(String fragment) throws Exception {
        String outputFragment = new TestRig()
            .withFixture(this)
            .withSourceFilter("/org/concordion/ext/resource")
            .processFragment(fragment)
            .getOutputFragmentXML();
        return outputFragment.substring(0, outputFragment.indexOf("[") + 1) + "timestamp" + outputFragment.substring(outputFragment.indexOf("]"));
    }

    public String getName() throws Exception {
        logger.info("Got name Frank");    
        return "Frank";
    }
}
