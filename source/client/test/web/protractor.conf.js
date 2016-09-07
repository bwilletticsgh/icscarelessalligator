exports.config = {
  framework: 'jasmine2',
  seleniumAddress: 'http://localhost:4444/wd/hub',

  // If true, only chromedriver will be started, not a standalone selenium.
  // Tests for browsers other than chrome will not run.
  //chromeOnly: true,
  baseUrl: 'http://localhost:9123/',
  specs: ['./*.spec.js'],
  capabilities: {
    browserName: 'chrome'
  }
};
