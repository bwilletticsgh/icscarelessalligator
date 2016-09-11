(function() {
  'use strict';


angular.module('kudosApp')
  .controller('RegisterCtrl', function ($q, authentication, $state, organizations) {
    var vm = this;
    vm.Username='';
    vm.Password='';
    vm.orgId  = '';
    vm.message='';
    vm.successMessage='You have successfully registered.';
    vm.failed = false;

    vm.organizations = organizations;

    vm.Register = function() {
      vm.failed = false;
      //function register(email, password, firstName, lastName) {
      var promise = authentication.register(vm.Username, vm.Password, vm.firstName, vm.lastName, vm.orgId);
      promise.then(function(result) {
        vm.failed = false;
        if (result.success) {
          vm.message = vm.successMessage;
          $state.go('app.users');
        }
        else{
          vm.message = result.message;
          vm.failed = true;
        }
      }).catch(function(e) {
          vm.message=e;
      });

      return promise;
    };
  });
})();
