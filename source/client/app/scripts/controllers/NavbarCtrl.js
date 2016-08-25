(function() {
'use strict';
    angular
        .module('kudosApp')
        .controller('NavbarCtrl', function(authentication, users, $state) {
            var vm = this;
            vm.authentication = authentication;

            vm.logout = function(){
              vm.authentication.logout();
              $state.go('app.home');
            };

            vm.UserFullName = function() {
                var user = users.getCurrentUser();
                if (user){
                  return user.firstName + ' ' + user.lastName;
                }
                return '';
            };
    });
})();
