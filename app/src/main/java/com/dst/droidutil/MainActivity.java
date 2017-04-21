package com.dst.droidutil;

import com.dst.droidlib.file.FastXmlSerializer;
import com.dst.droidlib.file.ProcFileReader;
import com.dst.droidlib.file.XmlUtils;
import com.dst.droidlib.hook.HookHelper;
import com.dst.droidlib.reflect.Reflect;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HookHelper.fixContext(this);
        try {
            String meta = getPackageManager().getApplicationInfo(getPackageName(), 0).metaData.getString("com.cmcc");
            Log.e("ggg", "ggg meta = " + meta);

            boolean aoi = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getBoolean("aoe_log_disable");
            Log.e("ggg", "ggg aoi log  = " + aoi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "hook NotificationManagerService", Toast.LENGTH_SHORT).show();
                    }
                }, 3 * 1000);
            }
        });

        ProcFileReader reader = null;
        try {
            reader = new ProcFileReader(new FileInputStream("/proc/" + Process.myPid() + "/maps"));
            while (reader.hasMoreData()) {
                String str = reader.nextString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        testReflect();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                testXmlWrite();
                testXmlRead();
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void testReflect() {
        Map<String, Reflect> map = Reflect.on(Activity.class).fields();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            Log.e("ggg", "ggg " + key);
            Reflect reflect = map.get(key);
            Log.e("ggg", "ggg " + reflect.get());
        }
    }

    private void testXmlWrite() {
        try {
            FileOutputStream fos = new FileOutputStream("/sdcard/test.xml");
            FastXmlSerializer serializer = new FastXmlSerializer();
            serializer.setOutput(fos, "utf-8");
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            serializer.startTag(null, "settings");

            serializer.startTag(null, "white_list");
            serializer.attribute(null, "name", "qq");
            serializer.attribute(null, "package", "com.tencent.mobileqq");
            serializer.endTag(null, "white_list");

            serializer.endTag(null, "settings");

            serializer.endDocument();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testXmlRead() {
        try {
            FileInputStream fis = new FileInputStream("/sdcard/test.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fis, null);

            XmlUtils.nextElement(parser);
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                Log.e("ggg", "ggg " + parser.getName() + ", " + parser.getAttributeCount());
                XmlUtils.nextElement(parser);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
}
