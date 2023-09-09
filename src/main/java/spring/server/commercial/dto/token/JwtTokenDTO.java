package spring.server.commercial.dto.token;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class JwtTokenDTO {
	private String token;
	private Date expried;
}
