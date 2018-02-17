window.onload=initAll;

var em,pass,login;
function initAll(){
	em=document.getElementById('em');
	pass=document.getElementById('pass');
	login=document.getElementById('login');
	login.onclick=userLogin;


}
function userLogin(){
	obj = new XMLHttpRequest();
	obj.open("GET","login.do?uem="+em.value+"pass="+pass.value,true);
	obj.onreadystatechange=loginStatus;
	obj.send(null);
}
function loginStatus(){
	if(obj.readyState==4 && obj.status==200){
		var flag=obj.responseText;
		if(flag=='true'){
			bt.style.visibility='hidden';
			form0.style.visibility='visible';
			
		}else{
			form0.innerHTML="<h3>User does not exist</h3>";
			form0.style.visibility='visible';
		}
	}
}