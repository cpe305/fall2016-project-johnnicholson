app.controller('locationController', ['$scope', '$state', '$http', 'loc', 'reqs',
 function(scope, state, $http, loc, reqs) {
    scope.reqs = reqs;
    scope.loc = loc;
    console.log(reqs);
}]);
