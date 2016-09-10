'use strict';

describe('The full username filter', function () {

  beforeEach(module('kudosApp'));
  var $filter;
  // Initialize the controller and a mock scope
  beforeEach(inject(function (_$filter_) {
    $filter = _$filter_;
  }));

  it('should show a first and last name concatenated', function () {
     expect($filter('fullUserName')({firstName:'John',lastName:'Smith'})).toBe('John Smith');
  });

  it('should return a blank string for a null user', function () {
    expect($filter('fullUserName')(null)).toBe('');
  });

  it('should return a blank string for an undefined user', function () {
    expect($filter('fullUserName')(undefined)).toBe('');
  });

  it('should an undefined string for a non user object', function () {
    expect($filter('fullUserName')({badObject: true})).toBe('undefined undefined');
  });

});
