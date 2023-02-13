package com.example.mushafconsolidated.receivers;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.List;

public class DownloadService extends Service {
    private DownloadManager downloadManager ;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppPreference.Downloading(true);

        Bundle extras = intent.getExtras();
        String downloadURL = extras.getString(AudioAppConstants.Download.DOWNLOAD_URL);
        String downloadLocation = extras.getString(AudioAppConstants.Download.DOWNLOAD_LOCATION);
        int type = extras.getInt(AudioAppConstants.Download.TYPE , -1);
        List<String> downloadLinks = extras.getStringArrayList(AudioAppConstants.Download.DOWNLOAD_LINKS);


        if(downloadLinks == null){
            downloadManager = new DownloadManager(this, true ,type);
            downloadManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, downloadURL, downloadLocation);
        } else{
            downloadManager = new DownloadManager(this, true, downloadLinks ,type);
            downloadManager.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "", downloadLocation);
        }

        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (downloadManager != null) {
            downloadManager.stopDownload = true;
        }
        AppPreference.Downloading(false);
    }

}
