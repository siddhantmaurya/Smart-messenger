package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import models.*;

public class SaveInfoServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		String label =request.getParameter("label");
		String value =request.getParameter("value");
		
		if ((label!=null ||label!="")&& (value!=null&&value!="")){
			String tmp = user.saveInfo(label,value);
			if(tmp!=null){
				response.getWriter().write(tmp);
			}else{
				response.getWriter().write("null");
			}
		}else{
				response.getWriter().write("null");
		}
	}
}