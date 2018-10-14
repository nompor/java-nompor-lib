package com.nompor.util;

public class PlatformUtil {
	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	public static boolean isWindows() {
		return OS_NAME.contains("windows");
	}

	public static boolean isMac() {
		return OS_NAME.contains("mac");
	}

	public static boolean isLinux() {
		return OS_NAME.contains("linux");
	}

	public static boolean isSolaris() {
		return OS_NAME.contains("sunos");
	}
}
