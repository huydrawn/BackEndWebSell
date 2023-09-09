package spring.server.commercial.dto.product.type;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddProductTypeDTO {
	private String name;
	private MultipartFile image;
}
