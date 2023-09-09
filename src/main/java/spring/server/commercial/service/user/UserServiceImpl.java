package spring.server.commercial.service.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.convert.file.ConvertFile;
import spring.server.commercial.dto.user.UpdateUserDTO;
import spring.server.commercial.dto.user.UserInfomationDTO;
import spring.server.commercial.mapper.user.UserMapper;
import spring.server.commercial.model.account.Account;
import spring.server.commercial.model.user.User;
import spring.server.commercial.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public Optional<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public Optional<User> findByAccount(Account account) {
		// TODO Auto-generated method stub
		return userRepository.findByAccount(account);
	}

	@Override
	public Optional<User> findById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public UserInfomationDTO getInfomation() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(authentication.getPrincipal().toString()).get();
		return UserMapper.userToUserInfomationDTO(user);
	}

	@Override
	public void update(UpdateUserDTO updateUserDTO , byte[] avatar) throws SerialException, SQLException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(authentication.getPrincipal().toString()).get();
		user.setNumberPhone(updateUserDTO.getNumberPhone());
		user.setName(updateUserDTO.getName());
		user.setBirth_day(updateUserDTO.getBirth_day());
		user.setGender(updateUserDTO.getGender());
		if(updateUserDTO.getAvatar() != null)
		user.setImages(ConvertFile.bytesToBlob(avatar));
		userRepository.save(user);

	}
}
