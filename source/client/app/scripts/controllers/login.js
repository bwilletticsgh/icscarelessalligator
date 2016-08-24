(function() {
  'use strict';


angular.module('kudosApp')
  .controller('Login', function ($q, authentication, $state, $timeout) {
    var vm = this;
    vm.Username='';
    vm.Password='';
    vm.message='';
    vm.failMessage='I don\'t know you';
    vm.successMessage='You have successfully logged in.';
    vm.failed = false;

    vm.Login = function() {
      vm.failed = false;
      var promise = authentication.login(vm.Username, vm.Password);
      promise.then(function(success) {
        vm.failed = false;
        if (success) {
          vm.message = vm.successMessage;
          $state.go('users');
        }
        else{
          vm.message = vm.failMessage;
          $timeout(function(){ vm.failed = true; });
        }
      }).catch(function(response) {
          vm.message=response.data;
          $timeout(function(){ vm.failed = true; });
      });

      return promise;
    };
  });
})();
