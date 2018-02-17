package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import models.*;

public class SearchServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		String friendName = request.getParameter("fn");

		ArrayList<User> friendList = user.getFriendList();
		ArrayList<User> searchList = Search.searchUser(friendName,user.getUserId());
		
		JSONArray arr = new JSONArray();
		try{
			for(int i=0;i<searchList.size();i++){
				JSONObject obj = new JSONObject();

				String status="false";
				User u=searchList.get(i);

				obj.put("userName",u.getUserName());
				obj.put("profilePic",u.getProfilePic());
				obj.put("userId",String.valueOf(u.getUserId()));
				
				for(int j=0;j<friendList.size();j++){
				
					if(friendList.get(j).getUserId()==u.getUserId()){
						status="true";
						break;						
					}
					
				}
				
				obj.put("status",status);
				arr.put(obj);
			}
			response.getWriter().write(arr.toString());
			
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
}