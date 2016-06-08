## Overview

This demo shows you how to use Spring Boot(1.3.5.RELEASE), Spring Security and JWT to protect your RESTful services.
On Client side, we use AngularJS to consume RESTful webservice api to get server resources.

In general, client first call server to login or signup, then server will return a valid token to client.
Client must include this valid token to http headers in following calls to authenticate.
Server will block all requests if it cannot find a valid token in headers except for login/signup cases.

## Dependencies

you need to install below dependencies to run this demo app.

* JDK 1.8
* Gradle 2.13
* Node.js (actually we only use npm to install dependencies)
* Bower (after installing Node.js, use npm to install bower)

## Technologies

* Spring Boot (1.3.5.RELEASE)
* Spring Security
* Spring Data Rest
* AngularJS

## How to run this demo app

1. check out this repo to your local machine
2. cd to 'src/main/resources/static', run 'bower install' to install angularjs
3. config application.properties if you have to
4. run './gradlew clean bootRun' to start this demo app
5. open your browser and visit 'http://localhost:8080', use 'jason/password' to login
