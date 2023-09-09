package spring.server.commercial.controller.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.user.UpdateUserDTO;
import spring.server.commercial.dto.user.UserInfomationDTO;
import spring.server.commercial.mapper.user.UserMapper;
import spring.server.commercial.model.user.User;
import spring.server.commercial.response.Response;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", methods = { RequestMethod.DELETE,
		RequestMethod.GET, RequestMethod.POST }, allowedHeaders = "*")
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<?> getUser() {
		return ResponseEntity.ok(userService.getInfomation());
	}

	@PostMapping
	public ResponseEntity<?> update(@RequestBody UpdateUserDTO updateUserDTO) {
		byte[] bytes = Base64.getDecoder().decode(updateUserDTO.getAvatar());

		try {
			userService.update(updateUserDTO, bytes);
			return ResponseEntity.ok().body(new Response("Success"));
		} catch (SQLException | IOException e) {
			return ResponseEntity.badRequest().body(new Response("Failure"));
		}
	}

	
}
