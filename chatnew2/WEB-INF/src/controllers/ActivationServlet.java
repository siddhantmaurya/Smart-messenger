package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import models.User;
public class ActivationServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException ,ServletException{
		HttpSession session = request.getSession();

		String nextPage="index.jsp";

		String email=request.getParameter("email");
		String activationCode=request.getParameter("act_code");

		User.activateAccount(email,activationCode);
		
		request.getRequestDispatcher(nextPage).forward(request,response);
	}
}