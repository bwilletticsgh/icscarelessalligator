'use strict';

angular.module('kudosApp')
  .controller('UserDetailsCtrl', function (user, kudosToUser, kudosFromUser, users, kudosCategories, _, $state, $rootScope) {
    var vm = this;
    vm.user = angular.copy(user);
    vm.toUser = angular.copy(kudosToUser);
    vm.fromUser = angular.copy(kudosFromUser);

    //TODO: replace with a VM from the api
    _.forEach(vm.toUser, function(u) {
      u.fromUserVM = users.getUser(u.fromUser);
      u.category = kudosCategories.getKudosCategory(u.kudosCat);
    });

    _.forEach(vm.fromUser, function(u) {
      u.toUserVM = users.getUser(u.toUser);
      u.category = kudosCategories.getKudosCategory(u.kudosCat);
    });

    if(vm.user.id) {
      var fullName = vm.user.firstName + ' ' + vm.user.lastName;
      $rootScope.pageTitle = fullName;
    }
  });
