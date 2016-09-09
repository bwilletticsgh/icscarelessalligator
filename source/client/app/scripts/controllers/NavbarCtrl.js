(function() {
'use strict';
    angular
        .module('kudosApp')
        .controller('NavbarCtrl', function(authentication, users, $state) {

          // $scope.$watch(users.getCurrentUser, function(){
          //   console.log("OK!", users.getCurrentUser());
          // });

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

            vm.UserAvatarUrl = function () {
              var user = users.getCurrentUser();
              if (user) {
                return user.avatarUrl;
              }
              return '';
            };

            vm.CurrentUser = function() {
              var user = users.getCurrentUser();
              if(user) {
                return user;
              }
              return '';
            };

            vm.Search = function() {
              $state.go('app.search',{q: vm.SearchString});
            };
    });
})();
