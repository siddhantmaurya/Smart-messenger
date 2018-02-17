package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import models.User;
import utils.EmailSender;
import utils.EmailFormat;

public class RegisterServlet extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		String email;
		String password;
		String rePassword;
		String gender;
		String userName;
		String mobile;
		userName= request.getParameter("un");
		email = request.getParameter("em");
		password = request.getParameter("pass");
		rePassword = request.getParameter("repass");
		gender = request.getParameter("gen");
		mobile = request.getParameter("mob");
		//validation of entries

		boolean flag=true;
		String msg="";

		if(userName.length()<2){
			flag=false;
			msg=msg+"Username is Invalid or too short";
		}
		if(email.length()<5){
			flag = false;
			msg = msg + " Invalid Email..<br />";
		}

		if(password.length()<6||password.length()>16){
			flag = false;
			msg = msg + "Password must be >5 and  < 12 characters..<br />";
		}

		if(!password.equals(rePassword)){
			flag = false;
			msg = msg + "Password and repassword must match..<br />";
		}

		if(gender==null){
			flag = false;
			msg = msg + "Gender is required..<br />";
		}
		if(flag){
			Random random=new Random();
			Date dt=new Date();
			String activationCode = dt.getTime()+"_"+random;

			User user=new User(userName,email,password,gender,mobile,activationCode);
			if(user.register()){
				String path=getServletContext().getRealPath("/uploads");
				File f= new File(path,user.getEmail());
				f.mkdir();

				request.setAttribute("message","successfully registered");
				request.setAttribute("hide_register","true");
				
				//EmailSender.sendEmail(email,EmailFormat.getActivationCodeEmail(userName,email,activationCode));
			}else{
				request.setAttribute("message","failed to register");
			}
			
		}else{
			request.setAttribute("message",msg);
		}
		request.getRequestDispatcher("index.jsp").forward(request,response);
	}
}