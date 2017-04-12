package com.dst.droidutil;

import com.dst.droidlib.hook.BinderInvocationStub;
import com.dst.droidlib.hook.HookHelper;
import com.dst.droidlib.hook.MethodInvocationProxy;
import com.dst.droidlib.hook.MethodInvocationStub;
import com.dst.droidlib.hook.MethodProxy;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.IInterface;

import java.lang.reflect.Method;

/**
 * Created by toor on 4/11/17.
 */

public class PackageManagerStub extends MethodInvocationProxy<MethodInvocationStub<IInterface>> {

    public PackageManagerStub(){
        super(new MethodInvocationStub<IInterface>(HookHelper.sPackageManager()));
    }

    @Override
    protected void onBindMethods() {
        super.onBindMethods();
        addMethodProxy(new MethodProxy() {
            @Override
            public String getMethodName() {
                return "getApplicationInfo";
            }

            @Override
            public Object call(Object who, Method method, Object... args) throws Throwable {
                ApplicationInfo applicationInfo = (ApplicationInfo) method.invoke(who, args);
                Bundle metaData = new Bundle();
                metaData.putString("com.cmcc", "fuckkkkkkkkk");
                metaData.putBoolean("aoe_log_disable", true);
                if (applicationInfo.metaData!=null){
                    applicationInfo.metaData.putAll(metaData);
                }else {
                    applicationInfo.metaData = metaData;
                }
                return applicationInfo;
            }
        });
    }

    @Override
    public boolean isEnvBad() {
        return getInvocationStub().getProxyInterface() != HookHelper.sPackageManager();
    }

    @Override
    public void inject() throws Throwable {
        final IInterface hookedPM = getInvocationStub().getProxyInterface();
        //ActivityThread.sPackageManager.set(hookedPM);
        HookHelper.setsPackageManager(hookedPM);

        BinderInvocationStub pmHookBinder = new BinderInvocationStub(getInvocationStub().getBaseInterface());
        pmHookBinder.copyMethodProxies(getInvocationStub());
        pmHookBinder.replaceService("package");
    }
}
