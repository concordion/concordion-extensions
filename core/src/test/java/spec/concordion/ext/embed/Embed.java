package spec.concordion.ext.embed;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.TestRig;

@RunWith(ConcordionRunner.class)
public class Embed {

    private String nextResult;

    @Before 
    public void installExtension() {
        System.setProperty("concordion.extensions", "org.concordion.ext.EmbedExtension");
    }    

    public String list(String item1, String item2) {
        return String.format("<ol><li>%s</li><li>%s</li></ol>", item1, item2);
    }

    public void setNextResult(String nextResult) {
        this.nextResult = nextResult;
    }

    public String render(String fragment, String namespacePrefix, String namespace) throws Exception {
            return new TestRig()
            .withNamespaceDeclaration(namespacePrefix, namespace)
            .withStubbedEvaluationResult(nextResult)
            .processFragment(fragment)
            .getOutputFragmentXML();
    }
}
