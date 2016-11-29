package app;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailHelper {

    private static String USER_NAME = "printerqueuejn";
    private static String PASSWORD = "hahaNope";
    private static String RECIPIENT = "slackerjn@gmail.com";

    public static void sendFromGMail(String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USER_NAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        javax.mail.Session session = javax.mail.Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(USER_NAME));
            InternetAddress toAddress;

            toAddress = new InternetAddress(to);
            

            message.addRecipient(Message.RecipientType.TO, toAddress);
            

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, USER_NAME, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}