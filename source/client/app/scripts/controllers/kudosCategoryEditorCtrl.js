(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCategoryEditorCtrl', function (kudosCategory, _, $state, kudosCategories, kudosInCategory) {
    var vm = this;
    vm.kudosCategory = kudosCategory;
    vm.kudosInCategory = kudosInCategory;

    vm.saveKudosCategory = function () {
      var promise;
      if (!vm.kudosCategory.id){
        promise = kudosCategories.addKudosCategory(vm.kudosCategory);
      }
      else{
        promise = kudosCategories.updateKudosCategory(vm.kudosCategory);
      }
      promise.then(function(){
        $state.go('app.kudosCategories');
      });
    };
  });
})();
