package spring.server.commercial.model.cart;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.user.Customer;

@Entity(name = "t_cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ElementCollection
	private List<Product> products;
	@OneToOne
	private Customer customer;
}
