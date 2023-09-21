package spring.server.commercial.service.product;

import java.util.List;
import java.util.Optional;

import spring.server.commercial.dto.product.type.IdAndNameProductType;
import spring.server.commercial.dto.product.type.ProductTypeResponseDTO;
import spring.server.commercial.model.product.ProductType;

public interface ProductTypeService {
	public void save(ProductType productType);
	public List<ProductTypeResponseDTO> getAll();
	public boolean existsByName(String name);
	public Optional<ProductType> findByName(String name);
	public List<IdAndNameProductType> findAllNameAndId();
	public ProductType findById(Integer valueOf);
}
