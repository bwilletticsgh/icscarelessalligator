(function(){
  'use strict';
  angular.module('kudosApp')
    .factory('kudos', function(_) {

        function pastelColors(){
          var r = (Math.round(Math.random()* 127) + 127).toString(16);
          var g = (Math.round(Math.random()* 127) + 127).toString(16);
          var b = (Math.round(Math.random()* 127) + 127).toString(16);
          return '#' + r + g + b;
        }

        var kudos = [{
          'id': 1,
          'name': 'Good Citizenship',
          'description': 'Promoting positive morale through actions of good spirit',
          'color': pastelColors(),
          'icon': 'fa-smile-o'
        }, {
          'id': 2,
          'name': 'Collaboration / Helping / Mentoring',
          'description': 'Leading others through partnerships',
          'color': pastelColors(),
          'icon': 'fa-group'
        }, {
          'id': 3,
          'name': 'Bright Idea / Creativity',
          'description': 'Honoring the creative problem solver',
          'color': pastelColors(),
          'icon': 'fa-lightbulb-o'
        }, {
          'id': 4,
          'name': 'Above and Beyond',
          'description': 'Modeling superior service',
          'color': pastelColors(),
          'icon': 'fa-bolt'
        }, {
          'id': 5,
          'name': 'Make it Happen',
          'description': 'Relentlessly resourceful and productive',
          'color': pastelColors(),
          'icon': 'fa-coffee'
        }, {
          'id': 6,
          'name': 'Unsung Hero',
          'description': 'Working behind the scenes',
          'color': pastelColors(),
          'icon': 'fa-star-half-o'
        }, {
          'id': 7,
          'name': 'Going Green',
          'description': 'Providing outstanding contributions towards sustainability',
          'color': pastelColors(),
          'icon': 'fa-tree'
        }, {
          'id': 8,
          'name': 'Saved Money',
          'description': 'Reduced cost or prevented unnecessary expenditures',
          'color': pastelColors(),
          'icon': 'fa-money'
        }, {
          'id': 9,
          'name': 'Increased Throughput',
          'description': 'Increased the speed or productivity of peers or processes',
          'color': pastelColors(),
          'icon': 'fa-fighter-jet'
        }, {
          'id': 10,
          'name': 'Enhanced Quality',
          'description': 'Enhanced the output quality of people or processes',
          'color': pastelColors(),
          'icon': 'fa-check'
        }, {
          'id': 11,
          'name': 'Holy Grail of Efficiency',
          'description': 'Saved Money, Increased Throughput and Enhanced Quality all at once',
          'color': pastelColors(),
          'icon': 'fa-thumbs-up'
        }
        ];

        function getKudos() {
          return kudos;
        }

        function addKudo(kudo){
          kudo.id = kudo.id || _.maxBy(kudos,'id').id+1;
          kudos.push(kudo);
        }

        function getKudo(id) {
          return _(kudos).find({id: id});
        }

        function updateKudo(kudo){
          angular.copy(kudo,getKudo(kudo.id));
        }

        return {
          addKudo: addKudo,
          updateKudo: updateKudo,
          getKudos: getKudos,
          getKudo: getKudo
        };
    });
})();
