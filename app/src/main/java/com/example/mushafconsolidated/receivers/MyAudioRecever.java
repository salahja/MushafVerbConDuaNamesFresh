package com.example.mushafconsolidated.receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAudioRecever extends Service {
    public int AudioPosition, pageNumber;
    private Context context;
    private String streamURL;
    public MediaPlayer mediaPlayer;
    public boolean isLooping, finished, bigNotification, previousFlag;
    private BroadcastReceiver OutgoingBroadcastReceiver;
    private TelephonyManager telephoneManger;
    private PhoneStateListener phoneStateListener;
    private boolean isInCall, isFirstStart;
    private List<QuranMetaEntity> ayat;
    private List<String> paths;
    public SmallMediaPlayer smallMediaPlayer;
    public LargeMediaPlayer largeMediaPlayer;
    public boolean stopFlag, oneVersePlay;
    public static boolean mediaPaused, screenOff;
    public static int surahNumber;

    public int fromAya;
    private int currentAyaAtAyasToListen;
    private String fileSource;

    int actualStart, actualEnd;
    public MyAudioRecever() {
    }

    private AppAudioManager audioManager;
    private int reader;
    private int AyaToPlay, suraOfAya;
    private  List<String> ayaLocations;

    public MyAudioRecever(Context context, List<QuranMetaEntity> ayat, String streamURL, int pageNumber, boolean oneVersePlay) {
        this.context = context;
        this.ayat = ayat;
        this.streamURL = streamURL;
        this.pageNumber = pageNumber;
        this.oneVersePlay = oneVersePlay;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fromAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.VERSE, -1);
        surahNumber = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURAH, -1);
        reader = intent.getIntExtra(AudioAppConstants.MediaPlayer.READER, -1);
        AyaToPlay = intent.getIntExtra(AudioAppConstants.MediaPlayer.ONE_VERSE, -1);
        suraOfAya = intent.getIntExtra(AudioAppConstants.MediaPlayer.SURA, -1);
        streamURL = intent.getStringExtra(AudioAppConstants.MediaPlayer.STREAM_LINK);
        prepareData();
        try {
            play();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return START_STICKY;



    }

    private synchronized void prepareData() {
        Utils util=new Utils(QuranGrammarApplication.getContext());
        //get page ayat
        List<QuranMetaEntity> verses = util.getAyahsOfSura(surahNumber);

        //get current page user will play
        surahNumber = verses.get(0).getSurahIndex();
        int pageFirstAya = verses.get(0).getAyahInSurahIndex();
        ayaLocations = new ArrayList<>();
        //Create files locations for the all page ayas
        for (QuranMetaEntity ayaItem : verses) {
            ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, reader, ayaItem.getAyahInSurahIndex(), ayaItem.getSurahIndex()));
        }

        /*
         audioManager = new AppAudioManager(AudioService.this, ayaLocations, ayatWithBasmala, surahNumber, false);
            audioManager.AudioPosition = fromAya;
            audioManager.execute();
         */

    }

    private void play() throws IOException {

        mediaPlayer = new MediaPlayer();
           int STREAM_MUSIC = 0;
 int ayasize=       ayaLocations.size();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                       // handler.sendEmptyMessage(0);
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Uri myUri =Uri.parse(ayaLocations.get(currentAyaAtAyasToListen));

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(getApplicationContext(), myUri);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {

                mediaPlayer.start();
            }
        });


       mediaPlayer.prepare();
       mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer1) {
                mediaPlayer.release();
                mediaPlayer = null;
              //  btnPlayPause.setEnabled(true);
                currentAyaAtAyasToListen++;

                if (currentAyaAtAyasToListen < ayaLocations.size()) {
                 //   Log.d(TAG, "@@  onCompletion: " + currentAyaAtAyasToListen);
                 //   displayAyahs();
                } else {
                    actualStart = -1;
                    actualEnd = -1;
               //     backToSelectionState();
                }

            }
        });

       // mediaPlayer.setDataSource(AudioHelper.createStreamLink(aya, streamURL));



    }
}
