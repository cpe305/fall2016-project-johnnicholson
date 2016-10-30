
app.config(['$stateProvider', '$urlRouterProvider',
   function($stateProvider, $router) {

      //redirect to home if path is not matched
      $router.otherwise("/");

      $stateProvider
      .state('base', {
         abstract: true,
         template: '<ui-view/>',
         resolve : {
            user: function(login) {
               login.relogin();
            }
         }
      })
      .state('home',  {
         parent: 'base',
         url: '/',
         templateUrl: 'Home/home.template.html',
         controller: 'homeController'

      })
      .state('register', {
         parent: 'base',
         url: '/register',
         templateUrl: 'Register/register.template.html',
         controller: 'registerController',
      })
      .state('login', {
         parent: 'base',
         url: '/login',
         templateUrl: 'Login/login.template.html',
         controller: 'loginController',
      })
      .state('location', {
         parent: 'base',
         url: '/loc/:locId',
         templateUrl: 'Location/loc.template.html',
         controller: 'locationController',
         resolve: {
            loc: function($http, $stateParams) {
               return $http.get('/api/locs/' + $stateParams.locId)
               .then(function(response) {
                  return response.data;
               });
            },
            reqs: function($http, $stateParams) {
               return $http.get('/api/locs/' + $stateParams.locId + "/reqs")
               .then(function(response) {
                  return response.data;
               });
            }
         }
      });
   }]);
