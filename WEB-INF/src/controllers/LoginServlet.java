package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import models.User;

public class LoginServlet extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		
		boolean flag=true;
		String nextPage="index.jsp";
		String email=request.getParameter("em");
		String password=request.getParameter("pass");
		
		//validation---
		
		String msg="";
		if(email.length()<5){
			flag=false;
			msg=msg+"Incorrect email id";
		}
		if(password.length()<6||password.length()>16){
			flag=false;
			msg=msg+"passsword length should be greater than 5 and less than 16";
		}
		if(flag){
			User user= new User(email,password);
			if(user.login()){
				session.setAttribute("user",user);
				
				session.setAttribute("friendList",user.getFriendList());
				nextPage="home.jsp";
			}else{
				request.setAttribute("message","Incorrect email id or password ");
			}
		}else{
			request.setAttribute("message",msg);
		}
		request.getRequestDispatcher(nextPage).forward(request,response);
	}
}