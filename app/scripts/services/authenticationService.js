(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('authentication', function($q, users) {

        var _isAuthenticated=false;
        function isAuthenticated() {
          return _isAuthenticated;
        }

        function login(username, password){
          var deferred = $q.defer();
          var user = users.getUser(username);
          var isValidUser = user && user.email.toUpperCase() === username.toUpperCase() && user.password === password;
          if (isValidUser){
            users.setCurrentUser(user);
            _isAuthenticated=true;
          }

          deferred.resolve(isValidUser);

          return deferred.promise;
        }

        return {
          isAuthenticated: isAuthenticated,
          login: login
        };
    });
})();
