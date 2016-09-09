(function(){
  'use strict';
  angular.module('kudosApp')
    .directive('userKudosType', function(users, kudos) {
      return{
        restrict: 'E',
        replace: true,
        templateUrl: 'views/directives/userKudosType.html',
        scope: {
          kudos: '=',
          association: '='
        },
        link: function(scope) {

          scope.showComments = false;

          scope.currentUser = users.getCurrentUser();

          scope.toggleComments = function() {
            console.log('toggle', scope.showComments);
            scope.showComments = !scope.showComments;
          }

          scope.newCommentKeydown = function(evt) {
            if(evt.keyCode == 13) {
              var commentText = (scope.newComment).trim();
              if(commentText.length > 0) {
                var updatedKudos = kudos.addComment(scope.kudos.id, commentText);
                scope.newComment = '';
                scope.kudos = updatedKudos;
                console.log(scope.kudos);
              }
            }
          }
        }
      };
    });
})();

