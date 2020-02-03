$(document).ready(function(){
	var i = 10;
	$.get({
		url:'clinic/' + 10 + '/room',
		//loggedUser.employeeProfile.clinic.id,
		success:function(data){
			alert(data)
		}
		
	})
})