/**
 *  edit project
 */
 $(function(){
	$('.btn-project-edit').click(function(){
		
		var sid =$(this).data("id");
		var link = $('#formProject').attr('action');
		$.ajax({
			url: link,
			type: 'GET',
			dataType: 'JSON',
			data: {
				id: sid,
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