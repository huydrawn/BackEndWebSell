package spring.server.commercial.mapper.product;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.convert.file.ConvertFile;
import spring.server.commercial.dto.product.AttributeGroupDTO;
import spring.server.commercial.dto.product.GroupProductDTO;
import spring.server.commercial.dto.product.ProductInfomationDTO;
import spring.server.commercial.dto.product.ProductInformationDetailsRequestDTO;
import spring.server.commercial.mapper.product.attribute.AttributeMapper;
import spring.server.commercial.model.order.StatusOrder;
import spring.server.commercial.model.product.Attribute;
import spring.server.commercial.model.product.GroupProduct;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.product.ProductType;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.repository.product.GroupProductRepository;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.service.product.ProductTypeService;
import spring.server.commercial.service.user.UserService;

@Service
@RequiredArgsConstructor
public class ProductMapper {
	private final ProductTypeService productTypeService;
	private final ProductRepository productRepository;
	private final UserService userService;
	private final GroupProductRepository groupProductRepository;
	private final AttributeMapper attributeMapper;

	public Product updateProductFromDTO(ProductInformationDetailsRequestDTO productInformationDetailsRequestDTO)
			throws SerialException, SQLException {
		Product product = productRepository.findById(productInformationDetailsRequestDTO.getId()).get();
		product.setId(productInformationDetailsRequestDTO.getId());
		product.setName(productInformationDetailsRequestDTO.getName());
		product.setDescriber(productInformationDetailsRequestDTO.getDescriber());
		product.setGender(productInformationDetailsRequestDTO.getGender());
		product.setPrice(productInformationDetailsRequestDTO.getPrice());
		product.setInStock(productInformationDetailsRequestDTO.getInStock());

		ProductType productType = productTypeService
				.findById(Integer.valueOf(productInformationDetailsRequestDTO.getIdProductType()));
		if (!productType.equals(product.getProductType())) {
			Iterator<Product> it = product.getProductType().getProducts().iterator();
			while (it.hasNext()) {
				if (it.next().equals(product)) {
					it.remove();
				}
			}
			productTypeService.save(product.getProductType());
			product.setProductType(productType);
		}

		List<GroupProduct> groups = new ArrayList<>();
		for (int i = 0; i < productInformationDetailsRequestDTO.getDetails().length; i++) {
			GroupProduct add = new GroupProduct();
			add.setPrice(productInformationDetailsRequestDTO.getDetails()[i].getPrice());
			add.setInStock(productInformationDetailsRequestDTO.getDetails()[i].getInStock());
			add.setImage(ConvertFile.bytesToBlob(
					Base64.getDecoder().decode(productInformationDetailsRequestDTO.getDetails()[i].getImage())));
			List<Attribute> attributes = new ArrayList<>();
			for (int j = 0; j < productInformationDetailsRequestDTO.getDetails()[i].getAttributes().length; j++) {
				Attribute a = attributeMapper
						.DTOtoEntity(productInformationDetailsRequestDTO.getDetails()[i].getAttributes()[j]);

				attributes.add(a);
			}
			add.setAttributes(attributes);
			groups.add(add);
		}
		List<GroupProduct> remove = new ArrayList<>(product.getGroup());

		product.setGroup(groups);
		productRepository.save(product);
		for (int i = 0; i < remove.size(); i++) {
			groupProductRepository.deleteById(remove.get(i).getId());
		}
		return product;
	}

	public Product addProductDTOtoProductEntity(ProductInformationDetailsRequestDTO productInformationDetailsRequestDTO)
			throws SerialException, SQLException {

		Product product = Product.builder().build();
		product.setId(productInformationDetailsRequestDTO.getId());
		product.setName(productInformationDetailsRequestDTO.getName());
		product.setDescriber(productInformationDetailsRequestDTO.getDescriber());
		product.setGender(productInformationDetailsRequestDTO.getGender());
		product.setPrice(productInformationDetailsRequestDTO.getPrice());
		product.setInStock(productInformationDetailsRequestDTO.getInStock());
		byte[] imageProduct = Base64.getDecoder().decode(productInformationDetailsRequestDTO.getImage());
		List<Blob> imagesProduct = Collections.singletonList(ConvertFile.bytesToBlob(imageProduct));
		product.setImages(imagesProduct);
		ProductType productType = productTypeService
				.findById(Integer.valueOf(productInformationDetailsRequestDTO.getIdProductType()));
		product.setProductType(productType);
		product.setOrders(new ArrayList<>());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer = userService.findCustommerByEmail(authentication.getPrincipal().toString());

		product.setSeller(customer);

		List<GroupProduct> groups = new ArrayList<>();
		for (int i = 0; i < productInformationDetailsRequestDTO.getDetails().length; i++) {
			GroupProduct add = new GroupProduct();
			add.setPrice(productInformationDetailsRequestDTO.getDetails()[i].getPrice());
			add.setInStock(productInformationDetailsRequestDTO.getDetails()[i].getInStock());
			add.setPrice(productInformationDetailsRequestDTO.getDetails()[i].getPrice());
			add.setImage(ConvertFile.bytesToBlob(
					Base64.getDecoder().decode(productInformationDetailsRequestDTO.getDetails()[i].getImage())));
			List<Attribute> attributes = new ArrayList<>();
			for (int j = 0; j < productInformationDetailsRequestDTO.getDetails()[i].getAttributes().length; j++) {
				Attribute a = attributeMapper
						.DTOtoEntity(productInformationDetailsRequestDTO.getDetails()[i].getAttributes()[j]);

				attributes.add(a);
			}
			add.setAttributes(attributes);
			groups.add(add);

		}
		product.setGroup(groups);
		return product;
	}

	public ProductInformationDetailsRequestDTO entityToProductDetailInforamtionDTO(Product product) {
		ProductInformationDetailsRequestDTO response = ProductInformationDetailsRequestDTO.builder().build();
		response.setId(product.getId());
		response.setName(product.getName());
		response.setDescriber(product.getDescriber());
		response.setGender(product.getGender());
		response.setOrders(
				product.getOrders().stream().filter(n -> n.getStatusOrder() == StatusOrder.CONFIRMED).toList().size());
		response.setIdProductType(product.getProductType().getId() + "");
		response.setImage(
				Base64.getEncoder().encodeToString(ConvertFile.extractBytesFromBlob(product.getImages().get(0))));
		response.setInStock(product.getInStock());
		response.setPrice(product.getPrice());
		List<GroupProduct> groupProduct = new ArrayList<>(product.getGroup());

		GroupProductDTO[] groupProductDTOs = new GroupProductDTO[groupProduct.size()];
		for (int i = 0; i < groupProduct.size(); i++) {
			GroupProduct group = groupProduct.get(i);
			AttributeGroupDTO atriAttributeGroupDTOs[] = new AttributeGroupDTO[group.getAttributes().size()];
			for (int j = 0; j < group.getAttributes().size(); j++) {
				Attribute attribute = group.getAttributes().get(j);

				atriAttributeGroupDTOs[j] = attributeMapper.EntityToDTO(attribute);
			}
			GroupProductDTO groupProductDTO = GroupProductDTO.builder().build();
			groupProductDTO.setInStock(group.getInStock());
			groupProductDTO.setPrice(group.getPrice());
			groupProductDTO.setId(group.getId());
			groupProductDTO
					.setImage(Base64.getEncoder().encodeToString(ConvertFile.extractBytesFromBlob(group.getImage())));
			groupProductDTO.setAttributes(atriAttributeGroupDTOs);
			groupProductDTOs[i] = groupProductDTO;
		}
		response.setDetails(groupProductDTOs);
		return response;

	}

	public static ProductInfomationDTO entityToProductInformationDTO(Product product) {
		return ProductInfomationDTO.builder().id(product.getId()).name(product.getName()).price(product.getPrice())
				.inStock(product.getInStock()).productType(product.getProductType().getName())
				.orders(product.getOrders().stream().filter(n -> n.getStatusOrder() == StatusOrder.CONFIRMED).toList()
						.size())
				.image(Base64.getEncoder().encodeToString(ConvertFile.extractBytesFromBlob(product.getImages().get(0))))
				.build();
	}

}
