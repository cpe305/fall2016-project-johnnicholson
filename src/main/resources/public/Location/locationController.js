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
      var fd = new FormData();
      fd.append("file", files[0]);
      fd.append("fileName", result.fileName);
      $http.post("/api/locs/" + loc.id + "/reqs", fd);
    });
  }

  scope.deleteReq = function(req) {
    $http.delete("/api/reqs/" + req.id)
  }

  scope.storeFile = function(formFiles) {
    files = formFiles;
  }
}]);
