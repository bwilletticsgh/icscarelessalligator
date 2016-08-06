'use strict';

/**
 * @ngdoc function
 * @name app6App.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the app6App
 */
angular.module('app6App')
  .controller('AboutCtrl', function (users) {
    var vm = this;

    vm.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

    vm.users = users.getUsers();

    this.addAwesomeThing = function(){
      vm.awesomeThings.push('Dude' + vm.awesomeThings.length);
      users.addUser();
    };
  });
