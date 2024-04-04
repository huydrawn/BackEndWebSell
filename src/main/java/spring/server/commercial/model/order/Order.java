package spring.server.commercial.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import spring.server.commercial.model.payment.Payment;
import spring.server.commercial.model.product.Product;
import spring.server.commercial.model.user.Customer;

@Entity(name = "t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int amount;
	private BigDecimal price;
	@CreationTimestamp
	private LocalDateTime createAt;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(columnDefinition = "customer_id")
	@JsonManagedReference
	private Customer customer;
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(columnDefinition = "product_id")
	@JsonManagedReference
	private Product product;

	private int groupProductId;
	@Enumerated
	private StatusOrder statusOrder;
	@Embedded
	private Payment payment;

	public void setCustomer(Customer customer) {
		this.customer = customer;
		customer.getOrders().add(this);
	}
	public void setProduct(Product product) {
		this.product = product;
		product.getOrders().add(this);
	}
	
	public void removeCustomer() {
		Iterator<Order> it = customer.getOrders().iterator();
		while(it.hasNext()) {
			Order remove = it.next();
			if(remove.getId() == this.getId()) {
				it.remove();
			}
		}
		this.customer = null;
	}
	public void removeProduct() {
		Iterator<Order> it = product.getOrders().iterator();
		while(it.hasNext()) {
			Order remove = it.next();
			if(remove.getId() == this.getId()) {
				it.remove();
			}
		}
		this.product = null;
	}


	

	@Override
	public String toString() {
		return "Order [id=" + id + ", createAt=" + createAt + ", attribute=" + ", statusOrder=" + statusOrder
				+ ", payment=" + payment + "]";
	}

}
