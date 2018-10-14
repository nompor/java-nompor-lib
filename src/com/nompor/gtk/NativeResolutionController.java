package com.nompor.gtk;

import com.nompor.jni.coregraphics.CGDisplayAccessor;
import com.nompor.jni.windows.WinDisplayAccessor;
import com.nompor.util.PlatformUtil;

public class NativeResolutionController {
	private static final ResolutionControllerBase ctrl;
	private static final long NULL = 0;
	private static boolean isChange = false;

	static {
		if ( PlatformUtil.isWindows() ) {
			ctrl = new WinResolutionController();
		} else if ( PlatformUtil.isMac() ) {
			ctrl = new MacResolutionController();
		} else {
			ctrl = null;
		}
	}

	public static boolean setDisplayMode(int width, int height) {
		if ( ctrl.setDisplayMode(width, height) ) {
			isChange = true;
			return true;
		}
		return false;
	}

	public static void defaultDisplayMode() {
		ctrl.defaultDisplayMode();
		isChange = false;
	}

	public static boolean isDisplayChangeSupported() {
		return ctrl != null && ctrl.isDisplayChangeSupported();
	}

	public static void close() {
		release();
	}

	public static void release() {
		ctrl.release();
	}

	public static boolean isResolutionChanged() {
		return isChange;
	}

	private static interface ResolutionControllerBase{
		boolean setDisplayMode(int width, int height);
		void defaultDisplayMode();
		boolean isDisplayChangeSupported();
		void release();
	}

	private static final class MacResolutionController implements ResolutionControllerBase{
		private final long displayId;
		private final long defaultDisplayPtr;
		private final long modeArray;
		private final long[] modes;

		private MacResolutionController(){
			displayId = CGDisplayAccessor.CGMainDisplayID();
			defaultDisplayPtr = CGDisplayAccessor.CGDisplayCopyDisplayMode(displayId);
			modeArray = CGDisplayAccessor.CGDisplayCopyAllDisplayModeArray(displayId);
			modes = CGDisplayAccessor.CGDisplayGetDisplayModes(modeArray);
		}

		@Override
		public boolean setDisplayMode(int width, int height) {
			long targetPtr = NULL;
			long absr = Integer.MAX_VALUE;
			for ( long ptr : modes ) {
				int modeW = CGDisplayAccessor.CGDisplayModeGetWidth(ptr);
				int modeH = CGDisplayAccessor.CGDisplayModeGetHeight(ptr);
				int modeR = CGDisplayAccessor.CGDisplayModeGetRefreshRate(ptr);
				if ( width == modeW && height == modeH && Math.abs(modeR - 60) < absr ) {
					targetPtr = ptr;
					absr = Math.abs(60 - modeR);
				}
			}

			if ( targetPtr != NULL ) {
				CGDisplayAccessor.CGDisplaySetDisplayMode(displayId, targetPtr);
				return true;
			}
			return false;
		}

		@Override
		public void defaultDisplayMode() {
			CGDisplayAccessor.CGDisplaySetDisplayMode(displayId, defaultDisplayPtr);
		}

		@Override
		public boolean isDisplayChangeSupported() {
			return true;
		}

		@Override
		public void release() {
			CGDisplayAccessor.CGDisplayModeRelease(defaultDisplayPtr);
			CGDisplayAccessor.CGDisplayModeArrayRelease(modeArray);
		}
	}
	private static final class WinResolutionController implements ResolutionControllerBase{

		private final long defaultPtr;

		private WinResolutionController(){
			defaultPtr = WinDisplayAccessor.GetCurrentDisplaySettings(NULL);
		}

		@Override
		public final boolean setDisplayMode(int width, int height) {
			long ptr = WinDisplayAccessor.GetCurrentDisplaySettings(NULL);
			WinDisplayAccessor.SetWidth(ptr, width);
			WinDisplayAccessor.SetHeight(ptr, height);
			boolean isSuccess = WinDisplayAccessor.ChangeDisplaySettings(NULL, ptr);
			WinDisplayAccessor.ReleaseDisplaySettings(ptr);
			return isSuccess;
		}

		@Override
		public final void defaultDisplayMode() {
			WinDisplayAccessor.ChangeDisplaySettings(NULL, defaultPtr);
		}

		@Override
		public final boolean isDisplayChangeSupported() {
			return true;
		}

		@Override
		public final void release() {
			WinDisplayAccessor.ReleaseDisplaySettings(defaultPtr);
		}
	}
}
