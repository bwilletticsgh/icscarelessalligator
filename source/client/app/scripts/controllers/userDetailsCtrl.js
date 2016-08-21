'use strict';

angular.module('kudosApp')
  .controller('UserDetailsCtrl', function (user, _, $state, $rootScope) {
    var vm = this;
    vm.user = angular.copy(user);
    if(vm.user.id) {
      var fullName = vm.user.firstName + ' ' + vm.user.lastName;
      $rootScope.pageTitle = fullName;
    }
  });
