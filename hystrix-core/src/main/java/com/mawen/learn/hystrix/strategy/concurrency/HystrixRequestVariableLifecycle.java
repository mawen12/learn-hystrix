package com.mawen.learn.hystrix.strategy.concurrency;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public interface HystrixRequestVariableLifecycle<T> {

	T initialValue();

	void shutdown(T value);
}
