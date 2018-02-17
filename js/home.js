window.onload=initAll;

var obj,msg,center,piclabel,proPic;
var sendbt,msgBody,msgList;
var fuserName,femail,fmobile,fproPic,fprofession,flivesIn,fdob;
var fid,fmsgTable,flist;
var word,newmsg,imgDiv,tmpImgSrc;
var selected,created,newmsgflag;

function initAll(){
	document.getElementById("about").href="about.jsp";
	sendbt=document.getElementById('sendbt');

	home = document.getElementById('home');
	
	msgList = window.frames[0].document.getElementById("msgList");
	
	proPic=document.getElementById('proPic');
	
	$("#pic").mouseenter(function(){
		$('#piclabel').animate({top:'170px',opacity:'0.9'});
	});
	
	$("#pic").mouseleave(function(){
		$('#piclabel').animate({top:'180px',opacity:'0'});
	});

	
	center=document.getElementById('center');
	fuserName=document.getElementById('fuserName');
	femail=document.getElementById('femail');
	fmobile=document.getElementById('fmobile');
	fproPic = document.getElementById("fproPic");
	fprofession=document.getElementById('fprofession');
	fdob=document.getElementById('fdob');
	flivesIn=document.getElementById('flivesIn');
	msg = document.getElementById("text");
	flist = document.getElementsByClassName("friendIconbt");
	
	msg.addEventListener('focus',function(){
		msg.addEventListener('keyup',test);
	});

	sendbt.onclick=sendmsg;
	
	newmsgflag=false;
	window.setInterval(function(){
		if(fid!=undefined && fid!==''&& newmsgflag==true){
			getNewMessage();
		}		
	},2000);
	//window.setInterval(function(){
	//	getNotification()},2000);
	
}

function getNotification(){
	
	obj =new XMLHttpRequest();
	obj.open("GET","getNotification.do",true);
	obj.onreadystatechange=setNotification;
	obj.send(null);
}
function setNotification(){
	if(obj.readyState==4 && obj.status==200){
		if(obj.responseText!=''&&obj.responseText!=null){
			var notifyList = JSON.parse(obj.responseText);
			var fnotify;
			for(var i=0;i<notifyList.length;i++){
				
				for(var j=0;j<flist.length;j++){
					if(flist[j].value==notifyList[i].notification ){
						flist[j].parentElement.style.backgroundColor ='#99ff00';
					}
				 }
			}
		}
	}
}

function test(){
	var pos = msg.value.search('#anim ');
		
	if(pos!=-1 && selected!=true){
		var afteranim = msg.value.substring(pos+6);
		var space = afteranim.search(' ');

		if(space!=-1){	
			word = msg.value.substr(pos+6,space);
			space=-1;
			pos=-1;
			if(word.trim().length>2){
				obj = new XMLHttpRequest();
				obj.open("GET","findServlet.do?word="+word,true);
				obj.onreadystatechange=getImage;
				obj.send(null);
			}
		}
	}else if(center.contains(imgDiv) && selected==true){
		center.removeChild(imgDiv);
		created=false;
	}else if(center.contains(imgDiv)){
		center.removeChild(imgDiv);
		created=false;
	}
	
}

function getImage(){
	if(obj.readyState==4 && obj.status==200){
		var imgarr = JSON.parse(obj.responseText);
			
		if(center.contains(imgDiv)){
			center.removeChild(imgDiv);
			created=false;
		}
		imgDiv = document.createElement('div');
		imgDiv.id ='imgDiv';

		for(var i=0;i<5;i++){
			var img = document.createElement('img');
			img.src = imgarr[i].src;
			img.name = imgarr[i].src;	
			img.onclick = function (){
				
				newmsg = msg.value.replace('#anim '+word,'@file/'+this.name+'/file@');
				
				center.removeChild(imgDiv);	
				selected=true;
			};
			imgDiv.appendChild(img);
		}
		center.appendChild(imgDiv);
		
		selected=false;
		created=true;
		
	}
}

//-----------------------------------------------------------

function getFriendInfo(element){
	newmsgflag=false;
	element.parentElement.style.backgroundColor ='#fff';

	obj = new XMLHttpRequest();
	obj.open('GET','setFriend.do?id='+element.value,true);
	obj.onreadystatechange=setFriendInfo;
	obj.send(null);
	
}
function setFriendInfo(){
	
	if(obj.readyState==4 && obj.status==200){
		
		
		var fobj = JSON.parse(obj.responseText);
		fid = fobj.fid;
		fmsgTable = fobj.fmsgTable;

		fuserName.innerHTML = fobj.fname;
		femail.innerHTML = fobj.femail;
		fproPic.src = fobj.fproPic;		
		fmobile.innerHTML = fobj.fmobile;
		flivesIn.innerHTML = fobj.flivesIn;
		fdob.innerHTML = fobj.fdob;
		fprofession.innerHTML= fobj.fprofession;

		getAllMessage(fid,fmsgTable);
		
		//fmob=fobj.mob;
	}
}

//---------------------

function getAllMessage(fid,fmsgTable){
	
	obj =new XMLHttpRequest();
	obj.open("GET","getMessage.do?fid="+fid+"&fmsgTable="+fmsgTable+"&newmsg="+newmsgflag,true);
	obj.onreadystatechange=setMessages;
	obj.send(null);
	
}
function setMessages(){
	if(obj.readyState==4 && obj.status==200){
		var msgarr=JSON.parse(obj.responseText);
		var msgli;
		msgList.innerHTML=null;
		for(var i=0;i<msgarr.length;i++){
			msgli = document.createElement('li');
			msgli.innerHTML=msgarr[i].msg;
			
			if(msgarr[i].right=="true"){
				msgli.style.textAlign="right";
			}
			msgList.appendChild(msgli);
			
		}		
		window.frames[0].scrollTo(0,msgList.lastElementChild.offsetTop);
		
		newmsgflag=true;
	}
}
function getNewMessage(){
	obj =new XMLHttpRequest();
	obj.open("GET","getMessage.do?fid="+fid+"&fmsgTable="+fmsgTable+"&newmsg="+newmsgflag,true);
	obj.onreadystatechange=setNewMessages;
	obj.send(null);
}
function setNewMessages(){
	if(obj.readyState==4 && obj.status==200){
		 
		var msgarr=JSON.parse(obj.responseText);		
		var msgli;
		for(var i=0;i<msgarr.length;i++){
			msgli = document.createElement('li');
			msgli.innerHTML=msgarr[i].msg;
			
			if(msgarr[i].right=="true"){
				msgli.style.textAlign="right";
			}
			msgList.appendChild(msgli);
		}
		window.frames[0].scrollTo(0,msgList.lastElementChild.offsetTop);
	}	
}

//------------------------------------------

function sendmsg(){
	var cmsg;
	if(newmsg!=null || newmsg!=undefined){
		cmsg=newmsg;
		newmsg=null;
		created=false;
		selected=false;
	}else{
		cmsg=msg.value;
	}
	if(fid!=null && fid!=undefined){
	
		obj = new XMLHttpRequest();
		obj.open('get','sendmsg.do?msg='+cmsg+"&fid="+fid+"&fmsgTable="+fmsgTable,true);
		obj.onreadystatechange=msgStatus;
		obj.send(null);
		msg.value=null;
	}
	
}
function sendFile(response){
	if(response.toString().search("object ")==-1){
		cmsg = '@file/'+response+'/file@';
		obj = new XMLHttpRequest();
		obj.open('get','sendmsg.do?msg='+cmsg+"&fid="+fid+"&fmsgTable="+fmsgTable,true);
		obj.onreadystatechange=msgStatus;
		obj.send(null);
	}else{
		alert("something went wrong please try again");	
	}

}
function msgStatus(){
	if(obj.readyState==4&&obj.status==200){
	getNewMessage(fid,fmsgTable);
	}
}
//-------------------------------------------
function edit(element){
	var button =element;
	button.style.display="none";
	
	var span = button.previousElementSibling;
	var li = button.parentElement;
	var tmp = span.innerHTML;
	span.innerHTML="";	

	var input = document.createElement('input');
	var cancelbt = document.createElement('button');
	var savebt = document.createElement('button');
	
	input.type="text";	
	span.appendChild(input);
	
	savebt.innerHTML='save';
	li.appendChild(savebt);

	cancelbt.innerHTML="cancel";
	li.appendChild(cancelbt);

	cancelbt.onclick=function(){     //---when user clicks on cancel button --
		span.removeChild(input);
		span.innerHTML=tmp;
		li.removeChild(savebt);
		li.removeChild(cancelbt);
		button.style.display='inline';
	};

	
	savebt.onclick=function(){			//--when user clicks on save button-------------
			
		var obj = new XMLHttpRequest();
		obj.open("get","saveInfo.do?value="+input.value+"&label="+span.id,true);
		obj.onreadystatechange=function(){
				
				if(obj.readyState==4&&obj.status==200){
					if(obj.responseText!="null"){
							
						li.removeChild(cancelbt);						
						span.removeChild(input);
						span.innerHTML=obj.responseText;
						button.style.display='inline';
						li.removeChild(savebt);
							
					}
				}
			};
		obj.send(null);

	};

}

