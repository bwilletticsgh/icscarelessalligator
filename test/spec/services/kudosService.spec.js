'use strict';

describe('Service: kudos', function () {

  // load the controller's module
  beforeEach(module('app6App'));

  var kudos;

  // Initialize the controller and a mock scope
  beforeEach(inject(function (_kudos_) {
    kudos = _kudos_;
    kudos.addKudo({name:'something else', id:-20});
  }));

  it('should return undefined for a kudos that doesn\'t exist', function () {
    expect(kudos.getKudo(200)).toBeUndefined();
  });

  it('should allow a kudos to be added', function () {
    var currentCount = kudos.getKudos().length;
    kudos.addKudo({name:'something', id:-100});
    expect(kudos.getKudo(-100)).toBeTruthy();
    expect(kudos.getKudos().length).toBe(currentCount+1);
  });

  it('should allow a kudos to be found', function () {
    expect(kudos.getKudo(-20)).toBeTruthy();
  });

  it('should autoincrement the id', function () {
    var currentCount = kudos.getKudos().length;
    kudos.addKudo({name:'something', id:100});
    var newKudo = {name:'something else'};
    kudos.addKudo(newKudo);
    expect(newKudo.id).toBe(101);
  });
});
