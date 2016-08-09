'use strict';

/**
 * @ngdoc overview
 * @name app6App
 * @description
 * # app6App
 *
 * Main module of the application.
 */
angular
  .module('app6App', [
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
        controller: 'MainCtrl',
        controllerAs: 'vm',
        url:'/home'
      })
      .state('kudos', {
        templateUrl: 'views/kudos/list.html',
        controller: 'KudosCtrl',
        controllerAs: 'vm',
        url:'/kudos/list'
      })
      .state('editKudos', {
        templateUrl: 'views/kudos/edit.html',
        controller: 'KudosEditorCtrl',
        controllerAs: 'vm',
        url:'/kudos/edit/{id:int}',
        resolve: {
          kudo : function($stateParams, kudos){
            return kudos.getKudo($stateParams.id);
          }
        }
      })
      .state('createKudos', {
        templateUrl: 'views/kudos/edit.html',
        controller: 'KudosEditorCtrl',
        controllerAs: 'vm',
        url:'/kudos/create/',
        resolve: {
          kudo : function(){
            return {};
          }
        }
      })
      .state('about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl',
        controllerAs: 'about',
        url:'/account/login'
      })
      .state('login', {
        templateUrl: 'views/account/login.html',
        controller: 'Login',
        controllerAs: 'vm',
        url:'/account/login.html'
      });
  });
