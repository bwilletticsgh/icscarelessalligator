(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('KudosCtrl', function (kudos, users, kudosCategories, _, $state) {
      var vm = this;
      vm.kudo = {};
      vm.users = users.getUsers();
      vm.kudosCategories = kudosCategories.getKudosCategories();
      vm.kudo.fromUser =  users.getCurrentUser().id;
      vm.kudo.toUser = $state.params.userId;

      vm.saveKudos = function () {
        kudos.addKudos(vm.kudo).then(function(){
          var selectedUser = _(vm.users).find(["id",vm.kudo.toUser]);
          $state.go('app.user',{id:vm.kudo.toUser, message: {title:'Awesome',message: 'And a kudos to you for making ' + selectedUser.firstName + '\'s day!' }});
        });
      };
    });
})();
