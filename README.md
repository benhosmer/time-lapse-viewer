# time-lapse-viewer
An on-demand, imagery flipbook...

## Requirements:

This application was built using the following:
- FFMPEG v2.1.4
- ImageMagick v6.9.1
- Java v1.7.0_45 (JDK)
- Groovy v2.3.7
- Grails v2.5.0

## Isolated Networks

If you are are configuring this to run on a stand-alone network without access to the internet, execute a "grails run-app" to create and populate the "mavenRepo" directory with the necessarily libraries before packaging. 

## Building a Stand-Alone .war for Deployment

From within the `timeLapse` directory of this repository, use `$ ./grailsw prod war timeLapse.war` to create a complete stand-alone .war file for deployment.

## Configuration Files

_Time Lapse Views_ reads configurations from a number of locations:

1. ???
2. ???
3. ???

