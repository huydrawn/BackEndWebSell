package spring.server.commercial.controller.product;

import java.sql.SQLException;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.product.ProductInformationDetailsRequestDTO;
import spring.server.commercial.mapper.product.ProductMapper;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.response.Response;
import spring.server.commercial.service.product.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.DELETE,
		RequestMethod.POST })
public class ProductController {
	private final ProductMapper productMapper;
	private final ProductService productService;
	private final ProductRepository productRepository;

	@GetMapping("/info/{id}")
	public ResponseEntity<?> getInfoById(@PathVariable int id) {
		Product product = productRepository.findById(id).get();
		return ResponseEntity.ok(ProductMapper.entityToProductDetailInforamtionDTO(product));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductDetailById(@PathVariable int id) {
		Product product = productRepository.findById(id).get();
		return ResponseEntity.ok(ProductMapper.entityToProductDetailInforamtionDTO(product));
	}

	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody ProductInformationDetailsRequestDTO saveProductRequestDTO) {
		Product product;
		try {
			product = productMapper.updateProductFromDTO(saveProductRequestDTO);
			productRepository.save(product);
			return ResponseEntity.ok(new Response("Success update product"));
		} catch (SQLException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody ProductInformationDetailsRequestDTO add) {
		try {
			Product product = productMapper.addProductDTOtoProductEntity(add);

			productService.save(product);

			return ResponseEntity.ok(new Response("Success Save Product"));
		} catch (SQLException e) {

			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		productService.delete(id);
		return ResponseEntity.ok(new Response("Detete Success Product have id " + id));
	}

	@GetMapping("/info")
	public ResponseEntity<?> getInfo() {
		return ResponseEntity.ok(productService.getInformations());
	}

	@GetMapping
	public ResponseEntity<?> get(@Param("type") String type, @Param("page") int page, @Param("offset") int offset,
			@Param("orderKey") String orderKey, @Param("orderType") String orderType) {

		return ResponseEntity.ok(productService.getByConditition(type, page, offset, orderKey, orderType));
	}
}
