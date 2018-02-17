<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/home.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<script src="js/home.js" ></script>
	<script src="js/jquery.js"></script>
	<script src="js/search.js"></script>
	<script src="js/jquery.ajaxfileupload.js"></script>
	<script language="javascript" src="js/upload.js"></script>
</head>
<body>
	<%@include file="header.jsp"%>

	<%@page import="models.*"%>
	<%@page import="java.util.ArrayList"%>
	<%User user=(User)session.getAttribute("user");%>
	<div id="searchBar" >
			<span id="searchText">&nbsp;&nbsp;Search </span>
			<input id="searchinp" type="text" placeholder="type username to search...." onfocus="search(this)"/>
			<div id="searchResultdiv">
				<ul id="searchul">
				</ul>
			</div>
	</div>
	<div id="container" class="container-fluid">
		<%@ page errorPage="index.jsp"%>
		<div class="row">
			<div id="sidebar" class="col-sm-3">
				<div id='pic'>
				<label  for="profilePicbt" >
					<img id="piclabel" src="images/upload5.png" height=30 width=30 />
				</label>
				<img id="proPic" src="<%=user.getProfilePic()%>" width="200" height="200"/><br>
						
				</div>
				<form >
					<input id="profilePicbt" type="file" name="proPic" size="50"/>
				</form>
				<div id="upload"></div>
				<ul class="sideList">
					<li>Name:&nbsp;&nbsp;<span class="info" id="user_name"><%=user.getUserName()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					</li>
					<li>Profession:&nbsp;&nbsp;<span class="info" id="profession"><%=user.getProfession()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					</li>
					<li>Lives in:&nbsp;&nbsp;<span class="info" id="lives_in"><%=user.getLivesIn()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					</li>
					<li>Born on:&nbsp;&nbsp;<span class="info" id="dob"><%=user.getDob()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					</li>
					<li>Languages:&nbsp;&nbsp;<span class="info" id="lang"><%=user.getLang()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					</li>
					<li>contact no.:&nbsp;&nbsp;<span class="info" id="mobile"><%=user.getMobile()%></span>
					<button onclick="edit(this)" ><img src="images/edit.png" height=15 width=15/></button>
					
					</li>				
				</ul>
			</div>
		
		<%ArrayList<User> friendList = (ArrayList<User>)session.getAttribute("friendList");%>
		
		<div id="center" class="col-sm-6">			
			<iframe id="msgBody" src="msg.jsp">
				
			</iframe>
			<div id="friend">
				<img id="fproPic" src="images/profile.png" width="150" height="150"/>
				<ul class="sideList">
					<li>Username :&nbsp;&nbsp;<span class="info" id="fuserName"></span></li>
					<li>Email id :&nbsp;&nbsp;<span class="info" id="femail"></span></li>
					<li>Contact no. :&nbsp;&nbsp;<span class="info" id="fmobile"></span></li>
					<li>Profession:&nbsp;&nbsp;<span class="info" id="fprofession"></span></li>
					<li>Birth date:&nbsp;&nbsp;<span class="info" id="fdob"></span></li>
					<li>Lives in:&nbsp;&nbsp;<span class="info" id="flivesIn"></span></li>
				</ul>
				
			</div>
					<!--	<iframe src="https://giphy.com/embed/veasUML22kw24" width="480" height="270" frameBorder="0"  allowFullScreen></iframe><p><a href="https://giphy.com/gifs/curious-veasUML22kw24">via GIPHY</a></p>
					-->	
			<label id="imglabel" for="fileSelect">
				<img src="images/upload2.png" height=35 width=35 />
			</label>
			<form>
				<input type="file" id="fileSelect" name="file" size="30"/>
			</form>
			
				<input id="text" type="text" placeholder="type here..."/>
				<input id="sendbt" type="button" value="Send" />
			
		</div>

			
		<div id="right" class="col-sm-3">
			<div id='temp'>Friends</div>
			
			<ul class="rightList">
				<%int i;%>
				<%int flistSize=friendList.size();%>

				<%if(friendList!=null){%>
					<%for(i=0;i<flistSize;i++){%>
					<li>
						<div class="friendIcon">
							<img class="friendIconimg" src="<%=friendList.get(i).getProfilePic()%>" width="70" height="70"/>
							<button class="friendIconbt" onclick="getFriendInfo(this)" value="<%=friendList.get(i).getUserId()%>" >
								<%=friendList.get(i).getUserName()%>
							</button>
						</div>
					</li>
					<%}%>
				<%}%>
			</ul>
		</div>
	</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>