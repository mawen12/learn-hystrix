package com.mawen.learn.hystrix;

import java.util.List;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public abstract class HystrixEventNotifier {

	public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {

	}

	public void markCommandExecution(HystrixCommandKey key, ExecutionIsolationStrategy isolationStrategy, int duration, List<HystrixEventType> eventsDuringExecution) {

	}
}
