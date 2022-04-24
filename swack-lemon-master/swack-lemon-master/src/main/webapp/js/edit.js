'use strict';
$('body').on('click', '.edit', function() {
	let message = $(this).nextAll(".log-post").text();
	const textarea = '<textarea id="edit-form" class="form-control">' + message + '</textarea>';
	const cancelButton = '<button class="btn btn-secondary btm-sm edit-cancel">キャンセル</button>';
	const editButton = '<button class="btn btn-primary btm-sm edit-comp">編集</button>';
	const roomId = $(this).nextAll(".HiddenroomId").val();
	const chatId = $(this).nextAll(".HiddenchatLogId").val();
	$('.edit').prop('disabled', true);
	$('.edit').css('opacity', 0.5);
	$(this).nextAll(".log-post").html(textarea + cancelButton + editButton);

	$('.msg > textarea').focus();

	$('body').on('input', '#edit-form', function() {
		var cnt = $('#edit-form').val().length;
		if (cnt == 0) {
			$('.edit-comp').prop('disabled', true);
			$('.edit-comp').css('opacity', 0.5);
		} else {
			$('.edit-comp').prop('disabled', false);
			$('.edit-comp').css('opacity', 1.0);
		}
	})

	$('body').on('click', '.edit-comp', function() {
		let inputVal = $('#edit-form').val();
		$(this).parent().text(inputVal);
		message = inputVal;
		location.href = 'ChatChangeServlet?roomId=' + roomId + '&chatLogId=' + chatId +
			'&message=' + message;
	})

	$('body').on('click', '.edit-cancel', function() {
		$(this).parent().text(message);
		$('.edit').prop('disabled', false);
		$('.edit').css('opacity', 1.0);
	})
})