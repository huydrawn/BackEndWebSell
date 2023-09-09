package spring.server.commercial.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oauth2LoginRequestDTO extends LoginRequestDTO {
	private String provideRegisteration;
	private String accessToken;
	private long expriedAt;
}
