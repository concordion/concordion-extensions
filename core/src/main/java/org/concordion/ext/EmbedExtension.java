package org.concordion.ext;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.extension.ConcordionExtensionFactory;
import org.concordion.ext.embed.EmbedCommand;

/**
 * Embeds HTML snippets in the Concordion output.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "org.concordion.ext.EmbedExtension");
 * </pre>
 * <h4>Custom Configuration</h4>
 * When embedding HTML, it is often necessary to add namespace declarations to the Concordion output.  
 * The extension can be customised using an {@link ConcordionExtensionFactory} to add namespace declarations.
 * Note that the declarations are added to a wrapper element around elements that contains the Embed command.  
 * <p>
 * An example factory that adds the Concordion namespace is:
 * <pre>
 * package test.concordion;
 * 
 * import org.concordion.api.extension.ConcordionExtension;
 * import org.concordion.api.extension.ConcordionExtensionFactory;
 * import org.concordion.ext.EmbedExtension;
 * import org.concordion.internal.ConcordionBuilder;
 * 
 * public class EmbedExtensionWithConcordionNamespaceFactory implements ConcordionExtensionFactory {
 * @Override
 *     public ConcordionExtension createExtension() {
 *         EmbedExtension extension = new EmbedExtension();
 *         extension.withNamespace("concordion", ConcordionBuilder.NAMESPACE_CONCORDION_2007);
 *         return extension;
 *     }
 * }
 * </pre>
 * To install this example extension factory:
 * <pre>
 *       System.setProperty("concordion.extensions", "test.concordion.EmbedExtensionWithConcordionNamespaceFactory");
 * </pre>
 * 
 * <h4>Using the Embed Command</h4>
 * Add an attribute named <code>embed</code> using the namespace <code>"urn:concordion-extensions:2010"</code> to your Concordion HTML. For example: 
 * <pre>
 * &lt;html xmlns:concordion="http://www.concordion.org/2007/concordion" xmlns:ext="urn:concordion-extensions:2010"&gt;
 * ....
 * &lt;div ext:embed="methodThatReturnsHTML()"/&gt;
 * ...
 * </pre> 
 */
public class EmbedExtension implements ConcordionExtension {
    
    public static final String EXTENSION_NAMESPACE = "urn:concordion-extensions:2010";
    public static final String COMMAND_NAME = "embed";
    
    private EmbedCommand embedCommand = new EmbedCommand();

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withCommand(EXTENSION_NAMESPACE, COMMAND_NAME, embedCommand);
    }

    public void withNamespace(String prefix, String namespace) {
        embedCommand.withNamespace(prefix, namespace);
    }
}
