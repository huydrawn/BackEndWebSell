package spring.server.commercial.config.path;

public class PathConfig {
	public static String[] getPathPermitAll() {
		return new String[] {"/product/**", "/register/**", "/verification/**", "/auth/**", "/swagger-ui.html/*", "/test/**" };
	}
}
