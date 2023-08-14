package com.example.mushafconsolidated.receivers;

import static com.example.utility.QuranGrammarApplication.getContext;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.R;
import com.example.utility.QuranGrammarApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadManagerExecute  {


    final String CHANNEL_ID = "DOWNLOAD_SERVICE_CHANNEL_ID";
    final String CHANNEL_NAME = "DOWNLOAD_SERVICE_CHANNEL_NAME";
    final String CHANNEL_DESCRIPTION = "DOWNLOAD_SERVICE_CHANNEL_DESCRIPTION";


    public static final int DOWNLOAD_CHUNK_SIZE = 1024 * 3;
    private int downloadType = -1;
    // --Commented out by Inspection (09/08/23, 2:29 pm):private ProgressBar downloadProgressBar;
    private List<String> downloadLinks;
@SuppressLint("StaticFieldLeak")
public Context context,activityc;
    // --Commented out by Inspection (09/08/23, 2:28 pm):private TextView downloadInfo;
    private RemoteViews remoteViews;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private final boolean notificationDownloaderFlag;
    private int notificationDivider;
    private String fileExtension, fileName, filePath;
    private PendingIntent notificationPending;
    public boolean stopDownload;
    private boolean aboveLollipopFlag;
    private OkHttpClient mOkHttpClient;
    int flag=0;
    final String TAG="StopDownloading";
    private Intent openApplication;
    ExecutorService ex = Executors.newSingleThreadExecutor();
    private String downloadLocation;
    Context applicationContext;
    public DownloadManagerExecute(Context context, boolean notificationDownloaderFlag, List<String> downloadLinks, Context applicationContext) {

        Log.i("TAFSEER_DOWN_TAG" , "start download tafseer");
        this.context = context;
        this.activityc = applicationContext;
        this.downloadLinks = downloadLinks;
        this.downloadLocation=downloadLocation;
        this.notificationDownloaderFlag = notificationDownloaderFlag;
        this.applicationContext=applicationContext;

        init();
    }

    private void init() {
        openApplication = new Intent(context, ShowMushafActivity.class);
        notificationPending = PendingIntent.getActivity(context, 0,
                openApplication, PendingIntent.FLAG_IMMUTABLE);
        aboveLollipopFlag = true;

    }


    public DownloadManagerExecute(Context context, List<String> downloadLinks, String downloadLocation) {
        notificationDownloaderFlag = false;
        this.context=context;

        this.downloadLinks = downloadLinks;
    //    this.notificationDownloaderFlag = notificationDownloaderFlag;


    }

    public synchronized boolean  multiDownload(List<String> links, String downloadLocation) {


        if (notificationDownloaderFlag) showNotificationDownloader();
        int counter = 0;
        //       publishProgress(0L, Long.valueOf(links.size()));
        //foreach for the all links
        for (String linkItem : links) {

            //flag to stop download
            if (stopDownload) break;

            //update progress
        //    publishProgress(Long.valueOf(counter++), Long.valueOf(links.size()));

            //file name
            fileName = linkItem.substring(linkItem.lastIndexOf('/') + 1, linkItem.length());
            filePath = downloadLocation;
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

            //divided update notification
            notificationDivider = 0;

            //new http connection
            OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.MINUTES).
                    readTimeout(15, TimeUnit.MINUTES).build();

            Call call = httpClient.newCall(new Request.Builder().url(linkItem).get().build());
            try (Response response = call.execute()) {


                //susses request
                // if (response.code() == 200) {
                InputStream inputStream = null;
                OutputStream output = null;

                try {
                    //path response to input stream
                    inputStream = response.body().byteStream();
                    output = new FileOutputStream(filePath + "/" + fileName);
                    byte[] buffer = new byte[DOWNLOAD_CHUNK_SIZE];
                    //set progress zero and response length

                    while (true) {

                        //flag to stop download
                        if (stopDownload) {

                            Log.e(TAG, "cancel her: ");
                            flag = 1;
                            break;
                        }

                        notificationDivider++;
                        //read buffer
                        int read = inputStream.read(buffer);
                        if (read == -1) break;
                  /*      if (isCancelled()) {
                            flag = 1;


                        }*/
                        output.write(buffer, 0, read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    if (inputStream != null) inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return true;

    }


    /**
     * Init notification channels for android 8.0 and higher
     */
    private String createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(CHANNEL_DESCRIPTION);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.setShowBadge(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        return CHANNEL_ID;
    }



    /**
     * Initialize and show notification of download statue
     */
    public void showNotificationDownloader() {


        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_download_progress);
        String channelID = createNotificationChannel(context);
        builder = new NotificationCompat.Builder(context, channelID);
        builder.setSmallIcon(aboveLollipopFlag ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setColor(Color.parseColor("#3E686A"))
                .setProgress(100, 0, false)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.download)+"")
                .setOngoing(true);
        builder.setContentIntent(notificationPending);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Initialize and show notification of download completes
     */
    @SuppressLint("RemoteViewLayout")
    public void showCompleteNotification() {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_download_finished);
        String channelID = createNotificationChannel(context);
        builder = new NotificationCompat.Builder(context, channelID);
        builder.setSmallIcon(aboveLollipopFlag ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.download_complete))
                .setColor(Color.parseColor("#3E686A"));
        builder.setContentIntent(notificationPending);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Initialize and show notification of download failed
     */
    @SuppressLint("RemoteViewLayout")
    public void showFailedNotification() {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_download_failed);
        String channelID = createNotificationChannel(context);
        builder = new NotificationCompat.Builder(context, channelID);
        builder.setSmallIcon(aboveLollipopFlag ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.download_failed))
                .setColor(Color.parseColor("#3E686A"));
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
    /**
     * Initialize and show notification of download canceled
     */
    @SuppressLint("RemoteViewLayout")
    public void showcancelNotification() {
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_download_failed);
        String channelID = createNotificationChannel(context);
        builder = new NotificationCompat.Builder(context, channelID);
        builder.setSmallIcon(aboveLollipopFlag ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("canceled")
                .setColor(Color.parseColor("#3E686A"));
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    public void start() {

        AlertDialog.Builder builder = new AlertDialog.Builder(QuranGrammarApplication.getContext());
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();

        ex.execute(new Runnable() {


            public void run() {
    /*            ((AppCompatActivity)   context).  runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        dialog.show();

                    }
                });*/

           new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        dialog.show();
                    }
                });
                //do inbackground

                try {
                    if (downloadLinks != null) {
                        multiDownload(downloadLinks, downloadLocation);
                    } else {
                        //      return singleDownload(url[0], url[1]);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(DownloadManager.class.getSimpleName(), "e : " + e.getLocalizedMessage());
                }

            }
        });




    }
}
