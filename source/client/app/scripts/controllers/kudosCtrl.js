(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('KudosCtrl', function (kudos, users, kudosCategories, _, $state) {
      var vm = this;
      vm.kudo = kudos.getKudos('');
      vm.users = users.getUsers();
      vm.kudosCategories = kudosCategories.getKudosCategories();

      vm.saveKudos = function () {
        vm.kudo.fromUser = '1'; //users.getCurrentUser().id;
        kudos.addKudos(vm.kudo);
        $state.go('home');
      }
    });
})();
