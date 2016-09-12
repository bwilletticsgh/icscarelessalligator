"use strict";
var EC = protractor.ExpectedConditions;
var theUser = {};

module.exports = {
  theUser : theUser,
  register: function () {
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
    element(by.model('vm.orgId')).sendKeys('d'); //dhs
    element(by.model('vm.startDate')).sendKeys('01/01/2015');
    $('.form-signin .btn-primary').click();
    return browser.driver.wait(function () {
      return browser.driver.getCurrentUrl().then(function (url) {
        return /users/.test(url);
      });
    }, 10000);
  }
};
