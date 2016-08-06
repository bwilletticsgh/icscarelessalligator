'use strict';

describe('Controller: Login', function () {

  // load the controller's module
  beforeEach(module('app6App'));

  var Login,
    $timeout,
    $httpBackend,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$timeout_, _$httpBackend_) {
    $timeout=_$timeout_;
    $httpBackend=_$httpBackend_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/about.html').respond(200);
    scope = $rootScope.$new();
    Login = $controller('Login', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should not let in an unknown user', function () {
    Login.Username='person';
    Login.Password='thepassword';
    Login.Login().then(function(){
      expect(Login.message).toBe(Login.failMessage);
    });
    $httpBackend.flush();
    $timeout.flush();
  });

  it('should let in an the admin user', function () {
    Login.Username='admin';
    Login.Password='admin';
    Login.Login().then(function(){
      expect(Login.message).toBe(Login.successMessage);
    });
    $httpBackend.flush();
    $timeout.flush();
  });
});
