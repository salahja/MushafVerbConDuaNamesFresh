package com.example.mushafconsolidated.receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.TimeUnit;
import android.media.AudioAttributes;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener {
    private TelephonyManager telephoneManger;
    private PhoneStateListener phoneStateListener;
    public static final String BROADCAST_ACTION = "com.example.mushafconsolidated.Activity.sendseekbar";
    private boolean oneVersePlay;
    private List<String> paths;
    private List<QuranMetaEntity> ayat;
    public boolean stopFlag;
    private MediaPlayer mediaPlayer;
    int tracksingleayahduration=0;
    int musliclistsize;
    private BroadcastReceiver OutgoingBroadcastReceiver;
    Intent seekIntent;
    int mediaPosition;
    int mediaMax;
    public boolean isLooping, finished, bigNotification, previousFlag;

    int AudioPosition;
    Timer timer;
    List<String> ayaLocations = new ArrayList<>();
    int current;
    private ArrayList<String> audiolinks;
    public static boolean mediaPaused, screenOff;
    public static int surahNumber;
    public static int pageNumber;
    public int fromAya;
    private AppAudioManager appAudioManager;
    private ExAppAudioManager exappAudioManager;
    private int reader;
    private int AyaToPlay, suraOfAya;
    private String streamURL;
    Context context;
    private OnPlayStoppedListener playStoppedListener;
    private final Handler handler = new Handler();
    private static int songEnded;
    private String surahAudioPath;
    private List<QuranMetaEntity> verses;
    private long totoDuration;
    private ArrayList<String> totalDurationList;

    public MyService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
        seekIntent = new Intent(BROADCAST_ACTION);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.pause();
        context = this;
        //media player intents
        IntentFilter mediaPlayerFilter = new IntentFilter();
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.PLAY);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.PAUSE);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.BACK);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.FORWARD);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.STOP);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.REPEAT_ON);
        mediaPlayerFilter.addAction(AudioAppConstants.MediaPlayer.REPEAT_OFF);
        registerReceiver(MediaPlayerBroadcast, mediaPlayerFilter);

        //screen on / of guard key unlock flag
        IntentFilter screenIntentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        screenIntentFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenIntentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(MediaPlayerNotificationShow, screenIntentFilter);
        initOutgoingBroadcastReceiver();
    }

    private void initOutgoingBroadcastReceiver() {
        OutgoingBroadcastReceiver = new BroadcastReceiver() {

            private boolean isInCall, isFirstStart;

            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

                    isInCall = true;

                    if (isInCall == true) {
                        //    smallMediaPlayer = SmallMediaPlayer.getInstance(context);
                        bigNotification = false;
                        pauseMedia();
                    }

                }
            }

            private void pauseMedia() {
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        context.registerReceiver(OutgoingBroadcastReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            mediaPlayer.release();
        }


      //  audioManager.finished = false;
        unregisterReceiver(MediaPlayerBroadcast);
        unregisterReceiver(MediaPlayerNotificationShow);
        // Unregister headsetReceiver


        // Unregister seekbar receiver


        // Stop the seekbar handler from sending updates to UI


        // Service ends, need to tell activity to display "Play" button

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


     //   surahAudioPath = intent.getStringExtra(AudioAppConstants.MediaPlayer.FULLSURAPATH);

        fromAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.VERSE, -1);
        surahNumber = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURAH, -1);
        reader = intent.getIntExtra(AudioAppConstants.MediaPlayer.READER, -1);
        AyaToPlay = intent.getIntExtra(AudioAppConstants.MediaPlayer.ONE_VERSE, -1);
        suraOfAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURA, -1);
        streamURL = intent.getStringExtra(AudioAppConstants.MediaPlayer.STREAM_LINK);
        playStoppedListener = (OnPlayStoppedListener) QuranGrammarApplication.appContext;

        registerReceiver(broadcastReceiver, new IntentFilter(
                ShowMushafActivity.BROADCAST_SEEKBAR));
        setupHandler();
        try {
            prepareVersesToPlay();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return START_STICKY;

    }

    private void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
    }

    private boolean nextAyah = false;
    private long currenttrack;
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            // // Log.d(TAG, "entered sendUpdatesToUI");

            nextAyah = false;
            LogMediaPosition();




            handler.postDelayed(this, 500);


        }

        private void LogMediaPosition() {
            // // Log.d(TAG, "entered LogMediaPosition");
            int duration = mediaPlayer.getDuration();
            int currentPosition = mediaPlayer.getCurrentPosition();

    //  String durations=      totalDurationList.get(tracksingleayahduration);
             //  currenttrack =currenttrack+ Long.parseLong(durations);
          //  if(tracksingleayahduration<=totalDurationList.size()) {
          //      tracksingleayahduration++;
         //   }
            mediaMax = mediaPlayer.getDuration();
            if (mediaPlayer.isPlaying()) {
                seekIntent.putExtra("ayaplaying", String.valueOf(AudioPosition-1 ));

                seekIntent.putExtra("counter", String.valueOf(currentPosition));
                seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
                seekIntent.putExtra("song_ended", String.valueOf(songEnded));
                sendBroadcast(seekIntent);

/*
                mediaPosition = mediaPlayer.getCurrentPosition();
                // if (mediaPosition < 1) {
                // Toast.makeText(this, "Buffering...", Toast.LENGTH_SHORT).show();
                // }
                mediaMax = mediaPlayer.getDuration();
                //seekIntent.putExtra("time", new Date().toLocaleString());
                seekIntent.putExtra("counter", String.valueOf(verses.get(AudioPosition)));
                seekIntent.putExtra("counter", String.valueOf(mediaPosition));
                seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
                seekIntent.putExtra("song_ended", String.valueOf(songEnded));
                sendBroadcast(seekIntent);*/
            }

        }

    };
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSeekPos(intent);
        }

        public void updateSeekPos(Intent intent) {
            int seekPos = intent.getIntExtra("seekpos", 0);
            if (!mediaPlayer.isPlaying()) {
                handler.removeCallbacks(sendUpdatesToUI);
                mediaPlayer.seekTo(seekPos);
                setupHandler();
            }

        }
    };

    public synchronized void prepareVersesToPlay() throws IOException {
        Utils util = new Utils(QuranGrammarApplication.getContext());
        //get page ayat
        verses = util.getAyahsOfSura(surahNumber);

        //get current page user will play
        surahNumber = verses.get(0).getSurahIndex();
        int pageFirstAya = verses.get(0).getAyahInSurahIndex();

        //check if you call media player in stream or download



        //Create files locations for the all page ayas
        for (QuranMetaEntity ayaItem : verses) {
            ayaLocations.add(FileManager.createAyaAudioLinkLocation(MyService.this, reader, ayaItem.getAyahInSurahIndex(), ayaItem.getSurahIndex()));
        }
        getTotalDuration();
        musliclistsize=ayaLocations.size();
        int current = 0;
        //modify aya list to add basmala between suras
        List<QuranMetaEntity> ayatWithBasmala = new ArrayList<>();
        ayatWithBasmala.addAll(verses);
        int previousSura = verses.get(0).getSurahIndex(), counter = 0, positionCounter = -1;

        if (fromAya == -1) {
            //loop to add basmala between suras
            for (QuranMetaEntity ayaItem : verses) {
                counter++;
                if (ayaItem.getSurahIndex() != previousSura) {
                    //  ayatWithBasmala.add(counter + positionCounter, new Aya(1, 1, 1));
                    ayaLocations.add(counter + positionCounter, FileManager.createAyaAudioLinkLocation(MyService.this, ShowMushafActivity.readerID, 1, 1));
                    previousSura = ayaItem.getSurahIndex();
                    positionCounter++;
                }
            }


        }


        nextAudio();

 /*       if (!mediaPlayer.isPlaying()) {

            try {
                mediaPlayer.setDataSource(ayaLocations.get(current++));
                mediaPlayer.prepareAsync();
            //   timer = new Timer();

            } catch (Exception E) {


            }
        }*/


//  mediaPlayer.prepareAsync();


    }

    private void getTotalDuration() {


        // load data file
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
    totalDurationList=new ArrayList<>();
      for(int i=0;i<ayaLocations.size();i++) {

          metaRetriever.setDataSource(ayaLocations.get(i));

          String out = "";
          // get mp3 info

          // convert duration to minute:seconds
          String duration =
                  metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
          totalDurationList.add(duration);
          Log.v("time", duration);
          long dur = Long.parseLong(duration);
          String seconds = String.valueOf((dur % 60000) / 1000);

          Log.v("seconds", seconds);
          String minutes = String.valueOf(dur / 60000);
          out = minutes + ":" + seconds;
          if (seconds.length() == 1) {
              System.out.println("0" + minutes + ":0" + seconds);

          } else {
              System.out.println("0" + minutes + ":" + seconds);
          }
          Log.v("minutes", minutes);
          // close object

      }
        metaRetriever.release();
        for (String s : totalDurationList) {

        Long     d = Long.parseLong(s);
            totoDuration=totoDuration+d;
            System.out.println(s);
        }
        System.out.println(totoDuration);


    }


    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        if (!isLooping) {
            try {
                nextAudio();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (ShowMushafActivity.repeatCounter != 0) {
            mediaPlayer.start();
            ShowMushafActivity.repeatCounter--;
        } else {
            try {
                nextAudio();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ShowMushafActivity.repeatCounter = ShowMushafActivity.repeateValue;
        }
        if (mp.isPlaying()) {
            mp.stop();
        }
        playStoppedListener.onStopped();
        playStoppedListener.onPauseMlayer(mediaPlayer);
        stopSelf();
    }

    public synchronized void nextAudio() throws IOException {

        int size = ayaLocations.size();

    /*    try {
            ConnectivityManager connec;
            connec = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

            // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {*/

        // if connected with internet

        //reset media player
        mediaPlayer.reset();



    /*    seekIntent.putExtra("ayaplaying", String.valueOf(verses.get(AudioPosition).getAyahIndex()));

        seekIntent.putExtra("counter", String.valueOf(mediaPosition));
        seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
        seekIntent.putExtra("song_ended", String.valueOf(songEnded));
        sendBroadcast(seekIntent);*/

        //HighlightImageView.selectionFromTouch = false;


        if (size <= AudioPosition) {

            //stop thread
            finished = false;

            if (size != 1) {
                //send broadcast to the next page
                previousFlag = false;
                LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                                .putExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, 1));

                //set the flag of next page false
                ShowMushafActivity.nextPage = false;
            }


            return;

        }

        Log.e("AUDIO_TAG", "size next: " + size);
        Log.e("AUDIO_TAG", "pos next: " + AudioPosition);
        //send broadcast to highlight image view

        //      int pos= Integer.parseInt(ayaLocations.get(AudioPosition));


        //show information in large notification


        //check if stream or from path


        mediaPlayer.setDataSource(ayaLocations.get(AudioPosition));
        AudioPosition++;

        //play mediaPlayer in other thread

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        mediaPlayer.prepareAsync();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (!isLooping) {
                    try {
                        nextAudio();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (ShowMushafActivity.repeatCounter != 0) {
                    mediaPlayer.start();
                    ShowMushafActivity.repeatCounter--;
                } else {
                    try {
                        nextAudio();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    ShowMushafActivity.repeatCounter = ShowMushafActivity.repeateValue;
                }

            }
        });
        //isToastShowing = false;
          /*  } else if (
                    connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

              //  if (!isToastShowing) {
                   // Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
                  //  isToastShowing = true;
               // }


                pauseMedia();

            }*/

/*
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(AppAudioManager.class.getSimpleName(), "nextAudio error : " + e.getLocalizedMessage());
            //stop the media service and dismiss the notification

        }*/
    }


    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (!mediaPlayer.isPlaying()) {
            mp.start();
        }

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "no progressive", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "no progressive", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "no progressive", Toast.LENGTH_SHORT).show();
                break;


        }
        return false;
    }

    public BroadcastReceiver MediaPlayerBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(AudioAppConstants.MediaPlayer.PLAY)) {
                resumeMedia();
                //  mediaPlayer.res();
                mediaPaused = false;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.PAUSE)) {
                //  appAudioManager.pauseMedia();
                mediaPlayer.pause();
                mediaPaused = true;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.BACK)) {
                //  appAudioManager.previousAudio();
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.FORWARD)) {
                //    appAudioManager.nextAudio();
                try {

                    nextAudio();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.STOP)) {
                //  appAudioManager.stopFlag = true;
                stopFlag = true;
                stopMedia();
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.REPEAT_ON)) {
                //   appAudioManager.isLooping = true;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.REPEAT_OFF)) {
                //  appAudioManager.isLooping = false;
            }

        }

        public synchronized void resumeMedia() {

            Intent resumeMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
            resumeMedia.putExtra(AudioAppConstants.MediaPlayer.RESUME, false);
            LocalBroadcastManager.getInstance(context).sendBroadcast(resumeMedia);

            mediaPlayer.start();

        }

        private void stopMedia() {

            Intent stopMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
            stopMedia.putExtra(AudioAppConstants.MediaPlayer.STOP, true);
            LocalBroadcastManager.getInstance(context).sendBroadcast(stopMedia);
            //cancelMediaPlayerNotification();

            AppPreference.setSelectionVerse(null);
            finished = false;
            if (telephoneManger != null)
                telephoneManger.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            playStoppedListener.onStopped();
        }
    };

    /**
     * Broadcast for the media player notification view
     */
    public BroadcastReceiver MediaPlayerNotificationShow = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                appAudioManager.showMediaPlayerNotification();
                appAudioManager.bigNotification = true;
                screenOff = false;
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                appAudioManager.smallMediaPlayer = SmallMediaPlayer.getInstance(context);
                appAudioManager.bigNotification = false;
                screenOff = true;
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                appAudioManager.smallMediaPlayer = SmallMediaPlayer.getInstance(context);
                appAudioManager.bigNotification = false;
                screenOff = true;
            }
        }
    };

    public void pause() {
        mediaPlayer.pause();
      //  audioManager.pauseMedia();
        mediaPaused = true;
    }
}