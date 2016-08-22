/*

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
    .factory('kudos', function(_) {

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

        function getAllKudos() {
          return kudos;
        }

        function getKudosByCategory(id) {
          return _.filter(kudos, {kudosCat: id});
        }

        function addKudos(kudo){
          kudo.id = kudo.id || _.maxBy(kudos,'id').id+1;
          kudos.push(kudo);
        }

        function getKudos(id) {
          return _(kudos).find({id: id});
        }

        function getKudosToUser(id) {
          //return _(kudos).filter({toUser: id});
          return _.filter(kudos, {toUser: id});
        }

        function getKudosFromUser(id) {
          return _.filter(kudos, {fromUser: id});
        }

        function updateKudos(kudo){
          angular.copy(kudo,getKudos(kudo.id));
        }

        return {
          addKudos: addKudos,
          updateKudos: updateKudos,
          getAllKudos: getAllKudos,
          getKudosByCategory: getKudosByCategory,
          getKudos: getKudos,
          getKudosToUser: getKudosToUser,
          getKudosFromUser: getKudosFromUser
        };
    });
})();
