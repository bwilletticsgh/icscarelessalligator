'use strict';

describe('Controller: Login', function () {

  // load the controller's module
  beforeEach(module('kudosApp'));

  var Login,
    $httpBackend,
    users,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _users_, _$httpBackend_) {
    console.log(sinon);
    users = _users_;
    users.addUser({email:'someperson@somewhere.com', password:'abc'});
    $httpBackend=_$httpBackend_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/users.html').respond(200);
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

  it('should let in a known user with the right password', function () {
    Login.Username='someperson@somewhere.com';
    Login.Password='abc';
    Login.Login().then(function(){
      expect(Login.message).toBe(Login.successMessage);
    });
    $httpBackend.flush();
    $timeout.flush();
  });

  it('should not let in a known user with the wrong password', function () {
    Login.Username='someperson@somewhere.com';
    Login.Password='abcde';
    Login.Login().then(function(){
      expect(Login.message).toBe(Login.failMessage);
    });
    $httpBackend.flush();
    $timeout.flush();
  });
});
