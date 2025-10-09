package com.maple.demo.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import com.maple.msdialog.utils.DisplayUtil

fun Activity.setContentViewAndSetWindowInsets(layoutResID: Int) {
    val rootView = LayoutInflater.from(this).inflate(layoutResID, null)
    setContentViewAndSetWindowInsets(rootView, null)
}

fun Activity.setContentViewAndSetWindowInsets(rootView: View) {
    setContentViewAndSetWindowInsets(rootView, null)
}

fun Activity.setContentViewAndSetWindowInsets(rootView: View, topBarView: View?, setWindowInsets: Boolean = true) {
    if (setWindowInsets) {
        activityEnableEdgeToEdge(this)
        setContentView(rootView)
        topBarView?.setPaddingTopWithStatusBar()
        rootView.updateWindowInsets()
    } else {
        setContentView(rootView)
    }
}

///**
// * 类似 BottomSheetDialog 从底部弹出的 Dialog 需要设置四周边距
// */
//fun Dialog.setContentViewAndSetWindowInsets(rootView: View, setWindowInsets: Boolean = true) {
//    if (setWindowInsets) {
//        window?.let {
//            WindowCompat.setDecorFitsSystemWindows(it, false)
//            windowEnableEdgeToEdge(it)
//        }
//        setContentView(rootView)
//        rootView.updateWindowInsets()
//    } else {
//        setContentView(rootView)
//    }
//}

/**
 * 启用 Activity 的边倒边特性
 */
fun activityEnableEdgeToEdge(
    activity: Activity,
    statusBarStyle: WbSystemBarStyle = WbSystemBarStyle.dark(Color.TRANSPARENT),
    navigationBarStyle: WbSystemBarStyle = WbSystemBarStyle.light(Color.RED, Color.GRAY)
) {
    if (activity is ComponentActivity) {
        activity.enableEdgeToEdge(statusBarStyle.toSystemBarStyle(), navigationBarStyle.toSystemBarStyle())
    } else {
        windowEnableEdgeToEdge(activity.window, statusBarStyle, navigationBarStyle)
    }
}


fun windowEnableEdgeToEdge(
    window: Window,
    statusBarStyle: WbSystemBarStyle = WbSystemBarStyle.light(Color.RED, Color.GRAY),
    navigationBarStyle: WbSystemBarStyle = WbSystemBarStyle.light(Color.RED, Color.GRAY)
) {
    val view = window.decorView
    val statusBarIsDark = statusBarStyle.detectDarkMode(view.resources)
    val navigationBarIsDark = navigationBarStyle.detectDarkMode(view.resources)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    if (Build.VERSION.SDK_INT >= 29) {
        window.statusBarColor = statusBarStyle.getScrimWithEnforcedContrast(statusBarIsDark)
        window.navigationBarColor = navigationBarStyle.getScrimWithEnforcedContrast(navigationBarIsDark)
        window.isStatusBarContrastEnforced = false
        window.isNavigationBarContrastEnforced = false //navigationBarStyle.nightMode == UiModeManager.MODE_NIGHT_AUTO
        WindowInsetsControllerCompat(window, view).run {
            isAppearanceLightStatusBars = !statusBarIsDark
            isAppearanceLightNavigationBars = !navigationBarIsDark
        }
    } else if (Build.VERSION.SDK_INT >= 26) {
        window.statusBarColor = statusBarStyle.getScrim(statusBarIsDark)
        window.navigationBarColor = navigationBarStyle.getScrim(navigationBarIsDark)
        WindowInsetsControllerCompat(window, view).run {
            isAppearanceLightStatusBars = !statusBarIsDark
            isAppearanceLightNavigationBars = !navigationBarIsDark
        }
    } else if (Build.VERSION.SDK_INT >= 23) {
        window.statusBarColor = statusBarStyle.getScrim(statusBarIsDark)
        window.navigationBarColor = navigationBarStyle.darkScrim
        WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = !statusBarIsDark
    } else if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    if (Build.VERSION.SDK_INT >= 30) {
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS
    } else if (Build.VERSION.SDK_INT >= 28) {
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
}

//inline
// 兼容 Android 15 系统栏内边距
fun View.updateWindowInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        Log.e("and15", "updateWindowInsets : $insets")
        v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            // topMargin = insets.top
            leftMargin = insets.left
            bottomMargin = insets.bottom
            rightMargin = insets.right
        }
        windowInsets
//        WindowInsetsCompat.CONSUMED
    }
}

// 设置 View 的 paddingTop 为 状态栏高度
fun View.setPaddingTopWithStatusBar() {
    setPadding(
        paddingLeft,
        DisplayUtil.getStatusBarHeight(context, 0),
        paddingRight,
        paddingBottom
    )
}