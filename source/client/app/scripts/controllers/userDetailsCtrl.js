'use strict';

angular.module('kudosApp')
  .controller('UserDetailsCtrl', function (user, kudosToUser, kudosFromUser, users) {
    var vm = this;
    vm.currentUser = users.getCurrentUser();
    vm.user = user;
    vm.toUser = kudosToUser;
    vm.fromUser = kudosFromUser;
  });
