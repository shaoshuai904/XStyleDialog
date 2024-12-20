package com.maple.demo;

import android.app.Application;
import android.util.Log;

import com.maple.demo.jacoco.JacocoHelper;

public class MsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }


//OnTrimMemory的参数是一个int数值，代表不同的内存状态：
//TRIM_MEMORY_COMPLETE：内存不足，并且该进程在后台进程列表最后一个，马上就要被清理
//TRIM_MEMORY_MODERATE：内存不足，并且该进程在后台进程列表的中部。
//TRIM_MEMORY_BACKGROUND：内存不足，并且该进程是后台进程。
//TRIM_MEMORY_UI_HIDDEN：内存不足，并且该进程的UI已经不可见了。
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Log.d("Jacoco", "应用切到后台");
            JacocoHelper.generateEcFile(this, true);
        }
    }

}
