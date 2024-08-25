package com.mawen.learn.hystrix.strategy.concurrency;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public class HystrixConcurrencyStrategyDefault extends HystrixConcurrencyStrategy {

	private static HystrixConcurrencyStrategyDefault INSTANCE = new HystrixConcurrencyStrategyDefault();

	public static HystrixConcurrencyStrategy getInstance() {
		return INSTANCE;
	}

	private HystrixConcurrencyStrategyDefault() {}
}
