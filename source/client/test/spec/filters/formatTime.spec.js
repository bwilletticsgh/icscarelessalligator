'use strict';

describe('The formatTime filter', function () {

  beforeEach(module('kudosApp'));
  var $filter;
  // Initialize the controller and a mock scope
  beforeEach(inject(function (_$filter_) {
    $filter = _$filter_;
  }));

  it('should show a friendly formatting string', function () {
     expect($filter('formatTime')(Date.now())).toContain('Today at');
  });


});
