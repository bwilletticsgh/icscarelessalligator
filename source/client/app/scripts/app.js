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
    'ui.bootstrap'
  ])
  .constant('_',window._)
  .factory('restUrl', function($window){
    return $window.location.protocol + '//' + $window.location.hostname + ':8080/KudosREST/v1/';
  })
  .config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
  })
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
      .state('home', {
        allowAnon: true,
        templateUrl: 'views/main.html',
        controller: 'MainCtrl as vm',
        url:'/home'
      })
      .state('kudos', {
        pageTitle: 'Kudos List',
        templateUrl: 'views/kudos/list.html',
        controller: 'KudosCtrl as vm',
        url:'/kudos/list'
      })
      .state('editKudos', {
        pageTitle: 'Edit Kudos',
        templateUrl: 'views/kudos/edit.html',
        controller: 'KudosEditorCtrl as vm',
        url:'/kudos/edit/{id:int}',
        resolve: {
          kudo : function($stateParams, kudos){
            return kudos.getKudo($stateParams.id);
          }
        }
      })
      .state('createKudos', {
        pageTitle: 'Create Kudos',
        templateUrl: 'views/kudos/edit.html',
        controller: 'KudosEditorCtrl as vm',
        url:'/kudos/create/',
        resolve: {
          kudo : function(){
            return {};
          }
        }
      })
      .state('users', {
        pageTitle: 'All Users',
        templateUrl: 'views/users.html',
        controller: 'UsersCtrl as vm',
        url:'/users'
      })
      .state('login', {
        allowAnon: true,
        templateUrl: 'views/account/login.html',
        controller: 'Login as vm',
        url:'/account/login'
      })
      .state('register', {
        allowAnon: true,
        templateUrl: 'views/account/register.html',
        controller: 'RegisterCtrl as vm',
        url:'/account/register'
    });
  })
  .factory('authInterceptor', function ($q, $cookieStore, $injector) {
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
          console.log(response.headers('Date'));
          console.log(response.headers('Authorization'));
          if (response.headers('Authorization')){
            console.log('ah ha' + response.headers('Authorization'));
            $cookieStore.put('token', response.headers('Authorization'));
          }
          return response;
      },
      // Intercept 401s and redirect you to login
      responseError: function(response) {
        if(response.status === 401) {

          $injector.invoke(function($state, $timeout) {
            $timeout(function(){
              $state.go('login');
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
  .run(function ($rootScope, authentication, $state, $cookieStore) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      $rootScope.pageTitle = toState.pageTitle;
    });
    $rootScope.$on('$stateChangeStart', function(event, toState) {
        if (!authentication.isAuthenticated() && $cookieStore.get('token')) {
          authentication.setAuthenticationFromToken($cookieStore.get('token'));
        }

        if (!toState.allowAnon && !authentication.isAuthenticated()){
          event.preventDefault();
          return $state.go('login');
        }
    });
 });
