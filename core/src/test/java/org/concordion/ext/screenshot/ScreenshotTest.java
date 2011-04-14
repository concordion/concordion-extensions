package org.concordion.ext.screenshot;

import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.Before;
import org.junit.runner.RunWith;

import test.concordion.ext.screenshot.DummyScreenshotFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Test to check that screenshots are not overriden when suite of tests is executed
 *
 * @author Kostya Marchenko <kostya.marchenko@gmail.com>
 */

@RunWith(ConcordionRunner.class)
public class ScreenshotTest {

    private static final String CONCORDION_OUTPUT_DIR = "concordion.output.dir";

    @Before
    public void setUp() {
        //Install screenshot extension - use a dummy screenshot taker to ensure screenshots of my system don't end up online..
        System.setProperty("concordion.extensions", DummyScreenshotFactory.class.getName());  

        try {
            //Delete existing screenshots
            File[] screenshotFiles = getScreenshotFiles();
            for (File screenshotFile : screenshotFiles) {
                screenshotFile.delete();
            }
        } catch (Exception e) {
            //do nothing
        }

    }

    public int getScreenshotsCount() throws IOException {
        return getScreenshotFiles().length;
    }


    /**
     * Returns list of screenshot files in concordion test results folder
     *
     * @return array of screenshot files
     */
    private File[] getScreenshotFiles() {
        File screenshotDir = getScreenshotsLocation();
        //Construct filename filter to list only screenshot files in folder
        FilenameFilter screenshotFilesFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        };

        File[] screenshotFiles = screenshotDir.listFiles(screenshotFilesFilter);
        return screenshotFiles;
    }

    /**
     * Returns screenshot files location for current test
     *
     * @return File object representing a folder with test screenshots
     */
    private File getScreenshotsLocation() {
        String outputPath = System.getProperty(CONCORDION_OUTPUT_DIR);
        String testRelativePath = this.getClass().getPackage().getName().replace(".", "/");
        if (outputPath == null) {
            return new File(System.getProperty("java.io.tmpdir"), "concordion/" + testRelativePath);
        }
        return new File(outputPath + "/" + testRelativePath);
    }

}
