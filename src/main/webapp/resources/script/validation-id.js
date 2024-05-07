$(document).ready(() => {
	$('#id').change(e => {
		const id = $('#id').val();
		
		if(id !== '') {
			$.ajax({
				"method": "POST",
				"url": `/search/user?id=${id}`,
			}).done(response => {
				if(!response.isValid) {
					$('#id').val('');
					$('#error-msg-id-dupl').show();
				} else {
					$('#error-msg-id-dupl').hide();
				}
			})
		}
		
	});
});