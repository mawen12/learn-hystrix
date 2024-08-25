package com.mawen.learn.hystrix;

import com.mawen.learn.hystrix.strategy.properties.HystrixPropertiesChainedArchaiusProperty;
import com.mawen.learn.hystrix.strategy.properties.HystrixProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static com.mawen.learn.hystrix.strategy.properties.HystrixPropertiesChainedArchaiusProperty.*;
import static com.mawen.learn.hystrix.strategy.properties.HystrixProperty.Factory.*;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/23
 */
@Slf4j
@Getter
public abstract class HystrixCommandProperties {

	// defaults
	private static final Integer default_metricsRollingStatisticalWindow = 10_000;
	private static final Integer default_metricsRollingStatisticalWindowBuckets = 10;
	private static final Integer default_circuitBreakerRequestVolumeThreshold = 20;
	private static final Integer default_circuitBreakerSleepWindowInMilliseconds = 5000;
	private static final Integer default_circuitBreakerErrorThresholdPercentage = 50;
	private static final Boolean default_circuitBreakerForceOpen = false;
	private static final Boolean default_circuitBreakerForceClosed = false;
	private static final Integer default_executionIsolationThreadTimeoutInMilliseconds = 1000;
	private static final ExecutionIsolationStrategy default_executionIsolationStrategy = ExecutionIsolationStrategy.THREAD;
	private static final Boolean default_executionIsolationThreadInterruptOnTimeout = true;
	private static final Boolean default_metricsRollingPercentileEnabled = true;
	private static final Boolean default_requestCacheEnabled = true;
	private static final Integer default_fallbackIsolationSemaphoreMaxConcurrentRequests = 10;
	private static final Integer default_executionIsolationSemaphoreMaxConcurrentRequests = 10;
	private static final Boolean default_requestLogEnabled = true;
	private static final Boolean default_circuitBreakerEnabled = true;
	private static final Integer default_metricsRollingPercentileWindow = 60_000;
	private static final Integer default_metricsRollingPercentileWindowBuckets = 6;
	private static final Integer default_metricsRollingPercentileBucketSize = 100;
	private static final Integer default_metricsHealthSnapshotIntervalInMilliseconds = 500;

	private final HystrixCommandKey key;
	private final HystrixProperty<Integer> circuitBreakerRequestVolumeThreshold;
	private final HystrixProperty<Integer> circuitBreakerSleepWindowInMilliseconds;
	private final HystrixProperty<Boolean> circuitBreakerEnabled;
	private final HystrixProperty<Integer> circuitBreakerErrorThresholdPercentage;
	private final HystrixProperty<Boolean> circuitBreakerForceOpen;
	private final HystrixProperty<Boolean> circuitBreakerForceClosed;
	private final HystrixProperty<ExecutionIsolationStrategy> executionIsolationStrategy;
	private final HystrixProperty<Integer> executionIsolationThreadTimeoutInMilliseconds;
	private final HystrixProperty<String> executionIsolationThreadPoolKeyOverride;
	private final HystrixProperty<Integer> executionIsolationSemaphoreMaxConcurrentRequests;
	private final HystrixProperty<Integer> fallbackIsolationSemaphoreMaxConcurrentRequests;
	private final HystrixProperty<Boolean> executionIsolationThreadInterruptOnTimeout;
	private final HystrixProperty<Integer> metricsRollingStatisticalWindowInMilliseconds;
	private final HystrixProperty<Integer> metricsRollingStatisticalWindowBuckets;
	private final HystrixProperty<Boolean> metricsRollingPercentileEnabled;
	private final HystrixProperty<Integer> metricsRollingPercentileWindow;
	private final HystrixProperty<Integer> metricsRollingPercentileWindowBuckets;
	private final HystrixProperty<Integer> metricsRollingPercentileBucketSize;
	private final HystrixProperty<Integer> metricsHealthSnapshotIntervalInMilliseconds;
	private final HystrixProperty<Boolean> requestLogEnabled;
	private final HystrixProperty<Boolean> requestCacheEnabled;

	public enum ExecutionIsolationStrategy {
		THREAD, SEMAPHORE
	}

	public HystrixCommandProperties(HystrixCommandKey key, HystrixCommandProperties.Setter builder, String propertyPrefix) {
		this.key = key;
		this.circuitBreakerEnabled = getProperty(propertyPrefix, key, "circuitBreaker.enabled", builder.getCircuitBreakerEnabled(), default_circuitBreakerEnabled);
		this.circuitBreakerRequestVolumeThreshold = getProperty(propertyPrefix, key, "circuitBreaker.requestVolumeThreshold", builder.getCircuitBreakerRequestVolumeThreshold(), default_circuitBreakerRequestVolumeThreshold);
		this.circuitBreakerSleepWindowInMilliseconds = getProperty(propertyPrefix, key, "circuitBreaker.sleepWindowInMilliseconds", builder.getCircuitBreakerSleepWindowInMilliseconds(), default_circuitBreakerSleepWindowInMilliseconds);
		this.circuitBreakerErrorThresholdPercentage = getProperty(propertyPrefix, key, "circuitBreaker.errorThresholdPercentage", builder.getCircuitBreakerErrorThresholdPercentage(), default_circuitBreakerErrorThresholdPercentage);
		this.circuitBreakerForceOpen = getProperty(propertyPrefix, key, "circuitBreaker.forceOpen", builder.getCircuitBreakerForceOpen(), default_circuitBreakerForceOpen);
		this.circuitBreakerForceClosed = getProperty(propertyPrefix, key, "circuitBreaker.forceClosed", builder.getCircuitBreakerForceClosed(), default_circuitBreakerForceClosed);
		this.executionIsolationStrategy = getProperty(propertyPrefix, key, "execution.isolation.strategy", builder.getExecutionIsolationStrategy(), default_executionIsolationStrategy);
		this.executionIsolationThreadTimeoutInMilliseconds = getProperty(propertyPrefix, key, "execution.isolation.thread.timeoutInMilliseconds", builder.getExecutionIsolationThreadTimeoutInMilliseconds(), default_executionIsolationThreadTimeoutInMilliseconds);
		this.executionIsolationThreadInterruptOnTimeout = getProperty(propertyPrefix, key, "execution.isolation.thread.interruptOnTimeout", builder.getExecutionIsolationThreadInterruptOnTimeout(), default_executionIsolationThreadInterruptOnTimeout);
		this.executionIsolationSemaphoreMaxConcurrentRequests = getProperty(propertyPrefix, key, "execution.isolation.semaphore.maxConcurrentRequests", builder.getExecutionIsolationSemaphoreMaxConcurrentRequests(), default_executionIsolationSemaphoreMaxConcurrentRequests);
		this.fallbackIsolationSemaphoreMaxConcurrentRequests = getProperty(propertyPrefix, key, "fallback.isolation.semaphore.maxConcurrentRequests", builder.getFallbackIsolationSemaphoreMaxConcurrentRequests(), default_fallbackIsolationSemaphoreMaxConcurrentRequests);
		this.metricsRollingStatisticalWindowInMilliseconds = getProperty(propertyPrefix, key, "metrics.rollingStats.timeInMilliseconds", builder.getMetricsRollingStatisticalWindowInMilliseconds(), default_metricsRollingStatisticalWindow);
		this.metricsRollingStatisticalWindowBuckets = getProperty(propertyPrefix, key, "metrics.rollingStats.numBuckets", builder.getMetricsRollingStatisticalWindowBuckets(), default_metricsRollingStatisticalWindowBuckets);
		this.metricsRollingPercentileEnabled = getProperty(propertyPrefix, key, "metrics.rollingPercentile.enabled", builder.getMetricsRollingPercentileEnabled(), default_metricsRollingPercentileEnabled);
		this.metricsRollingPercentileWindow = getProperty(propertyPrefix, key, "metrics.rollingPercentile.timeInMilliseconds", builder.getMetricsRollingPercentileWindowInMilliseconds(), default_metricsRollingPercentileWindow);
		this.metricsRollingPercentileWindowBuckets = getProperty(propertyPrefix, key, "metrics.rollingPercentile.numBuckets", builder.getMetricsRollingPercentileWindowBuckets(), default_metricsRollingPercentileWindowBuckets);
		this.metricsRollingPercentileBucketSize = getProperty(propertyPrefix, key, "metrics.rollingPercentile.bucketSize", builder.getMetricsRollingPercentileBucketSize(), default_metricsRollingPercentileBucketSize);
		this.metricsHealthSnapshotIntervalInMilliseconds = getProperty(propertyPrefix, key, "metrics.healthSnapshot.intervalInMilliseconds", builder.getMetricsHealthSnapshotIntervalInMilliseconds(), default_metricsHealthSnapshotIntervalInMilliseconds);
		this.requestCacheEnabled = getProperty(propertyPrefix, key, "requestCache.enabled", builder.getRequestCacheEnabled(), default_requestCacheEnabled);
		this.requestLogEnabled = getProperty(propertyPrefix, key, "requestLog.enabled", builder.getRequestLogEnabled(), default_requestLogEnabled);

		this.executionIsolationThreadPoolKeyOverride = asProperty(new DynamicStringProperty(propertyPrefix + ".command." + key.name() + ".threadPoolKeyOverride", null));
	}

	public static HystrixProperty<Boolean> getProperty(String propertyPrefix, HystrixCommandKey key, String instanceProperty,
	                                                   Boolean buildOverrideValue, Boolean defaultValue) {
		return asProperty(new BooleanProperty(
				new DynamicBooleanProperty(propertyPrefix + ".command." + key.name() + "." + instanceProperty, buildOverrideValue),
				new DynamicBooleanProperty(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
		));
	}


	public static HystrixProperty<Integer> getProperty(String propertyPrefix, HystrixCommandKey key, String instanceProperty,
	                                                   Integer builderOverrideValue, Integer defaultValue) {
		return asProperty(new IntegerProperty(
				new DynamicIntegerProperty(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue),
				new DynamicIntegerProperty(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
		));
	}

	private static HystrixProperty<String> getProperty(String propertyPrefix, HystrixCommandKey key, String instanceProperty,
	                                                                       String builderOverrideValue, String defaultValue) {
		return asProperty(new StringProperty(
				new DynamicStringProperty(propertyPrefix + ".command." + key.name() + "." + instanceProperty, builderOverrideValue),
				new DynamicStringProperty(propertyPrefix + ".command.default." + instanceProperty, defaultValue)
		));
	}

	private static HystrixProperty<ExecutionIsolationStrategy> getProperty(final String propertyPrefix, final HystrixCommandKey key, final String instanceProperty,
	                                                                       final ExecutionIsolationStrategy builderOverrideValue, final ExecutionIsolationStrategy defaultValue) {
		return new ExecutionIsolationStrategyHystrixProperty(builderOverrideValue, key, propertyPrefix, defaultValue, instanceProperty);
	}

	private static final class ExecutionIsolationStrategyHystrixProperty implements HystrixProperty<ExecutionIsolationStrategy> {

		private final StringProperty property;
		private volatile ExecutionIsolationStrategy value;
		private final ExecutionIsolationStrategy defaultValue;

		private ExecutionIsolationStrategyHystrixProperty(ExecutionIsolationStrategy builderOverrideValue, HystrixCommandKey key,
		                                                  String propertyPrefix, ExecutionIsolationStrategy defaultValue, String instanceProperty) {
			this.defaultValue = defaultValue;
			String overrideValue = null;
			if (builderOverrideValue != null) {
				overrideValue = builderOverrideValue.name();
			}
			property = new StringProperty(
					new DynamicStringProperty(propertyPrefix + ".command." + key.name() + "." + instanceProperty, overrideValue),
					new DynamicStringProperty(propertyPrefix + ".command.default." + instanceProperty, defaultValue.name())
			);

			parseProperty();

			property.addCallback(() -> parseProperty());
		}

		@Override
		public ExecutionIsolationStrategy get() {
			return null;
		}

		private void parseProperty() {
			try {
				value = ExecutionIsolationStrategy.valueOf(property.get());
			}
			catch (Exception e) {
				log.error("Unable to derive ExecutionIsolationStrategy from property value: " + property.get(), e);
				value = defaultValue;
			}
		}
	}

	public static Setter Setter() {
		return new Setter();
	}

	public static class Setter {

		private Boolean circuitBreakerEnabled = null;
		private Integer circuitBreakerErrorThresholdPercentage = null;
		private Boolean circuitBreakerForceClosed = null;
		private Boolean circuitBreakerForceOpen = null;
		private Integer circuitBreakerRequestVolumeThreshold = null;
		private Integer circuitBreakerSleepWindowInMilliseconds = null;
		private Integer executionIsolationSemaphoreMaxConcurrentRequests = null;
		private ExecutionIsolationStrategy executionIsolationStrategy = null;
		private Boolean executionIsolationThreadInterruptOnTimeout = null;
		private Integer executionIsolationThreadTimeoutInMilliseconds = null;
		private Integer fallbackIsolationSemaphoreMaxConcurrentRequests = null;
		private Integer metricsHealthSnapshotIntervalInMilliseconds = null;
		private Integer metricsRollingPercentileBucketSize = null;
		private Boolean metricsRollingPercentileEnabled = null;
		private Integer metricsRollingPercentileWindowInMilliseconds = null;
		private Integer metricsRollingPercentileWindowBuckets = null;
		/* null means it hasn't been overridden */
		private Integer metricsRollingStatisticalWindowInMilliseconds = null;
		private Integer metricsRollingStatisticalWindowBuckets = null;
		private Boolean requestCacheEnabled = null;
		private Boolean requestLogEnabled = null;

		private Setter() {
		}

		public Boolean getCircuitBreakerEnabled() {
			return circuitBreakerEnabled;
		}

		public Integer getCircuitBreakerErrorThresholdPercentage() {
			return circuitBreakerErrorThresholdPercentage;
		}

		public Boolean getCircuitBreakerForceClosed() {
			return circuitBreakerForceClosed;
		}

		public Boolean getCircuitBreakerForceOpen() {
			return circuitBreakerForceOpen;
		}

		public Integer getCircuitBreakerRequestVolumeThreshold() {
			return circuitBreakerRequestVolumeThreshold;
		}

		public Integer getCircuitBreakerSleepWindowInMilliseconds() {
			return circuitBreakerSleepWindowInMilliseconds;
		}

		public Integer getExecutionIsolationSemaphoreMaxConcurrentRequests() {
			return executionIsolationSemaphoreMaxConcurrentRequests;
		}

		public ExecutionIsolationStrategy getExecutionIsolationStrategy() {
			return executionIsolationStrategy;
		}

		public Boolean getExecutionIsolationThreadInterruptOnTimeout() {
			return executionIsolationThreadInterruptOnTimeout;
		}

		public Integer getExecutionIsolationThreadTimeoutInMilliseconds() {
			return executionIsolationThreadTimeoutInMilliseconds;
		}

		public Integer getFallbackIsolationSemaphoreMaxConcurrentRequests() {
			return fallbackIsolationSemaphoreMaxConcurrentRequests;
		}

		public Integer getMetricsHealthSnapshotIntervalInMilliseconds() {
			return metricsHealthSnapshotIntervalInMilliseconds;
		}

		public Integer getMetricsRollingPercentileBucketSize() {
			return metricsRollingPercentileBucketSize;
		}

		public Boolean getMetricsRollingPercentileEnabled() {
			return metricsRollingPercentileEnabled;
		}

		public Integer getMetricsRollingPercentileWindowInMilliseconds() {
			return metricsRollingPercentileWindowInMilliseconds;
		}

		public Integer getMetricsRollingPercentileWindowBuckets() {
			return metricsRollingPercentileWindowBuckets;
		}

		public Integer getMetricsRollingStatisticalWindowInMilliseconds() {
			return metricsRollingStatisticalWindowInMilliseconds;
		}

		public Integer getMetricsRollingStatisticalWindowBuckets() {
			return metricsRollingStatisticalWindowBuckets;
		}

		public Boolean getRequestCacheEnabled() {
			return requestCacheEnabled;
		}

		public Boolean getRequestLogEnabled() {
			return requestLogEnabled;
		}

		public Setter withCircuitBreakerEnabled(boolean value) {
			this.circuitBreakerEnabled = value;
			return this;
		}

		public Setter withCircuitBreakerErrorThresholdPercentage(int value) {
			this.circuitBreakerErrorThresholdPercentage = value;
			return this;
		}

		public Setter withCircuitBreakerForceClosed(boolean value) {
			this.circuitBreakerForceClosed = value;
			return this;
		}

		public Setter withCircuitBreakerForceOpen(boolean value) {
			this.circuitBreakerForceOpen = value;
			return this;
		}

		public Setter withCircuitBreakerRequestVolumeThreshold(int value) {
			this.circuitBreakerRequestVolumeThreshold = value;
			return this;
		}

		public Setter withCircuitBreakerSleepWindowInMilliseconds(int value) {
			this.circuitBreakerSleepWindowInMilliseconds = value;
			return this;
		}

		public Setter withExecutionIsolationSemaphoreMaxConcurrentRequests(int value) {
			this.executionIsolationSemaphoreMaxConcurrentRequests = value;
			return this;
		}

		public Setter withExecutionIsolationStrategy(ExecutionIsolationStrategy value) {
			this.executionIsolationStrategy = value;
			return this;
		}

		public Setter withExecutionIsolationThreadInterruptOnTimeout(boolean value) {
			this.executionIsolationThreadInterruptOnTimeout = value;
			return this;
		}

		public Setter withExecutionIsolationThreadTimeoutInMilliseconds(int value) {
			this.executionIsolationThreadTimeoutInMilliseconds = value;
			return this;
		}

		public Setter withFallbackIsolationSemaphoreMaxConcurrentRequests(int value) {
			this.fallbackIsolationSemaphoreMaxConcurrentRequests = value;
			return this;
		}

		public Setter withMetricsHealthSnapshotIntervalInMilliseconds(int value) {
			this.metricsHealthSnapshotIntervalInMilliseconds = value;
			return this;
		}

		public Setter withMetricsRollingPercentileBucketSize(int value) {
			this.metricsRollingPercentileBucketSize = value;
			return this;
		}

		public Setter withMetricsRollingPercentileEnabled(boolean value) {
			this.metricsRollingPercentileEnabled = value;
			return this;
		}

		public Setter withMetricsRollingPercentileWindowInMilliseconds(int value) {
			this.metricsRollingPercentileWindowInMilliseconds = value;
			return this;
		}

		public Setter withMetricsRollingPercentileWindowBuckets(int value) {
			this.metricsRollingPercentileWindowBuckets = value;
			return this;
		}

		public Setter withMetricsRollingStatisticalWindowInMilliseconds(int value) {
			this.metricsRollingStatisticalWindowInMilliseconds = value;
			return this;
		}

		public Setter withMetricsRollingStatisticalWindowBuckets(int value) {
			this.metricsRollingStatisticalWindowBuckets = value;
			return this;
		}

		public Setter withRequestCacheEnabled(boolean value) {
			this.requestCacheEnabled = value;
			return this;
		}

		public Setter withRequestLogEnabled(boolean value) {
			this.requestLogEnabled = value;
			return this;
		}

		/**
		 * Utility method for creating baseline properties for unit tests.
		 */
		/* package */
		static HystrixCommandProperties.Setter getUnitTestPropertiesSetter() {
			return new HystrixCommandProperties.Setter()
					.withExecutionIsolationThreadTimeoutInMilliseconds(1000)// when an execution will be timed out
					.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD) // we want thread execution by default in tests
					.withExecutionIsolationThreadInterruptOnTimeout(true)
					.withCircuitBreakerForceOpen(false) // we don't want short-circuiting by default
					.withCircuitBreakerErrorThresholdPercentage(40) // % of 'marks' that must be failed to trip the circuit
					.withMetricsRollingStatisticalWindowInMilliseconds(5000)// milliseconds back that will be tracked
					.withMetricsRollingStatisticalWindowBuckets(5) // buckets
					.withCircuitBreakerRequestVolumeThreshold(0) // in testing we will not have a threshold unless we're specifically testing that feature
					.withCircuitBreakerSleepWindowInMilliseconds(5000000) // milliseconds after tripping circuit before allowing retry (by default set VERY long as we want it to effectively never allow a singleTest for most unit tests)
					.withCircuitBreakerEnabled(true)
					.withRequestLogEnabled(true)
					.withExecutionIsolationSemaphoreMaxConcurrentRequests(20)
					.withFallbackIsolationSemaphoreMaxConcurrentRequests(10)
					.withCircuitBreakerForceClosed(false)
					.withMetricsRollingPercentileEnabled(true)
					.withRequestCacheEnabled(true)
					.withMetricsRollingPercentileWindowInMilliseconds(60000)
					.withMetricsRollingPercentileWindowBuckets(12)
					.withMetricsRollingPercentileBucketSize(1000)
					.withMetricsHealthSnapshotIntervalInMilliseconds(0);
		}

	}
}
