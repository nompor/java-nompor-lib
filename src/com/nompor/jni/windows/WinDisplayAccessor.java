package com.nompor.jni.windows;

import com.nompor.jni.JNIUtil;
import com.nompor.util.LibraryLogger;

public class WinDisplayAccessor{
	static{
		try{
			JNIUtil.loadLibraryFromJarFile(WinDisplayAccessor.class);
		}catch(Exception e){
			LibraryLogger.error(e);
		}
	}

	public static native long GetDisplayDevice(int dNum);
	public static native long GetCurrentDisplaySettings(long displayPtr);
	public static native long GetDisplaySettings(long displayPtr, int dNum);
	public static native void SetWidth(long settingsPtr, int width);
	public static native void SetHeight(long settingsPtr, int height);
	public static native int GetWidth(long settingsPtr);
	public static native int GetHeight(long settingsPtr);
	public static native int GetRefreshRate(long settingsPtr);
	public static native int GetBits(long settingsPtr);
	public static native boolean ChangeDisplaySettings(long displayPtr, long settingsPtr);
	public static native void ReleaseDisplayDevice(long displayPtr);
	public static native void ReleaseDisplaySettings(long settingsPtr);
}