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
