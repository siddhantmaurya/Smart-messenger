package controllers;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.*;

import models.*;

public class GetNotificationServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		ArrayList<Integer> notifyList = user.getNotification();
		
		JSONArray jarr = new JSONArray();
		try{	
			for(int i=0;i<notifyList.size();i++){
				JSONObject obj = new JSONObject();
				obj.put("notification",String.valueOf(notifyList.get(i)));
				
				jarr.put(obj);	
			}
		response.getWriter().write(jarr.toString());
		}catch(JSONException e){
				e.printStackTrace();
		}
		
	}
}