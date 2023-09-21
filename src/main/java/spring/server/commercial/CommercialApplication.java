package spring.server.commercial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import spring.server.commercial.repository.product.ProductTypeRepository;

@SpringBootApplication
@EnableCaching
@EnableWebSecurity
@EnableMethodSecurity 
@EnableJpaAuditing
public class CommercialApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommercialApplication.class, args);

	}

}

@Component
@RequiredArgsConstructor
class InsertProductType implements CommandLineRunner {
	private final ProductTypeRepository productTypeRepository;

	@Override
	public void run(String... args) throws Exception {
//		ProductType pt1 = ProductType.builder().describer("Thời trang nam").build();
//		ProductType pt2 = ProductType.builder().describer("Thời trang nữ").build();
//		ProductType pt3 = ProductType.builder().describer("Điện thoại và Phụ kiện").build();
//		ProductType pt4 = ProductType.builder().describer("Mẹ và Bé").build();
//		ProductType pt5 = ProductType.builder().describer("Máy Tính và LaTop").build();
//		ProductType pt6 = ProductType.builder().describer("Máy ảnh và Máy quay phim").build();
//		ProductType pt7 = ProductType.builder().describer("Đồng hồ").build();
//		ProductType pt8 = ProductType.builder().describer("Túi xách").build();
//		ProductType pt9 = ProductType.builder().describer("Giày dép Nam").build();
//		ProductType pt10 = ProductType.builder().describer("Giày dép nữ").build();
//		ProductType pt11 = ProductType.builder().describer("Nhà cửa và dời sống").build();
//		ProductType pt12 = ProductType.builder().describer("Ô tô xe máy xe đạp").build();
//		ProductType pt13 = ProductType.builder().describer("Thiết bị điện tử").build();
//		List<ProductType> productTypes = new ArrayList<>();
//		productTypes.add(pt1);
//		productTypes.add(pt2);
//		productTypes.add(pt3);
//		productTypes.add(pt4);
//		productTypes.add(pt5);
//		productTypes.add(pt6);
//		productTypes.add(pt7);
//		productTypes.add(pt8);
//		productTypes.add(pt9);
//		productTypes.add(pt10);
//		productTypes.add(pt11);
//		productTypes.add(pt12);
//		productTypes.add(pt13);
//		productTypeRepository.saveAll(productTypes);
	}

}