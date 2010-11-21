package nz.co.twoten.concordion.annotate;

import java.io.ByteArrayOutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import nz.co.twoten.jul.formatter.TimeAndMessageFormatter;

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

public class TooltipRenderingListener implements AssertEqualsListener, AssertTrueListener, AssertFalseListener, ExecuteListener,
        SpecificationProcessingListener, VerifyRowsListener, ThrowableCaughtListener {

    private final ByteArrayOutputStream baos;
    private final StreamHandler streamHandler;
    private final TooltipRenderer renderer;

    private Resource resource;

    public TooltipRenderingListener(Resource iconResource, String loggerNames, Level loggingLevel, boolean displayRootConsoleLogging) {
        baos = new ByteArrayOutputStream(4096);
        streamHandler = new StreamHandler(baos, new TimeAndMessageFormatter());
        streamHandler.setLevel(loggingLevel);
        for (String loggerName : loggerNames.split(",")) {
            Logger logger = Logger.getLogger(loggerName.trim());
            logger.addHandler(streamHandler);
        }
        renderer = new TooltipRenderer(iconResource);
        if (!displayRootConsoleLogging) {
            removeRootConsoleHandler();
        }
    }

    private  void removeRootConsoleHandler() {
        Logger logger = Logger.getLogger("");
        Handler[] handlers = logger.getHandlers();
        for (Handler handler : handlers) {
            if (handler instanceof ConsoleHandler) {
                System.out.println("LoggingTooltipExtension: removing root console logging handler");
                logger.removeHandler(handler);
            }
        }
    }

    @Override
    public void beforeProcessingSpecification(SpecificationProcessingEvent event) {
        resource = event.getResource();
    }

    @Override
    public void afterProcessingSpecification(SpecificationProcessingEvent event) {
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
        String text = getLogMessages();

        if (text.length() > 0) {
            renderer.hoverText(resource, element, text);
        }
    }

    private String getLogMessages() {
        streamHandler.flush();
        String text = baos.toString();
        baos.reset();
        return text;
    }
}
