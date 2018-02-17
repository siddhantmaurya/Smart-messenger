package controllers;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import models.*;

public class AddFriendServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");

		Integer id =Integer.parseInt(request.getParameter("id"));
		
		user.addFriend(id);
		response.getWriter().write("true");
	}
}