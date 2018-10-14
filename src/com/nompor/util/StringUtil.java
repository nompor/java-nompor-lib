package com.nompor.util;

public class StringUtil {
	public static String escapeRegex(String regex){
		return regex.replace("\\", "\\\\")
		.replace("*", "\\*")
		.replace("+", "\\+")
		.replace(".", "\\.")
		.replace("?", "\\?")
		.replace("{", "\\{")
		.replace("}", "\\}")
		.replace("(", "\\(")
		.replace(")", "\\)")
		.replace("[", "\\[")
		.replace("]", "\\]")
		.replace("^", "\\^")
		.replace("$", "\\$")
		.replace("-", "\\-")
		.replace("|", "\\|");
	}

	public static String escapeJson(String target){
		target = target.replace("\\", "\\\\");
		target = target.replace("\"", "\\\"");
		target = target.replace("/", "\\/");
		target = target.replace("\b", "\\b");
		target = target.replace("\f", "\\f");
		target = target.replace("\n", "\\n");
		target = target.replace("\r", "\\r");
		target = target.replace("\t", "\\t");
		return target;
	}

	public static String repeat(String s, int count) {
		StringBuilder sb =  new StringBuilder();
		for ( int i = 0;i < count;i++ ) {
			sb.append(s);
		}
		return sb.toString();
	}
}
