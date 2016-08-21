(function() {
  'use strict';
angular.module('kudosApp')
  .controller('KudosCategoryCtrl', function (kudosCategories) {
    var vm = this;
    vm.kudosCategories = kudosCategories.getKudosCategories();
  });
})();
