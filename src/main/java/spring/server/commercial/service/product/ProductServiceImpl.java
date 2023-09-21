package spring.server.commercial.service.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.product.ProductInfomationDTO;
import spring.server.commercial.dto.product.ProductInformationDetailsRequestDTO;
import spring.server.commercial.mapper.product.ProductMapper;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.product.ProductType;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.service.permission.PermissionService;
import spring.server.commercial.service.user.UserService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final UserService userService;
	private final PermissionService permissionService;
	private final ProductTypeService productTypeService;

	@Override
	public void save(Product product) {
		productRepository.save(product);
		permissionService.setOwner(new ObjectIdentityImpl(product));
		
	}

	@Override
	public List<ProductInfomationDTO> getInformations() {
		List<ProductInfomationDTO> list = getCustommer().getSellProducts().stream()
				.map(ProductMapper::entityToProductInformationDTO).toList();
		return list;
	}

	@Override
	public void deleteAll() {

	}

	private Customer getCustommer() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return userService.findCustommerByEmail(authentication.getPrincipal().toString());
	}

	@Override
	public void delete(int id) {
		Product p = productRepository.findById(id).get();
		Customer user = p.getSeller();
		Iterator<Product> iteratorProducts = user.getSellProducts().iterator();
		while (iteratorProducts.hasNext()) {
			Product remove = iteratorProducts.next();
			if (remove.getId() == id) {
				iteratorProducts.remove();
				break;
			}
		}
		ProductType productType = p.getProductType();
		Iterator<Product> iteratorProductsType = productType.getProducts().iterator();
		while (iteratorProductsType.hasNext()) {
			Product remove = iteratorProductsType.next();
			if (remove.getId() == id) {
				iteratorProductsType.remove();
				break;
			}
		}
		userService.save(user);
		productTypeService.save(productType);
		permissionService.deleteObject(new ObjectIdentityImpl(p));
		productRepository.delete(p);

	}

	@Override
	public void update(Product product) {
		productRepository.save(product);
	}

	@Override
	public List<ProductInformationDetailsRequestDTO> getByConditition(String type, int page, int offset,
			String orderKey, String orderType) {
		Page<Product> pages = null;
		Pageable pageable = null;
		if (StringUtils.hasText(orderKey))
			pageable = PageRequest.of(page, offset, Sort.by(Direction.valueOf(orderType), orderKey));
		else
			pageable = PageRequest.of(page, offset);
		if (!StringUtils.hasText(type)) {
			pages = productRepository.findAll(pageable);
		} else {
			pages = productRepository.findByProductType_Name(type, pageable);
		}
		List<ProductInformationDetailsRequestDTO> result = new ArrayList<>();
		pages.getContent().forEach(n -> {
			result.add(ProductMapper.entityToProductDetailInforamtionDTO(n));
		});
		return result;
	}
}
