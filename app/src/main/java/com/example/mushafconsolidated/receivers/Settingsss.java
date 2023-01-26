package com.example.mushafconsolidated.receivers;


import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.DisplayMetrics;

import com.example.utility.QuranGrammarApplication;

public class Settingsss {

    /**
     * Function to check service running of not
     *
     * @param context      Application context
     * @param serviceClass Service class
     * @return Service running or not
     */
    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to check mobile connected to internet or not and connection type
     *
     * @param context Application context
     * @return Connection type
     */
    public static int checkInternetStatus(Context context) {


        ConnectivityManager connMgr =
                (ConnectivityManager) context.getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting()) {
            return 1;
        } else if (mobile.isConnectedOrConnecting()) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Function to convert english numbers to arabic
     * if mobile language arabic
     *
     * @param number Number to convert
     * @return Converted number
     */
    public static String ChangeNumbers(Context context, String number) {

        try {
            if (context.getResources().getConfiguration().locale.getDisplayLanguage().equals("العربية"))
                return number.replaceAll("0", "٠").replaceAll("1", "١")
                        .replaceAll("2", "٢").replaceAll("3", "٣")
                        .replaceAll("4", "٤").replaceAll("5", "٥")
                        .replaceAll("6", "٦").replaceAll("7", "٧")
                        .replaceAll("8", "٨").replaceAll("9", "٩");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }


    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = QuranGrammarApplication.getInstance().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
