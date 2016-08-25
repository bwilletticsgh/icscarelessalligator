'use strict';

angular.module('kudosApp')
  .controller('UserDetailsCtrl', function (user, kudosToUser, kudosFromUser) {
    var vm = this;
    vm.user = user;
    vm.toUser = kudosToUser;
    vm.fromUser = kudosFromUser;
  });
