package spring.server.commercial.dto.user;

import java.util.Date;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import spring.server.commercial.model.Address;
import spring.server.commercial.model.Gender;

@Data
@AllArgsConstructor
public class UserInfomationDTO {
	private String userName;
	private String name;
	private Date birth_day;
	private String email;
	private Gender gender;
	private String numberPhone;
	private byte[] avatar;
}
