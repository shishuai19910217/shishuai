package com.ido85.log;

/**
 * User: zjzhai
 * Date: 3/2/14
 * Time: 9:27 PM
 */
public class BusinessLogBaseException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2729428975572285352L;

	public BusinessLogBaseException() {
    }

    public BusinessLogBaseException(String message) {
        super(message);
    }

    public BusinessLogBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessLogBaseException(Throwable cause) {
        super(cause);
    }
}
