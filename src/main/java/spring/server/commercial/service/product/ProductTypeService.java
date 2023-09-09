package spring.server.commercial.service.product;

import java.util.List;
import java.util.Optional;

import spring.server.commercial.dto.product.type.ProductTypeResponseDTO;
import spring.server.commercial.model.product.ProductType;

public interface ProductTypeService {
	public void save(ProductType productType);
	public List<ProductTypeResponseDTO> responseProductsType();
	public boolean existsByName(String name);
	public Optional<ProductType> findByName(String name);
}
