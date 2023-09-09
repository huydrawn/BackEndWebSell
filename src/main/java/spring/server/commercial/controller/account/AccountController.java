package spring.server.commercial.controller.account;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.model.user.User;
import spring.server.commercial.response.Response;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", methods = { RequestMethod.DELETE,
		RequestMethod.GET, RequestMethod.POST }, allowedHeaders = "*")
public class AccountController {

	private final UserService userService;

	@GetMapping
	public ResponseEntity<?> checkStatus(@PathParam("status") String status, Authentication authentication) {
		User user = userService.findByEmail(authentication.getPrincipal().toString()).get();
		if (user.getAccount().getStatus().toString().equals(status)) {
			return ResponseEntity.ok(new Response("true"));
		}
		return ResponseEntity.ok(new Response("false"));
	}

	@GetMapping("/status")
	public ResponseEntity<?> getStatus(Authentication authentication) {
		User user = userService.findByEmail(authentication.getPrincipal().toString()).get();

		return ResponseEntity.ok(new Response(user.getAccount().getStatus().toString()));
	}

}
