var app = angular.module('flight', ['ngRoute']);
app.factory("services", [function () {
    var obj = {};

    obj.flights = [
        {
            id: 1,
            flightNumber: "OS201",
            from: "FRZ",
            to: "DUS"
        },
        {
            id: 2,
            flightNumber: "LH5434",
            from: "VIE",
            to: "MUC"
        }
    ];
    obj.nextId = 3;

    obj.getFlights = function () {
        return obj.flights;
    };

    obj.getFlight = function (flightId) {
        var flights = obj.flights.filter(
            function (element) {
                return element.id == flightId
            }
        );
        return flights[0];
    };

    obj.insertFlight = function (flight) {
        flight.id = obj.nextId;
        obj.flights.push(flight);
        obj.nextId++;
    };

    obj.updateFlight = function (flight) {
        var original = obj.getFlight(flight.id);
        original.flightNumber = flight.flightNumber;
        original.from = flight.from;
        original.to = flight.to;
    };

    obj.deleteFlight = function (flight) {
        var idx;
        for (idx = 0; idx < obj.flights.length; idx++) {
            if (obj.flights[idx].id == flight.id)
                break;
        }
        obj.flights.splice(idx, 1);
    };

    return obj;
}]);

app.controller('listCtrl', function ($scope, services) {
    $scope.flights = services.getFlights();
});

app.controller('editCtrl', function ($scope, $rootScope, $location, $routeParams, services, flight) {
    $scope.flight = angular.copy(flight);

    $scope.deleteFlight = function (flight) {
        $location.path('/');
        services.deleteFlight(flight);
    };

    $scope.saveFlight = function (flight) {
        $location.path('/');
        if (flight.id > 0) {
            services.updateFlight(flight);
        }
        else {
            services.insertFlight(flight);
        }
    };
});

app.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/', {
            title: 'Flights',
            templateUrl: 'partials/flights.html',
            controller: 'listCtrl'
        })
            .when('/edit-flight/:flightId', {
                title: 'Edit Flight',
                templateUrl: 'partials/edit-flight.html',
                controller: 'editCtrl',
                resolve: {
                    flight: function (services, $route) {
                        var flightId = $route.current.params.flightId;
                        return services.getFlight(flightId);
                    }
                }
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);
app.run(['$location', '$rootScope', function ($location, $rootScope) {
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.title = current.$$route.title;
    });
}]);