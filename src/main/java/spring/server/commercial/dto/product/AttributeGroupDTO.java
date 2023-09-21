package spring.server.commercial.dto.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttributeGroupDTO {
	private String name;
	private String value;
}