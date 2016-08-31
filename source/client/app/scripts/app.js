'use strict';

/**
 * @ngdoc overview
 * @name kudosApp
 * @description
 * # kudosApp
 *
 * Main module of the application.
 */
angular
  .module('kudosApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ui.router',
    'ngSanitize',
    'ngTouch',
    'ui.bootstrap',
    'kudosAppConfig'
  ])
  .constant('_',window._)
  .constant('swal',window.swal)
  .factory('restUrl', function($window,apiInfo) {
    console.log('port number is: ' + apiInfo.port);
    var KudosRESTAPIPortNumber=':' + apiInfo.port;
    return $window.location.protocol + '//' + $window.location.hostname + KudosRESTAPIPortNumber + '/KudosREST/v1/';
  })
  .run(function ($rootScope, authentication, $state, $cookieStore, swal, _) {
    _.each($state.get(), function(state){
      state.resolve = state.resolve || {};
    });

    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      if($rootScope.alertMessage) {
        swal($rootScope.alertMessage.title,$rootScope.alertMessage.message,"success");
        $rootScope.alertMessage = null;
      }
      $rootScope.pageTitle = toState.pageTitle || $state.$current.locals.globals.pageTitle;
    });
    $rootScope.$on('$stateChangeStart', function(event, toState) {
        toState.resolve.pauseStateChange = [ //see: http://stackoverflow.com/questions/20094273/stop-angular-ui-router-navigation-until-promise-is-resolved
          '$q',
          function($q) {
            var defer = $q.defer();
            if ($cookieStore.get('token')) {
                authentication.setAuthenticationFromToken($cookieStore.get('token')).then(
                  function(){
                    defer.resolve();
              }, function(){
                  alert('Something went horribly wrong!');
                  console.log(e);
                  defer.reject();
                });
            }
            else{
              defer.resolve();
            }
            return defer.promise;
          }
        ];

        if (!toState.allowAnon && !$cookieStore.get('token')) {
          event.preventDefault();
          return $state.go('app.login');
        }
    });
 });
