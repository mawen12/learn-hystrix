package com.mawen.learn.hystrix;

import java.util.concurrent.Future;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public interface HystrixExecutable<R> {

	R execute();

	Future<R> queue();
}
