describe('the kudos list page', function() {

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

  beforeEach(function () {
    // browser.get(browser.baseUrl + '#/account/login');
    // theUser.email = "a@a.com";
    // theUser.password = "a";
    // element(by.model('vm.Username')).sendKeys(theUser.email);
    // element(by.model('vm.Password')).sendKeys(theUser.password);
    // element(by.css('.btn-primary')).click();
    // return browser.driver.wait(function() {
    //   return browser.driver.getCurrentUrl().then(function(url) {
    //     return /users/.test(url);
    //   });
    // }, 10000);
  });

  it('should add a new kudos to a user', function() {
    browser.get(browser.baseUrl + '#/users');
    element(by.linkText("ADMIN, kudos")).click();
    element(by.cssContainingText(".btn-primary","Send them a Kudos")).click();
    element(by.cssContainingText("option","AWWW SNAP")).click();
    var comment = 'some comment or other' + Date.now();
    element(by.model("vm.kudo.comments")).sendKeys(comment);

    element(by.buttonText('Send Kudos!')).click();

    expect(element(by.cssContainingText('.userKudosType',comment)).isPresent()).toBeTruthy();


  });

  it('should add new kudos category', function() {
    browser.get(browser.baseUrl + '#/kudosCategory/list');
    var e = element(by.id('addNewKudosCategory'));
    browser.wait(EC.presenceOf(e), 10000);

    var ks = element.all(by.repeater('kudosCategory in vm.kudosCategories'));
    var currentCount = ks.count();

    element(by.id('addNewKudosCategory')).click();

    element(by.model('vm.kudosCategory.name')).sendKeys('My New Kudos Category' + Date.now());
    element(by.model('vm.kudosCategory.desc')).sendKeys('This is the newest kudos category' + Date.now());

    element(by.buttonText('Save Kudo Category')).click();

    return browser.driver.wait(function() {
      return browser.driver.getCurrentUrl().then(function(url) {
        return /list/.test(url);
      });
    }, 10000);

    expect(ks.count()).toEqual(currentCount+1);

    //expect(ks.last().getText()).toContain('My New Kudos');
  });

  it('should not show a send kudos button for the user when they click on themselves', function() {
    browser.get(browser.baseUrl + '#/users');
    element(by.linkText(theUser.lastName.toUpperCase()  + ", " + theUser.firstName)).click();

    expect($('.btn-primary').isDisplayed()).toBeFalsy();
  });

  it('should show a send kudos button for the user when they click on someone else', function() {
    browser.get(browser.baseUrl + '#/users');
    element(by.linkText("ADMIN, kudos")).click();
    expect($('.btn-primary').isDisplayed()).toBeTruthy();
  });


});

