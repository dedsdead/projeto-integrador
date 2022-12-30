package ifpr.pgua.eic.trabalhofinal.models.repositories;

import java.util.Properties;

import ifpr.pgua.eic.trabalhofinal.models.results.Result;
import ifpr.pgua.eic.trabalhofinal.utils.Env;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailsRepository {
    final String username = Env.get("MAIL_USER");
    final String password = Env.get("MAIL_PASSWORD");

    String from;
    String receiver;

    String host;
    String port;

    String put_auth;
    String put_ttls;
    String put_host;
    String put_port;

    public EmailsRepository(){
        from = "dedstads@gmail.com";
        receiver = "teste@email.com";
        host = "smtp.mailtrap.io";
        port = "2525";

    }

    public Result send(String subject, String content){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
            new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);

            return Result.success("Email enviado com sucesso!");
            
        }catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}