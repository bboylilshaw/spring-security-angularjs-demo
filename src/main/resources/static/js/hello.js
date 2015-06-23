var app = angular.module('hello', []);

app.controller('AuthCtrl', function ($scope, $http) {
    $scope.authenticated = false;

    $scope.init = function () {
        $http.get('/api/users/current')
            .success(function (user) {
                console.log(user);
                if(user.name !== 'anonymousUser'){
                    $scope.authenticated = true;
                    $scope.username = user.username;
                }
            });
    };

    $scope.login = function () {
        $http.post('/api/login', { username: $scope.username, password: $scope.password })
            .success(function (result, status, headers) {
                if (status === 200) {
                    $scope.authenticated = true;
                }
                console.log(result);
                console.log(status);
                console.log(headers());
            });
    };

    $scope.logout = function () {
        $scope.authenticated = false;
    };
});