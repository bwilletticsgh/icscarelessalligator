(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCtrl', function (kudos) {
    var vm = this;
    vm.kudos = kudos.getKudos();
  });
})();
