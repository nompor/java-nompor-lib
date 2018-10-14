
package com.nompor.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;


/**
 * とりあえず後々修正するのも面倒なんでログ出力用にラップしとく。
 * @author nompor
 *
 */
public class LibraryLogger{
	private static Level level = Level.INFO;
	private static boolean isConsoleLog=true;
	private static boolean isFileLog=false;

	private static BiFunction<Level, String, String> printFmt = LibraryLogger::simpleFormat;
	private static BiFunction<Level, Throwable, String> errFmt = LibraryLogger::errorFormat;

	private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

	public enum Level{
		DEBUG,INFO,WARN,FATAL;
	}

	static {
		/*
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				//ファイルクローズ
			}
		});
		*/
	}

	public static void setDetailFormat(boolean isDetail) {
		if ( isDetail ) {
			printFmt = LibraryLogger::simpleFormat;
			errFmt = LibraryLogger::errorFormat;
		} else {
			printFmt = LibraryLogger::detailFormat;
			errFmt = LibraryLogger::detailErrorFormat;
		}
	}

	public static void setLevel(Level level) {
		LibraryLogger.level = level;
	}

	public static Level getLevel() {
		return LibraryLogger.getLevel();
	}

	public static void setConsoleLog(boolean isConsoleLog) {
		LibraryLogger.isConsoleLog = isConsoleLog;
	}

	public static boolean isConsoleLog() {
		return isConsoleLog;
	}

	public static void setFileLog(boolean isFileLog) {
		LibraryLogger.isFileLog = isFileLog;
	}

	public static boolean isFileLog() {
		return isFileLog;
	}

	public static void log(String msg) {
		_println(Level.DEBUG,msg);
	}
	public static void debug(String msg) {
		_println(Level.DEBUG,msg);
	}
	public static void debug(Throwable t) {
		_printThrowable(Level.DEBUG,t);
	}
	public static void print(String msg) {
		_print(Level.DEBUG,msg);
	}
	public static void println(String msg) {
		_println(Level.DEBUG,msg);
	}
	public static void info(String msg) {
		_println(Level.INFO,msg);
	}
	public static void info(Throwable t) {
		_printThrowable(Level.INFO,t);
	}
	public static void warn(String msg) {
		_errorln(Level.WARN,msg);
	}
	public static void warn(Throwable t) {
		_errorThrowable(Level.WARN,t);
	}
	public static void fatal(String msg) {
		_errorln(Level.FATAL,msg);
	}
	public static void fatal(Throwable t) {
		_errorThrowable(Level.FATAL,t);
	}
	public static void error(String msg) {
		_errorln(Level.WARN,msg);
	}
	public static void error(Throwable t) {
		_errorThrowable(Level.WARN,t);
	}


	private static String simpleFormat(Level level, String msg) {
		return msg;
	}
	private static String detailFormat(Level level, String msg) {
		StackTraceElement elem = Thread.currentThread().getStackTrace()[3];
		StringBuilder sb = new StringBuilder();
		sb.append(LocalDateTime.now().format(fmt));
		sb.append(' ');
		sb.append(String.valueOf(Thread.currentThread().getId()));
		sb.append(' ');
		sb.append(elem.getClassName()).append('.').append(elem.getMethodName());
		sb.append(' ');
		sb.append('[');
		sb.append(level.name());
		sb.append(']');
		sb.append(' ');
		sb.append(msg);
		return sb.toString();
	}
	private static String errorFormat(Level level, Throwable t) {
		StringBuilder sb = new StringBuilder();
		sb.append(t.toString());
		sb.append(System.lineSeparator());
		for (StackTraceElement trace : t.getStackTrace()) {
			sb.append('\t');
			sb.append(trace.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
	private static String detailErrorFormat(Level level, Throwable t) {
		StringBuilder sb = new StringBuilder();
		sb.append(LocalDateTime.now().format(fmt));
		sb.append(' ');
		sb.append(String.valueOf(Thread.currentThread().getId()));
		sb.append(' ');
		sb.append('[');
		sb.append(level.name());
		sb.append(']');
		sb.append(' ');
		sb.append(System.lineSeparator());
		sb.append(t.toString());
		sb.append(System.lineSeparator());
		for (StackTraceElement trace : t.getStackTrace()) {
			sb.append('\t');
			sb.append(trace.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public static void print(Level level, String msg) {
		_print(level, msg);
	}
	public static void println(Level level, String msg) {
		_println(level, msg);
	}
	public static void printThrowable(Level level, Throwable t) {
		_printThrowable(level, t);
	}
	public static void error(Level level, String msg) {
		_error(level, msg);
	}
	public static void errorln(Level level, String msg) {
		_errorln(level, msg);
	}
	public static void errorThrowable(Level level, Throwable t) {
		_errorThrowable(level, t);
	}


	private static void _error(Level level, String msg) {
		String s = printFmt.apply(level, msg);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.err.print(s);
			}
		}
	}
	private static void _errorln(Level level, String msg) {
		String s = printFmt.apply(level, msg);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.err.println(s);
			}
		}
	}
	private static void _errorThrowable(Level level, Throwable t) {
		String s = errFmt.apply(level, t);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.err.print(s);
			}
		}
	}
	private static void _print(Level level, String msg) {
		String s = printFmt.apply(level, msg);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.out.print(s);
			}
		}
	}
	private static void _println(Level level, String msg) {
		String s = printFmt.apply(level, msg);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.out.println(s);
			}
		}
	}
	private static void _printThrowable(Level level, Throwable t) {
		String s = errFmt.apply(level, t);
		if ( LibraryLogger.level.ordinal() <= level.ordinal() ) {
			if ( isConsoleLog ) {
				System.out.print(s);
			}
		}
	}
}