package spring.server.commercial.service.auth;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.auth.LoginRequestDTO;
import spring.server.commercial.dto.auth.Oauth2LoginRequestDTO;
import spring.server.commercial.exception.login.LoginException;
import spring.server.commercial.exception.login.Oauth2LoginException;
import spring.server.commercial.model.account.Account;
import spring.server.commercial.model.account.Oauth2Account;
import spring.server.commercial.model.account.StatusAccount;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.model.user.User;
import spring.server.commercial.service.user.UserService;

@Service(value = "oauth2")
@RequiredArgsConstructor
public class OauthAuthenticationResolve extends AuthenticationResolver {

	private final UserService userService;
	private final Oauth2UserResolver oauth2UserResolver;
	private final ClientRegistrationRepository clientRegistrationRepository;

	@Override
	public User valid(LoginRequestDTO request) throws LoginException {

		if (!(request instanceof Oauth2LoginRequestDTO requestDTO)) {
			throw new Oauth2LoginException("Request is not instance of \"" + Oauth2LoginRequestDTO.class + "\" ");
		}

		// Get Infomation about oauth2 user from accesstoken
		OAuth2User oAuth2User = this.processAccessToken(requestDTO);
		// check if have this email used yet?
		// if true check whetther Is it's account instance of Oauth2Account:
		// if true update this account
		Optional<User> userCheck = userService.findByEmail(oAuth2User.getAttribute("email"));
		// Create new object for handle
		User user = null;
		if (userCheck.isPresent()) {
			Account account = userCheck.get().getAccount();
			if (account instanceof Oauth2Account) {
				// Update in database
				userService.save(userCheck.get());
				// assgin to the user
				user = userCheck.get();
			} else {
				throw new Oauth2LoginException(
						"This email have used for Other type of Account Can't use as Oauth2Acount");
			}
		}
		// if false we have save this account to database
		else {
			Account account = Oauth2Account.builder().provideId(requestDTO.getProvideRegisteration())
					.status(StatusAccount.Authorized).build();
			user = Customer.builder().email(oAuth2User.getAttribute("email")).account(account).build();

			userService.save(user);
		}

		return user;

	}

	private OAuth2User processAccessToken(Oauth2LoginRequestDTO requestDTO) {
		OAuth2AccessToken accessToken = new OAuth2AccessToken(TokenType.BEARER, requestDTO.getAccessToken(),
				Instant.ofEpochMilli(new Date().getTime()), Instant.ofEpochMilli(requestDTO.getExpriedAt()));

		ClientRegistration clientRegistration = clientRegistrationRepository
				.findByRegistrationId(requestDTO.getProvideRegisteration());

		OAuth2UserRequest request = new OAuth2UserRequest(clientRegistration, accessToken);
		OAuth2User oAuth2User = null;

		oAuth2User = oauth2UserResolver.loadUser(request);

		return oAuth2User;
	}

}
