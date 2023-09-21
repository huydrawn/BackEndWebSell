package spring.server.commercial.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import spring.server.commercial.model.role.Role;

@Data
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
	private String username;
	private String password;
	private String email;
}
