//we want to collapse nav whenever we click
'use strict';
(function(){
  $(document).ready(function() {
    $("body").click(function() {
      if ($(".navbar-collapse").is(":visible") && $(".navbar-toggle").is(":visible") ) {
        $('.navbar-collapse').collapse('toggle');
      }
    });
  });
})();
