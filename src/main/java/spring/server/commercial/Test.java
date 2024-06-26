package spring.server.commercial;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class Test {

	@GetMapping("/{id}")
	public ResponseEntity<?> test(@PathVariable int id) {
		
		return ResponseEntity.ok("ok");
	}

}
