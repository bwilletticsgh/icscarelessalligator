(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('userKudosType', function() {
      return{
        restrict: 'E',
        replace: true,
        templateUrl: 'views/directives/userKudosType.html',
        scope: {
          kudos: '=',
          association: '='
        }
      };
    });
})();

