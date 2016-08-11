(function() {
  'use strict';


angular.module('kudosApp')
  .controller('Login', function ($q, authentication, $state) {
    var vm = this;
    vm.Username='';
    vm.Password='';
    vm.message='';
    vm.failMessage='I don\'t know you';
    vm.successMessage='You have successfully logged in.';
    vm.failed = false;

    vm.Login = function() {
      var promise = authentication.login(vm.Username, vm.Password);
      promise.then(function(success) {
        if (success) {
          vm.message = vm.successMessage;
          $state.go('users');
        }
        else{
          vm.message = vm.failMessage;
          vm.failed = true;
        }
      }).catch(function() {
          vm.message='This was an error logging in';
      });

      return promise;
    };
  });
})();
