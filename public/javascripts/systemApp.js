
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
    
    $scope.courses = [];
    $scope.newCourse = {};
    $scope.selectedCourse = {};
    
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
        var stringRole = new Array();
        if ($scope.newPerson.isAdministrator === 'true')
            stringRole.push("ADMINISTRATOR");
        if ($scope.newPerson.isInstructor === 'true')
            stringRole.push("INSTRUCTOR");
        if ($scope.newPerson.isStudent === 'true')
            stringRole.push("STUDENT");
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
    
    $scope.getRoles = function(person) {
        var stringRoles = "";
        console.log("Selected: " + JSON.stringify(person.roles));
        angular.forEach(person.roles, function(role){
            console.log(role.type);
            stringRoles = stringRoles + " " + role.type;
        });
        return stringRoles;
    };
    
    $scope.listCourses = function() {
        $http.get('/api/courses', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.courses = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });  
    };
    
    $scope.createCourse = function() {
        $http.post('/api/course', $scope.newCourse, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCourses();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.listPersons();
    $scope.listCourses();
        
}]);

systemApp.controller("instructorCtrl", ['$scope', '$http', function ($scope, $http) {
    
    $scope.instructors = [];
    $scope.newInstructor = {};
    $scope.selectedInstructor = {};
    
    var config = {
        headers : {
            'Content-Type' : 'application/json',
            'Accept' : "application/json"
        }
    };
    
    $scope.listInstructors = function() {
        $http.get('/api/instructors', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.instructors = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });  
    };
    
    $scope.listCourses = function() {
        $http.get('/api/courses', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.courses = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });  
    };
    
    $scope.listInstructors();
    $scope.listCourses();
        
}]);

systemApp.controller("studentCtrl", ['$scope', function ($scope) {
    $scope.msg = "Student";
}]);

