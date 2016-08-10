(function() {
  'use strict';
angular.module('app6App')
  .controller('KudosEditorCtrl', function (kudo, kudos, _, $state) {
    var vm = this;
    vm.kudo = angular.copy(kudo);
    if (kudo.id){
      vm.pageTitle='Edit Kudos';
    }
    else{
      vm.pageTitle='Create Kudos';
    }

    vm.saveKudo = function () {
      if (!kudo.id){
        kudos.addKudo(vm.kudo);
      }
      else{
        kudos.updateKudo(vm.kudo);
      }
      $state.go('kudos');
      //console.log(vm.kudo);
    };
  });
})();
