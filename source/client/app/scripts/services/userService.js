(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('users', function(_, $resource, restUrl) {

        var url = restUrl + '/user';

        var userResource = $resource(url, {}, {
          all: {method:"get",isArray:true, url: url + "/all"}
        });

        var _currentUser;

        function setCurrentUser(user){
          _currentUser=user;
        }

        function getCurrentUser() {
          return _currentUser;
        }

        function getUsers() {
          var allUsers = userResource.all();

          //give everyone a picture. TODO: make this happen on the backend
          allUsers.$promise.then(function(data){
            _.each(data, function(u){u.avatar="http://robohash.org/" + u.id + ".png?size=300x300&set=set1";});
          });

          return allUsers;
        }

        function addUser(user){
          //users.push(user);
        }

        function getUser(email) {
          //return _(users).find(function(p) {return p.email.toUpperCase() === email.toUpperCase();});
        }

        return {
          addUser: addUser,
          getUsers: getUsers,
          getUser : getUser,
          setCurrentUser : setCurrentUser,
          getCurrentUser : getCurrentUser
        };
    });
})();
