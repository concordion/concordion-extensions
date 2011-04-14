package org.concordion.ext.screenshot;

import org.concordion.api.*;
import org.concordion.api.listener.*;
import org.concordion.ext.ScreenshotExtension;
import org.concordion.ext.ScreenshotTaker;
import org.concordion.ext.ScreenshotUnavailableException;
import org.concordion.internal.util.Check;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

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
        String fileName = "img"+(new Random()).nextLong();
        String fileExtension = getFileExtension();

        return String.format("%s.%s", fileName, fileExtension);
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
