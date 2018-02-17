package controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.*;

import models.*;

public class SetFriendServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		String id =request.getParameter("id");
		User u = user.getFriendInfo(id);	
	
	try{	
		JSONObject obj = new JSONObject();
		obj.put("fid",String.valueOf(u.getUserId()));
		obj.put("fname",u.getUserName());
		obj.put("femail",u.getEmail());
		obj.put("fproPic",u.getProfilePic());
		obj.put("fmsgTable",u.getMessageTable());
		obj.put("fprofession",u.getProfession());
		obj.put("fmobile",u.getMobile());
		obj.put("flivesIn",u.getLivesIn());
		obj.put("fdob",u.getDob());
		
		response.getWriter().write(obj.toString());
		
	}catch(JSONException e){
			e.printStackTrace();
		}
	}
}