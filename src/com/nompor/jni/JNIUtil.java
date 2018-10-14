
package com.nompor.jni;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.UUID;

import com.nompor.util.LibraryLogger;
import com.nompor.util.PlatformUtil;

public class JNIUtil{
	public static void loadLibraryFromJarFile(URL url) {
		try {
			File f = File.createTempFile(UUID.randomUUID().toString(), "."+"dat");
			f.deleteOnExit();
			Files.write(f.toPath(), url.openStream().readAllBytes());
			System.load(f.getAbsolutePath());
		} catch (IOException e) {
			LibraryLogger.error(e);
			throw new UnsatisfiedLinkError();
		}
	}
	public static void loadLibraryFromJarFile(Class<?> cls) {
		String ext = null;
		if ( PlatformUtil.isWindows() ) {
			ext = "dll";
		} else if ( PlatformUtil.isMac() ) {
			ext = "dylib";
		} else if ( PlatformUtil.isLinux() ) {
			ext = "so";
		} else {
			throw new UnsatisfiedLinkError();
		}
		loadLibraryFromJarFile(cls.getResource(cls.getSimpleName()+"."+ext));
	}
}