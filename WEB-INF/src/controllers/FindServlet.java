package controllers;

import java.io.*;
import java.util.*;
import java.lang.Math;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

public class FindServlet extends HttpServlet{
	static String add="searchimages";

	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
		HttpSession session = request.getSession();
		String word = request.getParameter("word");
		String dir = getServletContext().getRealPath("/searchimages");
		String d,n,image;
		
			JSONArray arr = new JSONArray();
		try{	
			for(int i=0;i<5;i++){
				JSONObject obj = new JSONObject();
				d = String.valueOf(Math.random());
				n =d.substring(2,3);
				image= findFile(word+n+".png",new File(dir));
				
				add="searchimages";
				obj.put("src",image);
				arr.put(obj);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}			
		
		response.getWriter().write(arr.toString());		
		
	}
		
    public static String findFile (String name,File file)throws SecurityException {
			
        File[] list = file.listFiles();

        if(list!=null){
			for (File fil : list){
				
				if (fil.isDirectory()){
					add = add+fil.getName();
					findFile(name,fil);
				}
				else if (name.equalsIgnoreCase(fil.getName())){
					add = add+"/"+fil.getName();
					break;
					//System.exit(1);
				}
			}
		}
		return add;
    }
}