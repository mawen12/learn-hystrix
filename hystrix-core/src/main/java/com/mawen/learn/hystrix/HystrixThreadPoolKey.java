package com.mawen.learn.hystrix;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public interface HystrixThreadPoolKey {

	String name();

	class Factory {

		private static ConcurrentHashMap<String, HystrixThreadPoolKey> intern = new ConcurrentHashMap<>();

		public static HystrixThreadPoolKey asKey(String name) {
			HystrixThreadPoolKey k = intern.get(name);
			if (k == null) {
				intern.putIfAbsent(name, new HystrixThreadPoolKeyDefault(name));
			}
			return intern.get(name);
		}

		private static class HystrixThreadPoolKeyDefault implements HystrixThreadPoolKey {

			private String name;

			public HystrixThreadPoolKeyDefault(String name) {
				this.name = name;
			}

			@Override
			public String name() {
				return name;
			}
		}
	}

}
