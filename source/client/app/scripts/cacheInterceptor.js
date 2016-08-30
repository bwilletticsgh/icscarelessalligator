angular.module('kudosApp')
.config(function($httpProvider) {
  $httpProvider.interceptors.push('noCacheInterceptor');
})
.factory('noCacheInterceptor', function () {
  return {
    request: function (config) {
      if(config.method=='GET' && config.url.toUpperCase().indexOf('/KUDOSREST/') > -1){
        var separator = config.url.indexOf('?') === -1 ? '?' : '&';
        config.url = config.url+separator+'noCache=' + new Date().getTime();
      }
      return config;
    }
  };
});
