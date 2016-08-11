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
