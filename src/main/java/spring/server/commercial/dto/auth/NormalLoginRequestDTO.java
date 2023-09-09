package spring.server.commercial.dto.auth;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NormalLoginRequestDTO extends LoginRequestDTO {
	private String username;
	private String password;

}
