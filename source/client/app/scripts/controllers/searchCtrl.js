'use strict';

(function(){
angular.module('kudosApp')
  .controller('SearchCtrl', function ($rootScope,$stateParams) {

    $rootScope.pageTitle="Search Results For: " + $stateParams.q;
  });
})();
