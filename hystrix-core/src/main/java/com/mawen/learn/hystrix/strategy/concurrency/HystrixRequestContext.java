package com.mawen.learn.hystrix.strategy.concurrency;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
@Slf4j
public class HystrixRequestContext {

	private static ThreadLocal<HystrixRequestContext> requestVariables = new ThreadLocal<>();

	ConcurrentHashMap<HystrixRequestVariableDefault<?>, HystrixRequestVariableDefault.LazyInitializer<?>> state = new ConcurrentHashMap<>();

	private HystrixRequestContext() {}

	public static boolean isCurrentThreadInitialized() {
		HystrixRequestContext context = requestVariables.get();
		return context != null && context.state != null;
	}

	public static HystrixRequestContext getContextForCurrentThread() {
		HystrixRequestContext context = requestVariables.get();
		if (context != null && context.state != null) {
			return context;
		}
		return null;
	}

	public static void setContextForCurrentThread(HystrixRequestContext state) {
		requestVariables.set(state);
	}

	public static HystrixRequestContext initializeContext() {
		HystrixRequestContext state = new HystrixRequestContext();
		requestVariables.set(state);
		return state;
	}

	public void shutdown() {
		if (state != null) {
			for (HystrixRequestVariableDefault<?> v : state.keySet()) {
				try {
					HystrixRequestVariableDefault.remove(this, v);
				}
				catch (Throwable t) {
					log.info("Error in shutdown, will continue with shutdown of other variables", t);
				}
			}
			state = null;
		}
	}
}
