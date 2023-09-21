package spring.server.commercial.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.user.Customer;

@Component
public interface CustommerRepository extends JpaRepository<Customer, Integer> {

}
