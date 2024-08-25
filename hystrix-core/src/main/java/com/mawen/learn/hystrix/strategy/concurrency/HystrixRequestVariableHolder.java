package com.mawen.learn.hystrix.strategy.concurrency;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
@Slf4j
public class HystrixRequestVariableHolder<T> {

	private static ConcurrentHashMap<RVCacheKey, HystrixRequestVariable<?>> requestVariableInstance = new ConcurrentHashMap<>();

	private final HystrixRequestVariableLifecycle<T> lifeCycleMethods;

	public HystrixRequestVariableHolder(HystrixRequestVariableLifecycle<T> methods) {
		this.lifeCycleMethods = methods;
	}

	public T get(HystrixConcurrencyStrategy concurrencyStrategy) {
		RVCacheKey key = new RVCacheKey(this, concurrencyStrategy);
		HystrixRequestVariable<?> rvInstance = requestVariableInstance.get(key);
		if (rvInstance == null) {
			requestVariableInstance.putIfAbsent(key, concurrencyStrategy.getRequestVariable(lifeCycleMethods));
			if (requestVariableInstance.size() > 100) {
				log.warn("Over 100 instances of HystrixRequestVariable are being stored. This is likely the sign of a memory leak caused by using unique instances of HystrixConcurrencyStrategy instead of a single instance. ");
			}
		}

		return (T) requestVariableInstance.get(key).get();
	}

	private static class RVCacheKey {

		private final HystrixRequestVariableHolder<?> rvHolder;
		private final HystrixConcurrencyStrategy concurrencyStrategy;

		public RVCacheKey(HystrixRequestVariableHolder<?> rvHolder, HystrixConcurrencyStrategy concurrencyStrategy) {
			this.rvHolder = rvHolder;
			this.concurrencyStrategy = concurrencyStrategy;
		}

		@Override
		public final boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof RVCacheKey that)) return false;

			return Objects.equals(rvHolder, that.rvHolder) && Objects.equals(concurrencyStrategy, that.concurrencyStrategy);
		}

		@Override
		public int hashCode() {
			int result = Objects.hashCode(rvHolder);
			result = 31 * result + Objects.hashCode(concurrencyStrategy);
			return result;
		}
	}
}
