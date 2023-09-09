package spring.server.commercial.service.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.model.account.NormalAccout;
import spring.server.commercial.repository.account.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Optional<NormalAccout> findByUsername(String username) {

		return accountRepository.findByUsername(username);
	}
}
