package spring.server.commercial.service.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import spring.server.commercial.dto.user.UpdateUserDTO;
import spring.server.commercial.dto.user.UserInfomationDTO;
import spring.server.commercial.model.account.Account;
import spring.server.commercial.model.user.User;

public interface UserService {
	public Optional<User> findByEmail(String email);

	public Optional<User> findByAccount(Account account);

	public boolean existsByEmail(String email);

	public void save(User user);
	
	public Optional<User> findById(int id);
	
	public UserInfomationDTO getInfomation() ;

	public void update(UpdateUserDTO updateUserDTO , byte[] avatar) throws SerialException, SQLException, IOException;
}
