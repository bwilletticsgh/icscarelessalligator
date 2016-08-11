(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('authentication', function($q, users) {

        var _isAuthenticated=false;
        function isAuthenticated() {
          return _isAuthenticated;
        }

        function register(email, password, firstName, lastName) {
          var ret = {
            success:true,
            message:'Success'
          };

          var deferred = $q.defer();
          if (users.getUser(email)){
            ret.message='Email address is already in use';
            ret.success=false;
            deferred.resolve(ret);
          }
          else{
            users.addUser({id: users.getUsers().length,
              email: email,
              password: password,
              firstName: firstName,
              lastName: lastName,
              isAdmin: false,
              avatar: 'https://robohash.org/' + users.getUsers().length+ '.png?size=300x300&set=set1'
            });

            login(email, password).then(function(){
              deferred.resolve(ret);
            });
          }

          return deferred.promise;
        }

        function login(username, password) {
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
          login: login,
          register : register
        };
    });
})();
