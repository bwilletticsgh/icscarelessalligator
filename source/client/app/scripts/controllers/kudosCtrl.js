(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('KudosCtrl', function (kudos, users, kudosCategories, _, $state) {
      var vm = this;
      vm.kudo = {};
      vm.users = users.getUsers();
      vm.kudosCategories = kudosCategories.getKudosCategories();
      vm.kudo.fromUser =  users.getCurrentUser().id;

      vm.saveKudos = function () {
        kudos.addKudos(vm.kudo);
        $state.go('user',{id:vm.kudo.toUser});
      };
    });
})();
