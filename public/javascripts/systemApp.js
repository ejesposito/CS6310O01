
var systemApp = angular.module('systemApp', ["ngRoute"]);

systemApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/administrator', {
        templateUrl: '/administrator',
        controller: 'administratorCtrl'
      })
      .when('/persons', {
        templateUrl: '/persons',
        controller: 'administratorCtrl'
      })
      .when('/courses', {
        templateUrl: '/courses',
        controller: 'administratorCtrl'
      })
      .when('/sessions', {
        templateUrl: '/sessions',
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
    
    $scope.coursesSessions = [];
    $scope.newCourseSession = {};
    $scope.selectedCourseSession = {};

    var config = {
        headers : {
            'Content-Type' : 'application/json',
            'Accept' : "application/json"
        }
    };

    $scope.loadCSVData = function() {
        $http.get('/loadCSV', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listPersons();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
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

    $scope.updateCourse = function() {
        $http.put('/api/course/' + $scope.selectedCourse.id, $scope.selectedCourse, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCourses();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.deleteCourse = function() {
        $http.delete('/api/course/' + $scope.selectedCourse.id, $scope.selectedCourse, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCourses();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.getTerms = function(course) {
        var stringTerms = "";
        if (course.inFall === true)
            stringTerms = stringTerms + "FALL ";
        if (course.inSpring === true)
            stringTerms = stringTerms + "SRPING ";
        if (course.inSummer === true)
            stringTerms = stringTerms + "SUMMER ";
        return stringTerms;
    };

    $scope.listCoursesSessions = function() {
        $http.get('/api/coursesessions', config)
            .success(function (data, status, headers, config) {
                console.log("courses sessions" + JSON.stringify(data) + " " + status);
                $scope.coursesSessions = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.createCourseSession = function() {
        console.log("newCourseSession: " + JSON.stringify($scope.newCourseSession));
        
        $http.post('/api/coursesession', $scope.newCourseSession, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCoursesSessions();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.updateCourseSession = function() {
        $http.put('/api/coursesession/' + $scope.selectedCourseSession.id, $scope.selectedCourseSession, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCoursesSessions();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.deleteCourseSession = function() {
        $http.delete('/api/coursesession/' + $scope.selectedCourseSession.id, $scope.selectedCourseSession, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listCoursesSessions();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.listPersons();
    $scope.listCourses();
    $scope.listCoursesSessions();
        
}]);

systemApp.controller("instructorCtrl", ['$scope', '$http', function ($scope, $http) {
    
    $scope.instructors = [];
    $scope.courseSessions = [];
    $scope.allocations = [];
    $scope.newAllocation = {};
    $scope.selectedInstructor = {};
    $scope.selectedRow = 0;
    
    var config = {
        headers : {
            'Content-Type' : 'application/json',
            'Accept' : "application/json"
        }
    };
    
    $scope.setClickedRow = function(index) {
        $scope.selectedRow = index;
        $scope.selectedInstructor = $scope.instructors[index];
    }
    
    $scope.listInstructors = function() {
        $http.get('/api/instructors', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.instructors = data;
                $scope.selectedInstructor = $scope.instructors[$scope.selectedRow];
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });  
    };
    
    $scope.listCourseSessions = function() {
        $http.get('/api/coursesessions', config)
            .success(function (data, status, headers, config) {
                console.log("courses sessions" + JSON.stringify(data) + " " + status);
                $scope.courseSessions = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.listAllocations = function() {
        $http.get('/api/allocations', config)
            .success(function (data, status, headers, config) {
                console.log("allocations" + JSON.stringify(data) + " " + status);
                $scope.allocations = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    /*$scope.listAllocations = function() {
        $http.get('/api/allocations/instructor_id?=' + $scope.selectedInstructor.person.id, config)
            .success(function (data, status, headers, config) {
                console.log("allocations" + JSON.stringify(data) + " " + status);
                $scope.allocations = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };*/

    $scope.createAllocation = function() {
        console.log("newAllocation: " + JSON.stringify($scope.newAllocation));
        $http.post('/api/allocation', $scope.newAllocation, config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.listAllocations();
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };
    
    $scope.listInstructors();
    $scope.listCourseSessions();
    $scope.listAllocations();
        
}]);

systemApp.controller("studentCtrl", ['$scope', '$http', function ($scope, $http) {

    $scope.students = [];
    $scope.currentStudent = {};
    $scope.currentStudentId = 1;
    $scope.courses = [];
    $scope.chosenCourse = {};
    $scope.enrollmentMessage = '';
    var config = {
        headers : {
            'Content-Type' : 'application/json',
            'Accept' : "application/json"
        }
    };

    $scope.listStudents = function() {
        $http.get('/api/students', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.students = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.requestCourse = function() {
        $http.get('/api/courseSession/register?studentId=' + $scope.currentStudentId.id + '&courseId=' + $scope.chosenCourse.id)
            .success(function (data, status, headers, config) {
                $scope.enrollmentMessage = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.changeStudent = function() {
        for (var i = 0; i < $scope.students.length; i++) {
            if ($scope.students[i].id === $scope.currentStudentId) {
                $scope.currentStudent = $scope.students[i];
            }
        }
    };
    $scope.listCourses = function() {
        $http.get('/api/courseSession', config)
            .success(function (data, status, headers, config) {
                console.log(JSON.stringify(data) + " " + status);
                $scope.courses = data;
            })
            .error(function (data, status, header, config) {
                console.log(JSON.stringify(data) + " " + status);
            });
    };

    $scope.listStudents();
    $scope.changeStudent();
    $scope.listCourses();
}]);

