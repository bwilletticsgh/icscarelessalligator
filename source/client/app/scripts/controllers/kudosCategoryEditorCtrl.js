(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCategoryEditorCtrl', function (kudosCategory, _, $state) {
    var vm = this;
    vm.kudosCategory = kudosCategory;
    vm.kudosInCategory = []; // = kudosInCategory;

    // if(vm.kudosCategory.id) {
    //   vm.kudosInCategory = kudos.getKudosByCategory(vm.kudosCategory.id);
    //
    //   _.forEach(vm.kudosInCategory, function(kudo) {
    //     kudo.toUserVM = users.getUser(kudo.toUser);
    //     kudo.fromUserVM = users.getUser(kudo.fromUser);
    //   });
    // }

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
