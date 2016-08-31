(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('KudosCtrl', function (kudos, users, kudosCategories, _, $state, $rootScope) {
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
          $rootScope.alertMessage = {title:'Awesome',message: 'And a kudos to you for making ' + selectedUser.firstName + '\'s day!' };
          $state.go('app.user',{id:vm.kudo.toUser});
        });
      };
    });
})();
