'use strict';
angular.module('kudosApp')
  .filter('fullUserName', function() {
    return function(user) {
      if(user) {
        return user.firstName + ' ' + user.lastName;
      } else {
        return '';
      }
    };
  });
