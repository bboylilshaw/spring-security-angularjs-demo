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
        config.headers['X-Auth-Token'] = TokenStore.get();
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

app.controller('HomeController', function ($rootScope, $scope, $state, $http, TokenStore) {
  if ($rootScope.currentUser === undefined) {
    if (TokenStore.get()) {
      $http.get("/api/user/current").then(function (response) {
        console.log('get current user...');
        $rootScope.currentUser = response.data;
      });
    } else {
      console.log('not authenticated');
      $state.go('login');
    }
  }

  $scope.logout = function () {
    TokenStore.clear();
    delete $rootScope.currentUser;
    $state.go('login')
  };

  $http.get('/api/books').then(function (response) {
    $scope.books = response.data._embedded.books;
  });

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

