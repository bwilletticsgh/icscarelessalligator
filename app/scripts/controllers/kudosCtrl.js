(function() {
  'use strict';
angular.module('app6App')
  .controller('KudosCtrl', function (kudos) {
    var vm = this;
    vm.kudos = kudos.getKudos();
  });
})();
