(function(){
  'use strict';
  angular.module('kudosApp')

    .factory('users', function(_, $resource, restUrl) {

        var url = restUrl + 'user';

        var userResource = $resource(url, {}, {
          all: {method:"get",isArray:true, url: url + "/all"},
          getUserById: {method:"get",url: url + "/byId/:id"},
          update: {method:"POST",url: url + "/update"},
          updateUserProfile: {method:"POST",url: url + "/updateProfile"},
          toggleAdminUser: {method:"POST",url: url + "/toggleAdmin/:id", params: {id:"@id"}}
        });

        var _currentUser;

        function setCurrentUser(user){
          _currentUser=user;
        }

        function toggleAdmin(userId){
          return userResource.toggleAdminUser({id:userId}).$promise;
        }

        function getCurrentUser() {
          return _currentUser;
        }

        function getUsers() {
          var allUsers = userResource.all();

          //give everyone a picture. TODO: make this happen on the backend
          allUsers.$promise.then(function(data){
            _.each(data, function(u){u.avatarUrl = u.avatarUrl || "http://robohash.org/" + u.id + ".png?size=300x300&set=set1";});
          });

          return allUsers;
        }

        function getUser(id) {
          var res = userResource.getUserById({id: id});
           res.$promise.then(function(user){
             user.avatarUrl = user.avatarUrl || "http://robohash.org/" + user.id + ".png?size=300x300&set=set1";
           });

          return res;
        }

        function updateUser(user){
          return userResource.update(user).$promise;
        }

      function updateUserProfile(user){
        return userResource.updateUserProfile({userId: user.id, firstName: user.firstName, lastName: user.lastName, avatarUrl: user.avatarUrl}).$promise;
      }

        return {
          getUsers: getUsers,
          toggleAdmin: toggleAdmin,
          getUser : getUser,
          setCurrentUser : setCurrentUser,
          getCurrentUser : getCurrentUser,
          updateUser : updateUser,
          updateUserProfile: updateUserProfile
        };
    });
})();
