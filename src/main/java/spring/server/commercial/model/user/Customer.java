package spring.server.commercial.model.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import spring.server.commercial.model.comment.Comment;
import spring.server.commercial.model.order.Order;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.role.Role;

@Entity(name = "t_customer")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ElementCollection(fetch = FetchType.LAZY)
	private List<Product> favoriteProducts;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Comment> comments;
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = false)
	@JsonBackReference
	private List<Order> orders;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.REMOVE })
	@JsonManagedReference
	private List<Product> sellProducts;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + Role.CUSTOMER));
	}

	public void addProduct(Product product) {
		this.sellProducts.add(product);
		product.setSeller(this);
	}

	public void removeProduct(Product product) {
		this.sellProducts.remove(product);
		product.setSeller(null);
	}
}
