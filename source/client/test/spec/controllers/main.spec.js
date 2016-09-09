'use strict';

describe('Controller: MainCtrl', function () {

  function resolve(val){
    return $q(function(resolve){ resolve(val); });
  }

  // load the controller's module
  beforeEach(module('kudosApp'));

  var MainCtrl,
    users,
    $q,
    kudos,
    $cookieStore,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope,_users_, _kudos_, _$q_, _$cookieStore_) {
    scope = $rootScope.$new();
    users = _users_;
    kudos = _kudos_;
    $q = _$q_;
    $cookieStore = _$cookieStore_;

    MainCtrl = $controller('MainCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should set the last kudos for the user if they are logged in', function () {
    sinon.stub(users,'getCurrentUser').returns({id: 123});
    sinon.stub(kudos,'getLastKudosForUser').returns(resolve({id: 'someid'}));
    MainCtrl.setup();
    scope.$digest();
    expect(MainCtrl.currentUser.id).toBe(123);
    expect(MainCtrl.lastKudosForUser.id).toBe('someid');
  });

  it('should not set the last kudos for the user if they are not logged in', function () {
    sinon.stub(users,'getCurrentUser').returns(null);
    var stub = sinon.stub(kudos,'getLastKudosForUser');
    MainCtrl.setup();
    scope.$digest();
    expect(MainCtrl.currentUser).toBeNull();
    expect(stub.called).toBeFalsy();
  });

  it('should set lastSentNotificationDismissed cookie when clicking hide notification', function () {
    var mock = sinon.mock($cookieStore);
    mock.expects('put').once();
    MainCtrl.hideNotification();
    mock.verify();

  });

  it('should set notificationDismissed when the cookie is present', function () {
    var mock = sinon.mock($cookieStore);
    mock.expects('get').once().returns('true');
    MainCtrl.setup();
    expect(MainCtrl.notificationDismissed).toBeTruthy();
  });

});
