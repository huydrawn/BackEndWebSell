package spring.server.commercial;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "t_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {
	@Id
	private int id;
}
