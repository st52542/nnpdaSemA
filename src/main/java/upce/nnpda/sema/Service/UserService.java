package upce.nnpda.sema.Service;

import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
@Service
public class UserService implements UserServiceInt{

    String SERVERMAIL = "";
    String PASS = "";
    public void sendMail(String mail, String uuid) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SERVERMAIL, PASS);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(SERVERMAIL, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject("Reset Password");
        msg.setContent("Token", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Token is: " + uuid, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
