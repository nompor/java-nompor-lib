//
//  CGDisplayAccessor.cpp
//  CGDisplayAccessor
//
//  Created by nompor on 2018/06/23.
//  Copyright © 2018年 nompor. All rights reserved.
//

#include "com_nompor_jni_coregraphics_CGDisplayAccessor.h"
#include <CoreGraphics/CoreGraphics.h>

JNIEXPORT jlong JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGMainDisplayID
(JNIEnv *, jclass){
    return CGMainDisplayID();
}

JNIEXPORT jlong JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayCopyDisplayMode
(JNIEnv *, jclass, jlong displayId){
    CGDisplayModeRef currentMode = CGDisplayCopyDisplayMode(static_cast<CGDirectDisplayID>(displayId));
    return reinterpret_cast<uintptr_t>(currentMode);
}


JNIEXPORT jint JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeGetWidth
(JNIEnv *, jclass, jlong modePtr){
    CGDisplayModeRef currentMode = reinterpret_cast<CGDisplayModeRef>(modePtr);
    return static_cast<jint>(CGDisplayModeGetWidth(currentMode)) ;
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeGetHeight
(JNIEnv *, jclass, jlong modePtr){
    CGDisplayModeRef currentMode = reinterpret_cast<CGDisplayModeRef>(modePtr);
    return static_cast<jint>(CGDisplayModeGetHeight(currentMode));
}

JNIEXPORT jint JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeGetRefreshRate
(JNIEnv *, jclass, jlong modePtr){
    CGDisplayModeRef currentMode = reinterpret_cast<CGDisplayModeRef>(modePtr);
    return static_cast<jint>(CGDisplayModeGetRefreshRate(currentMode));
}

JNIEXPORT jlong JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeGetIOFlags
(JNIEnv *, jclass, jlong modePtr){
    CGDisplayModeRef currentMode = reinterpret_cast<CGDisplayModeRef>(modePtr);
    return CGDisplayModeGetIOFlags(currentMode);
}

JNIEXPORT void JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeRelease
(JNIEnv *, jclass, jlong modePtr){
    CGDisplayModeRelease(reinterpret_cast<CGDisplayModeRef>(modePtr));
}

JNIEXPORT void JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplaySetDisplayMode
(JNIEnv *, jclass, jlong displayId, jlong modePtr){
    CGDisplaySetDisplayMode(static_cast<CGDirectDisplayID>(displayId), reinterpret_cast<CGDisplayModeRef>(modePtr), nullptr);
}

JNIEXPORT jlong JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayCopyAllDisplayModeArray
(JNIEnv *, jclass, jlong displayId){
    CFArrayRef arr = CGDisplayCopyAllDisplayModes(static_cast<CGDirectDisplayID>(displayId), NULL);
    return reinterpret_cast<uintptr_t>(arr);
}

JNIEXPORT jlongArray JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayGetDisplayModes
(JNIEnv * env, jclass, jlong arrayPtr){
    
    CFArrayRef arr = reinterpret_cast<CFArrayRef>(arrayPtr);
    jint len = static_cast<jint>(CFArrayGetCount(arr));
    jlongArray result = env->NewLongArray(len);
    jlong* ptr = env->GetLongArrayElements(result, 0);
    for ( int i = 0;i < len;i++ ) {
        jlong modePtr = reinterpret_cast<uintptr_t>(CFArrayGetValueAtIndex(arr, i));
        ptr[i] = modePtr;
    }
    env->ReleaseLongArrayElements(result, ptr, 0);
    return result;
}


JNIEXPORT void JNICALL Java_com_nompor_jni_coregraphics_CGDisplayAccessor_CGDisplayModeArrayRelease
(JNIEnv *, jclass, jlong arrayPtr){
    CFRelease(reinterpret_cast<CFArrayRef>(arrayPtr));
}
