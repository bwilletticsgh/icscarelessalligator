'use strict';

angular.module('kudosApp')
  .controller('UserDetailsCtrl', function (user, kudosToUser, kudosFromUser, _, $state, $rootScope) {
    var vm = this;
    vm.user = angular.copy(user);
    vm.toUser = angular.copy(kudosToUser);
    vm.fromUser = angular.copy(kudosFromUser);

    if(vm.user.id) {
      var fullName = vm.user.firstName + ' ' + vm.user.lastName;
      $rootScope.pageTitle = fullName;
    }
  });
