package spring.server.commercial.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import spring.server.commercial.model.product.GroupProduct;

@Component
public interface GroupProductRepository extends JpaRepository<GroupProduct, Integer> {

}
