package models;

import java.util.Date;

public class Message{
	private int msgId;
	private String msg;
	private Date time;
	private String status;
	private String sender;
	private String reciever;
	//------------
	public Message(){}
	//------------
	public static String decode(String msg){
		String s1,s2;
		
		if(msg.contains(".mp3")){
			s1 = msg.replaceAll("@file/","<audio controls><source src='");
			s2 = s1.replaceAll("/file@","' type='audio/mpeg'</source></audio>");
		}else if(msg.contains(".mp4")){
			s1 = msg.replaceAll("@file/","<video width='320' height='240' controls><source src='");
			s2 = s1.replaceAll("/file@","' type='video/mp4'</source></video>");
		}else{
			s1 = msg.replaceAll("@file/","<img src='");
			s2 = s1.replaceAll("/file@","' height=250 width=250 />");
		}
		
		return s2;
	} 
	//--------------
	public void setMsgId(int msgId){
		this.msgId=msgId;
	}
	public int getMsgId(){
		return msgId;
	}
	public String getMsg(){
		return msg;
	}
	public void setMsg(String msg){
		this.msg=msg;
	}
	
	public void setTime(Date time){
		this.time=time;
	}
	public Date getTime(){
		return time;
	}
	
	public void setStatus(String status){
		this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setSender(String sender){
		this.sender=sender;
	}
	public String getSender(){
		return sender;
	}
	public void setReciever(String reciever){
		this.reciever=reciever;
	}
	public String getReciever(){
		return reciever;
	}
}