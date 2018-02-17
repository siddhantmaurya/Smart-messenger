package models;

import java.sql.*;
import java.util.*;
import org.jasypt.util.password.StrongPasswordEncryptor;

import models.Message;

public class User {

	private String userName;
	private Integer userId;
	private String email;
	private String password;
	private String gender;
	private String profession;
	private String livesIn;
	private String dob;
	private String lang;
	private String mobile;
	private String profilePic;
	private String activationCode;
	private int status;
	private String messageTable;
	private String friendListTable;
	private ArrayList<User> friendList;
	
	
	//````````````````
	public User(){
	
	}
	public User(Integer userId,String userName,String profilePic){
		this.userId=userId;
		this.userName=userName;
		this.profilePic = profilePic;
	}
	
	public User(String email,String password){
		this.email=email;
		this.password=password;
	}
	public User(String userName,String email,String password,String gender,String mobile,String activationCode){
		this.userName=userName;
		this.email=email;
		this.password=password;
		this.gender=gender;
		this.mobile=mobile;
		this.activationCode=activationCode;
	}
	
	//``````````````
	public String saveInfo(String label,String value){
	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			
			String q1="update users set "+label+"=? where user_id=?;";
			PreparedStatement ps = con.prepareStatement(q1);
		
			ps.setString(1,value);
			ps.setInt(2,userId);			

			int rs=ps.executeUpdate();
			if(rs!=1){
				value=null;
			}
			
			con.close();
		}catch(SQLException e){
			e.printStackTrace();	
		}catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		
		return value;
	}
	public static boolean activateAccount(String email, String activationCode){
		boolean flag=false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			
			String q1="select user_id from users where email=? and activation_code=?;";
			PreparedStatement ps = con.prepareStatement(q1);
		
			ps.setString(1,email);
			ps.setString(2,activationCode);
			

			ResultSet res=ps.executeQuery();
			if(res.next()){
				int userId=res.getInt(1);
				
				PreparedStatement ps2=con.prepareStatement("update users set status=2, activation_code='--' where user_id=?;");
				ps2.setInt(1,userId);
				int rs=ps2.executeUpdate();
				if (rs==1){
					flag=true;
				}
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();	
		}catch(ClassNotFoundException e){
			e.printStackTrace();	
		}
		
		return flag;
		
	}

	public boolean sendMessage(String msg,String fid,String fmsgTable){
		boolean flag=false;
	
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			String q="insert into "+fmsgTable+"(msg,sender,reciever,time) values(?,?,?,now());";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1,msg);
			ps.setString(2,String.valueOf(userId));
			ps.setString(3,fid);
			
			int rs = ps.executeUpdate();
			if(rs==1){
				flag=true;
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public ArrayList<Integer> getNotification(){
		ArrayList<Integer> notifyList = new ArrayList<Integer>();
		try{	
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			
			String qn = "select sender from "+messageTable+" where status='unread';";
			PreparedStatement psn = con.prepareStatement(qn);
			ResultSet resn=psn.executeQuery();
			while(resn.next()){
				notifyList.add(resn.getInt(1));
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return notifyList;
	}

	public ArrayList<Message> getAllMessages(int fid,String msgTable,String newmsg){
		ArrayList<Message> msgList = new ArrayList<Message>();
		boolean flag=false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			
			String q1,q2;
				if(newmsg.equals("true")){
					q1 = "select msg,time,sender,status from "+messageTable+" where sender=? and status='unread' union ";
					q2 = "select msg,time,sender,status from "+msgTable+" where sender=? and fstatus='unread' order by time;";
				}else{
					q1 = "select msg,time,sender,status from "+messageTable+" where sender=? union ";
					q2 = "select msg,time,sender,status from "+msgTable+" where sender=? order by time;";
					
				}
				PreparedStatement ps = con.prepareStatement(q1+q2);
				ps.setInt(1,fid);
				ps.setInt(2,userId);
				ResultSet res=ps.executeQuery();			
				while(res.next()){
					flag=true;
					Message msg = new Message();

					msg.setMsg(res.getString(1));
					msg.setTime(res.getDate(2));
					msg.setSender(res.getString(3));
					msg.setStatus(res.getString(4));

					msgList.add(msg);
					
				}
				if(flag){
					String q3="update "+messageTable+" set status='read' where sender=?;";
					ps = con.prepareStatement(q3);
					ps.setInt(1,fid);
					int rs = ps.executeUpdate();	
					q3="update "+msgTable+" set fstatus='read' where sender=?;";
					ps = con.prepareStatement(q3);
					ps.setInt(1,userId);
					rs = ps.executeUpdate();
				}
			
			
			con.close();		
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}	
		return msgList;
	}
	public void addFriend(Integer id){
		
		String un,proPic;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			
			String q="insert into "+friendListTable+"(f_id) value(?);";
			PreparedStatement ps = con.prepareStatement(q);
			ps = con.prepareStatement(q);
			ps.setInt(1,id);
			int rs1 = ps.executeUpdate();
			
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	public boolean uploadProfilePic(String proPic){
		boolean flag = false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");

			String q="update users set profile_pic=? where user_id=?;";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1,proPic);
			ps.setInt(2,userId);
			int rs = ps.executeUpdate();
			if(rs==1){
				flag=true;
				profilePic=proPic;
			}
			con.close();
			
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return flag;
	}

	public User getFriendInfo(String id){
		
		User u = new User();		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			String q="select user_id,user_name,email,mobile,profession,lives_in,dob,profile_pic,msg_tbl from users where user_id=?;";
			PreparedStatement ps=con.prepareStatement(q);
			
			ps.setInt(1,Integer.parseInt(id));
			ResultSet rs=ps.executeQuery();

			if(rs.next()){
				u.setUserId(rs.getInt(1));
				u.setUserName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setMobile(rs.getString(4));
				u.setProfession(rs.getString(5));
				u.setLivesIn(rs.getString(6));
				u.setDob(rs.getString(7));
				u.setProfilePic(rs.getString(8));
				u.setMessageTable(rs.getString(9));
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return u;
	}
	public ArrayList<User> getFriendList(){
		boolean flag =false;
		friendList =new ArrayList<User>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			String q1="select user_id,u.user_name,u.profile_pic ";
			String q2 = "from "+friendListTable+" as m inner join users as u on m.f_id=u.user_id;";
			PreparedStatement ps=con.prepareStatement(q1+q2);
			ResultSet rs=ps.executeQuery();
			
			int id;
			String un,proPic;
			while(rs.next()){
				id = rs.getInt(1);
				un = rs.getString(2);				
				proPic = rs.getString(3);
				User u=new User(id,un,proPic);
				friendList.add(u);
			}
			con.close();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return friendList;
	}
	public boolean register(){
		boolean flag=false;
		StrongPasswordEncryptor spe =new StrongPasswordEncryptor();
		String encPass= spe.encryptPassword(password);

		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			String q="insert into Users(user_name,email,password,gender,mobile,activation_code,status) values(?,?,?,?,?,?,2);";
			PreparedStatement ps = con.prepareStatement(q);
			ps.setString(1,userName);
			ps.setString(2,email);
			ps.setString(3,encPass);
			ps.setString(4,gender);
			ps.setString(5,mobile);
			ps.setString(6,activationCode);

			int rs = ps.executeUpdate();
			
			if(rs==1){
				
					
				String q2="select user_id from users where email=?;";
				ps=con.prepareStatement(q2);
				ps.setString(1,email);
				ResultSet res=ps.executeQuery();
				if(res.next()){
					userId = res.getInt(1);
				}
				//-------creating friend list table-----

				friendListTable=userName.trim()+userId;
				String q3= "create table "+friendListTable+"(f_id int primary key not null,unique(f_id));";
				
				ps=con.prepareStatement(q3);
				
				rs = ps.executeUpdate();
				
				if(rs==0){
					
						
					//-----creating Message table-----------

					messageTable=userName.trim()+userId+userName.trim();
					String q5 = "create table "+messageTable+"(msg_id int not null primary key auto_increment,msg varchar(200) not null,";
					String q6 ="sender varchar(30) not null,reciever varchar(30) not null,time datetime not null,";
					String q7 = "status varchar(10) not null default 'unread',fstatus varchar(10) not null default 'unread');";
					
					ps = con.prepareStatement(q5+q6+q7);
					
					rs = ps.executeUpdate();
					
					if(rs==0){
						
						
						//----inserting message Table & friendListTable into users--

						String q8="update users set f_list_tbl=?,msg_tbl=? where user_id=?;";
						ps=con.prepareStatement(q8);
						ps.setString(1,friendListTable);
						ps.setString(2,messageTable);
						ps.setInt(3,userId);
						rs= ps.executeUpdate();
						
						if(rs==1){
							
							flag=true;
							
						}
					}
				}

			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return flag;
	}
	public boolean login(){
		boolean flag=false;

		StrongPasswordEncryptor spe =new StrongPasswordEncryptor();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart?user=root&password=mysql");
			String q1 = "select user_id,user_name,email,password,gender,profession,lives_in,dob,lang,";
			String q2 = "profile_pic,f_list_tbl,msg_tbl,status from users where email=?;";
			PreparedStatement ps = con.prepareStatement(q1+q2);
			ps.setString(1,email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userId = rs.getInt(1);
				userName = rs.getString(2);
				email = rs.getString(3);
				boolean p = spe.checkPassword(password,rs.getString(4));
				
				if(p){
					gender = rs.getString(5);
					profession = rs.getString(6);
					livesIn = rs.getString(7);
					dob =  rs.getString(8);
					lang =  rs.getString(9);
					profilePic = rs.getString(10);
					friendListTable=rs.getString(11);
					messageTable = rs.getString(12);
					status = rs.getInt(13);
					if(status==2){
						flag=true;
					}
				}
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return flag;
	}
	
	
	//```````````````````````
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId=userId;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName=userName;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getProfilePic(){
		return profilePic;
	}
	public void setProfilePic(String profilePic){
		this.profilePic=profilePic;
	}
	public String getFriendListTable(){
		return friendListTable;
	}
	public void setFriendListTable(String friendListTable){
		this.friendListTable=friendListTable;
	}
	
	public String getMessageTable(){
		return messageTable;
	}
	public void setMessageTable(String messageTable){
		this.messageTable=messageTable;
	}
	public String getProfession(){
		return profession;
	}
	public void setProfession(String profession){
		this.profession=profession;
	}
	public String getLivesIn(){
		return livesIn;
	}
	public void setLivesIn(String livesIn){
		this.livesIn=livesIn;
	}
	public String getDob(){
		return dob;
	}
	public void setDob(String dob){
		this.dob=dob;
	}
	public String getLang(){
		return lang;
	}
	public void setLang(String lang){
		this.lang=lang;
	}
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile=mobile;
	}

}