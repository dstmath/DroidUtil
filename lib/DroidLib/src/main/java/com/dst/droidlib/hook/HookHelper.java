package com.dst.droidlib.hook;

import com.dst.droidlib.reflect.Reflect;
import com.dst.droidlib.reflect.ReflectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class HookHelper {
    private static Logger logger = LoggerFactory.getLogger("HookHelper");
    public static IBinder getService(String serviceName){
        IBinder binder = null;
        try {
            binder = Reflect.on("android.os.ServiceManager").call("getService", serviceName).get();
        }catch (ReflectException e){
            logger.error("error = {}", e.getMessage());
        }
        return binder;
    }

    public static Class<?> load(String clazz){
        Class<?> klass = null;
        try {
            klass = Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            logger.error("error = {}", e.getMessage());
        }

        return klass;
    }

    public static Map<String, IBinder> getsCache(){
        Map<String, IBinder> sCache = new HashMap<>();
        try {
            sCache = Reflect.on("android.os.ServiceManager").field("sCache").get();
        }catch (ReflectException e){
            logger.error("error = {}", e.getMessage());
        }
        return sCache;
    }

    public static Object getAcvtivityThread(){
        Object object = null;
        try {
            object = Reflect.on("android.app.ActivityThread").call("currentActivityThread").get();
        }catch (RuntimeException e){

        }
        return object;
    }

    public static IInterface sPackageManager(){
        Object object = getAcvtivityThread();
        IInterface iInterface = null;
        try {
            iInterface = Reflect.on(object).field("sPackageManager").get();
        }catch (ReflectException e){

        }
        return iInterface;
    }

    public static void setsPackageManager(IInterface iInterface){
        try {
            Reflect.on("android.app.ActivityThread").set("sPackageManager", iInterface);
        }catch (ReflectException e){

        }
    }

    /**
     *
      *  ContextImpl.getPackageManager()
     */
    public static void fixContext(Context context){
        try {
            context.getPackageName();
        } catch (Throwable e) {
            return;
        }

        int deep = 0;
        while (context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
            deep++;
            if (deep >= 10) {
                return;
            }
        }

//        ContextImpl.mPackageManager.set(context, null);
        try {
            Reflect.on(context).set("mPackageManager", null);
        }catch (ReflectException e){
            Log.e("ggg", "ggg error = " + e.getMessage());
        }
    }
}
