'use strict';

/**
 * @ngdoc function
 * @name app6App.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the app6App
 */
angular.module('app6App')
  .controller('UsersCtrl', function (users) {
    var vm = this;
    vm.users = users.getUsers();
  });
