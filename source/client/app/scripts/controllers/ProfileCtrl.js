(function() {
  'use strict';


angular.module('kudosApp')
  .controller('ProfileCtrl', function (users,swal) {
    var vm = this;
    vm.editUser = angular.copy(users.getCurrentUser());
    vm.updateProfile = function() {
      users.updateUserProfile(vm.editUser).then(function(updatedUser){
        swal("Profile Updated", "Your profile has been updated " + vm.editUser.firstName + "!", "success");
        users.setCurrentUser(updatedUser);
      }).catch(function(resp){
        swal("Uh oh", resp.data, "error");
      });
    };
  });
})();
