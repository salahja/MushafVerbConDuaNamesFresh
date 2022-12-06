package mm.prayer.muslimmate.ui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import java.util.Locale;

public class ApplicationContext extends Application {

    private static Context appContext;

    public static Context getContext() {
        return appContext;
    }

    public static Context getInstance() {
        //Check application language
        Locale locale;
        Configuration config = new Configuration();
        appContext.getResources().updateConfiguration(config, appContext.getResources().getDisplayMetrics());
        return appContext;
    }

    public void onCreate() {
        super.onCreate();

        if (appContext == null) {
            appContext = this;
        }
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        //  String theme = sharedPreferences.getString("theme", 1);
        String themePref = sharedPreferences.getString("themepref", "white");
      //  ThemeHelper.applyTheme(themePref);
    }

}
