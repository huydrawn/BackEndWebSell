package spring.server.commercial.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.jwt.JwtUtils;
import spring.server.commercial.dto.auth.LoginRequestDTO;
import spring.server.commercial.exception.login.LoginException;
import spring.server.commercial.exception.permission.PermissionException;
import spring.server.commercial.model.user.User;
import spring.server.commercial.service.permission.PermissionService;
import spring.server.commercial.service.user.UserService;

@Service
public abstract class AuthenticationResolver {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private UserService userService;

	public abstract User valid(LoginRequestDTO request) throws LoginException;

	public String resolve(LoginRequestDTO request) throws LoginException {

		User user = this.valid(request);
		// set owner for this user for the first login

		
		return jwtUtils.gennerateJwtToken(user);

	}
}