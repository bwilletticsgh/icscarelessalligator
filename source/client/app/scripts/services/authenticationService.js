(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('authentication', function($q, users, $resource, restUrl, $cookieStore) {

        var url = restUrl + '/user';

        var UserResource = $resource(url, {}, {
          register: {method:"POST", url: url + "/register"},
          login: {method:"POST", url: url + "/login"}
        });

        var _isAuthenticated=false;
        function isAuthenticated() {
          return _isAuthenticated;
        }

        function register(email, password, firstName, lastName) {
          var ret = {
            success:true,
            message:'Success'
          };

          var newUser = {
            email: email,
            password: password,
            firstName: firstName,
            lastName: lastName
          };

          var deferred = $q.defer();

          UserResource.register(newUser).$promise.then(
            function(){
              login(email, password).then(function(){
                deferred.resolve(ret);
              });
            },
            function(resp){
              ret.message=resp.data;
              ret.success=false;
              deferred.resolve(ret);
            }
          );

          return deferred.promise;
        }

        function logout() {
          users.setCurrentUser(null);
          _isAuthenticated=false;
          $cookieStore.remove('token');
        }

        function login(username, password) {
          return UserResource.login({ email: username, password: password },function(user){
            users.setCurrentUser(user);
            _isAuthenticated=true;
          }).$promise;
        }

        function setAuthenticationFromToken(token) {
          var deferred = $q.defer();
          try {
            var claims = JSON.parse(atob(token.split('.')[1]));
            _isAuthenticated = true;
            if (!users.getCurrentUser()) {
              users.getUser(JSON.parse(claims.kudosUser).id).$promise.then(function (u) {
                users.setCurrentUser(u);
                deferred.resolve();
              }).catch(function () {
                deferred.reject()
              });
            }
            else {
              deferred.resolve();
            }
          }
          catch (err) {
            deferred.reject(err);
          }
          return deferred.promise;
        }

        return {
          isAuthenticated: isAuthenticated,
          login: login,
          logout: logout,
          register : register,
          setAuthenticationFromToken: setAuthenticationFromToken
        };
    });
})();
