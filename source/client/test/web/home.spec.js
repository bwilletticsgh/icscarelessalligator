describe('the homepage', function() {

  beforeEach(function() {
    browser.get(browser.baseUrl + '#/home');
  });

  it('should have title', function() {
    expect(browser.getTitle()).toEqual('KUDOS Home');
  });

  it('should click sendKudos', function() {
    element(by.id('sendKudos')).click();
  });
});
