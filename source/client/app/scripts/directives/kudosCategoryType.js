
(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('kudosCategoryType', function() {
        return{
            restrict: 'E',
            replace:true,
            templateUrl: 'views/directives/kudosCategoryType.html',
            scope:{
              category: '=',
              allowLink: '='
            }
        };
    });
})();
