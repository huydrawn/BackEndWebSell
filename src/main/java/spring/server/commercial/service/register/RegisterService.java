package spring.server.commercial.service.register;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.auth.RegisterRequestDTO;
import spring.server.commercial.exception.registeration.IllegalInputCredentialsException;
import spring.server.commercial.exception.registeration.RegisterationException;
import spring.server.commercial.exception.vertificate_email.EmailExistedException;
import spring.server.commercial.model.account.NormalAccout;
import spring.server.commercial.model.account.StatusAccount;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.model.user.User;
import spring.server.commercial.service.account.AccountService;
import spring.server.commercial.service.user.UserService;
import spring.server.commercial.service.vertificate_email.VertificateEmailService;

@Service
@RequiredArgsConstructor
public class RegisterService {
	private final UserService userService;
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;
	private final VertificateEmailService vertificateEmailService;

	public void resolve(RegisterRequestDTO request) throws EmailExistedException, RegisterationException {
		// check whether Email exsist
		if (userService.existsByEmail(request.getEmail())) {
			throw new EmailExistedException("Email has existed");
		}
		
		// check whether Username exsist
		if (accountService.findByUsername(request.getUsername()).isPresent()) {
			throw new IllegalInputCredentialsException("Username has existed");
		}
		// create account to save
		NormalAccout account = NormalAccout.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).status(StatusAccount.Unauthozied).build();
		// create user to save
		// above account is injected
		User user = Customer.builder().build();
		user.setAccount(account);
		user.setEmail(request.getEmail());

		// check if we have had a EmailVertifycation of this email
		vertificateEmailService.checkMailVertificationExists(request.getEmail());
		System.out.println("Ok");
		// send Email Vertification for User
		vertificateEmailService.sendMailVertification(request.getEmail());
		// Save user
		

		userService.save(user);
	}
}
