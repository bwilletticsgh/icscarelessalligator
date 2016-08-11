(function() {
'use strict';
    angular
        .module('kudosApp')
        .controller('NavbarCtrl', function(authentication, users) {
            var vm = this;
            vm.authentication = authentication;
            vm.UserFullName = function() {
                var user = users.getCurrentUser();
                if (user){
                  return user.firstName + ' ' + user.lastName;
                }
                return '';
            };
    });
})();
