exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  baseUrl: 'http://localhost:9123/',
  specs: ['*-spec.js'],
  multiCapabilities: [{
    browserName: 'chrome'
  }]
};
