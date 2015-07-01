
 // Calling the function
$(function() {
    $('.toggle-nav').click(function() {
        toggleNavigation();
    });
});


// The toggleNav function itself
function toggleNavigation() {
	
    if ($('#container').hasClass('display-nav span5')) {
        // Close Nav
        $('#container').removeClass('display-nav span5');
//		$('#map').removeClass('span6').addClass('span10');
//		$('#bar').removeClass('span5').addClass('span1');
    } else {
        // Open Nav
        $('#container').addClass('display-nav span5');
//		$('#map').removeClass('span10').addClass('span6');
//		$('#bar').removeClass('span1').addClass('span5');
    }
}


// SLiding codes
$("#toggle > li > div").click(function () {
    if (false == $(this).next().is(':visible')) {
        $('#toggle ul').slideUp();
    }

    var $currIcon=$(this).find("span.the-btn");

    $("span.the-btn").not($currIcon).addClass('fa-plus').removeClass('fa-minus');

    $currIcon.toggleClass('fa-minus fa-plus');

    $(this).next().slideToggle();

    $("#toggle > li > div").removeClass("active");
    $(this).addClass('active');

});