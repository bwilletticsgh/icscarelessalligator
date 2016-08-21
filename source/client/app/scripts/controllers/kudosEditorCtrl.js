(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosEditorCtrl', function (kudo, kudos, _, $state) {
    var vm = this;
    vm.kudo = angular.copy(kudo);

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
