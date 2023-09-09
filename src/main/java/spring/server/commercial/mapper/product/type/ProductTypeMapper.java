package spring.server.commercial.mapper.product.type;

import spring.server.commercial.config.convert.file.ConvertFile;
import spring.server.commercial.dto.product.type.ProductTypeResponseDTO;
import spring.server.commercial.model.product.ProductType;

public class ProductTypeMapper {
	public static ProductTypeResponseDTO productTypeToProductTypeResponseDTO(ProductType productType) {
		return new ProductTypeResponseDTO(productType.getId(), productType.getName(),
				ConvertFile.extractBytesFromBlob(productType.getImage()));
	}
}
