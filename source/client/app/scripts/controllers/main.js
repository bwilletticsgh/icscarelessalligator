'use strict';

/**
 * @ngdoc function
 * @name kudosApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the kudosApp
 */
angular.module('kudosApp')
  .controller('MainCtrl', function ($uibModal) {
    var vm = this;
    vm.pageTitle = 'Home';
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
