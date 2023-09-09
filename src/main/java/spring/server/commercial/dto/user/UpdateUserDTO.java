package spring.server.commercial.dto.user;

import java.util.Date;

import lombok.Data;
import spring.server.commercial.model.Gender;
@Data
public class UpdateUserDTO {
	private String name;
	private Date birth_day;
	private Gender gender;
	private String numberPhone;
	private String avatar;
}
