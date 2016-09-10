'use strict';

(function(){


/**
 * @ngdoc function
 * @name kudosApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the kudosApp
 */
angular.module('kudosApp')
  .controller('UsersCtrl', function (users) {
    var vm = this;
    vm.users = users.getUsers();
    vm.listViewMode = false;
    vm.setListMode = function(listMode){
      vm.listViewMode = listMode;
    };
  });
})();
