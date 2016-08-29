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
  .factory('restUrl', function($window,apiInfo){

    console.log('port number is: ' + apiInfo.port);
    var KudosRESTAPIPortNumber=':' + apiInfo.port;

    return $window.location.protocol + '//' + $window.location.hostname + KudosRESTAPIPortNumber + '/KudosREST/v1/';

  })
  .config(function ($httpProvider) {
    $httpProvider.interceptors.push('authInterceptor');
  })
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    function addRequiredResolve() {
      //we're doing this because we want to resolve the currentUser during each state, but in order for that to work, the state needs to have other resolves, otherwise we can't add new
      var oldStateFunc = $stateProvider.state;
      $stateProvider.state = function (name, def) {
        def.resolve = def.resolve || {};
        oldStateFunc(name, def);
        return $stateProvider;
      };
    }

    addRequiredResolve();

    $stateProvider
      .state('app',{
        abstract: true,
        template:'<ui-view/>'
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
      .state('app.profile', {
        pageTitle: 'Your Profile',
        templateUrl: 'views/account/profile.html',
        controller: 'ProfileCtrl as vm',
        url:'/account/profile'
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
        resolve:{
          doStuff : function(){
            return 'abc'
          }
        },
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
                authentication.setAuthenticationFromToken($cookieStore.get('token')).then(function(){
                defer.resolve();
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
