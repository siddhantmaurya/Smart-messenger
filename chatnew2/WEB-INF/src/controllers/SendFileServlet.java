package controllers;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import models.User;

public class SendFileServlet extends HttpServlet{
	public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String path=null;	
		boolean flag = ServletFileUpload.isMultipartContent(request);
		if(flag){
			DiskFileItemFactory dfif = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(dfif);
			
			try{
				List<FileItem> allFields = sfu.parseRequest(request);
				Iterator<FileItem> itr = allFields.iterator();
				while(itr.hasNext()){
					FileItem fi = itr.next();
					if(!fi.isFormField()){
						String em = user.getEmail();
						String fname = fi.getName();
						String realPath = getServletContext().getRealPath("/uploads/"+em);

						path="uploads/"+em+"/send/"+fname;

						File f1 = new File(realPath,"send");
						f1.mkdir();
						File f2 =new File(f1,fname);					

						try{
							fi.write(f2);
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}
			}catch(FileUploadException e){
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
			
			response.getWriter().write(path);
		}
	}
}