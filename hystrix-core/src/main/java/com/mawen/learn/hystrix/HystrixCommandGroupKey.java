package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public interface HystrixCommandGroupKey {

	String name();

	class Factory {

		private static ConcurrentHashMap<String, HystrixCommandGroupKey> intern = new ConcurrentHashMap<>();

		private Factory() {}

		public static HystrixCommandGroupKey asKey(String name) {
			HystrixCommandGroupKey k = intern.get(name);
			if (k == null) {
				intern.putIfAbsent(name, new HystrixCommandGroupKeyDefault(name));
			}
			return intern.get(name);
		}

		private static class HystrixCommandGroupKeyDefault implements HystrixCommandGroupKey {

			private String name;

			private HystrixCommandGroupKeyDefault(String name) {
				this.name = name;
			}

			@Override
			public String name() {
				return name;
			}
		}
	}
}
