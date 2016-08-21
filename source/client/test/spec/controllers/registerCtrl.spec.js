'use strict';

describe('Controller: RegisterCtrl', function () {

  // load the controller's module
  beforeEach(module('kudosApp'));

  var
    RegisterCtrl,
    $timeout,
    $httpBackend,
    users,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$timeout_, _$httpBackend_, _users_) {
    $timeout=_$timeout_;
    users = _users_;
    users.addUser({email:'someperson@somewhere.com', password:'abc'});
    $httpBackend=_$httpBackend_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/users/list.html').respond(200);
    scope = $rootScope.$new();
    RegisterCtrl = $controller('RegisterCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should allow a user to register', function () {
    RegisterCtrl.Username='theRandomPerson';

    expect(users.getUser(RegisterCtrl.Username)).toBeUndefined();

    RegisterCtrl.Password='thepassword';
    RegisterCtrl.Register().then(function(){
      expect(RegisterCtrl.message).toBe(RegisterCtrl.successMessage);
      expect(users.getUser(RegisterCtrl.Username)).toBeTruthy();
    });
    $httpBackend.flush();
    $timeout.flush();
  });

  it('should not allow an email address to be reused', function () {
    RegisterCtrl.Username='someperson@somewhere.com';

    expect(users.getUser(RegisterCtrl.Username)).toBeTruthy();

    RegisterCtrl.Password='thepassword';
    RegisterCtrl.Register().then(function(){
      expect(RegisterCtrl.failed).toBeTruthy();
    });
    $httpBackend.flush();
    $timeout.flush();
  });

});
