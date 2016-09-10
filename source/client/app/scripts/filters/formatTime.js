'use strict';
angular.module('kudosApp')
  .constant('moment',window.moment)
  .filter('formatTime', function(moment) {
    return function(input, lowerCase) {
      var result = moment(parseInt(input)).calendar();
      if(lowerCase) {
        result = result.replace('Last', 'last').replace('Today', 'today').replace('Tomorrow', 'tomorrow').replace('Yesterday', 'yesterday');
      }
      return result;
    };
  });
