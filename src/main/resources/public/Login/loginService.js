app.service("login", ["$http", "$rootScope", '$q', 'notifyDlg',
function($http, $rootScope, $q, nDlg) {

   logout = function() {
      return $http.delete("api/ssns/curssn")
      .then(function() {
         $rootScope.user = null;
      });
   }
   $rootScope.logout = logout;
   login = function(user) {
      return $http.post("/api/ssns", user)
      .then(function(response) {
         return $http.get("/api/ssns/curssn");
      })
      .then(function(response) {
         return $http.get('/api/prss/' + response.data.prsId);
      })
      .then(function(response) {
         $rootScope.user = response.data;
         return $q.resolve(response.data);
      })
      .catch(function(err) {
         nDlg.show(rootScope, "Login failed", "Error");
      });
   }

   relogin = function() {
      return $http.get("/api/ssns/curssn")
      .then(function(response) {
         return $http.get('/api/prss/' + response.data.prsId);
      })
      .then(function(response) {
         $rootScope.user = response.data;
         return $q.resolve(response.data);
      });
   }
   
   return {
      login: login,
      relogin: relogin,
      logout: logout,
      getUser: function() {
         return $rootScope.user;
      },
      isLoggedIn: function() {
         return $rootScope.user === null;
      }
   };
}]);
