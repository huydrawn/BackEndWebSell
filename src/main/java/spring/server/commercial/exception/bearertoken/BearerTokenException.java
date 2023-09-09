package spring.server.commercial.exception.bearertoken;

import jakarta.servlet.ServletException;

public class BearerTokenException extends ServletException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BearerTokenException(String msg) {
		super(msg);
	}
}
