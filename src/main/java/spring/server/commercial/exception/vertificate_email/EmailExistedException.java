package spring.server.commercial.exception.vertificate_email;

public class EmailExistedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailExistedException(String msg) {
		super(msg);
	}
}
