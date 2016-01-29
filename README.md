# Headless Browser Chromium Automation Project

This project will walk you through the process of starting a headless chromium browser using a REST API application which can start several threads and execute basic commands, such as opening urls, navigating to certain links, executing javascript in the site, etc.

##  Dependencies

You need to install:
*  Xvfb
*  JDK 1.7 or higher
*  maven
*  google-chromium
*  python


## Building

Just use mvn package on the root directory of the GenericBrowserTest project.

## Making chromium headless

Xvfb allows you to create a "fake" screen where all the output of the chrome instance will go.

	Xvfb :1 -screen 5 1024x768x8 &

	export DISPLAY=:1.5

You need to run the export on the terminal that launches the REST API (BrowserTest-snapshot). This indicates the java app to use this "fake" screen for rendering the browser.

You need to either have chrome in /usr/lib/chromium-browser/chromium-browser, or have a symbolic link in place...

Afterwards run java -jar target/BrowserTest-1.0-SNAPSHOT.jar from the same terminal where you exported the display.


## Format of messages

This is an example of a message preparing 1 thread of chrome opening localhost:8080 and wait 50 seconds and then close the tabs

The prepare message opens the browser, but it doesn't execute the commands yet.

  {"id": 1, "action":"PREPARE_BROWSER", "parameters": {"chrome":{"instructions":"open:http://localhost:8080;wait:50;close"}}}

To execute the commands you have to send a message like this:

   {"id":1, "action":"RUN"}

Make sure to send the same id that you sent when you executed the prepare statement. In this way the bot will be able to send commands to the right browser :)









