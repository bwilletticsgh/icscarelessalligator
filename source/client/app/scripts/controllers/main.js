'use strict';

/**
 * @ngdoc function
 * @name kudosApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the kudosApp
 */
angular.module('kudosApp')
  .controller('MainCtrl', function ($uibModal, users, kudos, $cookieStore) {
    var vm = this;

    vm.setup = function() {
      vm.lastKudosForUser = null;
      vm.currentUser = users.getCurrentUser();
      vm.notificationDismissed = $cookieStore.get('lastSentNotificationDismissed') === 'true';

      if (vm.currentUser) {
        kudos.getLastKudosForUser(vm.currentUser.id).then(function (data) {
          vm.lastKudosForUser = data;
        });
      }

      vm.hideNotification = function(){
        $cookieStore.put('lastSentNotificationDismissed','true');
      };
    };

    vm.setup();

    vm.showInfo = function(){
      var modalInstance = $uibModal.open({
        templateUrl: 'myModalContent.html',
        controller: ['$scope', function($scope) {
          $scope.title='Hello foo!';
          $scope.modalInstance = modalInstance;
          $scope.ok = function(){
            modalInstance.close();
          };

          $scope.cancel = function() {
            modalInstance.close();
          };
        }]
      });
    };
  });
