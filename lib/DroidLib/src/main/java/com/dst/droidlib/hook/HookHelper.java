package com.dst.droidlib.hook;

import com.dst.droidlib.reflect.Reflect;
import com.dst.droidlib.reflect.ReflectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.IBinder;

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
}
