(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCategoryEditorCtrl', function (kudosCategory, _, $state, kudosCategories, kudosInCategory) {
    var vm = this;
    vm.kudosCategory = kudosCategory;
    vm.kudosInCategory = kudosInCategory;

    // if(vm.kudosCategory.id) {
    //   vm.kudosInCategory = kudos.getKudosByCategory(vm.kudosCategory.id);
    //
    //   _.forEach(vm.kudosInCategory, function(kudo) {
    //     kudo.toUserVM = users.getUser(kudo.toUser);
    //     kudo.fromUserVM = users.getUser(kudo.fromUser);
    //   });
    // }

    vm.saveKudosCategory = function () {
      var promise;
      if (!vm.kudosCategory.id){
        promise = kudosCategories.addKudosCategory(vm.kudosCategory);
      }
      else{
        promise = kudosCategories.updateKudosCategory(vm.kudosCategory);
      }
      promise.then(function(){
        $state.go('kudosCategories');
      });
    };
  });
})();
