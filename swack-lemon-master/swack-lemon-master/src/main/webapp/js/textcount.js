'use strict';

$(window).on('load', function() {
	if ($('#name').text().length == 0) {
		$('#send').prop('disabled', true);
		$('#send').css('opacity', 0.5);
	}
})

$('#message').on('input', function() {

	var cnt = $('#message').val().length;

	if (cnt == 0) {
		$('#sendButton').prop('disabled', true);
		$('#sendButton').css('opacity', 0.5);
	} else {
		$('#sendButton').prop('disabled', false);
		$('#sendButton').css('opacity', 1.0);
	}
})

$('#name').on('input', function() {

	var cnt = $('#name').val().length;
	console.log(cnt)

	if (cnt == 0) {
		$('#send').prop('disabled', true);
		$('#send').css('opacity', 0.5);
	} else if (cnt > 20) {
		$('#send').prop('disabled', true);
		$('#send').css('opacity', 0.5);
	} else {
		$('#send').prop('disabled', false);
		$('#send').css('opacity', 1.0);
	}
})