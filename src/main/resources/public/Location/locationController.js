app.controller('locationController', ['$scope', '$state', '$http', 'loc', 'reqs', '$uibModal', 'notifyDlg',
 function(scope, state, $http, loc, reqs, $uibM, nDlg) {
  scope.reqs = reqs;
  scope.loc = loc;
  files = undefined;
  scope.postReq = function() {
    $uibM.open({
      templateUrl: "Request/postReq.modal.html",
      scope: scope
    }).result
    .then(function(result) {
      return $http({
        method: "POST",
        url: "/api/locs/" + loc.id + "/reqs",
        headers: {
          'Content-Type': undefined
        },
        data: {
          ownerId: scope.user.id,
          description: result.description,
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
    })
    .then(function() {
      return nDlg.show(scope, "Request Created Successfully", "Success");
    }).then(function() {
      state.reload()
    }).catch(function() {
      nDlg.show(scope, "Request Creation Failed", "Error");
    })
  }

  scope.deleteReq = function(req) {
    $http.delete("/api/reqs/" + req.id)
    .then(function() {
      return nDlg.show(scope, "Request Deleted Successfully", "Success");
    })
    .then(function() {
      state.reload();
    })
    .catch(function() {
      nDlg.show(scope, "Request Deletion Failed", "Error");
    })
  }

  scope.storeFile = function(formFiles) {
    files = formFiles;
  }
}]);
