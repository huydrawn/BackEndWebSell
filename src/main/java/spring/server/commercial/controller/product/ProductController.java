package spring.server.commercial.controller.product;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class ProductController {
	 
}
