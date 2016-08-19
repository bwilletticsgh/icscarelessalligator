describe('angularjs home', function() {

  beforeEach(function() {
    browser.get(browser.baseUrl + '#/kudos/list');
  });

  it('should add new kudos category', function() {

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

