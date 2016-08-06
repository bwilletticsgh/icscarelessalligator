(function(){
  'use strict';
  angular.module('app6App')
    .factory('users', function() {
        var users = [
          {
            lastName:'Jones',
            firstName: 'Frank'
          },
          {
            lastName:'Smith',
            firstName: 'Tom'
          },
          {
            lastName:'Tom',
            firstName: 'Smith'
          }
        ];


        function getUsers() {
          return users;
        }

        function addUser(){
          users.push({
            lastName:'Clark',
            firstName: 'Albert'
          });
        }

        return {
          addUser: addUser,
          getUsers: getUsers
        };
    });
})();
