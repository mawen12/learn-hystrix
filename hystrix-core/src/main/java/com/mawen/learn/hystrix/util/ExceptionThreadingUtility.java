package com.mawen.learn.hystrix.util;

import java.util.Arrays;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/8/21
 */
public class ExceptionThreadingUtility {

	private static final String messageForCause = "Calling Thread included as the last 'caused by on the chain.";

	private static ThreadLocal<Thread> callingThreadCache = new ThreadLocal<>();

	public static void assignCallingThread(Thread callingThread) {
		callingThreadCache.set(callingThread);
	}

	public static void attachCallingThreadStack(Throwable e) {
		try {
			if (callingThreadCache.get() != null) {
				attachCallingThreadStack(e, callingThreadCache.get().getStackTrace());
			}
		}
		catch (Throwable ex) {

		}
	}

	private static void attachCallingThreadStack(Throwable e, StackTraceElement[] stack) {
		Throwable callingThrowable = new Throwable(messageForCause);
		if (stack[0].toString().startsWith("java.lang.Thread.getStackTrace")) {
			StackTraceElement[] newStack = Arrays.copyOfRange(stack, 1, stack.length);
			stack = newStack;
		}
		callingThrowable.setStackTrace(stack);

		while (e.getCause() != null) {
			e = e.getCause();
		}

		if (!messageForCause.equals(e.getMessage())) {
			try {
				e.initCause(callingThrowable);
			}
			catch (Throwable t) {

			}
		}
	}

	private static String getStackTraceAsString(StackTraceElement[] stack) {
		StringBuilder sb = new StringBuilder();
		boolean firstLine = true;
		for (StackTraceElement e : stack) {
			if (e.toString().startsWith("java.lang.Thread.getStackTrace")) {
				continue;
			}

			if (!firstLine) {
				sb.append("\n\t");
			}
			sb.append(e.toString());
			firstLine = false;
		}

		return sb.toString();
	}
}
