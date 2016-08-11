'use strict';

describe('Controller: UsersCtrl', function () {

  // load the controller's module
  beforeEach(module('kudosApp'));

  var UsersCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    UsersCtrl = $controller('UsersCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should have a list of users on the scope', function () {
    expect(UsersCtrl.users).toBeDefined();
  });
});
