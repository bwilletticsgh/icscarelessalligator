//we want to collapse nav whenever we click

$(document).ready(function() {
  $("body").click(function(event) {
    if ($(".navbar-collapse").is(":visible") && $(".navbar-toggle").is(":visible") ) {
      $('.navbar-collapse').collapse('toggle');
    }
  });
});
