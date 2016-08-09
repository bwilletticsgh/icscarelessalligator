(function() {
  'use strict';


angular.module('app6App')
  .controller('Login', function ($q, authentication, $state) {
    var vm = this;
    vm.Username='';
    vm.Password='';
    vm.message='';
    vm.failMessage='I don\'t know you';
    vm.successMessage='You have successfully logged in.';

    vm.Login = function() {
      var promise = authentication.login(vm.Username, vm.Password);

      promise.then(function(success) {
        if (success) {
          vm.message = vm.successMessage;
          $state.go('about');
        }
        else{
          vm.message = vm.failMessage;
        }
      }).catch(function() {
          vm.message='This was an error logging in';
      });

      return promise;
    };
  });
})();
