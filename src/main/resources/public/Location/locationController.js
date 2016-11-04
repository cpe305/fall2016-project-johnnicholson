app.controller('locationController', ['$scope', '$state', '$http', 'loc', 'reqs', '$uibModal',
 function(scope, state, $http, loc, reqs, $uibM) {
  scope.reqs = reqs;
  scope.loc = loc;
  files = undefined;
  scope.postReq = function() {
    $uibM.open({
      templateUrl: "Location/postLoc.modal.html",
      scope: scope
    }).result
    .then(function(result) {
      $http({
        method: "POST",
        url: "/api/locs/" + loc.id + "/reqs",
        headers: {
          'Content-Type': undefined
        },
        data: {
          ownerId: scope.user.id,
          file: files[0]
        },
        transformRequest: function (data, headersGetter) {
          var formData = new FormData();
          angular.forEach(data, function (value, key) {
            formData.append(key, value);
          });

          var headers = headersGetter();
          return formData;
        }
      });
    });
  }

  scope.deleteReq = function(req) {
    $http.delete("/api/reqs/" + req.id)
  }

  scope.storeFile = function(formFiles) {
    files = formFiles;
  }
}]);
