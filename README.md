# Java Developer Programming Challenge
A set of Java coding tasks to assess Java programming and design skills. This document outlines how to build and use the application and what is required for the challenge.

## Overview of the Challenge
The following is a simple Java project which does very little. The Person class can be instanciated and populated but is not properly displayed and immediately lost. The project has a number of known issues some bugs and many other non-functional issues with respect to robustness and usability which could be improved.

The primary task is to resolve the issues and submit the solutions in pull requests to be merged into master.

## Build
To build the application, clone this repository and execute a standard maven build on the HEAD of the master branch. This will produce a jar file in the target folder.

## Usage
The application has a simple command line interface which is run using a java -jar command on the jar file produced in the build step above. It is completely undocumented, to discover how it is used you must examine the code in main().

## Goals
* The three open issues should be resolved in separate pull requests.
* Each Pull request must be based on the same (initial) version of the code as at the HEAD of master, such that they can be accepted independently of one another.
* Make improvements to the existing code not covered in any issue. There are areas of the code that may have bugs or could be improved by refactoring. Submit pull requests with descriptions that describe the problem and your solution.

## Constraints
* It is expected that the tasks can be completed within a couple of hours but the Pull requests must be submitted within 24 hours.
* Observe the coding convensions in the legacy code and maintain consistent coding style in any code you submit.
