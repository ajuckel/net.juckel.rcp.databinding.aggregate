About
=====

This project is a set of useful additions to the Eclipse Databinding Properties
API which provides some useful extensions for more complex databinding into
structured viewers using the ObservableMaps API.  For example, if your content
provider returns instances of Student, and each Student contains a list of
TestResult objects, and each TestResult has a numerical score property, you
can use this API to show the min, max and average of each Students' TestResults
in a TableViewer.

Code examples are provided in the test project.

Building
========

To build from the command line, you must use Maven 3.0 (tested with 3.0b3).

  $ mvn clean install

Otherwise, simply import the projects into Eclipse.  You must be targetting 
a 3.5-based target platform.

