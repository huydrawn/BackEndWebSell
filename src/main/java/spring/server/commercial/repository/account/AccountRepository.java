package spring.server.commercial.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.account.Account;
import spring.server.commercial.model.account.NormalAccout;

@Component
public interface AccountRepository extends JpaRepository<Account, Integer> {

	Optional<NormalAccout> findByUsername(String username);

}
