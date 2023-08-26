package org.sj.conjugator.utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class VerbApplication extends Application {
    private static Context appContext;

// --Commented out by Inspection START (20/08/23, 8:37 am):
//    public static Context getContext() {
//        return appContext;
//    }
// --Commented out by Inspection STOP (20/08/23, 8:37 am)

    public void onCreate() {
        super.onCreate();
        if (appContext == null) {
            appContext = this;
        }
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //  String theme = sharedPreferences.getString("theme", 1);
        String themePref = sharedPreferences.getString("theme", "white");
        ThemeHelper.applyTheme(themePref);
    }

}