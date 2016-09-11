(function() {
  'use strict';


angular.module('kudosApp')
  .controller('UserAccountCtrl', function (users,swal,$state, user, $q) {
    var vm = this;
    vm.currentUser = users.getCurrentUser();
    vm.allowMakeAdmin = vm.currentUser.isAdmin;

    vm.editUser = {};
    var originalUser = angular.copy(user);
    vm.editUser = user;

    vm.updateUserAccount = function() {

      function showSuccess(){
        swal('Account Updated', 'The account has been updated', "success");
      }

      var promises = [];

      users.updateUserProfile(vm.editUser).then(function() {
        if (originalUser.isAdmin !== vm.editUser.isAdmin ) {
            promises.push(users.toggleAdmin(vm.editUser.id));
            originalUser.isAdmin = vm.editUser.isAdmin;
        }

        if (originalUser.isHrUser !== vm.editUser.isHrUser ) {
          promises.push(users.toggleHrUser(vm.editUser.id));
          originalUser.isAdmin = vm.editUser.isAdmin;
        }

        $q.all(promises).then(showSuccess);

      }).catch(function(resp) {
        swal("Uh oh", resp.data, "error");
      });
    };
  });
})();
