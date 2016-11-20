var app = angular.module('flight', ['ngRoute']);
app.factory("flightService", ['$http', function ($http) {
    var serviceBase = "webresources/";

    var obj = {};

    obj.getFlights = function () {
        return $http.get(serviceBase + "flights").then(function (result) {
            return result.data;
        });
    };

    obj.getFlight = function (flightId) {
        return $http.get(serviceBase + "flights/" + flightId).then(function (result) {
            return result.data;
        });
    };

    obj.insertFlight = function (flight) {
        return $http.post(serviceBase + "flights", flight).then(function (result) {
            return result.data;
        });
    };

    obj.updateFlight = function (flight) {
        return $http.put(serviceBase + "flights/" + flight.id, flight).then(function (result) {
            return result.status;
        });
    };

    obj.deleteFlight = function (flight) {
        return $http.delete(serviceBase + "flights/" + flight.id).then(function (result) {
            return result.status;
        });
    };

    return obj;
}]);

app.controller('flightListController', function ($scope, flightService) {
    flightService.getFlights().then(function (flights) {
        $scope.flights = flights;
    });
});

app.controller('flightController', function ($scope, $location, flightService, flight) {
    $scope.flight = angular.copy(flight);

    $scope.deleteFlight = function (flight) {
        flightService.deleteFlight(flight).then(function () {
            $location.path('/');
        });
    };

    $scope.saveFlight = function (flight) {
        if (flight.id > 0) {
            flightService.updateFlight(flight).then(function () {
                $location.path('/');
            });
        }
        else {
            flightService.insertFlight(flight).then(function () {
                $location.path('/');
            });
        }
    };
});

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'partials/flights.html',
                controller: 'flightListController'
            })
            .when('/edit-flight/:flightId', {
                templateUrl: 'partials/edit-flight.html',
                controller: 'flightController',
                resolve: {
                    flight: function (flightService, $route) {
                        var flightId = parseInt($route.current.params.flightId);
                        if (flightId > 0)
                            return flightService.getFlight(flightId);
                        else
                            return {id: 0}
                    }
                }
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);
app.run();