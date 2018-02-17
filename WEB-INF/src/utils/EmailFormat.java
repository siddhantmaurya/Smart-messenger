package utils;

public class EmailFormat{
	public static String getActivationCodeEmail(String userName,String email, String activationCode){
		return "Welcome "+userName+"<br /><br /><a href='http://localhost:8080/chatnew/activation.do?act_code="+activationCode+"&email="+email+"'>Activate Your Account By Clicking Here</a>";
	}
}