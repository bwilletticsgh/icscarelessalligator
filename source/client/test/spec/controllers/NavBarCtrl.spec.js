'use strict';

describe('The NavBarCtrl', function () {
  var NavBarCtrl,
    authentication,
    $httpBackend,
    users,
    $q,
    $state,
    $stateParams,
    $rootScope,
    $cookieStore,
    scope;

  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  function resolveResource(val){
    return {$promise : resolve(val) };
  }

  beforeEach(module('kudosApp'));

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, _$rootScope_, _users_, _$httpBackend_, _authentication_, _$q_, _$state_, _$cookieStore_, _$stateParams_) {
    $state = _$state_;
    $stateParams = _$stateParams_;
    $rootScope = _$rootScope_;
    $q = _$q_;
    users = _users_;
    $httpBackend=_$httpBackend_;
    $cookieStore=_$cookieStore_;

    sinon.stub(users,'getCurrentUser').returns({id: 123, firstName:'Frank',lastName:'Jones', avatarUrl: 'url'});
    sinon.stub(users,'getUsers').returns(resolveResource([{id: 123},{id: 456},{id: 789}]));

    scope = $rootScope.$new();
    authentication = _authentication_;
    $httpBackend.when('GET', 'views/main.html').respond(200);
    $httpBackend.when('GET', 'views/searchResults.html').respond(200);
    NavBarCtrl = $controller('NavbarCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
    scope.$digest();
  }));

  it('should log out the logged in user', function () {
    var stub = sinon.stub(authentication,'logout');
    NavBarCtrl.logout();
    expect(stub.calledOnce).toBeTruthy();
  });

  it('should redirect the user to home when logging out', function () {
    NavBarCtrl.logout();
    $httpBackend.flush();
    expect($state.current.name).toBe('app.home');
  });

  it('shows the users full name', function () {
    expect(NavBarCtrl.UserFullName()).toBe(users.getCurrentUser().firstName + ' ' + users.getCurrentUser().lastName);
  });

  it('shows the users avatar', function () {
    expect(NavBarCtrl.UserAvatarUrl()).toBe(users.getCurrentUser().avatarUrl);
  });

  it('has the current user', function () {
    expect(NavBarCtrl.CurrentUser()).toBe(users.getCurrentUser());
  });

  it('redirects the user to the search state with the correct query string', function () {
    sinon.stub(authentication,'isAuthenticated').returns(true);
    sinon.stub(authentication,'setAuthenticationFromToken').returns(resolve());
    sinon.stub($cookieStore,'get').withArgs('token').returns('blah');

    $rootScope.$apply();

    NavBarCtrl.SearchString = "new search";
    NavBarCtrl.Search();
    $httpBackend.flush();
    $rootScope.$apply();
    expect($state.current.name).toBe('app.search');
    expect($stateParams.q).toBe(NavBarCtrl.SearchString);
  });

});
