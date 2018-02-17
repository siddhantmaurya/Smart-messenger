<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" type="text/css" href="css/index.css"/>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css"/>
	<script type="text/javascript" src="js/index.js" ></script>
	
</head>
<body>
	<%@ page isErrorPage="true"%>
	<%@include file="header.jsp"%>
	<div id="container" class="container">
		<%String msg = (String)request.getAttribute("message");%>
		<%String hide = (String)request.getAttribute("hide_register");%>
		
		<div class="row">
			
			<div id="login" class="col-sm-6">
				<%if(msg!=null){%>
					<div id="msg" <%if(hide=="true"){%> <%="style='background-color:#ccff99'"%><%}%>>
					<%=msg%>
					</div>
				<%}%>
				<form method="post" id="form1" class="form-group" action="login.do">
				
					Email&nbsp;&nbsp;<input class="form-control" id="em" type="email" name="em" />
					Password&nbsp;&nbsp;<input class="form-control" id="pass" type="password" name="pass" /><br>
					<button type=" button" class="btn btn-success">Login</button>
				</form>
			</div>
			<%if(hide!="true"){%>
			<div id="register" class="col-sm-6">
				<form method="post" id="form2" class="form-group" action="register.do">
					<table>
						<tr>
							<td>Username</td>
							<td>:</td>
							<td><input class="form-control" type="text" name="un" placeholder="Your name here"/></td>
						</tr>
						<tr>
							<td>Email</td>
							<td>:</td>
							<td><input class="form-control" type="email" name="em"/></td>
						</tr>
						<tr>
							<td>Password</td>
							<td>:</td>
							<td><input class="form-control" type="password" name="pass"></td>
						</tr>
						<tr>
							<td>Retype password</td>
							<td>:</td>
							<td><input class="form-control" type="password" name="repass"></td>
						</tr>
						<tr>
							<td>Gender</td>
							<td>:</td>
							<td>
								<label class="radio-inline">M&nbsp;&nbsp;<input type="radio" name="gen" value="M"/></label>
								<label class="radio-inline">F&nbsp;&nbsp;<input type="radio" name="gen" value="F"/></label>
							</td>
						</tr>
						<tr>
							<td>mobile</td>
							<td>:</td>
							<td><input class="form-control" type="text" name="mob"/></td>
						</tr>
						<tr>
							<td><button type=" button" class="btn btn-success">Submit</button></td>
						</tr>
					</table>	
					
				</form>
			</div>
			<%}%>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</body>
</html>