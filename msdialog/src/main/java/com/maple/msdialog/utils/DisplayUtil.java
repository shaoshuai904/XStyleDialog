package com.maple.msdialog.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * 手机屏幕显示工具类
 * 兼容 Android 11、12
 *
 * @author shaoshuai
 */
public class DisplayUtil {
    public static final int DEF_VALUE = 0;// 默认值

    // 获取App显示区 宽高
    public static int getAppDisplayWidth(Context context) {
        return getAppDisplayRect(context, DEF_VALUE).width();
    }

    public static int getAppDisplayWidth(Context context, int defValue) {
        return getAppDisplayRect(context, defValue).width();
    }

    public static int getAppDisplayHeight(Context context) {
        return getAppDisplayRect(context, DEF_VALUE).height();
    }

    public static int getAppDisplayHeight(Context context, int defValue) {
        return getAppDisplayRect(context, defValue).height();
    }

    // 获取设备屏幕 宽高
    public static int getDeviceScreenWidth(Context context) {
        return getDeviceScreenRect(context, DEF_VALUE).width();
    }

    public static int getDeviceScreenWidth(Context context, int defValue) {
        return getDeviceScreenRect(context, defValue).width();
    }

    public static int getDeviceScreenHeight(Context context) {
        return getDeviceScreenRect(context, DEF_VALUE).height();
    }

    public static int getDeviceScreenHeight(Context context, int defValue) {
        return getDeviceScreenRect(context, defValue).height();
    }

    @NonNull
    public static Size getAppDisplaySize(Context context, int defValue) {
        Size size = getAppDisplaySize(context);
        if (size == null)
            size = new Size(defValue, defValue);
        return size;
    }

    /**
     * 获得App可用的显示区域大小
     * 不包含 导航条 和 刘海屏 区域。
     */
    @Nullable
    public static Size getAppDisplaySize(Context context) {
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= 30) {
            final WindowMetrics metrics = wm.getCurrentWindowMetrics();
            Insets insets = metrics.getWindowInsets().getInsets(WindowInsets.Type.systemBars());
            int insetsWidth = insets.right + insets.left;
            int insetsHeight = insets.top + insets.bottom;
            // Legacy size that Display#getSize reports
            final Rect bounds = metrics.getBounds();
            Size size = new Size(bounds.width() - insetsWidth, bounds.height() - insetsHeight);
            Log.i("Display12", "new-app-size: " + size.getWidth() + " - " + size.getHeight());
            return size;
        } else {
            // 获得App可用的显示区域信息，表示显示器的大小减去任何系统装饰。
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 11 弃用 Display.getSize()、Display.getMetrics()
                display.getMetrics(dm);
                Log.i("Display12", "app-size: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Size(dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

    @NonNull
    public static Rect getAppDisplayRect(Context context, int defValue) {
        Rect rect = getAppDisplayRect(context);
        if (rect == null)
            rect = new Rect(defValue, defValue, defValue, defValue);
        return rect;
    }

    /**
     * 获得App可用的显示区域
     * 表示显示器的大小减去任何系统装饰，不包含 导航条 和 刘海屏 区域。
     */
    @Nullable
    public static Rect getAppDisplayRect(Context context) {
        // androidx.window.layout.WindowMetricsCalculatorCompat
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= 30) {
            final WindowMetrics metrics = wm.getCurrentWindowMetrics();
            Insets insets = metrics.getWindowInsets().getInsets(WindowInsets.Type.systemBars());
            Log.i("Display12", "所有系统栏: " + insets);
//            Insets insets1 = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout());
//            Log.i("Display12", "insets111: " + insets1);
            // Legacy size that Display#getSize reports
            final Rect bounds = metrics.getBounds();
            Rect rect = new Rect(
                    bounds.left + insets.left,
                    bounds.top + insets.top,
                    bounds.right - insets.right,
                    bounds.bottom - insets.bottom
            );
            Log.i("Display12", "new-app-rect: " + rect.width() + " - " + rect.height());
            return rect;
        } else {
            // 获得App可用的显示区域信息，表示显示器的大小减去任何系统装饰。
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 11 弃用 Display.getSize()、Display.getMetrics()
                display.getMetrics(dm);
                Log.i("Display12", "app-rect: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Rect(0, 0, dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

    @NonNull
    public static Rect getDeviceScreenRect(Context context, int defValue) {
        Rect rect = getDeviceScreenRect(context);
        if (rect == null)
            rect = new Rect(defValue, defValue, defValue, defValue);
        return rect;
    }

    /**
     * 获得设备屏幕大小，包含所有系统栏区域
     * 当系统模拟较小的显示器时，它可能小于物理尺寸。
     * <p>
     * Mi 11 : (0, 0 - 1440, 3200)
     * 1+ 9  : (0, 0 - 1080, 2400)
     */
    @Nullable
    public static Rect getDeviceScreenRect(Context context) {
        // androidx.window.layout.WindowMetricsCalculatorCompat
        WindowManager wm = getWindowManager(context);
        if (wm == null)
            return null;
        if (Build.VERSION.SDK_INT >= 30) {
            Rect rect = wm.getCurrentWindowMetrics().getBounds();
            Log.i("Display12", "new-device: " + rect.width() + " - " + rect.height());
            return rect;
        } else {
            Display display = wm.getDefaultDisplay();
            if (display != null) {
                DisplayMetrics dm = new DisplayMetrics();
                // Android 12 弃用 Display.getRealSize()、Display.getRealMetrics()
                display.getRealMetrics(dm);
                Log.i("Display12", "device: " + dm.widthPixels + "   " + dm.heightPixels);
                return new Rect(0, 0, dm.widthPixels, dm.heightPixels);
            }
        }
        return null;
    }

    /**
     * 获取屏幕密度
     */
    public static int getDensityDpi(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return context.getResources().getConfiguration().densityDpi;
        } else {
            // int metrics = context.getResources().getDisplayMetrics().densityDpi;
            WindowManager wm = getWindowManager(context);
            if (wm == null)
                return 0;
            Display display = wm.getDefaultDisplay();
            if (display == null)
                return 0;
            DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            return outMetrics.densityDpi;
        }
    }

    @Nullable
    public static WindowManager getWindowManager(Context context) {
        if (context == null)
            return null;
        if (context instanceof Activity) {
            return ((Activity) context).getWindowManager();
        } else {
            return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
    }

    public static int getStatusBarHeight(Context context) {
        return getStatusBarHeight(context, 0);
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context, int defValue) {
        if (context == null)
            return defValue;
        Resources resources = context.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return resources.getDimensionPixelOffset(resId);
        }
        return defValue;
    }

    public static int getNavigationBarHeight(Context context) {
        return getNavigationBarHeight(context, 0);
    }

    /**
     * 获取底部导航栏的高度
     */
    public static int getNavigationBarHeight(Context context, int defValue) {
        if (context == null) {
            return defValue;
        }
        int resId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return defValue;
    }

    public static void setStatusBarVisibility(@NonNull Window window, boolean isShow) {
        View decorView = window.getDecorView();
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, decorView);
        if (isShow) {
            controller.show(WindowInsetsCompat.Type.statusBars());
        } else {
            controller.hide(WindowInsetsCompat.Type.statusBars());
        }
    }

    public static boolean isStatusBarVisible(@NonNull Window window) {
        View decorView = window.getDecorView();
        WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(decorView);
        if (insets != null) {
            return insets.getInsets(WindowInsetsCompat.Type.statusBars()).top > 0;
        }
        return false;
    }

    public static void setNavBarVisibility(@NonNull Window window, boolean isShow) {
        View decorView = window.getDecorView();
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(window, decorView);
        if (isShow) {
            controller.show(WindowInsetsCompat.Type.navigationBars());
        } else {
            controller.hide(WindowInsetsCompat.Type.navigationBars());
        }
    }

    public static boolean isNavBarVisible(@NonNull Window window) {
        View decorView = window.getDecorView();
        WindowInsetsCompat insets = ViewCompat.getRootWindowInsets(decorView);
        if (insets != null) {
            boolean isShow = insets.isVisible(WindowInsetsCompat.Type.navigationBars());
            Log.e("Display12", "isShow : " + isShow);
            return isShow;
        } else {
            Log.e("Display12", "insets 为空： ");
            return isNavBarVisible(window.getContext());
        }
    }

    /**
     * 底部导航栏 是否显示
     */
    public static boolean isNavBarVisible(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return false;
        if (Build.VERSION.SDK_INT >= 30) {
            WindowMetrics windowMetrics = wm.getCurrentWindowMetrics();
            WindowInsets insets = windowMetrics.getWindowInsets();
            // 检查导航栏是否占用屏幕空间
            boolean hasNav = insets.isVisible(WindowInsets.Type.navigationBars());
            Log.i("Display12", "显示底导： " + hasNav);
            return hasNav;
        } else {
            Display display = wm.getDefaultDisplay();
            Point appSize = new Point();
            Point realSize = new Point();
            display.getSize(appSize); // 应用区域尺寸（不含导航栏）
            display.getRealSize(realSize); // 屏幕物理尺寸
            // 横屏 对比 宽度差异, 竖屏 对比 高度差异
            int diffY = isHengPing(context) ? (realSize.x - appSize.x) : (realSize.y - appSize.y);
            // 华为mate 30，不显示底导时差值为 105，但是 statusBarHeight = 104
            Log.i("Display12", " " + realSize + " vs " + appSize + ", 差值：" + diffY);
            return diffY > (getStatusBarHeight(context) + 1);
        }
    }

    /**
     * 当前是否是横屏
     */
    public static boolean isHengPing(Context context) {
        int curOrientation = context.getResources().getConfiguration().orientation; // 获取屏幕方向
        if (curOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;// 横屏
        } else if (curOrientation == Configuration.ORIENTATION_PORTRAIT) {
            return false;// 竖屏
        }
        return false;
    }


    public static int getHengPingRotation(Context context) {
        Display display = null;
        if (Build.VERSION.SDK_INT >= 30) {
            display = context.getDisplay();
        } else {
            display = getWindowManager(context).getDefaultDisplay();
        }
        if (display != null) {
            int rotation = display.getRotation();
            Log.e("Display12", "当前旋转角度: " + rotation);
            switch (rotation) {
                case Surface.ROTATION_0 -> {
                    return 0;
                }
                // 设备向左旋转（左侧横屏）
                case Surface.ROTATION_90 -> {
                    return 90;
                }
                case Surface.ROTATION_180 -> {
                    return 180;
                }
                // 设备向右旋转（右侧横屏）
                case Surface.ROTATION_270 -> {
                    return 270;
                }
            }
        }
        return -1;
    }

}
