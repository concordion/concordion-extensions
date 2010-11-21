package nz.co.twoten.concordion.screenshot;

import java.io.IOException;
import java.io.OutputStream;

import nz.co.twoten.concordion.ScreenshotExtension;
import nz.co.twoten.concordion.ScreenshotTaker;
import nz.co.twoten.concordion.ScreenshotUnavailableException;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.Resource;
import org.concordion.api.ResultRecorder;
import org.concordion.api.Target;
import org.concordion.api.listener.AssertEqualsListener;
import org.concordion.api.listener.AssertFailureEvent;
import org.concordion.api.listener.AssertFalseListener;
import org.concordion.api.listener.AssertSuccessEvent;
import org.concordion.api.listener.AssertTrueListener;
import org.concordion.api.listener.ConcordionBuildEvent;
import org.concordion.api.listener.ConcordionBuildListener;
import org.concordion.api.listener.SpecificationProcessingEvent;
import org.concordion.api.listener.SpecificationProcessingListener;
import org.concordion.api.listener.ThrowableCaughtEvent;
import org.concordion.api.listener.ThrowableCaughtListener;
import org.concordion.internal.util.Check;

public class ScreenshotEmbedder extends AbstractCommand implements AssertEqualsListener, AssertTrueListener, AssertFalseListener, ConcordionBuildListener, SpecificationProcessingListener, ThrowableCaughtListener {

    private int index = 1;
    private ScreenshotTaker screenshotTaker = new RobotScreenshotTaker();
    private int maxWidth = 600;
    private boolean screenshotOnAssertionFailure = true;
    private boolean screenshotOnAssertionSuccess = false;
    private boolean screenshotOnThrowable = true;
    
    private Resource resource;
    private Target target;
    
    @Override
    public void successReported(AssertSuccessEvent event) {
        if (screenshotOnAssertionSuccess) {
            addScreenshotTo(event.getElement(), true);
        }
    }

    @Override
    public void failureReported(AssertFailureEvent event) {
        if (screenshotOnAssertionFailure) {
            addScreenshotTo(event.getElement(), true);
        }
    }

    @Override
    public void throwableCaught(ThrowableCaughtEvent event) {
        if (screenshotOnThrowable) {
            addScreenshotTo(event.getElement(), true);
        }
    }

    @Override
    public void execute(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        Check.isFalse(commandCall.hasChildCommands(), "Nesting commands inside an 'screenshot' is not supported");
        Element element = commandCall.getElement();

        String props = element.getAttributeValue(ScreenshotExtension.COMMAND_NAME, ScreenshotExtension.EXTENSION_NAMESPACE);
        
        boolean linked = false;
        if ("linked".equals(props)){
            linked = true;
        }

        addScreenshotTo(element, linked);
    }
    
    private void addScreenshotTo(Element element, boolean hidden) {
        String imageName = getNextImageName();
        Resource imageResource = resource.getRelativeResource(imageName);
        
        int imageWidth;
        try {
            OutputStream outputStream = target.getOutputStream(imageResource);
            imageWidth = screenshotTaker.writeScreenshotTo(outputStream);
            new ImageRenderer(hidden, maxWidth).addImageToElement(element, imageName, imageWidth);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ScreenshotUnavailableException ignore) {
        }
    }

    private String getNextImageName() {
        String fileExtension = getFileExtension();
        return String.format("img%d.%s", index++, fileExtension);
    }

    private String getFileExtension() {
        return screenshotTaker.getFileExtension();
    }

    @Override
    public void beforeProcessingSpecification(SpecificationProcessingEvent event) {
        resource = event.getResource();
    }

    @Override
    public void afterProcessingSpecification(SpecificationProcessingEvent event) {
    }

    @Override
    public void concordionBuilt(ConcordionBuildEvent event) {
        target = event.getTarget();
    }

    public void setScreenshotTaker(ScreenshotTaker screenshotTaker) {
        this.screenshotTaker = screenshotTaker;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public void setScreenshotOnAssertionFailure(boolean takeShot) {
        this.screenshotOnAssertionFailure = takeShot;
    }

    public void setScreenshotOnAssertionSuccess(boolean takeShot) {
        this.screenshotOnAssertionSuccess = takeShot;
    }
    
    public void setScreenshotOnThrowable(boolean takeShot) {
        this.screenshotOnThrowable = takeShot;
    }

    public String getCSS() {
        return ImageRenderer.CSS;
    }
    
    public String getJavaScript() {
        return ImageRenderer.script;
    }
}
