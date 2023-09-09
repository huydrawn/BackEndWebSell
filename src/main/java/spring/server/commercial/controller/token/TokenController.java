package spring.server.commercial.controller.token;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.jwt.JwtUtils;
import spring.server.commercial.dto.token.JwtTokenDTO;
import spring.server.commercial.exception.jwt.JwtTokenException;
import spring.server.commercial.model.user.User;
import spring.server.commercial.response.Response;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class TokenController {
	private final JwtUtils jwt;
	private final UserService userService;

	@GetMapping("/valid")
	public ResponseEntity<?> validToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		String token = null;
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			token = bearerToken.substring(7);
			try {
				return ResponseEntity.badRequest().body(new Response(jwt.valiadJwtToken(token) + ""));
			} catch (JwtTokenException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.badRequest().body("Empty Token");
		}
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refresh(Authentication authentication) {
		User user = userService.findByEmail(authentication.getPrincipal().toString()).get();
		String token = jwt.gennerateJwtToken(user);
		return ResponseEntity.ok(new JwtTokenDTO(token, jwt.getExpriedTime(token)));
	}
}
