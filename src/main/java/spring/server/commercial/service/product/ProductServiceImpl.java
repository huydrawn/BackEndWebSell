package spring.server.commercial.service.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.IdentityUnavailableException;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.product.ProductInfomationDTO;
import spring.server.commercial.dto.product.ProductInformationDetailsRequestDTO;
import spring.server.commercial.exception.permission.PermissionException;
import spring.server.commercial.mapper.product.ProductMapper;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.product.ProductType;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.service.auth.AuthenticationService;
import spring.server.commercial.service.permission.PermissionService;
import spring.server.commercial.service.user.UserService;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final UserService userService;
	private final PermissionService permissionService;
	private final ProductTypeService productTypeService;
	private final ProductMapper productMapper;
	private final AuthenticationService authenticationService;

	@Override
	@Transactional
	public void save(Product product) {
		Product a = productRepository.save(product);
		permissionService.setOwner(new ObjectIdentityImpl(a));

	}

	@Override
	public List<ProductInfomationDTO> getInformations() {
		Customer cus = userService.findCustommerByEmail(authenticationService.getAuthentication().getPrincipal().toString());
		List<ProductInfomationDTO> list = cus.getSellProducts().stream()
				.map(ProductMapper::entityToProductInformationDTO).toList();
		return list;
	}

	@Override
	public void deleteAll() {

	}
	
	
	public void detete(Product p) {
		Customer user = p.getSeller();
		Iterator<Product> iteratorProducts = user.getSellProducts().iterator();
		while (iteratorProducts.hasNext()) {
			Product remove = iteratorProducts.next();
			if (remove.getId() == p.getId()) {
				iteratorProducts.remove();
				break;
			}
		}
		ProductType productType = p.getProductType();
		Iterator<Product> iteratorProductsType = productType.getProducts().iterator();
		while (iteratorProductsType.hasNext()) {
			Product remove = iteratorProductsType.next();
			if (remove.getId() == p.getId()) {
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
	@Transactional
	@PreAuthorize("hasPermission(#id, 'spring.server.commercial.model.product.Product', 'ADMINISTRATION')")
	public void delete(int id) {
		Product p = productRepository.findById(id).get();
		detete(p);
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
			result.add(productMapper.entityToProductDetailInforamtionDTO(n));
		});
		return result;
	}

	@Override
	@PreAuthorize("hasPermission(#id, 'spring.server.commercial.model.product.Product', 'READ')")
	public Optional<Product> getByID(int id) {
		
		Optional<Product> p = productRepository.findById(id);
		try {
			System.out.println(permissionService.checkPermission(new ObjectIdentityImpl(p.get()), BasePermission.ADMINISTRATION));
		} catch (IdentityUnavailableException | PermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
}
