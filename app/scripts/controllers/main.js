'use strict';

/**
 * @ngdoc function
 * @name app6App.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the app6App
 */
angular.module('app6App')
  .controller('MainCtrl', function ($uibModal) {
    var vm = this;
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
