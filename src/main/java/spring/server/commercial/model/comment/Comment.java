package spring.server.commercial.model.comment;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.user.Customer;
import spring.server.commercial.model.user.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "t_comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String comment;
	private int rate;
	@ManyToOne
	private Customer user;
	@OneToOne
	private Product product;
	@CreationTimestamp
	private LocalDateTime create_at;
}
