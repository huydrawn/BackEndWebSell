package spring.server.commercial.model.account;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity(name = "t_oauth2_account")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Oauth2Account extends Account {
	private String provideId;
}
