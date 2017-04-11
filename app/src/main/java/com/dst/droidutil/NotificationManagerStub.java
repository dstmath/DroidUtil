package com.dst.droidutil;


import com.dst.droidlib.hook.BinderInvocationProxy;
import com.dst.droidlib.hook.MethodProxy;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

public class NotificationManagerStub extends BinderInvocationProxy {

    public NotificationManagerStub() {
        super("android.app.INotificationManager$Stub", Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        addMethodProxy(new MethodProxy() {
            @Override
            public String getMethodName() {
                return "enqueueToast";
            }

            @Override
            public Object call(Object who, Method method, Object... args) throws Throwable {
                Log.e("ggggg", "gggg method name = " + method.getName());
                args[0] = "android";
                return super.call(who, method, args);
            }
        });

        addMethodProxy(new MethodProxy() {
            @Override
            public String getMethodName() {
                return "enqueueToastEx";
            }

            @Override
            public Object call(Object who, Method method, Object... args) throws Throwable {
                Log.e("ggggg", "gggg method name = " + method.getName());
                args[0] = "android";
                return super.call(who, method, args);
            }
        });
    }
}
