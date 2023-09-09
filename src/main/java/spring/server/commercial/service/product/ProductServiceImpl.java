package spring.server.commercial.service.product;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.repository.product.ProductRepository;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Override
	public void save(Product product) {
		productRepository.save(product);
	}
}
