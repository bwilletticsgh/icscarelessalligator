'use strict';
(function(){
  angular.module('kudosApp').factory('authInterceptor', function ($q, $cookieStore, $injector) {
    return {
      // Add authorization token to headers
      request: function (config) {
        config.headers = config.headers || {};
        if ($cookieStore.get('token')) {
          config.headers.Authorization = $cookieStore.get('token');
        }
        return config;
      },
      response: function(response){
        if (response.headers('Authorization')){

          // var h = response.headers('Authorization');
          // console.log('auth', h);

          $cookieStore.put('token', response.headers('Authorization'));
        }
        return response;
      },
      // Intercept 401s and redirect you to login
      responseError: function(response) {
        if(response.status === 401) {
          $injector.invoke(function($state, $timeout) {
            $timeout(function(){
              $state.go('app.login');
            });
          });

          // remove any stale tokens
          $cookieStore.remove('token');
          return $q.reject(response);
        }
        else {
          return $q.reject(response);
        }
      }
    };
  })
  .config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
  });
})();
