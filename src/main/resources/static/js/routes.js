angular.module('app').config(function($stateProvider, $urlRouterProvider, $httpProvider) {

  $httpProvider.interceptors.push('authInterceptor');

  $urlRouterProvider.otherwise('/');

  $stateProvider
    .state('home', {
      url: '/',
      templateUrl: 'template/home.html',
      controller: 'HomeController'
    })
    .state('login', {
      url: '/login',
      templateUrl: 'template/login.html',
      controller: 'LoginController'
    });

});