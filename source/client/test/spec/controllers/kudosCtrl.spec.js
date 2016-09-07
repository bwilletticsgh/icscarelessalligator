'use strict';

describe('KudosCtrl', function () {
  var Login,
    authentication,
    $httpBackend,
    users,
    $q,
    $state,
    $timeout,
    $rootScope,
    kudosCategories,
    kudos,
    scope;

  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  function resolveResource(val){
    return {$promise : resolve(val) };
  }

  beforeEach(module('kudosApp'));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, _users_, _$httpBackend_, _authentication_, _$q_, _$timeout_, _$state_, _kudosCategories_, _kudos_) {
    $state = _$state_;
    $rootScope = _$rootScope_;
    $q = _$q_;
    $timeout = _$timeout_;
    kudosCategories = _kudosCategories_;
    users = _users_;
    $httpBackend=_$httpBackend_;
    kudos =_kudos_;
    sinon.stub(users,'getCurrentUser').returns({id: 123});
    sinon.stub(users,'getUsers').returns(resolveResource([{id: 123},{id: 456},{id: 789}]));
    sinon.stub(kudosCategories,'getKudosCategories').returns({});
    scope = $rootScope.$new();
    authentication = _authentication_;
    Login = $controller('KudosCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
    scope.$digest();
  }));

  it('should not let a user give a kudos to themselves', function () {
    expect(Login.users).toContain({id:456});
    expect(Login.users).not.toContain({id:123});
  });

  it('gets all kudos categories', function () {
    expect(Login.kudosCategories).toBeDefined();
  });

  it('calls add kudos when saveKudos is called', function () {
    var stub = sinon.stub(kudos,'addKudos').returns(resolve({}));
    Login.saveKudos();
    expect(stub.calledOnce).toBe(true);
    expect(stub.calledWith(Login.kudo)).toBe(true);
  });

});
