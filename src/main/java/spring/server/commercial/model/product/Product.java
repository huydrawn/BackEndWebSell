package spring.server.commercial.model.product;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.commercial.model.order.Order;
import spring.server.commercial.model.user.Customer;

@Data
@Entity(name = "t_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String describer;
	private BigDecimal price;
	@ElementCollection
	List<Byte[]> images;
	@ManyToOne
	private ProductType productType;
	private int inStock;
	@ManyToOne
	private Customer seller;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Order> orders;
	@ElementCollection(fetch = FetchType.LAZY)
	private List<VariantProduct> variants;
}
