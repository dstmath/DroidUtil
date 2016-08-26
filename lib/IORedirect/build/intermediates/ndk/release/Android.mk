LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := iohook
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/hook/Hook.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/hook/HookNative.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/Hooker.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/ARM.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/Thumb.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/hook.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/Debug.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/util.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/x86.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/x86_64.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/MSHook/PosixMemory.cpp \
	/home/toor/code/DroidUtil/lib/IORedirect/src/main/jni/core.cpp \

LOCAL_C_INCLUDES += /home/toor/code/DroidUtil/lib/IORedirect/src/main/jni
LOCAL_C_INCLUDES += /home/toor/code/DroidUtil/lib/IORedirect/src/release/jni

include $(BUILD_SHARED_LIBRARY)
