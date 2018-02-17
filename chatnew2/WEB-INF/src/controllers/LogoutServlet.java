package controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogoutServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		HttpSession session =request.getSession();
		
		session.invalidate();

		request.getRequestDispatcher("index.jsp").forward(request,response);
		
	}
}