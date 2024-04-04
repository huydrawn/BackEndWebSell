package spring.server.commercial.model.product;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
import spring.server.commercial.model.Gender;
import spring.server.commercial.model.order.Order;
import spring.server.commercial.model.user.Customer;

@Data
@Entity(name = "t_product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String describer;
	private BigDecimal price;

	@Enumerated
	private Gender gender;
	@ElementCollection
	List<Blob> images;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonBackReference
	@JsonManagedReference
	private ProductType productType;
	private int inStock;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JsonBackReference
	@JsonManagedReference
	private Customer seller;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Order> orders;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<GroupProduct> group;

	public void setProductType(ProductType productType) {
		this.productType = productType;
		productType.getProducts().add(this);
	}

	public void setSeller(Customer seller) {
		this.seller = seller;
		List<Product> list = seller.getSellProducts();
		if (list == null) {
			list = new ArrayList<>();
			list.add(this);
		} else {
			list.add(this);
		}
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", describer=" + describer + ", price=" + price + ", gender="
				+ gender + ", productType=" + productType + ", inStock=" + inStock + ", seller=" + seller + ", orders="
				+ orders + ", group=" + group + "]";
	}

}
