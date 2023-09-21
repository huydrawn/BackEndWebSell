package spring.server.commercial.model.cart;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.user.Customer;

@Entity(name = "t_cart")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToMany
	private List<Product> products;
	@OneToOne
	private Customer customer;
}
