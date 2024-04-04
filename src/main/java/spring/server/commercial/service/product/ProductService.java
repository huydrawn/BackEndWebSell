package spring.server.commercial.service.product;

import java.util.List;
import java.util.Optional;

import spring.server.commercial.dto.product.ProductInfomationDTO;
import spring.server.commercial.dto.product.ProductInformationDetailsRequestDTO;
import spring.server.commercial.model.product.Product;

public interface ProductService {
	public void save(Product product);

	public void update(Product product);

	public List<ProductInfomationDTO> getInformations();

	public void deleteAll();

	public void delete(int id);

	public Optional<Product> getByID(int id);

	public List<ProductInformationDetailsRequestDTO> getByConditition(String type, int page, int offset,
			String orderKey, String orderType);
}
