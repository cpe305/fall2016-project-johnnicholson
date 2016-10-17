app.controller('loginController',
 ['$scope', '$state', '$http', 'notifyDlg', 'login',
 function(scope, $state, $http, nDlg, login) {

   scope.user = {};

   scope.login = function() {
      login.login(scope.user)
      .then(function(response) {
         $state.go('home');
      });
   };
}]);
