package spring.server.commercial;

import java.sql.Blob;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.repository.product.ProductTypeRepository;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {
	private final ProductTypeRepository productTypeService;
	private final ProductRepository productRepository;
	private final UserService userService;

	@PostMapping
	public ResponseEntity<?> test(@RequestBody byte[] file) {
		System.out.println(file);
		return ResponseEntity.ok(file);
	}
}
