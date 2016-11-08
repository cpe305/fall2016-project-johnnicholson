app.service("login", ["$http", "$rootScope", '$q', 'notifyDlg', '$state',
  function($http, $rootScope, $q, nDlg, $state) {
    userPromise = $q.defer();

    getUserPromise = function() {
      return userPromise.promise;
    };

    logout = function() {
      return $http.delete("api/ssns/curssn")
        .then(function() {
          $rootScope.user = null;
        })
        .catch(function() {
          $rootScope.user = null;
          return $q.when();
        })
        .then(function() {
          $state.transitionTo("home", {}, {
            cache: false,
            reload: true,
            inherit: false,
            notify: true
          });
        });
    };

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
          userPromise.resolve(user);
          return $q.resolve(response.data);
        })
        .catch(function(err) {
          nDlg.show($rootScope, "Login failed", "Error");
          return $q.reject();
        });
    };

    relogin = function() {
      return $http.get("/api/ssns/curssn")
        .then(function(response) {
          return $http.get('/api/prss/' + response.data.prsId);
        })
        .then(function(response) {
          $rootScope.user = response.data;
          userPromise.resolve();
          return $q.resolve(response.data);
        });
    };

    return {
      getUserPromise: getUserPromise,
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
  }
]);
