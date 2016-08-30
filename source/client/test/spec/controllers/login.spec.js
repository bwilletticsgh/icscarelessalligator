'use strict';

describe('Controller: Login', function () {
  var Login,
    authentication,
    $httpBackend,
    users,
    $q,
    $state,
    $timeout,
    $rootScope,
    scope;

  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  function reject(val){
    return $q(function(resolve, reject){ reject(val); });
  }

  beforeEach(module('kudosApp'));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, _users_, _$httpBackend_, _authentication_, _$q_, _$timeout_, _$state_) {
    $state = _$state_;
    $rootScope = _$rootScope_;
    $q = _$q_;
    $timeout = _$timeout_;
    users = _users_;
    $httpBackend=_$httpBackend_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/account/login.html').respond(200);
    $httpBackend.when('GET', 'views/users/list.html').respond(200);
    scope = $rootScope.$new();
    authentication = _authentication_;
    Login = $controller('Login', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should display an error message on bad login', function () {
    Login.Username='person';
    Login.Password='thepassword';
    var response = {data:'some random message'};
    sinon.stub(authentication,'login').returns(reject(response));
    Login.Login();
    scope.$digest();
    expect(Login.message).toBe(response.data);
  });


  it('should display success message on good login', function () {
    Login.Username='person';
    Login.Password='thepassword';
    sinon.stub(authentication,'login').returns(resolve('yay'));
    Login.Login();
    scope.$digest();
    expect(Login.message).toBe(Login.successMessage);
  });


  it('should call the authentication service with the right parameters', function () {
    $rootScope.$apply();
    Login.Username='person';
    Login.Password='thepassword';
    var stub = sinon.stub(authentication,'login').returns(resolve('yay'));
    Login.Login();
    expect(stub.calledOnce).toBe(true);
    expect(stub.calledWith(Login.Username, Login.Password)).toBe(true);
  });
});
