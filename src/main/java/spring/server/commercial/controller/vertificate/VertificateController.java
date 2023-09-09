package spring.server.commercial.controller.vertificate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.exception.vertificate_email.EmailExistedException;
import spring.server.commercial.service.vertificate_email.VertificateEmailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class VertificateController {
	private final VertificateEmailService vertificateEmailService;

	@GetMapping("/{token}")
	public ResponseEntity<?> verficate(@PathVariable("token") String token) {
		try {
			vertificateEmailService.vertify(token);
			return ResponseEntity.ok("Success verification");
		} catch (EmailExistedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@GetMapping("/send/{email}")
	public ResponseEntity<?> sendAgain(@PathVariable("email") String email) {
		try {
			vertificateEmailService.sendMailVertification(email);
			return ResponseEntity.ok("Send successfully verification email");
		} catch (EmailExistedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
