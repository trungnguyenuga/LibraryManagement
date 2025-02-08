package com.aehanoidz123.librarymanagement.Services;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class SendingEmailService {
    public static void sendEmail(String studentEmail, String studentId) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String username = "aehndz65@gmail.com";
        String password = "fwmxoekatdlibqkw";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO, new InternetAddress[]{
                            new InternetAddress(studentEmail)
                    });
            message.setSubject("Library QR code");
            StringBuilder msg = new StringBuilder("Hello from AeHnDz's Library.\n")
                    .append("This is your QR code. Save it for later use");
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(msg.toString());
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            String fileName = "qrcodes" + File.separator + "qr_code_of_" + studentId + ".png";
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }
    }
}
