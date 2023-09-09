package spring.server.commercial.controller.regis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.auth.RegisterRequestDTO;
import spring.server.commercial.dto.register.RegisterResponseDTO;
import spring.server.commercial.exception.registeration.RegisterationException;
import spring.server.commercial.exception.vertificate_email.EmailExistedException;
import spring.server.commercial.service.register.RegisterService;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
	private final RegisterService registerService;
	@PostMapping
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
		
		try {
			registerService.resolve(request);

		} catch (EmailExistedException | RegisterationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(new RegisterResponseDTO("Success Register"));
	}

}
