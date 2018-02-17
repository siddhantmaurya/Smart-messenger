package models;

import java.sql.*;
import java.util.*;
import models.User;

public class Search{

	public static ArrayList<User> searchUser(String un,Integer userId){
		ArrayList<User> list = new ArrayList<User>();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");

			String q="select user_name,profile_pic,user_id from users where user_name like '"+un+"%' and user_id!=?;";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setInt(1,userId);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				User user=new User();
				user.setUserName(rs.getString(1));
				user.setProfilePic(rs.getString(2));
				user.setUserId(rs.getInt(3));
				list.add(user);
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
}