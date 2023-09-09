package spring.server.commercial.config.clientRegisteration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigClientRegisteration {
	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
	}

	private ClientRegistration googleClientRegistration() {
		return CommonOAuth2Provider.GOOGLE.getBuilder("google")
				.clientId("790048615602-63eqj1ec5eilufn3negb75ctl1mo5an0.apps.googleusercontent.com")
				.clientSecret("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").build();
	}
}
