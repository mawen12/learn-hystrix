package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/24
 */
public interface HystrixCircuitBreaker {


	boolean allowRequest();

	boolean isOpen();

	void makeSuccess();

	class Factory {

		private static ConcurrentHashMap<String, HystrixCircuitBreaker> circuitBreakersByCommand = new ConcurrentHashMap<>();

		public static HystrixCircuitBreaker getInstance(HystrixCommandKey key, ) {
			// TODO start here
		}

	}
}
