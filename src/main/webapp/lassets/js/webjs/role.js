/**
 *  edit role
 */
 $(function(){
	$('.btn-role-edit').click(function(){
		
		var vid =$(this).data("id");
		var link = $('#formRole').attr('action');
		$.ajax({
			url: link,
			type: 'GET',
			dataType: 'JSON',
			data: {
				id:  vid,
				action: 'edit'
			},
			success: function(resp){
				$("#id").val(resp.id);
				$("#name").val(resp.name);
				$("#description").val(resp.description);
			},
			error: function(error){
				showToastr('error',error)
			}
		})
	})
});