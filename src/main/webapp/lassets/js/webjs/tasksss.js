/**
 *  edit
 */
 $(function(){
	$('.btn-tasksss-edit').click(function(){
		
		var sid =$(this).data("id");
		var link = $('#formTask').attr('action');
		$.ajax({
			url: link,
			type: 'GET',
			dataType: 'JSON',
			data: {
				id : sid,
				action: 'edit'
			},
			success: function(resp){
				$("#id").val(resp.id);
				$("#name").val(resp.name);
				$("#group_id ").val(resp.group_id);
				$("#account_id ").val(resp.account_id);
				$("#status_id").val(resp.status_id);
				
			},
			error: function(error){
				showToastr('error',error)
			}
		})
	})
});