(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('kudosCommentType', function() {
      return{
        restrict: 'E',
        replace: true,
        templateUrl: 'views/directives/kudosCommentType.html',
        scope: {
          comment: '='
        }
      };
    });
})();
