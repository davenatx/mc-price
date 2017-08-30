# McPrice Wedding Website

**The mc-price.com website is a web application built with Scala using the Lift web framework.  The CSS styles are compiled with LESS and are based on Twitter Bootstrap 3.3.7.**

## Overview

This web application is served by the Jetty application server.  Nginx is the web server that sits in front of it.

Presently, the database layer is not in use.  However, I left the hooks for it incase I need it in the future.

## Lift

Lift's templating system is used to satisfy the DRY (Don't Repeat Yourself) principle for the html markup.  First, in the ```src/main/webapp/templates-hidden``` directory, the header, footer and navigation are contained in separate html files.  Next, ```default.html``` is provided as the template for each specific page.  In other words, ```default.html``` is provided as a template that combines the header, footer and navigation while providing a div for the other pages to attach to.  The standard web pages are merged into this default template, so they are constructed without worrying about maintaining common features (header, footer, navigation) across each page.

Next, the ```src/main/webapp/my-templates``` directory contains html templates loaded by different pieces of the application.  For example, the structure of the Notice message is a template in this directory.  This is structured this way so these items can be changed by modifying these templates instead of re-compiling the application.

## SBT

The Simple Build Tool (SBT) is used for dependency management and to build this project.  It not only retrieves the dependencies, but it compiles the Scala code, compiles the LESS files and combines and minifies the Javascript.  It also uses the ```xsbt-web-plugin``` to run the application with an embedded version of Jetty and packages the project as a WAR file.

To use this project, one must navigate to this directory in a terminal and enter ```sbt``` to launch the build tool.  The basic SBT tasks are the following:

* ```clean``` - Removes compiled files in the target directory

* ```webStage``` - Runs the LESS compiler to compile and minify the CSS and uses Uglify to combine and minify Javascript

* ```compile``` - Compiles the Scala code

* ```jetty:start``` - Launches the embedded Jetty server on port 8080

* ```jetty:stop``` - Stops the embedded Jetty server

* ```package``` - Packages the project in a WAR file.  This task also includes the minified ```styles.css```, minified ```script.js```, production ```production.default.logback.xml``` 
file and finally the ```production.default.props``` file.

## LESS/CSS

LESS is used for the styling of this project.  The main file is under ```src/main/assets/styles/styles.less```.  The LESS compiler is run by SBT during the ```webStage``` task.

## Uncss

Uncss is configured as a Grunt task under the ```uncss``` directory.  This process is not managed by SBT and is run independently.  This directory contains a standalone Grunt process to run uncss over this project.  This means NodeJs and Grunt must be installed to use this process.

There appears to be some compatibility issues with the more recent version of grunt-uncss.  Specifically, version 0.4.4 does not properly load the URLS in this project.  However, specifying version 0.4.3 works.  Here are the instructions to install Grunt and the required plugins with specific versions:

1. ```sudo npm install -g grunt-cli```

2. ```npm install grunt-uncss grunt-contrib-cssmin --save-dev```

Show npm versions: ```npm list --depth=0```

The purpose of using uncss is to eliminate the Bootstrap CSS this project is not using.  However, there are some difficulties with this approach.  Specifically, I had to identify the selectors that are added with Javascript, like the Bootstrap dropdown, etc. They are placed in the Gurnfile.js ignore section to ensure they are included with the css file produced by uncss. 

The next issue with integrating uncss with this project is Lift's designer friendly templates.  As I understand it, uncss renders an entire page with PhantomJS to determine which css selectors are in use.  However, the Lift templates are not entire pages.  Therefore, I need to actually launch the lift project in Jetty and point uncss to the different pages running under ```http://localhost:8080``` to retrieve the complete pages.

Due to the reasons I outlined above, I have not incorporated this into the build process in SBT.  Running uncss is a manual task:

1. From within SBT perform a ```jetty:start```

2. Open a command line and navigate into the ```uncss``` directory under the root of this project

3. Run ```grunt```

4. The ```uncss.css``` file represents the css selectors used in this application

5. The ```styles.css``` file is the version of ```uncss.css``` file with unused elements removed.  The css in this file is also minified.

6. After SBT is used to generate a war file, move the war file to a staging directory.  Under this staging directory, also create a directory structure like this: ```media/styles``` and 
place the ```styles.css``` generated by uncss in it.  Finally, use the Java jar utility to add this styles.css to the war file:  ```jar uvf mc-price_2.11-0.1.war media/styles/styles.css``` replacing the one it already contained