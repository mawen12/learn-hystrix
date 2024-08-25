package com.mawen.learn.hystrix.exception;

import com.mawen.learn.hystrix.HystrixCommand;
import com.mawen.learn.hystrix.util.ExceptionThreadingUtility;
import lombok.Getter;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
@Getter
public class HystrixRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6174660595406926139L;

	private final Class<? extends HystrixCommand> commandClass;
	private final Throwable fallbackException;
	private final FailureType failureCause;

	public HystrixRuntimeException(FailureType failureCause, Class<? extends HystrixCommand> commandClass, String message, Exception cause, Throwable fallbackException) {
		super(message, cause);
		this.commandClass = commandClass;
		this.fallbackException = fallbackException;
		this.failureCause = failureCause;
		ExceptionThreadingUtility.attachCallingThreadStack(this);
	}

	public HystrixRuntimeException(FailureType failureCause, Class<? extends HystrixCommand> commandClass, String message, Throwable cause, Throwable fallbackException) {
		super(message, cause);
		this.commandClass = commandClass;
		this.fallbackException = fallbackException;
		this.failureCause = failureCause;
		ExceptionThreadingUtility.attachCallingThreadStack(this);
	}

	public enum FailureType {
		COMMAND_EXCEPTION,
		TIMEOUT,
		SHORTCIRCUIT,
		REJECTED_THREAD_EXECUTION,
		REJECTED_SEMAPHORE_EXECUTION,
		REJECTED_SEMAPHORE_FALLBACK,
	}
}
