package com.mawen.learn.hystrix.strategy.properties;


import static com.mawen.learn.hystrix.strategy.properties.HystrixPropertiesChainedArchaiusProperty.*;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public interface HystrixProperty<T> {

	T get();

	class Factory {

		public static <T> HystrixProperty<T> asProperty(final T value) {
			return () -> value;
		}

		public static HystrixProperty<Integer> asProperty(final DynamicIntegerProperty value) {
			return () -> value.get();
		}

		public static HystrixProperty<Long> asProperty(final DynamicLongProperty value) {
			return () -> value.get();
		}

		public static HystrixProperty<String> asProperty(final DynamicStringProperty value) {
			return () -> value.get();
		}

		public static HystrixProperty<Boolean> asProperty(final DynamicBooleanProperty value) {
			return () -> value.get();
		}

		public static <T> HystrixProperty<T> asProperty(final HystrixProperty<T> value, final T defaultValue) {
			return () -> {
				T v = value.get();
				return v == null ? defaultValue : v;
			};
		}

		public static <T> HystrixProperty<T> asProperty(final HystrixProperty<T>... values) {
			return () -> {
				for (HystrixProperty<T> v : values) {
					if (v.get() != null) {
						return v.get();
					}
				}
				return null;
			};
		}

		public static <T> HystrixProperty<T> asProperty(final HystrixPropertiesChainedArchaiusProperty.ChainLink<T> chainedProperty) {
			return () -> chainedProperty.get();
		}

		public static <T> HystrixProperty<T> nullProperty() {
			return () -> null;
		}
	}
}
