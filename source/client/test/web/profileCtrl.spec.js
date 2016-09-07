describe('the profile page', function() {

  var theUser = {};
  var EC = protractor.ExpectedConditions;

  beforeAll(function() {
    //return;
    browser.get(browser.baseUrl + '#/account/register');
    var firstNameEl = element(by.model('vm.firstName'));

    browser.wait(EC.presenceOf(firstNameEl), 10000);
    theUser.firstName = 'newUser' + Date.now();
    theUser.lastName = 'newUser' + Date.now();
    theUser.email = 'person@' + Date.now() + '.com';
    browser.get(browser.baseUrl + '#/account/register');
    firstNameEl.sendKeys(theUser.firstName);
    element(by.model('vm.lastName')).sendKeys(theUser.lastName);
    element(by.model('vm.Username')).sendKeys(theUser.email);
    element(by.model('vm.Password')).sendKeys('abc123');
    element(by.model('vm.Password2')).sendKeys('abc123');
    $('.btn-primary').click();
    return browser.driver.wait(function() {
      return browser.driver.getCurrentUrl().then(function(url) {
        return /users/.test(url);
      });
    }, 10000);
  });

  it('should allow a user to update their profile', function() {
    browser.get(browser.baseUrl + '#/account/profile');
    element(by.model("vm.editUser.firstName")).clear().sendKeys('frankie');
    element(by.model("vm.editUser.lastName")).clear().sendKeys('twotoes');
    element(by.model("vm.editUser.email")).clear().sendKeys('twotoes@' + Date.now() + '.com');
    element(by.css('.btn-primary')).click();
    expect(element(by.model("vm.editUser.firstName")).getAttribute("value")).toBe('frankie');
  });


});

