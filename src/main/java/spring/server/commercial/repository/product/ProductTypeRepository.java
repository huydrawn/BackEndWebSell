package spring.server.commercial.repository.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.product.ProductType;

@Component
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

	public boolean existsByName(String name);

	public Optional<ProductType> findByName(String name);
	
}
