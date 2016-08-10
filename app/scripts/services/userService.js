(function(){
  'use strict';
  angular.module('app6App')
    .factory('users', function(_) {
        var users = [{
          'id': 1,
          'firstName': 'Daniel',
          'lastName': 'Bishop',
          'email': 'admin@admin.com',
          'gender': 'Male',
          'avatar': 'https://robohash.org/voluptatequiquaerat.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': true
        }, {
          'id': 2,
          'firstName': 'Judith',
          'lastName': 'Grant',
          'email': 'jgrant1@networksolutions.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/utreruminventore.png?size=300x300&set=set1',
          'password': 'a',
          'isAdmin': false
        }, {
          'id': 3,
          'firstName': 'Jeffrey',
          'lastName': 'Freeman',
          'email': 'jfreeman2@soup.io',
          'gender': 'Male',
          'avatar': 'https://robohash.org/quaequiaasperiores.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 4,
          'firstName': 'Sharon',
          'lastName': 'Gomez',
          'email': 'sgomez3@amazon.co.uk',
          'gender': 'Female',
          'avatar': 'https://robohash.org/quodrerumodio.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 5,
          'firstName': 'Kathy',
          'lastName': 'Meyer',
          'email': 'kmeyer4@dion.ne.jp',
          'gender': 'Female',
          'avatar': 'https://robohash.org/quivoluptatemdoloribus.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 6,
          'firstName': 'Jesse',
          'lastName': 'Griffin',
          'email': 'jgriffin5@hhs.gov',
          'gender': 'Male',
          'avatar': 'https://robohash.org/adreprehenderitminima.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 7,
          'firstName': 'Tina',
          'lastName': 'Cook',
          'email': 'tcook6@reverbnation.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/voluptatumipsameos.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 8,
          'firstName': 'George',
          'lastName': 'Williamson',
          'email': 'gwilliamson7@github.io',
          'gender': 'Male',
          'avatar': 'https://robohash.org/eiusidet.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 9,
          'firstName': 'Peter',
          'lastName': 'Arnold',
          'email': 'parnold8@cyberchimps.com',
          'gender': 'Male',
          'avatar': 'https://robohash.org/laboreidmaiores.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 10,
          'firstName': 'Bonnie',
          'lastName': 'Wells',
          'email': 'bwells9@mit.edu',
          'gender': 'Female',
          'avatar': 'https://robohash.org/quiasedut.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 11,
          'firstName': 'Wanda',
          'lastName': 'Parker',
          'email': 'wparkera@mapquest.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/voluptasmodisint.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 12,
          'firstName': 'Joshua',
          'lastName': 'Porter',
          'email': 'jporterb@a8.net',
          'gender': 'Male',
          'avatar': 'https://robohash.org/voluptatemaspernaturet.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 13,
          'firstName': 'Carl',
          'lastName': 'Carpenter',
          'email': 'ccarpenterc@hc360.com',
          'gender': 'Male',
          'avatar': 'https://robohash.org/doloressuscipitiure.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 14,
          'firstName': 'Adam',
          'lastName': 'White',
          'email': 'awhited@google.com.hk',
          'gender': 'Male',
          'avatar': 'https://robohash.org/quasdelectuspraesentium.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 15,
          'firstName': 'Johnny',
          'lastName': 'Wilson',
          'email': 'jwilsone@skype.com',
          'gender': 'Male',
          'avatar': 'https://robohash.org/totaminquas.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 16,
          'firstName': 'Ruby',
          'lastName': 'Kelley',
          'email': 'rkelleyf@live.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/sitblanditiiscum.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 17,
          'firstName': 'Rebecca',
          'lastName': 'Boyd',
          'email': 'rboydg@nbcnews.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/veniamremnumquam.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 18,
          'firstName': 'Mary',
          'lastName': 'Clark',
          'email': 'mclarkh@newsvine.com',
          'gender': 'Female',
          'avatar': 'https://robohash.org/nostrumminusquibusdam.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 19,
          'firstName': 'Sean',
          'lastName': 'Elliott',
          'email': 'selliotti@elpais.com',
          'gender': 'Male',
          'avatar': 'https://robohash.org/eaetquam.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }, {
          'id': 20,
          'firstName': 'Willie',
          'lastName': 'Ryan',
          'email': 'wryanj@e-recht24.de',
          'gender': 'Male',
          'avatar': 'https://robohash.org/animietoptio.png?size=300x300&set=set1',
          'password': 'password123',
          'isAdmin': false
        }];

        var _currentUser;

        function setCurrentUser(user){
          _currentUser=user;
        }

        function getCurrentUser() {
          return _currentUser;
        }

        function getUsers() {
          return users;
        }

        function addUser(user){
          users.push(user);
        }

        function getUser(email) {
          return _(users).find(function(p) {return p.email.toUpperCase() === email.toUpperCase();});
        }

        return {
          addUser: addUser,
          getUsers: getUsers,
          getUser : getUser,
          setCurrentUser : setCurrentUser,
          getCurrentUser : getCurrentUser
        };
    });
})();
