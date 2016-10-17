app.controller('homeController', ['$scope', '$state', 'login',
 function(scope, state, login) {
   login.relogin();
   scope.goToLogin = function() {
      state.go('login');
   };

   scope.goToRegister = function() {
      state.go('register');
   };
}]);
