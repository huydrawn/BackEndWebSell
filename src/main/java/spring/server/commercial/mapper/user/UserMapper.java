package spring.server.commercial.mapper.user;

import spring.server.commercial.config.convert.file.ConvertFile;
import spring.server.commercial.dto.user.UserInfomationDTO;
import spring.server.commercial.model.user.User;

public class UserMapper {
	public static UserInfomationDTO userToUserInfomationDTO(User user) {
		return new UserInfomationDTO(user.getAccount().getUsername(), user.getName(), user.getBirth_day(),
				user.getEmail(), user.getGender(), user.getNumberPhone(),
				ConvertFile.extractBytesFromBlob(user.getImages()));
	}
}
