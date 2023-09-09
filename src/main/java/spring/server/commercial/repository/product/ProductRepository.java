package spring.server.commercial.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.product.Product;

@Component
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
