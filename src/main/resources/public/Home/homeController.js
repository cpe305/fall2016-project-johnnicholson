app.controller('homeController', ['$scope', '$state', 'login', '$http',
 function(scope, state, login, $http) {
   scope.goToLogin = function() {
      state.go('login');
   };

   scope.goToRegister = function() {
      state.go('register');
   };

   scope.goToLoc = function(loc) {
      state.go('location', {locId: loc.id});
   }

   $http.get("/api/locs")
   .then(function(response) {
      scope.locs = response.data;
   })

}]);
