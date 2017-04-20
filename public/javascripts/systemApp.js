
var systemApp = angular.module('systemApp', ["ngRoute"]);

systemApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/administrator', {
        templateUrl: '/administrator',
        controller: 'administratorCtrl'
      })
      .when('/instructor', {
        templateUrl: '/instructor',
        controller: 'instructorCtrl'
      })
      .when('/student', {
        templateUrl: '/student',
        controller: 'studentCtrl'
      });
}]);

systemApp.controller('mainCtrl', ['$scope', '$route', '$routeParams', function ($scope, $route, $routeParams) {
    $scope.msg = "Main";
}]);

systemApp.controller("administratorCtrl", ['$scope', function ($scope) {
    $scope.msg = "Administrator";
}]);

systemApp.controller("instructorCtrl", ['$scope', function ($scope) {
    $scope.msg = "Instructor";
}]);

systemApp.controller("studentCtrl", ['$scope', function ($scope) {
    $scope.msg = "Student";
}]);

