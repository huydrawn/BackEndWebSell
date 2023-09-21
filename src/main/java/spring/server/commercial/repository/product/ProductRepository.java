package spring.server.commercial.repository.product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.product.Product;

@Component
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findByProductType_Name(String type, Pageable pageable);
	
	List<Product> findByProductType_Name(String type);
	
}
