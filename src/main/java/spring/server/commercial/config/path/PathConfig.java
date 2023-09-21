package spring.server.commercial.config.path;

public class PathConfig {
	public static String[] getPathPermitAll() {
		return new String[] { "/test/**", "/product/type", "/register/**", "/verification/**", "/auth/**",
				"/swagger-ui/**", "/v3/api-docs/**" };
	}

	public static String[] getPathPermitAllForGetMethod() {
		return new String[] { "/product", "/product/{id}" };
	}
}
