$(document).ready(function(){
	$('#search').click(function(){
		
		$('#searchBar').slideToggle("fast");
		});	

	$('#searchBar').mouseleave(function(){
		$('#searchBar').slideToggle("fast");
		});

});

var obj;
var searchul,searchResultdiv;

function clear(element){
	alert('hey');
	//searchul.innerHTML='';
}
function search(element){
	searchul = document.getElementById('searchul');
	searchResultdiv = document.getElementById('searchResultdiv');
	element.addEventListener('keyup',function(){
		searchul.innerHTML='';		
		fn=element.value;
		if(fn!=null && fn!=''){
			obj =new XMLHttpRequest();
			try{	
				obj.onreadystatechange=getUsers;
				obj.open("GET","search.do?fn="+fn,true);
				obj.send(null);
			}catch(e){
			alert("unable to connect to the server");
			}
		}
	});
}
function getUsers(){
	if(obj.readyState==4 && obj.status==200){
		
		var arr = JSON.parse(obj.responseText);
		
		if(arr.length>0){
			searchResultdiv.style.display="block";
		
			for(var i=0;i<arr.length;i++){
				var li = document.createElement('li');
				var img = document.createElement('img');
				var sp2 = document.createElement('span');
				var btAdd = document.createElement('button');
				if(arr[i].status=="true"){
					btAdd.innerHTML='friend';
					btAdd.disabled='disabled';
				}else{
					btAdd.innerHTML='Add';
				}
				btAdd.value=arr[i].userId;
				btAdd.onclick=getUser;

				img.src = arr[i].profilePic;
				li.appendChild(img);
				sp2.innerHTML = arr[i].userName;
				
				li.appendChild(sp2);
				li.appendChild(btAdd);
				searchul.appendChild(li);
			}
		}
	}
}

function getUser(){
	
	obj = new XMLHttpRequest();
	obj.onreadystatechange=addUser;
	obj.open("get","addFriend.do?id="+this.value,true);
	obj.send(null);
}
function addUser(){
	if(obj.readyState==4 && obj.status==200){
		if(obj.responseText=='true'){
			alert("added");
		}
	}
}
