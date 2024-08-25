package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
@Slf4j
public class HystrixCommandMetrics {

	private static final ConcurrentHashMap<String, HystrixCommandMetrics> metrics = new ConcurrentHashMap<>();

	public static HystrixCommandMetrics getInstance(HystrixCommandKey key, HystrixCommandGroupKey commandGroupKey,
	                                                HystrixThreadPoolKey threadPoolKey, HystrixCommandProperties properties,
	                                                HystrixEventNotifer eventNotifer) {

	}
}
