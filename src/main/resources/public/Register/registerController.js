app.controller('registerController', ['$scope', '$state', '$http', 'notifyDlg', '$q', 'login',
 function(scope, $state, $http, nDlg, $q, login) {
   scope.user = {role: "Student"};

   scope.registerUser = function() {
      $http.post("/api/prss", scope.user)
      .then(function(response) {
         return nDlg.show(scope, "Registration succeeded.  Login automatically?",
          "Login", ["Yes", "No"]);
      })
      .then(function(btn) {
         if (btn == "Yes") {
            login.login({email: scope.user.email, password: scope.user.password});
            $state.go('home');
         }
         else {
            $state.go('home');
            return $q.reject()
         }
      })
   };

   scope.quit = function() {
      $state.go('home');
   };
}]);
