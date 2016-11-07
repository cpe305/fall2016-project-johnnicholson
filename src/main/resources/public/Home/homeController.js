app.controller('homeController', ['$scope', '$state', 'login', '$http', 'notifyDlg', '$uibModal',
 function(scope, state, login, $http, nDlg, $uibM) {
   getLocs = function() {
     login.getUserPromise()
     .then(function() {
       return $http.get("/api/locs")
     })
     .then(function(response) {
       scope.locs = response.data;
     })
   }
   scope.postLoc = function() {
     $uibM.open({
       templateUrl: "Location/postLoc.modal.html",
       scope: scope
     }).result
     .then(function(result) {
       $http.post("/api/locs", result);
     }).then(function() {
       return nDlg.show(scope, "Location Created Successfully", "Success");
     }).then(function() {
       getLocs();
     }).catch(function() {
       nDlg.show(scope, "Location Creation Failed", "Error");
     })
   }

   scope.goToLogin = function() {
      state.go('login');
   };

   scope.goToRegister = function() {
      state.go('register');
   };

   scope.goToLoc = function(loc) {
      state.go('location', {locId: loc.id});
   }

   getLocs();

}]);
