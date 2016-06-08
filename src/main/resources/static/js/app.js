var app = angular.module('demo', [
    'ui.router'
]);

app.factory('TokenStorage', function() {
    var storageKey = 'auth_token';
    return {
        store: function(token) {
            return localStorage.setItem(storageKey, token);
        },
        retrieve: function() {
            return localStorage.getItem(storageKey);
        },
        clear: function() {
            return localStorage.removeItem(storageKey);
        }
    };
});

app.factory('TokenAuthInterceptor', function($q, TokenStorage) {
    return {
        request: function(config) {
            var authToken = TokenStorage.retrieve();
            if (authToken) {
                config.headers['X-AUTH-TOKEN'] = authToken;
            }
            return config;
        },
        responseError: function(error) {
            if (error.status === 401 || error.status === 403) {
                TokenStorage.clear();
            }
            return $q.reject(error);
        }
    };
});

app.config(function($httpProvider, $stateProvider, $urlRouterProvider) {
    $httpProvider.interceptors.push('TokenAuthInterceptor');
    // For any unmatched url, redirect to /
    $urlRouterProvider.otherwise('/');
    // Now set up the states
    $stateProvider
        .state('login', {
            url: '/',
            templateUrl: 'template/login.html',
            controller: 'authCtrl'
        })
        .state('books', {
            url: "/books",
            templateUrl: "template/book.html",
            controller: 'bookCtrl'
        });
});

app.controller('authCtrl', function ($scope, $http, $state, TokenStorage) {
    $scope.authenticated = false;

    $scope.init = function () {
        $http.get('/api/user/current')
            .success(function (user) {
                console.log(user);
                if(user.name !== 'anonymousUser'){
                    $scope.authenticated = true;
                    $state.go('books');
                }
            });
    };

    $scope.login = function () {
        var user = {
            username: $scope.username,
            password: $scope.password
        };

        $http.post('/api/login', user)
            .success(function (data, status, headers) {
                if (status === 200) {
                    $scope.authenticated = true;
                    TokenStorage.store(headers('X-AUTH-TOKEN'));
                    $state.go('books');
                }
            })
            .error(function (data) {
                console.log(data);
            });
    };
});

app.controller('bookCtrl', function ($scope, $http, $state, TokenStorage) {
    $scope.username = JSON.parse(atob(TokenStorage.retrieve().split('.')[1])).sub;
    $http.get('/api/books')
        .success(function (data) {
            console.log(data);
            $scope.books = data._embedded.books;
        });
    $scope.logout = function () {
        TokenStorage.clear();
        $scope.authenticated = false;
        $state.go('login');
    };
});