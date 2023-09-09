package spring.server.commercial.controller.product.type;

import java.sql.Blob;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateFileDTO {
	private String name;
	private MultipartFile images[];
}
