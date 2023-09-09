package spring.server.commercial.repository.vertificate_email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.VerificationEmail;

@Component
public interface VertificateEmailRepository extends JpaRepository<VerificationEmail, Integer> {
	public boolean existsByEmail(String email);

	public Optional<VerificationEmail> findByToken(String token);
}
