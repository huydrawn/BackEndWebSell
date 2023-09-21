package spring.server.commercial.model.product;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "t_group_product")
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	@ManyToOne
	private Product product;
	private Blob image;
	private BigDecimal price;
	private int inStock;
	@ElementCollection
	private List<Attribute> attributes;
}
