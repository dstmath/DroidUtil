package com.cmcc.ioredirect;

import android.os.Build;
import android.util.Log;

/**
 * VirtualApp Native Project
 */
public class IOHook {

	private static final String TAG = IOHook.class.getSimpleName();

	static {
		try {
			System.loadLibrary("iohook");
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public static String getRedirectedPath(String orgPath) {
		try {
			return nativeGetRedirectedPath(orgPath);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	public static String restoreRedirectedPath(String orgPath) {
		try {
			return nativeRestoreRedirectedPath(orgPath);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}

	public static void redirect(String orgPath, String newPath) {
		try {
			nativeRedirect(orgPath, newPath);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public static void hook() {
		try {
			nativeHook(Build.VERSION.SDK_INT);
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/*public static void hookNative() {
		try {
			nativeHookNative(openDexFileNative, VirtualRuntime.isArt());
		} catch (Throwable e) {
			VLog.e(TAG, VLog.getStackTraceString(e));
		}
	}*/

	public static void onKillProcess(int pid, int signal) {
		Log.e(TAG, "killProcess: pid = " + pid);
		if (pid == android.os.Process.myPid()) {
			Log.e("ggg", "ggg error");
		}
	}

	private static native String nativeRestoreRedirectedPath(String redirectedPath);

	private static native String nativeGetRedirectedPath(String orgPath);

	private static native void nativeRedirect(String orgPath, String newPath);

	private static native void nativeHook(int apiLevel);

	private static native void nativeHookNative(Object method, boolean isArt);
}
