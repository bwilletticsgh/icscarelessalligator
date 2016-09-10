'use strict';

describe('The ProfileCtrl', function () {
  var ProfileCtrl,
    authentication,
    $httpBackend,
    users,
    $q,
    $state,
    $stateParams,
    $rootScope,
    $cookieStore,
    sandbox,
    scope;



  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  beforeEach(module('kudosApp'));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, _users_, _$httpBackend_, _authentication_, _$q_, _$state_, _$cookieStore_, _$stateParams_) {
    sandbox = sinon.sandbox.create();
    $state = _$state_;
    $stateParams = _$stateParams_;
    $rootScope = _$rootScope_;
    $q = _$q_;
    users = _users_;
    $httpBackend=_$httpBackend_;
    $cookieStore=_$cookieStore_;

    sandbox.stub(users,'getCurrentUser').returns({id: 123, firstName:'Frank',lastName:'Jones', avatarUrl: 'url'});

    scope = $rootScope.$new();
    authentication = _authentication_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/searchResults.html').respond(200);
    ProfileCtrl = $controller('ProfileCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
    scope.$digest();
  }));

  it('should update the current user\'s profile', function () {

    ProfileCtrl.editUser.firstName='NewFirst';
    var stub = sandbox.stub(users,'updateUserProfile').returns(resolve(ProfileCtrl.editUser));
    ProfileCtrl.updateProfile();
    $rootScope.$apply();
    expect(stub.calledWith(ProfileCtrl.editUser)).toBeTruthy();
    sandbox.restore();
    expect(users.getCurrentUser()).toBe(ProfileCtrl.editUser);
  });
});
