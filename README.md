## Spring Security AngularJS demo application

[![Build Status](https://travis-ci.org/bboylilshaw/spring-security-angularjs-demo.svg?branch=master)](https://travis-ci.org/bboylilshaw/spring-security-angularjs-demo)

This demo shows you how to use Spring Boot(1.5.2.RELEASE), Spring Security and JWT to protect your backend RESTful web services.


### Overview

On server side, we use Spring Boot + Spring Data Rest to expose RESTful web service api, and Spring Security to protect the RESTful web service api.
On client side, we use AngularJS to consume RESTful api to get server resources.

Client needs to call `/api/login` to login first. If it succeeds, server will return a JWT auth token along with response header. Client stores the auth token to broswer's `localStorage` and add it to request headers of all following api calls.

Server is stateless, meaning it needs to authenticate user for every request by looking up the auth token header on request header list. If it authenticates successfully, server will return the resources, otherwise server denied the request and return `401` error.


### Dependencies

you need to install below dependencies to run this demo app.

* [jdk 1.8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [bower](https://bower.io/)

### Technologies

* Spring Boot (1.5.2.RELEASE)
* Spring Security
* Spring Data Rest
* AngularJS

### How to run this demo app

1. use `git clone` to get this repo to your local machine
2. cd into the project directory
2. run `bower install` to install angularjs
3. run `./gradlew clean bootRun` to start this demo app
4. open your browser and visit `http://localhost:8080`, use `jason/password` to login
