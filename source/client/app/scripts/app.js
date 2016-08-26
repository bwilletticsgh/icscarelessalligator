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
  .constant('swal',window.swal)
  .factory('restUrl', function($window){
    return $window.location.protocol + '//' + $window.location.hostname + '/KudosREST/v1/';
  })
  .config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
  })
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
      .state('app',{
        abstract: true,
        template:'<ui-view/>',
        resolve: {
          currentUser: function(users, $rootScope){
            $rootScope.currentUser = users.getCurrentUser();
            return $rootScope.currentUser;
          }
        }
      })
      .state('app.home', {
        allowAnon: true,
        pageTitle: 'Home',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl as vm',
        url:'/home'
      })
      .state('app.kudosCategories', {
        pageTitle: 'Kudos Category List',
        templateUrl: 'views/kudosCategory/list.html',
        controller: 'KudosCategoryCtrl as vm',
        url:'/kudosCategory/list'
      })
      .state('app.editKudosCategory', {
        pageTitle: 'Edit Kudos Category',
        templateUrl: 'views/kudosCategory/edit.html',
        controller: 'KudosCategoryEditorCtrl as vm',
        url:'/kudosCategory/edit/{id:string}',
        resolve: {
          kudosCategory: function($stateParams, kudosCategories){
            return kudosCategories.getKudosCategoryById($stateParams.id);
          },
          kudosInCategory: function($stateParams, kudos) {
             return kudos.getKudosByCategory($stateParams.id);
          }
        }
      })
      .state('app.createKudosCategory', {
        pageTitle: 'Create Kudos Category',
        templateUrl: 'views/kudosCategory/edit.html',
        controller: 'KudosCategoryEditorCtrl as vm',
        url:'/kudosCategory/create/',
        resolve: {
          kudosCategory : function(){
            return {};
          },
          kudosInCategory: function() {
            return [];
          }
        }
      })
      .state('app.createKudos', {
        pageTitle: 'Create Kudos',
        templateUrl: 'views/kudos/create.html',
        controller: 'KudosCtrl as vm',
        url: '/kudos/create/{userId}'
      })
      .state('app.users', {
        pageTitle: 'All Users',
        templateUrl: 'views/users/list.html',
        controller: 'UsersCtrl as vm',
        url:'/users'
      })
      .state('app.user', {
        templateUrl: 'views/users/details.html',
        controller: 'UserDetailsCtrl as vm',
        url:'/user/{id:string}',
        params: {
          message: null
        },
        resolve: {
          user: function($stateParams, users){
            return users.getUser($stateParams.id).$promise;
          },
          kudosToUser: function($stateParams, kudos) {
            return kudos.getKudosToUser($stateParams.id);
          },
          kudosFromUser: function($stateParams, kudos) {
            return kudos.getKudosFromUser($stateParams.id);
          },
          pageTitle: function(user){
            return user.firstName + ' ' + user.lastName;
          }
        }
      })
      .state('app.login', {
        allowAnon: true,
        templateUrl: 'views/account/login.html',
        controller: 'Login as vm',
        url:'/account/login'
      })
      .state('app.register', {
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
          if (response.headers('Authorization')){
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
  .run(function ($rootScope, authentication, $state, $cookieStore, swal) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      if($rootScope.alertMessage) {
        swal($rootScope.alertMessage.title,$rootScope.alertMessage.message,"success");
        $rootScope.alertMessage = null;
      }
      $rootScope.pageTitle = toState.pageTitle || $state.$current.locals.globals.pageTitle;
    });
    $rootScope.$on('$stateChangeStart', function(event, toState) {
        if (!authentication.isAuthenticated() && $cookieStore.get('token')) {
          authentication.setAuthenticationFromToken($cookieStore.get('token'));
        }

        if (!toState.allowAnon && !authentication.isAuthenticated()){
          event.preventDefault();
          return $state.go('app.login');
        }
    });
 });
