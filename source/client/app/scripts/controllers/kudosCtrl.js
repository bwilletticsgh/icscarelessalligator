(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('KudosCtrl', function (kudos, users, kudosCategories, _, $state, swal) {
      var vm = this;
      vm.kudo = {};
      vm.kudosCategories = kudosCategories.getKudosCategories();
      vm.kudo.fromUser =  users.getCurrentUser().id;
      vm.kudo.toUser = $state.params.userId;

      //we want to hide the person from the list so they can't give a kudos to themselves
      users.getUsers().$promise.then(function(data){
        vm.users = _.filter(data, function(x){return x.id !== vm.kudo.fromUser;});
      });

      vm.saveKudos = function () {
        kudos.addKudos(vm.kudo).then(function(){
          var selectedUser = _(vm.users).find(["id",vm.kudo.toUser]);
          swal('Awesome!', 'And a kudos to you for making ' + selectedUser.firstName + '\'s day!','success');
          vm.theForm.$setPristine();
          vm.theForm.$setUntouched();
          vm.kudo.toUser = '';
          vm.kudo.comments = '';
        });
      };
    });
})();
