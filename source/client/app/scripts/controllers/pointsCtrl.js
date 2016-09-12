(function() {
  'use strict';
  angular.module('kudosApp')
    .controller('pointsCtrl', function (kudos) {
      console.log('dude')
      var vm = this;
      vm.kudos = kudos.all();
    });
})();
