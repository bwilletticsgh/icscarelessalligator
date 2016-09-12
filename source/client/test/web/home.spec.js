describe('the homepage', function() {

  beforeEach(function() {
    browser.get(browser.baseUrl + '#/home');
  });

  it('should have title', function() {
    expect(browser.getTitle()).toEqual('KUDOS');
  });

  it('the send kudos button should be hidden for non logged in users', function() {
    expect(element(by.id('sendKudos')).isDisplayed()).toBeFalsy();
  });
});
