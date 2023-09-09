package spring.server.commercial.service.vertificate_email;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.exception.vertificate_email.EmailExistedException;
import spring.server.commercial.model.VerificationEmail;
import spring.server.commercial.model.account.StatusAccount;
import spring.server.commercial.model.user.User;
import spring.server.commercial.repository.user.UserRepository;
import spring.server.commercial.repository.vertificate_email.VertificateEmailRepository;

@Service
@RequiredArgsConstructor
@EnableAsync
public class VertificateEmailService {
	private final VertificateEmailRepository vertificateEmailRepository;
	private final TemplateEngine templateEngine;
	private final JavaMailSender javaMailSender;
	private final UserRepository userRepository;
	@Value("${server.port}")
	private String port;
	@Value("${server.host}")
	private String host;
	@Value("${spring.mail.username}")
	private String from;

	public void checkMailVertificationExists(String email) throws EmailExistedException {
		if (vertificateEmailRepository.existsByEmail(email)) {
			throw new EmailExistedException(" This email have used");
		}
	}

	@Async
	public void sendMailVertification(String email) throws EmailExistedException {
		this.send(email);
	}

	private String generateVertificationToken() {
		return UUID.randomUUID().toString();
	}

	public void vertify(String token) throws EmailExistedException {
		
		// take user from database by email
		Optional<VerificationEmail> emailVertification = vertificateEmailRepository.findByToken(token);

		User user = userRepository.findByEmail(emailVertification.get().getEmail()).get();

		user.getAccount().setStatus(StatusAccount.Authorized);

		userRepository.save(user);
	}

	private void send(String email) {
		// create a new email vertification for save in database
		VerificationEmail emailVertification = VerificationEmail.builder().email(email)
				.token(generateVertificationToken()).build();

		// Create Email
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper;

		try {
			mimeMessageHelper = new MimeMessageHelper(message, true);
			mimeMessageHelper.setPriority(1);
			mimeMessageHelper.setSubject("Email Vertification");
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(email);

			Context context = new Context();
			context.setVariable("name", email);
			context.setVariable("url", getURLVertification(emailVertification.getToken()));
			String text = templateEngine.process("emailVertification", context);
			MimeMultipart mimeMultipart = new MimeMultipart();

			BodyPart content = new MimeBodyPart();
			content.setContent(text, "text/html");

			mimeMultipart.addBodyPart(content);

			message.setContent(mimeMultipart);
			javaMailSender.send(message);

			// save the emailVertificationRepository if it have send

			vertificateEmailRepository.save(emailVertification);

		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private String getURLVertification(String token) {

		return "http://" + host + ":" + port + "/verification/" + token;
	}
}
