$(document).ready(function(){
	
	var logedUser = 10;
//	$.get({
//		url:'user/logedin',
//		success:function(data){
//			logedUser = data;
			$.get({
				url:'clinic/' + logedUser,
				//loggedUser.employeeProfile.clinic.id,
				success:function(data){
					alert(data.name)
					$("#clinicsName").text(data.name);
					$("#clinicsAddress").text(data.address);
					$("#clinicsDescription").text(data.description);

				}
				
			})
			alert(window.location.pathname)
//		}
//	})
	
	$("#doctors").on('click', function(){
		$("#home").load( 'doctors.html');
	
	})
	
	$("#examSlots").on('click', function(){
		
	})
	
	$("#rooms").on('click', function(){
		$.get({
			  url: 'rooms.html',
		      contentType: 'text/html',
		      success: function(result){
		         $('#home').html(result);
		      }
		   });
	})
	
	$("#clinicsReports").on('click', function(){
		
	})
	
	$("#examTypes").on('click', function(){
		
	})
	
	$("#examRequests").on('click', function(){
		
	})
	
})