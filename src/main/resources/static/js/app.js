var app = angular.module('app', [
  'ui.router'
]);

app.factory('TokenStore', function ($window) {
  var storageKey = 'auth_token';
  return {
    save: function (token) {
      return $window.localStorage.setItem(storageKey, token);
    },
    get: function () {
      return $window.localStorage.getItem(storageKey);
    },
    clear: function () {
      return $window.localStorage.removeItem(storageKey);
    }
  };
});

app.factory('authInterceptor', function ($rootScope, $q, TokenStore) {
  return {
    request: function (config) {
      config.headers = config.headers || {};
      if (TokenStore.get()) {
        config.headers['X-AUTH-TOKEN'] = TokenStore.get();
      }
      return config;
    },
    response: function (response) {
      if (response.status === 401) {
        // handle the case where the user is not authenticated
      }
      return response || $q.when(response);
    }
  };
});

app.controller('HomeController', function ($rootScope, $scope, $state, TokenStore) {
  if ($rootScope.currentUser === undefined) {
    console.log('not authenticated');
    $state.go('login');
  }

  $scope.logout = function () {
    TokenStore.clear();
    delete $rootScope.currentUser;
    $state.go('login')
  };

});

app.controller('LoginController', function ($rootScope, $scope, $http, $state, TokenStore) {
  if ($rootScope.currentUser) {
    console.log('already logged in');
    $state.go('home');
  }

  $scope.login = function () {
    console.log('login...');
    $http.post('/api/login', {
      username: $scope.username,
      password: $scope.password
    }).then(function (response) {
      var authToken = response.headers()['x-auth-token'];
      if (authToken) {
        TokenStore.save(authToken);
        //get current user
        return $http.get('/api/user/current')
      }
    }).then(function (response) {
      $rootScope.currentUser = response.data;
      $state.go('home');
    });
  }

});


//
// app.factory('TokenAuthInterceptor', function($q, $state, TokenStorage) {
//     return {
//         request: function(config) {
//             var authToken = TokenStorage.retrieve();
//             if (authToken) {
//                 config.headers['X-AUTH-TOKEN'] = authToken;
//             }
//             return config;
//         },
//         responseError: function(error) {
//             if (error.status === 401 || error.status === 403) {
//                 TokenStorage.clear();
//                 $state.go('login');
//             }
//             return $q.reject(error);
//         }
//     };
// });
//
// app.config(function($httpProvider, $stateProvider, $urlRouterProvider) {
//     $httpProvider.interceptors.push('TokenAuthInterceptor');
//     // For any unmatched url, redirect to /
//     $urlRouterProvider.otherwise('/');
//     // Now set up the states
//     $stateProvider
//         .state('login', {
//             url: '/',
//             templateUrl: 'template/login.html',
//             controller: 'authCtrl'
//         })
//         .state('books', {
//             url: "/books",
//             templateUrl: "template/book.html",
//             controller: 'bookCtrl'
//         });
// });
//
// app.controller('authCtrl', function ($scope, $http, $state, TokenStorage) {
//     $scope.authenticated = false;
//
//     $scope.init = function () {
//         $http.get('/api/user/current')
//             .success(function (user) {
//                 console.log(user);
//                 if(user.name !== 'anonymousUser'){
//                     $scope.authenticated = true;
//                     $state.go('books');
//                 }
//             });
//     };
//
//     $scope.login = function () {
//         var user = {
//             username: $scope.username,
//             password: $scope.password
//         };
//
//         $http.post('/api/login', user)
//             .success(function (data, status, headers) {
//                 if (status === 200) {
//                     $scope.authenticated = true;
//                     TokenStorage.store(headers('X-AUTH-TOKEN'));
//                     $state.go('books');
//                 }
//             })
//             .error(function (data) {
//                 console.log(data);
//             });
//     };
// });
//
// app.controller('bookCtrl', function ($scope, $http, $state, TokenStorage) {
//     $scope.username = JSON.parse(atob(TokenStorage.retrieve().split('.')[1])).sub;
//     $http.get('/api/books')
//         .success(function (data) {
//             console.log(data);
//             $scope.books = data._embedded.books;
//         });
//     $scope.logout = function () {
//         TokenStorage.clear();
//         $scope.authenticated = false;
//         $state.go('login');
//     };
// });