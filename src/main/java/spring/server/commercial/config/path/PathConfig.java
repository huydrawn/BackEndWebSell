package spring.server.commercial.config.path;

import org.springframework.util.AntPathMatcher;

public class PathConfig {
	public static String[] getPathPermitAll() {
		return new String[] { "/", "/test/**", "/product/type", "/register/**", "/verification/**", "/auth/**",
				"/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**" };
	}
	public static String[] oligate() {
		return new String[] { "/product/info" }; 
	}

	public static String[] getPathPermitAllForGetMethod() {
		return new String[] {  };
	}

	public static String connectWebsocket() {
		return "/ws";
	}

	public static boolean matcherConnectWebSocketPath(String path) {
		return path.contains(connectWebsocket());
	}

	public static boolean checkPathForAuthentication(String path, String methodRequest) {
		boolean isCheck = true;
		for (var x : PathConfig.oligate()) {

			if (new AntPathMatcher().match(x, path)) {
				return true;
			}
		}
		for (var x : PathConfig.getPathPermitAll()) {

			if (new AntPathMatcher().match(x, path)) {
				isCheck = false;
				break;
			}
		}
		if (methodRequest.equalsIgnoreCase("GET") && isCheck == true) {

			for (var x : PathConfig.getPathPermitAllForGetMethod()) {
				if (new AntPathMatcher().match(x, path)) {
					isCheck = false;

				}
			}
		}

		return isCheck;
	}
}
