package spring.server.commercial.service.account;

import java.util.Optional;

import spring.server.commercial.model.account.NormalAccout;


public interface AccountService {

	Optional<NormalAccout> findByUsername(String username);

}
