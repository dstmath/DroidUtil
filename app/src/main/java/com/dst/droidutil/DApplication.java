package com.dst.droidutil;

import android.app.Application;

/**
 * Created by toor on 4/10/17.
 */

public class DApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            new NotificationManagerStub().inject();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
