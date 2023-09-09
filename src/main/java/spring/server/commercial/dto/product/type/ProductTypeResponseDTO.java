package spring.server.commercial.dto.product.type;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductTypeResponseDTO {
		private int id;
		private String name;
		private byte[] image;
}
