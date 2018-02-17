package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import models.User;

public class SendServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		String msg = request.getParameter("msg");
		String fid = request.getParameter("fid");
		String fmsgTable = request.getParameter("fmsgTable");

		user.sendMessage(msg,fid,fmsgTable);
		response.getWriter().write("successfull");
		
	}
}