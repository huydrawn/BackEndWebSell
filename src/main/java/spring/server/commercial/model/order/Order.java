package spring.server.commercial.model.order;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.server.commercial.model.payment.Payment;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.user.Customer;

@Entity(name = "t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@CreationTimestamp
	private LocalDateTime createAt;
	@ManyToOne
	@JoinColumn(columnDefinition = "customer_id")
	private Customer customer;
	@ManyToOne
	@JoinColumn(columnDefinition = "product_id")
	private Product product;

	private StatusOrder statusOrder;
	@Embedded
	private Payment payment;
}
