package com.maple.msdialog.utils

import android.app.UiModeManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.annotation.ColorInt


/**
 * 仿 SystemBarStyle 实现
 * 开放了 detectDarkMode 方法
 */
class WbSystemBarStyle private constructor(
    val lightScrim: Int,
    val darkScrim: Int,
    val nightMode: Int,
    val detectDarkMode: (Resources) -> Boolean
) {

    fun toSystemBarStyle(): SystemBarStyle {
        return when (nightMode) {
            UiModeManager.MODE_NIGHT_NO -> SystemBarStyle.light(lightScrim, darkScrim)
            UiModeManager.MODE_NIGHT_YES -> SystemBarStyle.dark(lightScrim)
            else -> SystemBarStyle.auto(lightScrim, darkScrim, detectDarkMode)
        }
    }

    companion object {

        @JvmStatic
        @JvmOverloads
        fun auto(
            @ColorInt lightScrim: Int,
            @ColorInt darkScrim: Int,
            detectDarkMode: (Resources) -> Boolean = { resources ->
                (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
                        Configuration.UI_MODE_NIGHT_YES
            }
        ): WbSystemBarStyle {
            return WbSystemBarStyle(
                lightScrim = lightScrim,
                darkScrim = darkScrim,
                nightMode = UiModeManager.MODE_NIGHT_AUTO,
                detectDarkMode = detectDarkMode
            )
        }

        @JvmStatic
        fun dark(@ColorInt scrim: Int): WbSystemBarStyle {
            return WbSystemBarStyle(
                lightScrim = scrim,
                darkScrim = scrim,
                nightMode = UiModeManager.MODE_NIGHT_YES,
                detectDarkMode = { _ -> true }
            )
        }

        @JvmStatic
        fun light(@ColorInt scrim: Int, @ColorInt darkScrim: Int): WbSystemBarStyle {
            return WbSystemBarStyle(
                lightScrim = scrim,
                darkScrim = darkScrim,
                nightMode = UiModeManager.MODE_NIGHT_NO,
                detectDarkMode = { _ -> false }
            )
        }
    }

    internal fun getScrim(isDark: Boolean) = if (isDark) darkScrim else lightScrim

    internal fun getScrimWithEnforcedContrast(isDark: Boolean): Int {
        return when {
            nightMode == UiModeManager.MODE_NIGHT_AUTO -> Color.TRANSPARENT
            isDark -> darkScrim
            else -> lightScrim
        }
    }
}