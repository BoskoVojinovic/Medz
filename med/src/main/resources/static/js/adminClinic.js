$(document).ready(function(){
	
	
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