/* DO NOT EDIT THIS FILE - it is machine generated */
#include "jni.h"
/* Header for class com_nompor_jni_windows_WinDisplayAccessor */

#ifndef _Included_com_nompor_jni_windows_WinDisplayAccessor
#define _Included_com_nompor_jni_windows_WinDisplayAccessor
#ifdef __cplusplus
extern "C" {
#endif
	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetDisplayDevice
	* Signature: (I)J
	*/
	JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetDisplayDevice
		(JNIEnv *, jclass, jint);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetCurrentDisplaySettings
	* Signature: (J)J
	*/
	JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetCurrentDisplaySettings
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetDisplaySettings
	* Signature: (JI)J
	*/
	JNIEXPORT jlong JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetDisplaySettings
		(JNIEnv *, jclass, jlong, jint);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    SetWidth
	* Signature: (JI)V
	*/
	JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_SetWidth
		(JNIEnv *, jclass, jlong, jint);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    SetHeight
	* Signature: (JI)V
	*/
	JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_SetHeight
		(JNIEnv *, jclass, jlong, jint);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetWidth
	* Signature: (J)I
	*/
	JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetWidth
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetHeight
	* Signature: (J)I
	*/
	JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetHeight
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetRefreshRate
	* Signature: (J)I
	*/
	JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetRefreshRate
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    GetBits
	* Signature: (J)I
	*/
	JNIEXPORT jint JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_GetBits
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    ChangeDisplaySettings
	* Signature: (JJ)Z
	*/
	JNIEXPORT jboolean JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ChangeDisplaySettings
		(JNIEnv *, jclass, jlong, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    ReleaseDisplayDevice
	* Signature: (J)V
	*/
	JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ReleaseDisplayDevice
		(JNIEnv *, jclass, jlong);

	/*
	* Class:     com_nompor_jni_windows_WinDisplayAccessor
	* Method:    ReleaseDisplaySettings
	* Signature: (J)V
	*/
	JNIEXPORT void JNICALL Java_com_nompor_jni_windows_WinDisplayAccessor_ReleaseDisplaySettings
		(JNIEnv *, jclass, jlong);

#ifdef __cplusplus
}
#endif
#endif
