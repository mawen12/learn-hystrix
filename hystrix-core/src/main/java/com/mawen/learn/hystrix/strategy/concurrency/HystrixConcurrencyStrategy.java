package com.mawen.learn.hystrix.strategy.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mawen.learn.hystrix.HystrixThreadPoolKey;
import com.mawen.learn.hystrix.strategy.properties.HystrixProperty;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public abstract class HystrixConcurrencyStrategy {

	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
	                                        HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
	                                        BlockingQueue<Runnable> workQueue) {
		return new ThreadPoolExecutor(corePoolSize.get(), maximumPoolSize.get(), keepAliveTime.get(), unit, workQueue);
	}

	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		if (maxQueueSize < 0) {
			return new SynchronousQueue<>();
		}
		else {
			return new LinkedBlockingQueue<>(maxQueueSize);
		}
	}

	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return callable;
	}

	public <T> HystrixRequestVariable<T> getRequestVariable(final HystrixRequestVariableLifecycle<T> rv) {
		return new HystrixRequestVariableDefault<>() {
			@Override
			public T initialValue() {
				return rv.initialValue();
			}

			@Override
			public void shutdown(T value) {
				rv.shutdown(value);
			}
		};
	}
}
