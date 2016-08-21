(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCategoryEditorCtrl', function (kudosCategory, kudosCategories, kudos, _, $state) {
    var vm = this;
    vm.kudosCategory = angular.copy(kudosCategory);

    if(vm.kudosCategory.id) {
      vm.kudosInCategory = kudos.getKudosByCategory(vm.kudosCategory.id);
    }

    vm.saveKudosCategory = function () {
      if (!vm.kudosCategory.id){
        kudosCategories.addKudosCategory(vm.kudosCategory);
      }
      else{
        kudosCategories.updateKudosCategory(vm.kudosCategory);
      }
      $state.go('kudosCategories');
    };
  });
})();
