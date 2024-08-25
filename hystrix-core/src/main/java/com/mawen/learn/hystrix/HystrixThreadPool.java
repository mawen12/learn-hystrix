package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public interface HystrixThreadPool {

	ThreadPoolExecutor getExecutor();

	void markThreadExecution();

	void markThreadCompletion();

	boolean isQueueSpaceAvailable();


	class Factory {

		private static ConcurrentHashMap<String, HystrixThreadPool> threadPools = new ConcurrentHashMap<>();

		static HystrixThreadPool getInstance(HystrixThreadPoolKey threadPoolKey, HystrixConcurrencyStrategy concurrencyStrategy,
		                                     HystrixMetricsPublisher metricsPublisher, HystrixPropertiesStrategy propertiesFactory,
		                                     HystrixThreadPoolProperties.Setter propertiesBuilder) {

		}
	}
}
