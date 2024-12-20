package com.maple.demo.jacoco;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JacocoHelper {
    private static final String TAG = "Jacoco";

    /**
     * 生成ec文件
     *
     * @param isNew 是否重新创建ec文件
     */
    public static void generateEcFile(Context context, boolean isNew) {
        try {
            String mCoverageFilePath = getCoverageFilePath(context, isNew);
            generateEcFile(mCoverageFilePath);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    /**
     * 获取 coverage.ec 文件目录
     */
    public static String getCoverageFilePath(Context context, boolean isNew) throws IOException {
        // ec文件的路径：/storage/emulated/0/Android/data/com.maple.ishare/files
        String coverageFilePath = context.getExternalFilesDir("") + "/coverage.ec";
        File mCoverageFile = new File(coverageFilePath);
        if (isNew && mCoverageFile.exists()) {
            Log.d(TAG, "清除旧的ec文件");
            mCoverageFile.delete();
        }
        if (!mCoverageFile.exists()) {
            Log.d(TAG, "新建ec文件：" + mCoverageFile);
            mCoverageFile.createNewFile();
        }
        return mCoverageFile.getPath();
    }

    /**
     * 生成ec文件
     * 通过反射执行 rg.jacoco.agent.rt.RT.getAgent().getExecutionData(false)
     * 获取运行时数据，并保存到当前设备中。
     */
    public static void generateEcFile(String coverageFilePath) throws Exception {
        OutputStream out = null;
        try {
            out = new FileOutputStream(coverageFilePath, false);
            Object agent = Class.forName("org.jacoco.agent.rt.RT")
                    .getMethod("getAgent")
                    .invoke(null);
            if (agent != null) {
                out.write((byte[]) agent.getClass()
                        .getMethod("getExecutionData", boolean.class)
                        .invoke(agent, false));
                Log.d(TAG, "生成ec文件：" + coverageFilePath);
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
