
(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('maxlengthHelper', function($compile) {
      return{
        scope: {
          maxlengthHelper:'=',
          myModel: '=ngModel'
        },
        link:function(scope, elem, attrs) {
          attrs.$set("maxlength",attrs.maxlengthHelper);
          var el = angular.element('<div class="help-block">{{maxlengthHelper - myModel.length}} character<span ng-hide="maxlengthHelper - myModel.length == 1">s</span> remaining</div>');
          $compile(el)(scope);
          el.insertAfter(elem);
        }
      };
    });
})();
