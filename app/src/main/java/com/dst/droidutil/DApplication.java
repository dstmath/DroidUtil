package com.dst.droidutil;

import com.dst.droidlib.hook.HookHelper;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by toor on 4/10/17.
 */

public class DApplication extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        android.support.multidex.MultiDex.install(this);
        try {
            new NotificationManagerStub().inject();
            new PackageManagerStub().inject();

            HookHelper.fixContext(base);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            String meta = getPackageManager().getApplicationInfo(getPackageName(), 0).metaData.getString("com.cmcc");
            Log.e("ggg", "ggg DApplication meta = " + meta);

            boolean aoi = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getBoolean("aoe_log_disable");
            Log.e("ggg", "ggg DApplication aoi log  = " + aoi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
