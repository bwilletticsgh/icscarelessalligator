
(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('equalsElement', function() {
      return{
        require: 'ngModel',
        link:function(scope, elm, attrs, ctrl) {
          attrs.$observe('equalsElement', function(){
            ctrl.$validate();
          });

          ctrl.$validators.equalsElement = function(modelValue, viewValue) {
            if (viewValue === undefined) {
              viewValue = '';
            }

            return viewValue == attrs.equalsElement ;
          };
        }
      };
    });
})();

