package com.example.mushafconsolidated.receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MyServicebroadcast extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener {

    public static final String BROADCAST_ACTION = "com.example.mushafconsolidated.Activity.sendseekbar";
    private  boolean oneVersePlay;
    private  List<String> paths;
    private  List<QuranMetaEntity> ayat;
    private MediaPlayer mediaPlayer;
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

    public MyServicebroadcast() {
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
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }

            mediaPlayer.release();
        }



        // Unregister headsetReceiver


        // Unregister seekbar receiver
        unregisterReceiver(broadcastReceiver);

        // Stop the seekbar handler from sending updates to UI
        handler.removeCallbacks(sendUpdatesToUI);

        // Service ends, need to tell activity to display "Play" button

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        surahAudioPath=       intent.getStringExtra(AudioAppConstants.MediaPlayer.FULLSURAPATH);
        registerReceiver(broadcastReceiver, new IntentFilter(
                ShowMushafActivity.BROADCAST_SEEKBAR));
        fromAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.VERSE, -1);
        surahNumber = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURAH, -1);
        reader = intent.getIntExtra(AudioAppConstants.MediaPlayer.READER, -1);
        AyaToPlay = intent.getIntExtra(AudioAppConstants.MediaPlayer.ONE_VERSE, -1);
        suraOfAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURA, -1);
        streamURL = intent.getStringExtra(AudioAppConstants.MediaPlayer.STREAM_LINK);
        playStoppedListener=(OnPlayStoppedListener) QuranGrammarApplication.appContext;
        if(surahAudioPath==null) {
            registerReceiver(broadcastReceiver, new IntentFilter(
                    ShowMushafActivity.BROADCAST_SEEKBAR));
            setupHandler();
        }
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


    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            // // Log.d(TAG, "entered sendUpdatesToUI");

            LogMediaPosition();

/*

            Uri uri = Uri.parse(ayaLocations.get(AudioPosition));
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(context,uri);
            String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            int millSecond = Integer.parseInt(durationStr);
*/


            handler.postDelayed(this, 3000); // 2 seconds

        }

        private void LogMediaPosition() {
            // // Log.d(TAG, "entered LogMediaPosition");
            if (mediaPlayer.isPlaying()) {
                mediaPosition = mediaPlayer.getCurrentPosition();
                // if (mediaPosition < 1) {
                // Toast.makeText(this, "Buffering...", Toast.LENGTH_SHORT).show();
                // }
                mediaMax = mediaPlayer.getDuration();
                //seekIntent.putExtra("time", new Date().toLocaleString());
                seekIntent.putExtra("counter", String.valueOf(mediaPosition));
                seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
                seekIntent.putExtra("song_ended", String.valueOf(songEnded));
                sendBroadcast(seekIntent);
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
            if (mediaPlayer.isPlaying()) {
                handler.removeCallbacks(sendUpdatesToUI);
                mediaPlayer.seekTo(seekPos);
                setupHandler();
            }

        }
    };
    public synchronized void prepareVersesToPlay() throws IOException {
        Utils util = new Utils(QuranGrammarApplication.getContext());
        //get page ayat
        List<QuranMetaEntity> verses = util.getAyahsOfSura(surahNumber);

        //get current page user will play
        surahNumber = verses.get(0).getSurahIndex();
        int pageFirstAya = verses.get(0).getAyahInSurahIndex();

        //check if you call media player in stream or download



        //Create files locations for the all page ayas
        for (QuranMetaEntity ayaItem : verses) {
            ayaLocations.add(FileManager.createAyaAudioLinkLocation(MyServicebroadcast.this, reader, ayaItem.getAyahInSurahIndex(), ayaItem.getSurahIndex()));
        }
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
                    ayaLocations.add(counter + positionCounter, FileManager.createAyaAudioLinkLocation(MyServicebroadcast.this, ShowMushafActivity.readerID, 1, 1));
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

                //HighlightImageView.selectionFromTouch = false;
                AudioPosition++;
                if (size<= AudioPosition) {

                    //stop thread
                    finished = false;

                    if(size != 1){
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
        if(!mediaPlayer.isPlaying()){
 mp.start();
        }

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
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
}