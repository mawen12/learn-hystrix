package com.mawen.learn.hystrix.exception;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public class HystrixBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 8211966075130334246L;

	public HystrixBadRequestException(String message) {
		super(message);
	}

	public HystrixBadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
