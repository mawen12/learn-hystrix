package com.mawen.learn.hystrix.strategy.concurrency;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public class HystrixContextRunnable implements Runnable{

	private final Runnable actual;
	private final HystrixRequestContext parentThreadState;

	public HystrixContextRunnable(Runnable actual) {
		this.actual = actual;
		this.parentThreadState = HystrixRequestContext.getContextForCurrentThread();
	}

	@Override
	public void run() {
		HystrixRequestContext existingState = HystrixRequestContext.getContextForCurrentThread();
		try {
			HystrixRequestContext.setContextForCurrentThread(parentThreadState);
			actual.run();
		}
		finally {
			HystrixRequestContext.setContextForCurrentThread(existingState);
		}
	}
}
