package spring.server.commercial.dto.product;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class GroupProductDTO {
	private int inStock;
	private BigDecimal price;
	private String image;
	private AttributeGroupDTO[] attributes;
}
