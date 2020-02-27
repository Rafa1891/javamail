import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {

	public static void main(String[] args) {
		String fecha = new Date().toString();
		String email = "jplb.fp@gmail.com";
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte [] hash = Arrays.copyOf(md.digest((fecha + email).getBytes()), 33);
			String base64 = Base64.getUrlEncoder().encodeToString(hash);
			StringBuilder cuerpo = new StringBuilder("<h1>Confirma tu eMail</h1>");
			cuerpo.append("<p>Para tener acceso a tu nueva cuenta de usuario necesitas confirmar tu eMail en el enlace siguiente:</p>");
			cuerpo.append(String.format("<p><a href=\"http://localhost/ge/activar?ca=%s\">Confirmar</a>", base64));
			enviarCorreo(email, "Confirmación de eMail", cuerpo.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void enviarCorreo(String to, String asunto, String cuerpo) {
		
		Properties properties = new Properties();

		// Nombre del host de correo (smtp.gmail.com para enviar a través de gmail)
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		properties.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		properties.setProperty("mail.smtp.port", "587");

		// Si requiere o no usuario y password para conectarse.
		properties.setProperty("mail.smtp.auth", "true");
		
		Session mailSession = Session.getInstance(properties);

		// Para obtener un log de salida más extenso
		mailSession.setDebug(true);
		
		try {
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress("alumnofpdaw@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(asunto);
			message.setContent(cuerpo, "text/html");
			message.setSentDate(new Date());
			
			Transport.send(message, "alumnofpdaw@gmail.com","practicas2020");		
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
