(function() {
  'use strict';


angular.module('kudosApp')
  .controller('ProfileCtrl', function (users,swal) {
    var vm = this;
    vm.editUser = angular.copy(users.getCurrentUser());
    vm.updateProfile = function() {
      users.updateUser(vm.editUser).then(function(){
        swal("Profile Updated", "Your profile has been updated " + vm.editUser.firstName + "!", "success");
      }).catch(function(resp){
        swal("Uh oh", resp.data, "error");
      });
    };
  });
})();
