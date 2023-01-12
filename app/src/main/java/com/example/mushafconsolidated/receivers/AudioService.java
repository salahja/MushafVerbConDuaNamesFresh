package com.example.mushafconsolidated.receivers;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.Utils;
import com.example.utility.QuranGrammarApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioService extends Service {
    private  Activity actcontext;
    public static boolean mediaPaused, screenOff;
    public static int surahNumber;
    public static int pageNumber;
    public int fromAya;
    private AppAudioManager appAudioManager;
    private ExAppAudioManager exappAudioManager;
    private int reader;
    private int AyaToPlay, suraOfAya;
    private String streamURL;

    public AudioService(ShowMushafActivity showMushafActivity) {
        this.actcontext=showMushafActivity;
    }

    public AudioService() {
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

        if (AyaToPlay == -1 && suraOfAya == -1) {
            prepareVersesToPlay();
        } else {
            prepareOneVerseToPlay();
        }

        return START_STICKY;
    }
    public void onCreate() {
        super.onCreate();

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

    }
    private void prepareOneVerseToPlay() {
    }
    public BroadcastReceiver MediaPlayerBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(AudioAppConstants.MediaPlayer.PLAY)) {
                appAudioManager.resumeMedia();
                mediaPaused = false;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.PAUSE)) {
                appAudioManager.pauseMedia();
                mediaPaused = true;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.BACK)) {
                appAudioManager.previousAudio();
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.FORWARD)) {
                appAudioManager.nextAudio();
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.STOP)) {
                appAudioManager.stopFlag = true;
                appAudioManager.stopMedia();
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.REPEAT_ON)) {
                appAudioManager.isLooping = true;
            } else if (intent.getAction().equals(AudioAppConstants.MediaPlayer.REPEAT_OFF)) {
                appAudioManager.isLooping = false;
            }

        }
    };

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

    public synchronized void previousPage() {
        pageNumber--;
        if (pageNumber == 0) {
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                            .putExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, 3));
            pageNumber = 604;
        }
        Utils utils = new Utils(QuranGrammarApplication.getContext());
        List<QuranMetaEntity> verses = utils.getPageAyat(pageNumber);
        //  List<QuranMetaEntity> verses = new DatabaseAccess().getPageAyat(pageNumber);
        fromAya = verses.size() - 2;
        prepareVersesToPlay();
    }

    public synchronized void nextPage() {
        pageNumber++;
        if (pageNumber == 605) {
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                            .putExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, 2));
            pageNumber = 1;
        }
        prepareVersesToPlay();
    }
    public synchronized void nextSurah() {
        surahNumber++;
        if (surahNumber == 114) {
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                            .putExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, 2));
            surahNumber = 1;
        }
        prepareVersesToPlay();
    }

    public synchronized void prepareVersesToPlay() {
        Utils util = new Utils(QuranGrammarApplication.getContext());
        //get page ayat
        List<QuranMetaEntity> verses = util.getAyahsOfSura(surahNumber);

        //get current page user will play
        surahNumber = verses.get(0).getSurahIndex();
        int pageFirstAya = verses.get(0).getAyahInSurahIndex();

        //check if you call media player in stream or download


            List<String> ayaLocations = new ArrayList<>();
            //Create files locations for the all page ayas
            for (QuranMetaEntity ayaItem : verses) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(AudioService.this, reader, ayaItem.getAyahInSurahIndex(), ayaItem.getSurahIndex()));
            }

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
                        ayaLocations.add(counter + positionCounter, FileManager.createAyaAudioLinkLocation(AudioService.this, ShowMushafActivity.readerID, 1, 1));
                        previousSura = ayaItem.getSurahIndex();
                        positionCounter++;
                    }
                }

                //add bsmala in the beginning
                if (surahNumber != 1 && surahNumber != 187 && pageFirstAya == 1) {
                    //  ayatWithBasmala.add(0, new Aya(1, 1, 1));
                     //    ayaLocations.add(0, FileManager.createAyaAudioLinkLocation(AudioService.this, reader, 1, 1));
                }
            }


     /*   AppAudioManagerExecutor au=new AppAudioManagerExecutor(AudioService.this, ayaLocations, ayatWithBasmala, surahNumber, false,QuranGrammarApplication.getContext());
           au.execute();*/
          appAudioManager = new AppAudioManager(AudioService.this, ayaLocations, ayatWithBasmala, surahNumber, false);
           appAudioManager.AudioPosition = fromAya;
           appAudioManager.execute();



    }
}
