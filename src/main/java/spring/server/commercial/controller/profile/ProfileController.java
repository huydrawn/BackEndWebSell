package spring.server.commercial.controller.profile;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
	private final UserService userService;

	@GetMapping("/{id}")
	public ResponseEntity<?> getProfile(@PathVariable String id) {
		return null;
	}
}
