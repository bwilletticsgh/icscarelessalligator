'use strict';

(function(){
angular.module('kudosApp')
  .controller('SearchCtrl', function ($rootScope,$stateParams, kudos, users) {
    var vm = this;
    $rootScope.pageTitle='Search Results For: ' + $stateParams.q;
    vm.results = kudos.searchUsersAndKudosCats($stateParams.q);
    vm.currentUser = users.getCurrentUser();

    vm.ToggleUserPanel = function(show){
      vm.ShowUserPanel = show;
    };
  });
})();
