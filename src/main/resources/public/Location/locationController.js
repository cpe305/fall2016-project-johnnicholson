app.controller('locationController', ['$scope', '$state', '$http', 'loc',
  'reqs', '$uibModal', 'notifyDlg', '$q',
  function(scope, state, $http, loc, reqs, $uibM, nDlg, $q) {
    scope.reqs = reqs;
    scope.loc = loc;
    files = undefined;
    scope.getReqFile = function(req) {
      $http.get("api/reqs/" + req.id + "/file", {
          responseType: "arraybuffer"
        })
        .then(function(response) {
          var file = new Blob([response.data]);
          saveAs(file, req.fileName);
        });
    };

    scope.postReq = function() {
      $uibM.open({
          templateUrl: "Request/postReq.modal.html",
          scope: scope
        }).result
        .then(function(result) {
          if (result === "QUIT")
            return $q.reject("QUIT");
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
            transformRequest: function(data, headersGetter) {
              var formData = new FormData();
              angular.forEach(data, function(value, key) {
                formData.append(key, value);
              });
              var headers = headersGetter();
              return formData;
            }
          });
        })
        .then(function() {
          return nDlg.show(scope, "Request Created Successfully",
            "Success");
        }).then(function() {
          state.reload();
        }).catch(function(result) {
          console.log(result);
          if (!(result === "QUIT" || result === 'backdrop click'))
            nDlg.show(scope, "Request Creation Failed", "Error");
        });
    };
    putReq = function(req) {
      return $http.put("/api/reqs/" + req.id, req);
    };

    scope.shiftReqUp = function(req) {
      req.sequence -= 1;
      return $http.put("/api/reqs/" + req.id, {sequence: req.sequence})
      .then(function(response) {
        state.reload();
      })
      .catch(function(response) {
        state.reload();
      });
    };

    scope.shiftReqDown = function(req) {
      req.sequence += 1;
      return $http.put("/api/reqs/" + req.id, {sequence: req.sequence})
      .then(function(response) {
        state.reload();
      })
      .catch(function(response) {
        state.reload();
      });
    };

    scope.editReq = function(req, status) {
      $http.put("/api/reqs/" + req.id, {status: status})
      .then(function(response) {
        state.reload();
      })
    }

    scope.deleteReq = function(req) {
      nDlg.show(scope, "Are you sure you want to delete " + req.fileName, "Confirm", [
          "Confirm", "Cancel"
        ])
        .then(function(btn) {
          if (btn == "Confirm") {
            return $http.delete("/api/reqs/" + req.id);
          } else {
            return $q.reject("Canceled");
          }
        })
        .then(function() {
          return nDlg.show(scope, "Request Deleted Successfully",
            "Success");
        })
        .then(function() {
          state.reload();
        })
        .catch(function(reason) {
          if (reason != "Canceled") {
            nDlg.show(scope, "Request Deletion Failed", "Error");
          }
        });
    };

    scope.storeFile = function(formFiles) {
      files = formFiles;
    };
  }
]);
