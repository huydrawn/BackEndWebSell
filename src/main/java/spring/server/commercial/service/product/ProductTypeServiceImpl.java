package spring.server.commercial.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.product.type.ProductTypeResponseDTO;
import spring.server.commercial.mapper.product.type.ProductTypeMapper;
import spring.server.commercial.model.product.ProductType;
import spring.server.commercial.repository.product.ProductTypeRepository;

@RequiredArgsConstructor
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
	private final ProductTypeRepository productTypeRepository;

	@Override
	public void save(ProductType productType) {
		productTypeRepository.save(productType);
	}

	@Override
	public List<ProductTypeResponseDTO> responseProductsType() {
		List<ProductType> productTypes = productTypeRepository.findAll();

		return productTypes.stream().map(ProductTypeMapper::productTypeToProductTypeResponseDTO).toList();
	}

	@Override
	public boolean existsByName(String name) {
		// TODO Auto-generated method stub
		return productTypeRepository.existsByName(name);
	}

	@Override
	public Optional<ProductType> findByName(String name) {
		// TODO Auto-generated method stub
		return productTypeRepository.findByName(name);
	}

}
