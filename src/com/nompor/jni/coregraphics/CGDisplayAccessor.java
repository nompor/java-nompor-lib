package com.nompor.jni.coregraphics;

import com.nompor.jni.JNIUtil;
import com.nompor.util.LibraryLogger;

public class CGDisplayAccessor {
	static {
		try{
			JNIUtil.loadLibraryFromJarFile(CGDisplayAccessor.class);
		}catch(Exception e){
			LibraryLogger.error(e);
		}
	}
	public static native long CGMainDisplayID() ;
	public static native long CGDisplayCopyDisplayMode(long displayId) ;
	public static native long CGDisplayCopyAllDisplayModeArray(long displayId) ;
	public static native long[] CGDisplayGetDisplayModes(long arrayPtr) ;
	public static native int CGDisplayModeGetWidth(long modePtr) ;
	public static native int CGDisplayModeGetHeight(long modePtr) ;
	public static native int CGDisplayModeGetRefreshRate(long modePtr) ;
	public static native long CGDisplayModeGetIOFlags(long modePtr) ;
	public static native void CGDisplayModeRelease(long modePtr);
	public static native void CGDisplayModeArrayRelease(long arrayPtr);
	public static native void CGDisplaySetDisplayMode(long displayId, long modePtr);

}
