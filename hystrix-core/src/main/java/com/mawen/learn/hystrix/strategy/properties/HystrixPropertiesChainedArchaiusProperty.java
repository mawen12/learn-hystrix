package com.mawen.learn.hystrix.strategy.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.netflix.config.DynamicStringProperty;
import com.netflix.config.PropertyWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
@Slf4j
public abstract class HystrixPropertiesChainedArchaiusProperty {

	public static abstract class ChainLink<T> {

		private final AtomicReference<ChainLink<T>> pReference;
		private final ChainLink<T> next;
		private final List<Runnable> callbacks;

		public abstract String getName();

		public abstract T getValue();

		public abstract boolean isValueAcceptable();

		public ChainLink() {
			this.next = null;
			this.pReference = new AtomicReference<>(this);
			this.callbacks = new ArrayList<>();
		}

		public ChainLink(ChainLink<T> nextProperty) {
			this.next = nextProperty;
			this.pReference = new AtomicReference<>(next);
			this.callbacks = new ArrayList<>();
		}

		protected void checkAndFlip() {
			if (next == null) {
				pReference.set(this);
				return;
			}

			if (this.isValueAcceptable()) {
				log.info("Flipping property: {} to use it's current value: {}", getName(), getValue());
				pReference.set(this);
			}
			else {
				log.info("Flipping property: {} to use NEXT property: {}", getName(), next);
				pReference.set(next);
			}

			for (Runnable r : callbacks) {
				r.run();
			}
		}

		public T get() {
			if (pReference.get() == this) {
				return this.getValue();
			}
			else {
				return pReference.get().get();
			}
		}

		public void addCallback(Runnable r) {
			callbacks.add(r);
		}

		public String toString() {
			return getName() + " = " + get();
		}
	}

	public static class StringProperty extends ChainLink<String> {

		private final DynamicStringProperty sProp;

		public StringProperty(DynamicStringProperty property) {
			super();
			this.sProp = property;
		}

		public StringProperty(String name, DynamicStringProperty property) {
			this(name, new StringProperty(property));
		}

		public StringProperty(String name, StringProperty next) {
			this(new DynamicStringProperty(name, null), next);
		}

		public StringProperty(DynamicStringProperty sProperty, DynamicStringProperty next) {
			this(sProperty, new StringProperty(next));
		}

		public StringProperty(DynamicStringProperty sProperty, StringProperty next) {
			super(next);

			this.sProp = sProperty;
			this.sProp.addCallback(() -> {
				log.info("Property changed: {} = {}", getName(), getValue());
				checkAndFlip();
			});

			checkAndFlip();
		}

		@Override
		public String getName() {
			return sProp.getName();
		}

		@Override
		public String getValue() {
			return sProp.getValue();
		}

		@Override
		public boolean isValueAcceptable() {
			return sProp.get() != null;
		}
	}

	public static class IntegerProperty extends ChainLink<Integer> {

		private final DynamicIntegerProperty sProp;

		public IntegerProperty(DynamicIntegerProperty property) {
			super();
			this.sProp = property;
		}

		public IntegerProperty(String name, DynamicIntegerProperty property) {
			this(name, new IntegerProperty(property));
		}

		public IntegerProperty(String name, IntegerProperty next) {
			this(new DynamicIntegerProperty(name, null), next);
		}

		public IntegerProperty(DynamicIntegerProperty sProperty, DynamicIntegerProperty next) {
			this(sProperty, new IntegerProperty(next));
		}

		public IntegerProperty(DynamicIntegerProperty sProperty, IntegerProperty next) {
			super(next);

			this.sProp = sProperty;
			this.sProp.addCallback(() -> {
				log.info("Property changed: {} = {}", getName(), getValue());
				checkAndFlip();
			});

			checkAndFlip();
		}

		@Override
		public String getName() {
			return sProp.getName();
		}

		@Override
		public Integer getValue() {
			return sProp.getValue();
		}

		@Override
		public boolean isValueAcceptable() {
			return sProp.get() != null;
		}
	}

	public static class BooleanProperty extends ChainLink<Boolean> {

		private final DynamicBooleanProperty sProp;

		public BooleanProperty(DynamicBooleanProperty property) {
			super();
			this.sProp = property;
		}

		public BooleanProperty(String name, DynamicBooleanProperty property) {
			this(name, new BooleanProperty(property));
		}

		public BooleanProperty(String name, BooleanProperty next) {
			this(new DynamicBooleanProperty(name, null), next);
		}

		public BooleanProperty(DynamicBooleanProperty sProperty, DynamicBooleanProperty next) {
			this(sProperty, new BooleanProperty(next));
		}

		public BooleanProperty(DynamicBooleanProperty sProperty, BooleanProperty next) {
			super(next);

			this.sProp = sProperty;
			this.sProp.addCallback(() -> {
				log.info("Property changed: {} = {}", getName(), getValue());
				checkAndFlip();
			});

			checkAndFlip();
		}

		@Override
		public String getName() {
			return sProp.getName();
		}

		@Override
		public Boolean getValue() {
			return sProp.getValue();
		}

		@Override
		public boolean isValueAcceptable() {
			return sProp.get() != null;
		}
	}

	public static class DynamicBooleanProperty extends PropertyWrapper<Boolean> {

		public DynamicBooleanProperty(String propName, Boolean defaultValue) {
			super(propName, defaultValue);
		}

		public Boolean get() {
			return prop.getBoolean(defaultValue);
		}

		@Override
		public Boolean getValue() {
			return get();
		}
	}

	public static class DynamicIntegerProperty extends PropertyWrapper<Integer> {

		public DynamicIntegerProperty(String propName, Integer defaultValue) {
			super(propName, defaultValue);
		}

		public Integer get() {
			return prop.getInteger(defaultValue);
		}

		@Override
		public Integer getValue() {
			return get();
		}
	}

	public static class DynamicLongProperty extends PropertyWrapper<Long> {

		protected DynamicLongProperty(String propName, Long defaultValue) {
			super(propName, defaultValue);
		}

		public Long get() {
			return prop.getLong(defaultValue);
		}

		@Override
		public Long getValue() {
			return get();
		}
	}

	public static class DynamicStringProperty extends PropertyWrapper<String> {

		public DynamicStringProperty(String propName, String defaultValue) {
			super(propName, defaultValue);
		}

		public String get() {
			return prop.getString(defaultValue);
		}

		@Override
		public String getValue() {
			return get();
		}
	}

}
