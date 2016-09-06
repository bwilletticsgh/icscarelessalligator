describe('the kudos list page', function() {

  beforeAll(function() {
    browser.get(browser.baseUrl + '#/account/register');
    element(by.model('vm.firstName')).sendKeys('newUser');
    browser.pause();
  });

  afterAll(function() {
    foo = 0;
  });


  beforeEach(function() {
    browser.pause();
    browser.get(browser.baseUrl + '#/kudos/list');
  });

  it('should add new kudos category when the add button is clicked', function() {
    browser.pause();
    var ks = element.all(by.repeater('kudo in vm.kudos'));
    expect(ks.count()).toEqual(11);

    element(by.id('addNewKudosCategory')).click();

    element(by.model('vm.kudo.name')).sendKeys('My New Kudos Category');
    element(by.model('vm.kudo.description')).sendKeys('This is the newest kudos category');

    element(by.buttonText('Save Kudos')).click();

    expect(ks.count()).toEqual(12);

    expect(ks.last().getText()).toContain('My New Kudos');
  });
});

