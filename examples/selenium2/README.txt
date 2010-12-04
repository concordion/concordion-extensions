
Introduction
============

Demonstrates the usage of Concordion extensions with Selenium2 (also known as WebDriver).

1. LoggingDemo demonstrates the LoggingTooltipExtension. 
   The log output is written by the SeleniumEventLogger class, which acts as a WebDriverEventListener.

2. ScreenshotDemo demonstrates the ScreenshotExtension.
   The SeleniumScreenshotTaker class uses Selenium's TakesScreenshot interface to take a 
   custom screenshot of the web page.

NOTE: Most of the demo code has been written using Groovy.  This is not required, and the code could have been written with plain Java instead. 

Running the Concordion tests
============================
The tests use Selenium's FirefoxDriver, so rely on Firefox being installed.

You can run build the tests using either Gradle or Maven.  

Using Gradle:
  - Download and install gradle 0.9-rc-1
  - Unzip this package
  - From a command line: 
        gradle test
  - view the Concordion output under the subfolder 'build/reports/spec/org/concordion/ext/demo/selenium/' of the project folder

Using Maven:
  - Download and install maven
  - Unzip this package
  - From a command line: 
        mvn test
  - view the Concordion output under the subfolder 'target/concordion/org/concordion/ext/demo/selenium/' of the project folder 


Generating an Eclipse Project
=============================
        
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
  
