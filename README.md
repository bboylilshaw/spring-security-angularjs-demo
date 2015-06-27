## Overview

This demo shows you how to use Spring Security(4.0.1) and JWT to protect your RESTful service.
On Client side, we use AngularJS to consume RESTful web service api to get server resources.

In general, client first call server to login or signup, then server will return a valid token to client.
Client must include this valid token to http headers in following calls to authenticate.
Server will block all requests if they don't find a token in headers except for login/signup case.

## Dependencies

you need to install below dependencies to run this app.

* JDK 1.8
* MySQL 5.6.x (you can also change the pom.xml to use H2/HSQLDB)
* Maven 3.1.x
* Bower (you need have Nodejs installed, and use npm to install bower)

## Technologies

* Spring Boot (1.2.4)
* Spring Security (4.0.1)
* Spring Data Rest
* AngularJS

## How to run this demo app

1. check out this repo to your local machine
2. cd to 'src/main/resources/static', run 'bower install' to install angularjs
3. config application.properties if you have to
4. run 'mvn spring:boot:run' to start this demo app