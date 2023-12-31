package spring.server.commercial.model.product;

import java.sql.Blob;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "t_product_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Basic(fetch = FetchType.LAZY)
	private Blob image;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	@JsonBackReference
	private List<Product> products;

	@Override
	public String toString() {
		return "ProductType [id=" + id + ", name=" + name + "]";
	}

}
