# PrinterQueue
Travis-ci:     
[![Build Status](https://travis-ci.org/cpe305/fall2016-project-johnnicholson.svg?branch=master)](https://travis-ci.org/cpe305/fall2016-project-johnnicholson)

## Purpose
This Project was inspired by a friend who volunteers for the Innovation Sandbox
and fellow officers for the cyber security club at Cal Poly when both needed
a better way of organizing requests to use their 3D Printer. Previously the
Sandbox used a google doc and local document storage.

## Design
This project is backed by a tomcat server managed by spring boot. The front
end is written in angular. It communicates with the server via a REST API that
is documented in the linked document.

## Use
Once setup on a server, it can keep track of various locations and individual
queues for each. Theses queues can be managed and the status of each print can
be adjusted. 

## Rest Documents
* [All](docs/3DPrinterQueue.md)
