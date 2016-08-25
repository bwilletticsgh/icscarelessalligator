'use strict';
angular.module('kudosApp')
  .constant('moment',window.moment)
  .filter('formatTime', function(moment) {
    return function(input) {
      return moment(parseInt(input)).calendar();
    };
  });
