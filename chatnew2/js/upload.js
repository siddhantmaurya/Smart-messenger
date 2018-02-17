$(document).ready(
	function(){ 
	  $('#profilePicbt').ajaxfileupload({
		'action': 'upload.do', 
		'method':'Post',
		'enctype':'multipart/form-data',
		'onComplete': function(response) {         
			$('#upload').hide();
			setPic(response);
		},
		'onStart': function() {
			$('#upload').show(); 
		}
	  })
		
	$('#fileSelect').ajaxfileupload({
		'params': 'charset="UTF-8"',
		'action': 'sendFile.do', 
		'method':'Post',
		'enctype':'multipart/form-data',
		'onComplete': function(response) {         
			
			sendFile(response);
		},
		'onStart': function() {
			 set();
		}
	  })

});

function setPic(response){
	var proPic = document.getElementById("proPic");
	
	proPic.src=response;
}
function set(){
	
}

