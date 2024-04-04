package spring.server.commercial.security.provider_auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.security.JwtTokenAuthentication;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private final JwtUserDetailsService jwtUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String email = authentication.getPrincipal().toString();
		UserDetails user = jwtUserDetailsService.loadUserByUsername(email);
		return new JwtTokenAuthentication(email, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(JwtTokenAuthentication.class);
	}

}
