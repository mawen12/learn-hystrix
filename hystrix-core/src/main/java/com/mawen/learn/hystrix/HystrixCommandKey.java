package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
public interface HystrixCommandKey {

	String name();

	class Factory {

		private static ConcurrentHashMap<String, HystrixCommandKey> intern = new ConcurrentHashMap<>();

		private Factory() {}

		public static HystrixCommandKey asKey(String name) {
			HystrixCommandKey k = intern.get(name);
			if (k == null) {
				intern.putIfAbsent(name, new HystrixCommandKeyDefault(name));
			}
			return intern.get(name);
		}

		private static class HystrixCommandKeyDefault implements HystrixCommandKey {

			private String name;

			private HystrixCommandKeyDefault(String name) {
				this.name = name;
			}

			@Override
			public String name() {
				return name;
			}
		}
	}
}
