'use strict';

describe('Service: users', function () {

  // load the controller's module
  beforeEach(module('kudosApp'));

  var users;

  // Initialize the controller and a mock scope
  beforeEach(inject(function (_users_) {
    users = _users_;
    users.addUser({email:'someperson@somewhere.com'});
  }));

  it('should return undefined for a user that doesn\'t exist', function () {
    expect(users.getUser('fake guy')).toBeUndefined();
  });

  it('should allow a user to be added', function () {
    var currentCount = users.getUsers().length;
    users.addUser({email:'someperson2@somewhere.com'});
    expect(users.getUser('someperson2@somewhere.com')).toBeTruthy();
    expect(users.getUsers().length).toBe(currentCount+1);
  });

  it('should allow a user to be found', function () {
    expect(users.getUser('someperson@somewhere.com')).toBeTruthy();
  });
});
