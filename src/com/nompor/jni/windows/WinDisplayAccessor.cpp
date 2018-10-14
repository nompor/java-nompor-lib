
#include <windows.h>
#include "com_nompor_jni_windows_WinDisplayAccessor.h"

JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetDisplayDevice
(JNIEnv *, jclass, jint dNum) {
	DISPLAY_DEVICE* dd = new DISPLAY_DEVICE();
	dd->cb = sizeof(*dd);
	EnumDisplayDevices(nullptr, dNum, dd, 0);
	return reinterpret_cast<uintptr_t>(dd);
}

JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetCurrentDisplaySettings
(JNIEnv * env, jclass cls, jlong devicePtr) {
	return Java_com_nompor_jni_windows_WinDisplayAccessor_GetDisplaySettings(env, cls, devicePtr, ENUM_CURRENT_SETTINGS);
}

JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetDisplaySettings
(JNIEnv * env, jclass, jlong devicePtr, jint dNum) {
	DISPLAY_DEVICE* dd = reinterpret_cast<DISPLAY_DEVICE*>(devicePtr);
	DEVMODE* dv = new DEVMODE();
	dv->dmSize = sizeof(*dv);
	if (!EnumDisplaySettings(dd == nullptr ? nullptr : dd->DeviceName, dNum, dv)) {
		delete dv;
		dv = nullptr;
	}
	return reinterpret_cast<uintptr_t>(dv);
}

JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_SetWidth
(JNIEnv *, jclass, jlong settingsPtr, jint width) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	dv->dmPelsWidth = width;
}


JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_SetHeight
(JNIEnv *, jclass, jlong settingsPtr, jint height) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	dv->dmPelsHeight = height;
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetWidth
(JNIEnv *, jclass, jlong settingsPtr) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	return dv->dmPelsWidth;
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetHeight
(JNIEnv *, jclass, jlong settingsPtr) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	return dv->dmPelsHeight;
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetRefreshRate
(JNIEnv *, jclass, jlong settingsPtr) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	return dv->dmDisplayFrequency;
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetBits
(JNIEnv *, jclass, jlong settingsPtr) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	return dv->dmBitsPerPel;
}

JNIEXPORT jboolean JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ChangeDisplaySettings
(JNIEnv *, jclass, jlong displayPtr, jlong settingsPtr) {
	DISPLAY_DEVICE* dd = reinterpret_cast<DISPLAY_DEVICE*>(displayPtr);
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	return ChangeDisplaySettingsEx(dd == nullptr ? nullptr : dd->DeviceName, dv, nullptr, CDS_FULLSCREEN, nullptr) == DISP_CHANGE_SUCCESSFUL;
}

JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ReleaseDisplayDevice
(JNIEnv *, jclass, jlong displayPtr) {
	DISPLAY_DEVICE* dd = reinterpret_cast<DISPLAY_DEVICE*>(displayPtr);
	delete dd;
}

JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ReleaseDisplaySettings
(JNIEnv *, jclass, jlong settingsPtr) {
	DEVMODE* dv = reinterpret_cast<DEVMODE*>(settingsPtr);
	delete dv;
}
