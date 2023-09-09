package spring.server.commercial.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.account.Account;
import spring.server.commercial.model.user.User;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByEmail(String email);
	
	public boolean existsByEmail(String email);

	public Optional<User> findByAccount(Account account);
}
