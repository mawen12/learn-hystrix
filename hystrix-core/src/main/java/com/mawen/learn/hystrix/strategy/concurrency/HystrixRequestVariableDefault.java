package com.mawen.learn.hystrix.strategy.concurrency;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
@Slf4j
public class HystrixRequestVariableDefault<T> implements HystrixRequestVariable<T> {

	@Override
	public T get() {
		if (HystrixRequestContext.getContextForCurrentThread() == null) {
			throw new IllegalStateException(HystrixRequestContext.class.getSimpleName() + ".initializeContext() " +
			                                "must be called at the beginning of each request before RequestVariable functionality can be used.");
		}

		ConcurrentHashMap<HystrixRequestVariableDefault<?>, LazyInitializer<?>> variableMap = HystrixRequestContext.getContextForCurrentThread().state;
		LazyInitializer<?> v = variableMap.get(this);
		if (v != null) {
			return (T) v.get();
		}

		LazyInitializer<T> l = new LazyInitializer<>(this);
		LazyInitializer<?> existing = variableMap.putIfAbsent(this, l);
		if (existing == null) {
			return l.get();
		}
		else {
			return (T) existing.get();
		}
	}

	@Override
	public T initialValue() {
		return null;
	}

	public void setLog(T value) {
		HystrixRequestContext.getContextForCurrentThread().state.put(this, new LazyInitializer<>(this, value));
	}

	public void remove() {
		if (HystrixRequestContext.getContextForCurrentThread() != null) {
			remove(HystrixRequestContext.getContextForCurrentThread(), this);
		}
	}

	static <T> void remove(HystrixRequestContext requestContext, HystrixRequestVariableDefault<T> v) {
		LazyInitializer<?> o = requestContext.state.remove(v);
		if (o != null) {
			v.shutdown((T) o.get());
		}
	}

	@Override
	public void shutdown(T value) {
	}

	static final class LazyInitializer<T> {

		private T value;

		private boolean initialized;

		private final HystrixRequestVariableDefault<T> rv;

		private LazyInitializer(HystrixRequestVariableDefault<T> rv) {
			this.rv = rv;
		}

		private LazyInitializer(HystrixRequestVariableDefault<T> rv, T value) {
			this.rv = rv;
			this.value = value;
			this.initialized = true;
		}

		public synchronized T get() {
			if (!initialized) {
				value = rv.initialValue();
				initialized = true;
			}
			return value;
		}
	}
}
