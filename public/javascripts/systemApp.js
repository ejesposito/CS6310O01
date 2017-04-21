
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

systemApp.controller("administratorCtrl", ['$scope', '$http', function ($scope, $http) {
    
    $scope.persons = [];
    $scope.newPerson = {};
    $scope.selectedPerson = {};
    
    var config = {
        headers : {
            'Content-Type' : 'application/json',
            'Accept' : "application/json"
        }
    };
    
    $scope.listPersons = function() {
        $http.get('/api/persons', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.persons = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });  
    };
    
    $scope.createPerson = function() {
        $http.post('/api/person', $scope.newPerson, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listPersons();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.updatePerson = function() {
        $http.put('/api/person/' + $scope.selectedPerson.id, $scope.selectedPerson, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listPersons();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.deletePerson = function() {
        $http.delete('/api/person/' + $scope.selectedPerson.id, $scope.selectedPerson, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listPersons();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.listPersons();
        
}]);

systemApp.controller("instructorCtrl", ['$scope', function ($scope) {
    $scope.msg = "Instructor";
}]);

systemApp.controller("studentCtrl", ['$scope', function ($scope) {
    $scope.msg = "Student";
}]);

