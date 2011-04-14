package test.concordion;

import java.io.IOException;

import org.concordion.Concordion;
import org.concordion.api.EvaluatorFactory;
import org.concordion.api.Resource;
import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;
import org.concordion.internal.SimpleEvaluatorFactory;


public class TestRig {

    private Object fixture = null;
    private EvaluatorFactory evaluatorFactory = new SimpleEvaluatorFactory();
    private StubSource stubSource = new StubSource();
    private StubTarget stubTarget = new StubTarget();
    private String namespaceDeclaration = "xmlns:concordion='" + ConcordionBuilder.NAMESPACE_CONCORDION_2007 + "'";

    public TestRig withFixture(Object fixture) {
        this.fixture = fixture;
        return this;
    }

    public TestRig withNamespaceDeclaration(String prefix, String namespace) {
        namespaceDeclaration += " " + String.format("xmlns:%s='%s'", prefix, namespace);
        return this;
    }
    
    public ProcessingResult processFragment(String fragment) {
        return process(wrapFragment(fragment), "/testrig");
    }

    public ProcessingResult processFragment(String fragment, String fixtureName) {
        return process(wrapFragment(fragment), fixtureName);
    }

    public ProcessingResult process(Resource resource) {
        EventRecorder eventRecorder = new EventRecorder();
        Concordion concordion = new ConcordionBuilder()
            .withAssertEqualsListener(eventRecorder)
            .withThrowableListener(eventRecorder)
            .withSource(stubSource)
            .withEvaluatorFactory(evaluatorFactory)
            .withTarget(stubTarget)
            .build();
        
        try {
            ResultSummary resultSummary = concordion.process(resource, fixture);
            String xml = stubTarget.getWrittenString(resource);
            return new ProcessingResult(resultSummary, eventRecorder, xml);
        } catch (IOException e) {
            throw new RuntimeException("Test rig failed to process specification", e);
        } 
    }

    public ProcessingResult process(String html, String fullName) {
        Resource resource = new Resource(fullName);
        withResource(resource, html);
        return process(resource);
    }

    private String wrapFragment(String fragment) {
        fragment = "<body><fragment>" + fragment + "</fragment></body>";
        return wrapWithNamespaceDeclaration(fragment);
    }
    
    private String wrapWithNamespaceDeclaration(String fragment) {
        return "<html " + namespaceDeclaration + ">"
            + fragment
            + "</html>";
    }

    public TestRig withStubbedEvaluationResult(Object evaluationResult) {
        this.evaluatorFactory = new StubEvaluator().withStubbedResult(evaluationResult);
        return this;
    }
    
    public TestRig withResource(Resource resource, String content) {
        stubSource.addResource(resource, content);
        return this;
    }
    
    public TestRig withOutputStreamer(OutputStreamer streamer) {
        stubTarget.setOutputStreamer(streamer);
        return this;
    }
    
    public boolean hasCopiedResource(Resource resource) {
        return stubTarget.hasCopiedResource(resource);
    }
}
