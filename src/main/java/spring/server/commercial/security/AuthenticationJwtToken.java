package spring.server.commercial.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationJwtToken extends AbstractAuthenticationToken {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;

	public AuthenticationJwtToken(String email, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.email = email;
		// TODO Auto-generated constructor stub
	}

	public static AuthenticationJwtToken authenticated(String email,
			Collection<? extends GrantedAuthority> authorities) {
		return new AuthenticationJwtToken(email, authorities);
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this.email;
	}

}
