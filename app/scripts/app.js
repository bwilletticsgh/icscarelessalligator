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
  .config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider
      .state('home', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'vm',
        url:'/home'
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
