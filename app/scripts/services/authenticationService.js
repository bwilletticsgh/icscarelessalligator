(function(){
  'use strict';
  angular.module('app6App')
    .factory('authentication', function($q, $timeout) {
        var _isAuthenticated=false;
        function isAuthenticated() {
          return _isAuthenticated;
        }

        function login(username, password){
          var deferred = $q.defer();
          var isValidUser = username==='admin' && password==='admin';
          if (isValidUser){
            _isAuthenticated=true;
          }

          $timeout(function(){
            //simulate response time
            deferred.resolve(isValidUser);
          },2000);

          return deferred.promise;
        }

        return {
          isAuthenticated: isAuthenticated,
          login: login
        };
    });
})();
