/*

 var kudos = [{
 'id': '1',
 'kudosCat': '1',
 'fromUser': '2',
 'toUser': '4',
 'comments': 'Kudo of cat 1 from user 2 to user 4',
 'dateCreated' : 1471878773719
 }, {
 'id': '2',
 'kudosCat': '1',
 'fromUser': '3',
 'toUser': '4',
 'comments': 'Kudo of cat 1 from user 3 to user 4',
 'dateCreated' : 1471878773719
 }, {
 'id': '2',
 'kudosCat': '2',
 'fromUser': '4',
 'toUser': '5',
 'comments': 'Kudo of cat 2 from user 4 to user 5',
 'dateCreated' : 1471878773719
 }
 ];

 # kudos
 id 			<- Unique
 kudosCat 	<- DBRef to KudosCategory
 fromUser 	<- DBRef to User
 toUser 		<- DBRef to User
 comments
 dateCreated
 dateModified

 */

(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('kudos', function(_, $resource, restUrl) {

      var url = restUrl + 'kudos';

      var kudosResources = $resource(url, {}, {
        getAllFromUser: { method: 'get', isArray: true, url: url + '/fromUser/all/:id'},
        getAllToUser: { method: 'get', isArray: true, url: url + '/toUser/all/:id'},
        getAllByCategory: { method: 'get', isArray: true, url: url + '/cat/all/:id'}
      });

      function getKudosByCategory(id) {
        console.log(id);
        var kudos = kudosResources.getAllByCategory({ id: id });
        return kudos;
      }

      function addKudos(kudo){
        kudo.id = kudo.id || _.maxBy(kudos,'id').id+1;
        kudos.push(kudo);
      }

      function getKudos(id) {
        return _(kudos).find({id: id});
      }

      function getKudosToUser(id) {
        var kudos = kudosResources.getAllToUser({ id: id });
        return kudos;
      }

      function getKudosFromUser(id) {
        var kudos = kudosResources.getAllFromUser({ id: id});
        return kudos;
      }

      function updateKudos(kudo){
        angular.copy(kudo,getKudos(kudo.id));
      }

      return {
        addKudos: addKudos,
        updateKudos: updateKudos,
        getKudosByCategory: getKudosByCategory,
        getKudos: getKudos,
        getKudosToUser: getKudosToUser,
        getKudosFromUser: getKudosFromUser
      };
    });
})();
