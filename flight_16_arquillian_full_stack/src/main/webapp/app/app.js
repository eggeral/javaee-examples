var app = angular.module('flight', ['ngRoute']);
app.factory("flightService", ['$http', function ($http) {
    var serviceBase = "webresources/";
    var obj = {};

    obj.flights = [
        {
            id: 1,
            flightNumber: "OS201",
            fromAirport: "FRZ",
            toAirport: "DUS"
        },
        {
            id: 2,
            flightNumber: "LH5434",
            fromAirport: "VIE",
            toAirport: "MUC"
        }
    ];

    obj.nextId = 3;

    obj.getFlights = function () {
        return $http.get(serviceBase + "flights").then(function (result) {
            return result.data;
        });
        // return obj.flights;

    };

    obj.getFlight = function (flightId) {
        return $http.get(serviceBase + "flights/" + flightId).then(function (result) {
            return result.data;
        });
        // var flights = obj.flights.filter(
        //     function (element) {
        //         return element.id == flightId
        //     }
        // );
        // return flights[0];
    };

    obj.insertFlight = function (flight) {
        return $http.post(serviceBase + "flights", flight).then(function (result) {
            return result.data;
        });

        // flight.id = obj.nextId;
        // obj.flights.push(flight);
        // obj.nextId++;

    };

    obj.updateFlight = function (flight) {
        return $http.put(serviceBase + "flights/" + flight.id, flight).then(function (result) {
            return result.status;
        });
        // var original = obj.getFlight(flight.id);
        // original.flightNumber = flight.flightNumber;
        // original.fromAirport = flight.fromAirport;
        // original.toAirport = flight.toAirport;
    };

    obj.deleteFlight = function (flight) {
        return $http.delete(serviceBase + "flights/" + flight.id).then(function (result) {
            return result.status;
        });

        // var idx;
        // for (idx = 0; idx < obj.flights.length; idx++) {
        //     if (obj.flights[idx].id == flight.id)
        //         break;
        // }
        // obj.flights.splice(idx, 1);
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

    flightService.getFlight().then(function (flight) {
        $scope.flight = flight;
    });


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