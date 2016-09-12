(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('organizations', function(_, $resource, restUrl) {

      var url = restUrl + 'org';

      var organizationResources = $resource(url, {}, {
        getAll: { method: 'get', isArray: true, url: url + '/all'} //[GET]   .../v1/org/all
      });

      function getAll() {
        var orgs = organizationResources.getAll();
        return orgs;
      }

      return {
        getAll: getAll
      };
    });
})();
