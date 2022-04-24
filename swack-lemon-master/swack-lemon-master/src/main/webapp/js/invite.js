
var selectVals = [];

$('body').on('change', '#users', function() {
	selectVals = $('#users').val();
	var selectCnt = selectVals.length;
	if (selectCnt> 0) {
		$('#sendInvite').prop('disabled', false);
		$('#sendInvite').css('opacity', 1.0);
	} else {
		$('#sendInvite').prop('disabled', true);
		$('#sendInvite').css('opacity', 0.5);
	}
})

$(window).on('load', function() {
	$('#sendInvite').prop('disabled', true);
	$('#sendInvite').css('opacity', 0.5);
});