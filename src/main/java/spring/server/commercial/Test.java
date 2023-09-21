package spring.server.commercial;

import javax.imageio.spi.RegisterableService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.dto.auth.RegisterRequestDTO;
import spring.server.commercial.exception.registeration.RegisterationException;
import spring.server.commercial.exception.vertificate_email.EmailExistedException;
import spring.server.commercial.mapper.product.ProductMapper;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.repository.product.ProductRepository;
import spring.server.commercial.repository.product.ProductTypeRepository;
import spring.server.commercial.repository.user.CustommerRepository;
import spring.server.commercial.service.auth.AuthenticationResolver;
import spring.server.commercial.service.permission.PermissionService;
import spring.server.commercial.service.product.ProductService;
import spring.server.commercial.service.register.RegisterService;
import spring.server.commercial.service.user.UserService;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {
	private final ProductTypeRepository productTypeService;
	private final ProductRepository productRepository;
	private final UserService userService;
	private final CustommerRepository custommerRepository;
	private final ProductService productService;
	private final PermissionService permissionService;
	private final RegisterService registerService;

	@GetMapping("/{id}")
	public ResponseEntity<?> test(@PathVariable int id) {
		try {
			registerService.resolve(RegisterRequestDTO.builder().email("21130376@st.hcmuaf.edu.vn").password("20102003")
					.username("quanghuy123").build());
		} catch (EmailExistedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RegisterationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok("ok");
	}
}
