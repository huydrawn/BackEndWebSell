package spring.server.commercial.service.auth;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.auth.LoginRequestDTO;
import spring.server.commercial.dto.auth.NormalLoginRequestDTO;
import spring.server.commercial.exception.login.IllegalLoginRequestException;
import spring.server.commercial.exception.login.LoginException;
import spring.server.commercial.model.account.NormalAccout;
import spring.server.commercial.model.user.User;
import spring.server.commercial.service.account.AccountService;
import spring.server.commercial.service.user.UserService;

@Service(value = "normal")
@RequiredArgsConstructor
public class NormalAuthenticationResolve extends AuthenticationResolver {

	private final AccountService accountService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public User valid(LoginRequestDTO request) throws LoginException {
		if (!(request instanceof NormalLoginRequestDTO requestDTO)) {
			throw new IllegalLoginRequestException(
					"Request is not instance of \"" + NormalLoginRequestDTO.class + "\" ");
		}

		if (!StringUtils.hasText(requestDTO.getUsername())) {
			throw new LoginException("Username is empty");
		}
		if (!StringUtils.hasText(requestDTO.getUsername())) {
			throw new LoginException("Password is empty");
		}

		Optional<NormalAccout> account = accountService.findByUsername(requestDTO.getUsername());

		if (account.isEmpty()) {
			throw new LoginException("UserName isn't exits");
		}
		if (!passwordEncoder.matches(requestDTO.getPassword(), account.get().getPassword())) {
			throw new LoginException("Password not match");
		}
		Optional<User> user = userService.findByAccount(account.get());

		return user.get();

	}

}
