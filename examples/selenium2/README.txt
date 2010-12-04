
Demonstrates the usage of Concordion extensions with Selenium2 (also known as WebDriver).

1. LoggingDemo demonstrates the LoggingTooltipExtension. 
   The log output is written by the SeleniumEventLogger class, which acts as a WebDriverEventListener.

2. ScreenshotDemo demonstrates the ScreenshotExtension.
   The SeleniumScreenshotTaker class uses Selenium's TakesScreenshot interface to take a 
   custom screenshot of the web page.

To build and run the Concordion tests:
  - Download and install gradle 0.9-rc-1
  - Unzip this package
  - From a command line: 
        gradle test
        
To generate an Eclipse project:
  - Download and install gradle 0.9-rc-1
  - Unzip this package
  - From a command line: 
        gradle eclipse
  - From the Eclipse menu Window > Preferences, select Java > Build > Classpath Variables
  - add a new variable GRADLE_CACHE with a path of the .gradle/cache folder under your user home folder
  - import this project into Eclipse (File > Import > Existing Project into Workspace, select this project)
  - run the tests LoggingDemoTest and ScreenshotDemoTest using Run As > JUnit Test

The dependencies are listed in build.gradle, and will be downloaded by Gradle.  
  
