(function(){
  'use strict';
  angular.module('kudosApp')

    .factory('users', function(_, $resource, restUrl) {

        var url = restUrl + '/user';

        var userResource = $resource(url, {}, {
          all: {method:"get",isArray:true, url: url + "/all"},
          getUserById: {method:"get",url: url + "/byId/:id"},
          update: {method:"POST",url: url + "/update"}
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

        function getUser(id) {
          var res = userResource.getUserById({id: id});
          res.$promise.then(function(user){
            //user.avatar="http://robohash.org/" + user.id + ".png?size=300x300&set=set1";
          });

          return res;
        }

        function updateUser(user){
          return userResource.update(user).$promise;
        }

        return {
          getUsers: getUsers,
          getUser : getUser,
          setCurrentUser : setCurrentUser,
          getCurrentUser : getCurrentUser,
          updateUser : updateUser
        };
    });
})();
