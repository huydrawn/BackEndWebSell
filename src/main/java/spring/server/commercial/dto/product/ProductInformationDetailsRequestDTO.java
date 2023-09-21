package spring.server.commercial.dto.product;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;
import spring.server.commercial.model.Gender;
@Data
@Builder
public class ProductInformationDetailsRequestDTO {
	private int id;
	private String image;
	private String name;
	private int orders;
	private String idProductType;
	private String describer;
	private Gender gender;
	private BigDecimal price;
	private int inStock;
	private GroupProductDTO details[];
}
