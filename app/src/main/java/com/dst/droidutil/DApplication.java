package com.dst.droidutil;

import android.app.Application;
import android.content.Context;

/**
 * Created by toor on 4/10/17.
 */

public class DApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Class clazz = Class.forName("android.app.INotificationManager$Stub");
            new NotificationManagerStub(clazz, Context.NOTIFICATION_SERVICE).inject();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
