
(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('kudosType', function() {
        return{
            restrict: 'E',
            replace:true,
            templateUrl: 'views/directives/kudosType.html',
            scope:{
              kudo: '=',
              allowLink: '='
            }
        };
    });
})();
