package com.ibm.Util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class EmailFileNetReconCE {
	private static ReadProperties props = new ReadProperties();
	void EmailNotification(String currentDate, String str, String str1) throws IOException, MessagingException {
		String Email_Subject = props.readPropertiesFile().getProperty("EMAIL_SUBJECT");
		String sub = Email_Subject+" ("+currentDate+")";
		String MailBody = "Hi All,\r\n"
				+ "\r\n"
				+ "Please find the reports for FileNetReconCE ("+currentDate+") on Central Claims, West Claims, East claims & FEP databases.\r\n"
				+ "\r\n"
				+ "Thanks & Regards,\r\n"
				//+ "Sakya Samanta\r\n"
				+ "FileNet LightsOn Support Team \r\n"
				+ "For FileNet Production Issues --> DL-FileNetLightsOnSupport@anthem.com\r\n"
				+ "For Non Production Issue Reporting issues --> dl-ecm_nonprodsupport@anthem.com\r\n";

		String Email_TO = props.readPropertiesFile().getProperty("EMAIL_TO");
		String Email_CC = props.readPropertiesFile().getProperty("EMAIL_CC");
		String Email_FROM = props.readPropertiesFile().getProperty("EMAIL_FROM");
		//System.out.println(Email_FROM);
		/////String to ="dl-ECM-SmartCaptureSupport@anthem.com";
		/////String cc ="DL-FileNetLightsOnSupport@anthem.com";
		//String to = "sakya.samanta@anthem.com";
		//String cc = "sakya.samanta@anthem.com";
		//String to = "nagendra.nemallapudi2@anthem.com,Suryaprakash.Pavalam@legatohealth.com,vipil.govil@anthem.com,prabhath.nair@anthem.com,pallavi.atul@anthem.com"; - not required
		//String cc = "DL-IBM_LO_ECM1@anthem.com";
		//String cc = "DL-FileNetLightsOnSupport@anthem.com,parthiban.eswaran@anthem.com"; - Not Required
		//String to = "Suryaprakash.Pavalam@legatohealth.com,DL-FileNetLightsOnSupport@anthem.com,Ramasamy.Madhubharath@legato.com,Kedarnath.Biradar@legato.com,PrabhkaranSingh.Bindra@legato.com,Srikanth.Karingana@legato.com";
		//String cc = "parthiban.eswaran@anthem.com,Anand.Govindarajan@legatohealth.com,nagendra.nemallapudi2@anthem.com,prabhath.nair@anthem.com,Ramasamy.Madhubharath@legato.com,vipil.govil@anthem.com,pallavi.atul@anthem.com";
		///////String from = "DL-FileNetLightsOnSupport@anthem.com";
		String host = "smtp.wellpoint.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(Email_FROM));
		if(!Email_TO.isEmpty()) {
		String[] recipientList = Email_TO.split(",");
		int counter =0;
		InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
		for(String recipient : recipientList) {
			recipientAddress[counter]=new InternetAddress(recipient.trim());
			counter++;
		}
		message.setRecipients(Message.RecipientType.TO, recipientAddress);
		}
		if(!Email_CC.isEmpty()) {
		String[] recipientList1 = Email_CC.split(",");
		int counter1 =0;
		InternetAddress[] recipientAddress1 = new InternetAddress[recipientList1.length];
		for(String recipient1 : recipientList1) {
			recipientAddress1[counter1]=new InternetAddress(recipient1.trim());
			counter1++;
		}
		message.setRecipients(Message.RecipientType.CC, recipientAddress1);
		}
		
		
			message.setSubject(sub);

		
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setText(MailBody);
			//System.out.println("inside1");
			//String Const_Path="\\\\dc04dwvfns306\\Shared\\ECM_Data\\FileNet_Data\\";
			//String Const_Path = props.readPropertiesFile().getProperty("Shared_Path");
			String Const_Path = props.readPropertiesFile().getProperty("Shared_Path");
			//windows//String Const_Path = Paths.get(props.readPropertiesFile().getProperty("Shared_Path")).toString();
			//System.out.println(Const_Path);
			MimeBodyPart attachmentPart1 = new MimeBodyPart();
			//attachmentPart1.attachFile(new File("C:\\Automation_L1\\Automated_FileNetReconCE\\WestClaims_"+str+".xlsx"));
			attachmentPart1.attachFile(new File(Const_Path+"/WestClaims_"+str+".xlsx"));

			MimeBodyPart attachmentPart2 = new MimeBodyPart();
			//attachmentPart2.attachFile(new File("C:\\Automation_L1\\Automated_FileNetReconCE\\CentralClaims_"+str+".xlsx"));
			attachmentPart2.attachFile(new File(Const_Path+"/CentralClaims1_"+str+".xlsx"));

			MimeBodyPart attachmentPart3 = new MimeBodyPart();
			//attachmentPart3.attachFile(new File("C:\\Automation_L1\\Automated_FileNetReconCE\\EastClaims_"+str+".xlsx"));
			attachmentPart3.attachFile(new File(Const_Path+"/EastClaims_"+str+".xlsx"));

			MimeBodyPart attachmentPart4 = new MimeBodyPart();
			//attachmentPart4.attachFile(new File("C:\\Automation_L1\\Automated_FileNetReconCE\\CentralClaims_"+str1+".xlsx"));
			attachmentPart4.attachFile(new File(Const_Path+"/CentralClaims2_"+str1+".xlsx"));

			MimeBodyPart attachmentPart5 = new MimeBodyPart();
			//attachmentPart5.attachFile(new File("C:\\Automation_L1\\Automated_FileNetReconCE\\FEP_"+str+".xlsx"));
			attachmentPart5.attachFile(new File(Const_Path+"/FEP_"+str+".xlsx"));

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentPart1);
			multipart.addBodyPart(attachmentPart2);
			multipart.addBodyPart(attachmentPart3);
			multipart.addBodyPart(attachmentPart4);
			multipart.addBodyPart(attachmentPart5);
			message.setContent(multipart);
			////////////////////////////////////////////
			Transport.send(message);
			System.out.println("Sent message successfully....");
	}
}
