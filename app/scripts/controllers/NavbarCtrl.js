(function() {
'use strict';
    angular
        .module('app6App')
        .controller('NavbarCtrl', function(authentication) {
            var vm = this;
            vm.authentication = authentication;
    });
})();
