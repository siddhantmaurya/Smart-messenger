package utils;

import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;

public class EmailSender{
	public static void sendEmail(String toEmail,String message){
		Properties prop=System.getProperties();

		prop.setProperty("mail.smtp.host","smtp.gmail.com");
		prop.setProperty("mail.smtp.port","587");
		prop.setProperty("mail.smtp.auth","true");
		prop.setProperty("mail.smtp.starttls.enable","true");
		prop.setProperty("mail.transport.protocol","smtp");

		Session session=Session.getInstance(prop,new MailAuthenticator());

		MimeMessage mm=new MimeMessage(session);
		try{
			mm.setFrom(new InternetAddress("vidyut232345@gmail.com"));
			mm.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
			mm.setSubject("Email varification Link...");
			mm.setText(message);

			Transport.send(mm);
		}catch (MessagingException e){
			e.printStackTrace();
		}
	}
}

class MailAuthenticator extends Authenticator{
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication("vidyut232345@gmail.com","Krishna#14$");
	}
}