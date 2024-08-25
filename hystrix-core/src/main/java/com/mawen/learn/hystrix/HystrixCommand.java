package com.mawen.learn.hystrix;

import javax.annotation.concurrent.ThreadSafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
@ThreadSafe
public abstract class HystrixCommand<R> implements HystrixExecutable<R> {

	private static final Logger log = LoggerFactory.getLogger(HystrixCommand.class.getName());


}
