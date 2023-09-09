package spring.server.commercial.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import spring.server.commercial.model.user.User;

@Getter
@Setter
@Entity(name = "t_normal_account")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class NormalAccout extends Account {
	
	private String password;
	@OneToOne
	@JsonBackReference
	private User user;
}
