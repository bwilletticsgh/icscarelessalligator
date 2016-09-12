describe('the profile page', function() {

  var commonHelpers = require('./commonHelpers.js');

  beforeAll(function() {
    commonHelpers.register();
  });

  it('should allow a user to update their profile', function() {
    browser.get(browser.baseUrl + '#/account/profile');
    element(by.model("vm.editUser.firstName")).clear().sendKeys('frankie');
    element(by.model("vm.editUser.lastName")).clear().sendKeys('twotoes');
    element(by.model("vm.editUser.email")).clear().sendKeys('twotoes@' + Date.now() + '.com');
    element(by.css('.mainSiteContainer .btn-primary')).click();
    expect(element(by.model("vm.editUser.firstName")).getAttribute("value")).toBe('frankie');
  });


});

