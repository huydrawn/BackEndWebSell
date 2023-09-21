package spring.server.commercial.controller.product.type;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.convert.file.ConvertFile;
import spring.server.commercial.dto.product.type.AddProductTypeDTO;
import spring.server.commercial.model.Gender;
import spring.server.commercial.model.product.ProductType;
import spring.server.commercial.service.product.ProductTypeService;

@RestController
@RequestMapping("/product/type")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProductTypeController {
	private final ProductTypeService productTypeService;

	@PostMapping
	public ResponseEntity<?> add(@ModelAttribute AddProductTypeDTO addProductTypeDTO) {
		try {
			ProductType productType = null;
			if (productTypeService.existsByName(addProductTypeDTO.getName())) {
				productType = productTypeService.findByName(addProductTypeDTO.getName()).get();
			} else {
				productType = ProductType.builder().name(addProductTypeDTO.getName()).build();
			}
			productType.setImage(ConvertFile.convertMultipartFileToBlob(addProductTypeDTO.getImage()));
			productTypeService.save(productType);
			return ResponseEntity.ok("Success");
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(productTypeService.getAll());
	}

	@GetMapping("/names")
	public ResponseEntity<?> getAllNames() {
		Map<String, Object> map = new HashMap<>();
		
		map.put("list", productTypeService.findAllNameAndId());
		
		
		return ResponseEntity.ok().body(map);
	}
	@GetMapping("/gender")
	public ResponseEntity<?> getGender() {
		Map<String, Object> map = new HashMap<>();
		
		map.put("genders", Gender.values());
		
		
		return ResponseEntity.ok().body(map);
	}

}
