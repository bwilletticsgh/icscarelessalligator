'use strict';

describe('Controller: RegisterCtrl', function () {

  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  function reject(val){
    return $q(function(resolve, reject){ reject(val); });
  }
  // load the controller's module
  beforeEach(module('kudosApp'));

  var
    RegisterCtrl,
    $timeout,
    $httpBackend,
    authentication,
    $q,
    users,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _$timeout_, _$httpBackend_, _users_, _authentication_,_$q_) {
    $timeout=_$timeout_;
    users = _users_;
    $q = _$q_;
    authentication = _authentication_;
    $httpBackend=_$httpBackend_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/account/login.html').respond(200);
    $httpBackend.when('GET', 'views/users/list.html').respond(200);
    scope = $rootScope.$new();
    RegisterCtrl = $controller('RegisterCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should allow a user to register', function () {
    RegisterCtrl.Username='theRandomPerson';
    RegisterCtrl.Password='thepassword';
    RegisterCtrl.firstName='firstname';
    RegisterCtrl.lastName='lastname';
    var stub = sinon.stub(authentication,'register').returns(resolve({success:true, message:'yay'}));
    RegisterCtrl.Register();
    scope.$digest();
    expect(stub.calledWith(RegisterCtrl.Username, RegisterCtrl.Password, RegisterCtrl.firstName, RegisterCtrl.lastName)).toBe(true);
    expect(RegisterCtrl.message).toBe(RegisterCtrl.successMessage);
  });

  it('should set the failure message to the message returned by the authentication service', function () {
    RegisterCtrl.Username='theRandomPerson';
    RegisterCtrl.Password='thepassword';
    RegisterCtrl.firstName='firstname';
    RegisterCtrl.lastName='lastname';
    var stub = sinon.stub(authentication,'register').returns(resolve({success:false, message:'no!'}));
    RegisterCtrl.Register();
    scope.$digest();
    expect(stub.calledWith(RegisterCtrl.Username, RegisterCtrl.Password, RegisterCtrl.firstName, RegisterCtrl.lastName)).toBe(true);
    expect(RegisterCtrl.message).toBe('no!');
  });


});
