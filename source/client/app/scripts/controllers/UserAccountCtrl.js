(function() {
  'use strict';


angular.module('kudosApp')
  .controller('UserAccountCtrl', function (users,swal,$state, user) {
    var vm = this;
    vm.editUser = {};
    var originalUser = angular.copy(user);
    vm.editUser = user;

    vm.updateUserAccount = function() {

      function showSuccess(){
        swal("Account Updated", "The account has been updated", "success");
      }

      users.updateUserProfile(vm.editUser).then(function() {
        if (originalUser.isAdmin !== vm.editUser.isAdmin) {
          users.toggleAdmin(vm.editUser.id).then(showSuccess);
          originalUser.isAdmin = vm.editUser.isAdmin;
        }
        else {
          showSuccess();
        }
      }).catch(function(resp){
        swal("Uh oh", resp.data, "error");
      });
    };
  });
})();
