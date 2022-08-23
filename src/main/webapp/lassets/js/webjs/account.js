/**
 *  edit
 */
 $(function(){
	$('.btn-account-edit').click(function(){
		
		var id =$(this).data("id");
		var link = $('#formAccount').attr('action');
		$.ajax({
			url: link,
			type: 'GET',
			dataType: 'JSON',
			data: {
				account_id: id,
				action: 'edit'
			},
			success: function(resp){
				$("#account_id").val(resp.account_id);
				$("#fullname").val(resp.fullname);
				$("#email").val(resp.email);
				$("#phone").val(resp.phone);
				$("#address").val(resp.address);
				$('.control-password').hide();
			},
			error: function(error){
				showToastr('error',error)
			}
		})
	})
});