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
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
      .state('home', {
        pageTitle: 'Home',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl as vm',
        url:'/home'
      })
      .state('kudosCategories', {
        pageTitle: 'Kudos Category List',
        templateUrl: 'views/kudosCategory/list.html',
        controller: 'KudosCategoryCtrl as vm',
        url:'/kudosCategory/list'
      })
      .state('editKudosCategory', {
        pageTitle: 'Edit Kudos Category',
        templateUrl: 'views/kudosCategory/edit.html',
        controller: 'KudosCategoryEditorCtrl as vm',
        url:'/kudosCategory/edit/{id:string}',
        resolve: {
          kudosCategory : function($stateParams, kudosCategories){
            return kudosCategories.getKudosCategory($stateParams.id);
          }
        }
      })
      .state('createKudosCategory', {
        pageTitle: 'Create Kudos Category',
        templateUrl: 'views/kudosCategory/edit.html',
        controller: 'KudosCategoryEditorCtrl as vm',
        url:'/kudosCategory/create/',
        resolve: {
          kudosCategory : function(){
            return {};
          }
        }
      })
      .state('createKudos', {
        pageTitle: 'Create Kudos',
        templateUrl: 'views/kudos/create.html',
        controller: 'KudosCtrl as vm',
        url: '/kudos/create/',
        result: {
          kudo: function() {
            console.log(kudos.getKudos(''));
            return kudos.getKudos('');
          }
        }
      })
      .state('users', {
        pageTitle: 'All Users',
        templateUrl: 'views/users/list.html',
        controller: 'UsersCtrl as vm',
        url:'/users'
      })
      .state('user', {
        pageTitle: 'User',
        templateUrl: 'views/users/details.html',
        controller: 'UserDetailsCtrl as vm',
        url:'/user/{id:string}',
        resolve: {
          user: function($stateParams, users){
            return users.getUser($stateParams.id);
          },
          kudosToUser: function($stateParams, kudos) {
            // var k = kudos.getKudosToUser($stateParams.id);
            // console.log(k);
            return kudos.getKudosToUser($stateParams.id);
          },
          kudosFromUser: function($stateParams, kudos) {
            return kudos.getKudosFromUser($stateParams.id);
          }
        }
      })
      .state('login', {
        templateUrl: 'views/account/login.html',
        controller: 'Login as vm',
        url:'/account/login'
      })
      .state('register', {
      templateUrl: 'views/account/register.html',
      controller: 'RegisterCtrl as vm',
      url:'/account/register'
    });
  })
  .run(function ($rootScope) {
    $rootScope.$on('$stateChangeSuccess', function (event, toState) {
      $rootScope.pageTitle = toState.pageTitle;
    });
  });
