
(function(){
  'use strict';
  angular.module('app6App')
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
