package controllers;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.json.*;

import models.*;

public class GetMessageServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute("user");
		int fid =Integer.parseInt(request.getParameter("fid"));
		String fmsgTable =request.getParameter("fmsgTable");
		String newmsg =request.getParameter("newmsg");

		ArrayList<Message> allMsgs = user.getAllMessages(fid,fmsgTable,newmsg);
		JSONArray jarr = new JSONArray();
	try{
		for(int i=0;i<allMsgs.size();i++){
			String sender="false";
			JSONObject obj = new JSONObject();
			Message m= allMsgs.get(i);
			
			if(m.getMsg().contains("@file/")){
				obj.put("msg",Message.decode(m.getMsg()));
			}else{
				obj.put("msg",m.getMsg());
			}			
			obj.put("time",String.valueOf(m.getTime()));
			
			if(m.getSender().equals(String.valueOf(user.getUserId()))){
				sender="true";
			}
			obj.put("right",sender);
			obj.put("status",m.getStatus());
			
			jarr.put(obj);
		}
		
		response.getWriter().write(jarr.toString());
	}catch(JSONException e){
			e.printStackTrace();
		}
	}
}