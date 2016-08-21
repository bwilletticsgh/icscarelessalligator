'use strict';

// src/app/app.spec.js
// Containing describe block (or "suite"), usually named for an app feature.
// In this case the feature is the App itself.
describe('Unit: App', function () {

  // Include Modules
  beforeEach(module('kudosApp'));

  // Suite for testing an individual piece of our feature.
  describe('App Abstract Route', function () {

    // Instantiate global variables (global to all tests in this describe block).
    var $state,
      $httpBackend,
      $rootScope;

    // Inject dependencies
    beforeEach(inject(function (_$state_, _$rootScope_,_$httpBackend_) {
      $state = _$state_;
      $rootScope = _$rootScope_;
      $httpBackend = _$httpBackend_;
      $httpBackend.when('GET', 'views/main.html').respond(200);
      $httpBackend.when('GET', 'views/users/list.html').respond(200);
    }));

    it('verifies state configuration', function () {
      $rootScope.$apply();
      expect($state.current.name).toBe('');
      $state.transitionTo('users');
      $httpBackend.flush();
      $rootScope.$apply();
      expect($state.current.name).toBe('users');
    });
  });
});

