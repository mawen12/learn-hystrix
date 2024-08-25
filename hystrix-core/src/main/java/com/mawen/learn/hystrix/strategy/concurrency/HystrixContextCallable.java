package com.mawen.learn.hystrix.strategy.concurrency;

import java.util.concurrent.Callable;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public class HystrixContextCallable<K> implements Callable<K> {

	private final Callable<K> actual;
	private final HystrixRequestContext parentThreadState;

	public HystrixContextCallable(Callable<K> actual) {
		this.actual = actual;
		this.parentThreadState = HystrixRequestContext.getContextForCurrentThread();
	}

	@Override
	public K call() throws Exception {
		HystrixRequestContext existingState = HystrixRequestContext.getContextForCurrentThread();
		try {
			HystrixRequestContext.setContextForCurrentThread(parentThreadState);
			return actual.call();
		}
		finally {
			HystrixRequestContext.setContextForCurrentThread(existingState);
		}
	}
}
