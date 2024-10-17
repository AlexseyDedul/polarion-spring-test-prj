package com.garantis.polarion.extension.license.analyzer.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PolarionUtils {
	@NotNull
	public static String getPolarionHomePath() {
		return (String)Objects.requireNonNull(System.getProperty("com.polarion.home", null));
	}
	
	@NotNull
	public static String getPolarionPathToDataFolder() {
		return (String)Objects.requireNonNull(System.getProperty("com.polarion.data", null));
	}
	
	private PolarionUtils() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
}
