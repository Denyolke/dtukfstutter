package com.example.stutter.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private static final String PREFS_NAME = "stutter_prefs";
    private static final String KEY_DARK   = "dark_mode";

    /** Call once in Application.onCreate() or MainActivity.onCreate() before setContentView. */
    public static void applyTheme(Context context) {
        boolean dark = isDarkMode(context);
        AppCompatDelegate.setDefaultNightMode(
                dark ? AppCompatDelegate.MODE_NIGHT_YES
                     : AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static boolean isDarkMode(Context context) {
        return prefs(context).getBoolean(KEY_DARK, false);
    }

    /** Toggle the theme and immediately apply it (activity will recreate). */
    public static void setDarkMode(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_DARK, enabled).apply();
        AppCompatDelegate.setDefaultNightMode(
                enabled ? AppCompatDelegate.MODE_NIGHT_YES
                        : AppCompatDelegate.MODE_NIGHT_NO);
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
