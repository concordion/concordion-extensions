package org.concordion.ext.logging;

import org.concordion.api.Element;
import org.concordion.api.Resource;
import org.concordion.api.listener.AssertEqualsListener;
import org.concordion.api.listener.AssertFailureEvent;
import org.concordion.api.listener.AssertFalseListener;
import org.concordion.api.listener.AssertSuccessEvent;
import org.concordion.api.listener.AssertTrueListener;
import org.concordion.api.listener.ExecuteEvent;
import org.concordion.api.listener.ExecuteListener;
import org.concordion.api.listener.ExpressionEvaluatedEvent;
import org.concordion.api.listener.MissingRowEvent;
import org.concordion.api.listener.SpecificationProcessingEvent;
import org.concordion.api.listener.SpecificationProcessingListener;
import org.concordion.api.listener.SurplusRowEvent;
import org.concordion.api.listener.ThrowableCaughtEvent;
import org.concordion.api.listener.ThrowableCaughtListener;
import org.concordion.api.listener.VerifyRowsListener;
import org.concordion.ext.tooltip.TooltipRenderer;

/**
 * Writes any new log messages to tooltips when invoked by Concordion events.   
 */
public class LogMessageTooltipWriter implements AssertEqualsListener, AssertTrueListener, AssertFalseListener, ExecuteListener,
        SpecificationProcessingListener, VerifyRowsListener, ThrowableCaughtListener {

    private final TooltipRenderer renderer;
    private final LogMessenger logMessenger;
    private Resource resource;

    public LogMessageTooltipWriter(Resource iconResource, LogMessenger logMessenger) {
        this.logMessenger = logMessenger;
        renderer = new TooltipRenderer(iconResource);
    }

    @Override
    public void beforeProcessingSpecification(SpecificationProcessingEvent event) {
        resource = event.getResource();
    }

    @Override
    public void afterProcessingSpecification(SpecificationProcessingEvent event) {
        resource = null;
    }

    @Override
    public void executeCompleted(ExecuteEvent event) {
        renderLogMessages(event.getElement());
    }

    @Override
    public void failureReported(AssertFailureEvent event) {
        renderLogMessages(event.getElement());
    }

    @Override
    public void successReported(AssertSuccessEvent event) {
        renderLogMessages(event.getElement());
    }

    @Override
    public void expressionEvaluated(ExpressionEvaluatedEvent event) {
        renderLogMessages(event.getElement());
    }

    @Override
    public void throwableCaught(ThrowableCaughtEvent event) {
        renderLogMessages(event.getElement());
    }

    @Override
    public void missingRow(MissingRowEvent event) {
    }

    @Override
    public void surplusRow(SurplusRowEvent event) {
    }

    private void renderLogMessages(Element element) {
        String text = logMessenger.getNewLogMessages();

        if (text.length() > 0) {
            renderer.renderTooltip(resource, element, text);
        }
    }
}
