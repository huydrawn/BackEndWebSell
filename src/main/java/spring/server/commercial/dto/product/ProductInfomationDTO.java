package spring.server.commercial.dto.product;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductInfomationDTO {
	private int id;
	private String name;
	private String image;
	private String productType;
	private BigDecimal price;
	private int inStock;
	private int orders;
	
}
