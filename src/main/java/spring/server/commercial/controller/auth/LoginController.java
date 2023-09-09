package spring.server.commercial.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.jwt.JwtUtils;
import spring.server.commercial.dto.auth.NormalLoginRequestDTO;
import spring.server.commercial.dto.auth.Oauth2LoginRequestDTO;
import spring.server.commercial.dto.token.JwtTokenDTO;
import spring.server.commercial.exception.login.LoginException;
import spring.server.commercial.service.auth.AuthenticationResolver;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class LoginController {
	private final JwtUtils jwtUtils;
	@Autowired
	@Qualifier("normal")
	private AuthenticationResolver authenticationResolver;

	@Autowired
	@Qualifier("oauth2")
	private AuthenticationResolver oauth2AuthenticationResolver;

	@GetMapping()
	public ResponseEntity<CsrfToken> csrfToken(HttpServletRequest request, HttpServletResponse response) {
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//		System.out.println(csrfToken.getToken() + " " + csrfToken.getHeaderName());
		return ResponseEntity.ok(csrfToken);
	}

	@PostMapping
	public ResponseEntity<?> normal(@RequestBody NormalLoginRequestDTO request) {
		try {
			String jwt = authenticationResolver.resolve(request);
			return ResponseEntity.ok(new JwtTokenDTO(jwt , jwtUtils.getExpriedTime(jwt)));
		} catch (LoginException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}

	@PostMapping("/oauth2")
	public ResponseEntity<?> oauth2(@RequestBody Oauth2LoginRequestDTO request) {
		try {
			String jwt = oauth2AuthenticationResolver.resolve(request);
			return ResponseEntity.ok(new JwtTokenDTO(jwt , jwtUtils.getExpriedTime(jwt)));
		} catch (LoginException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
