package com.dst.droidlib.test;

import com.dst.droidlib.stack.StackTrace;

/**
 * Created by toor on 10/28/16.
 */

public class TestMain {
    public static void main(String[] args) {
        StackTrace.Info info = StackTrace.getMethodInfo();
        System.out.println(info.toString());
    }
}
