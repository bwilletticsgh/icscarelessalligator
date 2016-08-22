'use strict';
angular.module('kudosApp')
  .filter('formatTime', function() {
    return function(input) {
      return moment(parseInt(input)).calendar();
    };
  });
