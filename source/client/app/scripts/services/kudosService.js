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
        getKudosByCategory: { method: 'get', isArray: true, url: url + '/cat/all/:catId'},
        create: { method: 'post', url: url + '/create/:fromUserId/:toUserId/:kudosCatId'}
          //POST /v1/kudos/create/{fromUserId}/{toUserId}/{kudosCatId}
      });

      function getKudosByCategory(id) {
        var kudos = kudosResources.getKudosByCategory({ catId: id });
        return kudos;
      }

      function addKudos(kudo){
        return kudosResources.create({fromUserId: kudo.fromUser, toUserId: kudo.toUser, kudosCatId: kudo.kudosCat}, {comments:kudo.comments}).$promise;
      }

      function getKudosToUser(id) {
        var kudos = kudosResources.getAllToUser({ id: id });
        setupAvatarsOnKudosResource(kudos);
        return kudos;
      }

      function getKudosFromUser(id) {
        var kudos = kudosResources.getAllFromUser({ id: id});
        setupAvatarsOnKudosResource(kudos);
        return kudos;
      }

      //TODO: So, this is really just a dummy placeholder. We should really put this into the backend
      function setupAvatarsOnKudosResource(kudos){
        kudos.$promise.then(function(data) {
          _.each(data, function(k) {
            k.toUser.avatarUrl = k.toUser.avatarUrl || "http://robohash.org/" + k.toUser.id + ".png?size=300x300&set=set1";
            k.fromUser.avatarUrl = k.fromUser.avatarUrl || "http://robohash.org/" + k.fromUser.id + ".png?size=300x300&set=set1";
          });
        });
        return kudos;
      }

      return {
        addKudos: addKudos,
        getKudosByCategory: getKudosByCategory,
        getKudosToUser: getKudosToUser,
        getKudosFromUser: getKudosFromUser
      };
    });
})();
