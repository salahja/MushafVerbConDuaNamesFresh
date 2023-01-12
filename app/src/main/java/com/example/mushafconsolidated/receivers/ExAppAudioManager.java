package com.example.mushafconsolidated.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.mushafconsolidated.Activity.ShowMushafActivity;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExAppAudioManager  {
    private AppCompatActivity activity;
    private AlertDialog dialog;
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
    private boolean isToastShowing = true;
    private ExecutorService ex;


    /**
     * Constructor for audio manger
     *
     * @param context Application context
     */
    public ExAppAudioManager(Context context, List<QuranMetaEntity> ayat, String streamURL, int pageNumber, boolean oneVersePlay) {
        this.context = context;
        this.ayat = ayat;
        this.streamURL = streamURL;
        this.pageNumber = pageNumber;
        this.oneVersePlay = oneVersePlay;

    }

        public ExAppAudioManager(Context context, AudioService audioService, List<String> paths, List<QuranMetaEntity> ayat, int pageNumber, boolean oneVersePlay) {
            this.context = context;
        this.paths = paths;
        this.ayat = ayat;
        this.pageNumber = pageNumber;
        this.oneVersePlay = oneVersePlay;
        ex = Executors.newSingleThreadExecutor();
        init();
    }



    private void init() {
        ex.execute(new Runnable() {
            @Override
            public void run() {

                //pre
                new Handler(Looper.getMainLooper()).post(new Runnable() {
              @Override
              public void run() {

                  mediaPlayer = new MediaPlayer();

                  Intent playMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
                  playMedia.putExtra(AudioAppConstants.MediaPlayer.PLAY, true);
                  LocalBroadcastManager.getInstance(context).sendBroadcast(playMedia);


                  IntentFilter intentFilter = new IntentFilter(AppAudioManager.NetworkStateChangeReceiver.NETWORK_AVAILABLE_ACTION);
                  LocalBroadcastManager.getInstance(context).registerReceiver(new BroadcastReceiver() {
                      @Override
                      public void onReceive(Context context, Intent intent) {
                          boolean isNetworkAvailable = intent.getBooleanExtra(AppAudioManager.NetworkStateChangeReceiver.IS_NETWORK_AVAILABLE, false);
                          String networkStatus = isNetworkAvailable ? "connected" : "disconnected";

                          if (!isNetworkAvailable) {
                              pauseMedia();
                          }
                      }
                  }, intentFilter);


                  //show notification media player
                  if (AudioService.screenOff != true)
                      showMediaPlayerNotification();

                  isInCall = false;
                  isFirstStart = true;
                  finished = true;
                  //initPhoneListener();
                  initOutgoingBroadcastReceiver();
              }
          });


//doin
                try {
                    if (!oneVersePlay)
                        nextAudio();
                    else
                        versePlayAudio();

                    while (finished) {

                        //Broadcast to announce playing
                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                                .putExtra(AudioAppConstants.MediaPlayer.PLAYING, true));

                    }

                } catch (Exception e) {
                    Log.e(AppAudioManager.class.getSimpleName(), "doInBackground error : " + e.getLocalizedMessage());
                    e.printStackTrace();
                    if (bigNotification) {
                        largeMediaPlayer.dismiss();
                    } else {
                        smallMediaPlayer.dismiss();
                    }

                }


//post
                new Handler(Looper.getMainLooper()).post(new Runnable() {
              @Override
              public void run() {
                  if (stopFlag != true && oneVersePlay == false) {
                      ((AudioService) context).fromAya = -1;

                      //to call next page or previous page
                      if (!previousFlag) {
                          ((AudioService) context).nextSurah();
                      } else {
                          ((AudioService) context).previousPage();
                      }

                  } else {

                      //unregister broadcasts and release media player
                      AudioService.screenOff = false;
                      context.unregisterReceiver(OutgoingBroadcastReceiver);
                      stopMedia();
                      mediaPlayer.release();

                      //dismiss the media players in notification
                      if (bigNotification) {
                          largeMediaPlayer.dismiss();
                      } else {
                          smallMediaPlayer.dismiss();
                      }

                      //delete all selection in the image
                      Intent resetImage = new Intent(AudioAppConstants.Highlight.RESET_IMAGE);
                      resetImage.putExtra(AudioAppConstants.Highlight.RESET , true);
                      LocalBroadcastManager.getInstance(context).sendBroadcast(resetImage);

                      //stop service
                      context.stopService(new Intent(context, AudioService.class));
                  }


              }
          });


            }
        });
    }



    public synchronized void versePlayAudio() {

        try {

            //reset media player
            mediaPlayer.reset();

       //     HighlightImageView.selectionFromTouch = false;

            //send broadcast to highlight image view
            final QuranMetaEntity aya = ayat.get(0);
          Intent highlightAya = new Intent(AudioAppConstants.Highlight.INTENT_FILTER);
            highlightAya.putExtra(AudioAppConstants.Highlight.VERSE_NUMBER, aya.getAyahIndex());
            highlightAya.putExtra(AudioAppConstants.Highlight.SORA_NUMBER, aya.getSurahIndex());
            highlightAya.putExtra(AudioAppConstants.Highlight.PAGE_NUMBER, aya.getPageNum());
            LocalBroadcastManager.getInstance(context).sendBroadcast(highlightAya);
            AppPreference.setSelectionVerse(aya.getPageNum() + "-" + aya.getSurahIndex() + "-" + aya.getSurahIndex());


            //check if stream or from path
            if (AppPreference.isStreamMood()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build());
                } else {
                    mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
                }
                mediaPlayer.setDataSource(AudioHelper.createStreamLink(aya, streamURL));

            } else {

                mediaPlayer.setDataSource(paths.get(0));

            }

            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (!isLooping) {
                        stopMedia();
                    } else if (ShowMushafActivity.repeatCounter != 0) {
                        mediaPlayer.start();
                        ShowMushafActivity.repeatCounter--;
                    } else {
                        stopMedia();
                        ShowMushafActivity.repeatCounter = ShowMushafActivity.repeateValue;
                    }

                }
            });

        } catch (IOException e) {
            Log.e(ExAppAudioManager.class.getSimpleName(), "versePlayAudio error : " + e.getLocalizedMessage());
            e.printStackTrace();
            //stop the media service and dismiss the notification
            stopFlag = true;
            stopMedia();
            if (smallMediaPlayer != null) smallMediaPlayer.dismiss();
            if (largeMediaPlayer != null) largeMediaPlayer.dismiss();
        }
    }

    /**
     * Function to play next Audio
     */
    public synchronized void nextAudio() {
        Utils utils=new Utils(context);
        try {
            ConnectivityManager connec;
            connec = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);

            // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                    connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                    connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {

                // if connected with internet

                //reset media player
                mediaPlayer.reset();

                //HighlightImageView.selectionFromTouch = false;
                AudioPosition++;
                if (ayat.size() <= AudioPosition) {

                    //stop thread
                    finished = false;

                    if(ayat.size() != 1){
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

                Log.e("AUDIO_TAG", "size next: " + ayat.size());
                Log.e("AUDIO_TAG", "pos next: " + AudioPosition);
                //send broadcast to highlight image view
                final QuranMetaEntity aya = ayat.get(AudioPosition);
                Intent highlightAya = new Intent(AudioAppConstants.Highlight.INTENT_FILTER);
                highlightAya.putExtra(AudioAppConstants.Highlight.VERSE_NUMBER, aya.getAyahIndex());
                highlightAya.putExtra(AudioAppConstants.Highlight.SORA_NUMBER, aya.getSurahIndex());
                highlightAya.putExtra(AudioAppConstants.Highlight.PAGE_NUMBER, aya.getPageNum());
                LocalBroadcastManager.getInstance(context).sendBroadcast(highlightAya);
                AppPreference.setSelectionVerse(aya.getPageNum() + "-" + aya.getAyahIndex() + "-" + aya.getSurahIndex());

                //show information in large notification
                if(largeMediaPlayer != null){

                    if(aya.getAyahIndex() == 1 && aya.getSurahIndex() == 1 && pageNumber != aya.getPageNum()){
                        ArrayList<ChaptersAnaEntity> sora = utils.getSingleChapter(aya.getSurahIndex());

                     //   Sora sora = new DatabaseAccess().getSuraNameByID(aya.getSurahIndex());
                        largeMediaPlayer.showAudioInformation(context.getString(R.string.basmala));
                    }else{
                        ArrayList<ChaptersAnaEntity> sora = utils.getSingleChapter(aya.getSurahIndex());
                   //     Sora sora = new DatabaseAccess().getSuraNameByID(aya.getSurahIndex());
                        largeMediaPlayer.showAudioInformation(context.getString(R.string.sora)+" "
                                +(AppPreference.isArabicMood(context) ? sora.get(0).getNameenglish() : sora.get(0).getNamearabic() )
                                +" "+context.getString(R.string.aya)+" "+ Settingsss.ChangeNumbers(context , aya.getAyahIndex()+"") );
                    }

                }

                //check if stream or from path
                if (AppPreference.isStreamMood()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mediaPlayer.setAudioAttributes(
                                new AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build());
                    } else {
                        mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
                    }
                    mediaPlayer.setDataSource(AudioHelper.createStreamLink(aya, streamURL));

                } else {
                    mediaPlayer.setDataSource(paths.get(AudioPosition));
                }

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
                            nextAudio();
                        } else if (ShowMushafActivity.repeatCounter != 0) {
                            mediaPlayer.start();
                            ShowMushafActivity.repeatCounter--;
                        } else {
                            nextAudio();
                            ShowMushafActivity.repeatCounter = ShowMushafActivity.repeateValue;
                        }

                    }
                });
                isToastShowing = false;
            } else if (
                    connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                            connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {

                if (!isToastShowing) {
                    Toast.makeText(context, " Not Connected ", Toast.LENGTH_LONG).show();
                    isToastShowing = true;
                }


                pauseMedia();

            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ExAppAudioManager.class.getSimpleName(), "nextAudio error : " + e.getLocalizedMessage());
            //stop the media service and dismiss the notification
            stopFlag = true;
            stopMedia();
            if (smallMediaPlayer != null) smallMediaPlayer.dismiss();
            if (largeMediaPlayer != null) largeMediaPlayer.dismiss();
        }
    }

    /**
     * Function to play previous order
     */
    public synchronized void previousAudio() {
        Utils utils=new Utils(context);
        try {

            //reset media player
            mediaPlayer.reset();

            if(ayat.size() == 1) {
                finished = false;
                return;
            }

        //    HighlightImageView.selectionFromTouch = false;
            AudioPosition--;
            if (AudioPosition < 0) {

                //stop thread
                finished = false;
                if(ayat.size() != 1) {
                    //send broad cast to go to previous flag
                    previousFlag = true;
                    LocalBroadcastManager.getInstance(context)
                            .sendBroadcast(new Intent(AudioAppConstants.MediaPlayer.INTENT)
                                    .putExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, 0));
                }
                return;
            }

            Log.e("AUDIO_TAG", "size prev: " + ayat.size());
            Log.e("AUDIO_TAG", "pos prev: " + AudioPosition);
            //send broadcast to highlight image view
            QuranMetaEntity aya = ayat.get(AudioPosition);
            Intent highlightAya = new Intent(AudioAppConstants.Highlight.INTENT_FILTER);
            highlightAya.putExtra(AudioAppConstants.Highlight.VERSE_NUMBER, aya.getAyahIndex());
            highlightAya.putExtra(AudioAppConstants.Highlight.SORA_NUMBER, aya.getSurahIndex());
            highlightAya.putExtra(AudioAppConstants.Highlight.PAGE_NUMBER, aya.getPageNum());
            LocalBroadcastManager.getInstance(context).sendBroadcast(highlightAya);
            AppPreference.setSelectionVerse(aya.getPageNum() + "-" + aya.getAyahIndex() + "-" + aya.getSurahIndex());

            //show information in large notification
            if(largeMediaPlayer != null){

                if(aya.getAyahIndex() == 1 && aya.getSurahIndex() == 1 && pageNumber != aya.getPageNum()){
                 //   Sora sora = new DatabaseAccess().getSuraNameByID(aya.getSurahIndex());
                    ArrayList<ChaptersAnaEntity> sora = utils.getSingleChapter(aya.getSurahIndex());
                    largeMediaPlayer.showAudioInformation(context.getString(R.string.basmala));
                }else{
                //    Sora sora = new DatabaseAccess().getSuraNameByID(aya.getSurahIndex());
                    ArrayList<ChaptersAnaEntity> sora = utils.getSingleChapter(aya.getSurahIndex());
                    largeMediaPlayer.showAudioInformation(context.getString(R.string.sora)+" "
                            +(AppPreference.isArabicMood(context) ? sora.get(0).getNamearabic() : sora.get(0).getNameenglish() )
                            +" "+context.getString(R.string.aya)+" "+ Settingsss.ChangeNumbers(context , aya.getAyahIndex()+"") );
                }

            }

            //check if stream or from path
            if (AppPreference.isStreamMood()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mediaPlayer.setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build());
                } else {
                    mediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
                }
                mediaPlayer.setDataSource(AudioHelper.createStreamLink(aya, streamURL));
            } else {
                mediaPlayer.setDataSource(paths.get(AudioPosition));
            }

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
                        nextAudio();
                    } else if (ShowMushafActivity.repeatCounter != 0) {
                        mediaPlayer.start();
                        ShowMushafActivity.repeatCounter--;
                    } else {
                        nextAudio();
                        ShowMushafActivity.repeatCounter = ShowMushafActivity.repeateValue;
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ExAppAudioManager.class.getSimpleName(), "previousAudio error : " + e.getLocalizedMessage());
            //stop the media service and dismiss the notification
            stopFlag = true;
            stopMedia();
            if (smallMediaPlayer != null) smallMediaPlayer.dismiss();
            if (largeMediaPlayer != null) largeMediaPlayer.dismiss();

        }
    }

    /**
     * Pause media
     */
    public synchronized void pauseMedia() {

        Intent pauseMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
        pauseMedia.putExtra(AudioAppConstants.MediaPlayer.PAUSE, true);
        pauseMedia.putExtra(AudioAppConstants.MediaPlayer.PLAY, false);
        LocalBroadcastManager.getInstance(context).sendBroadcast(pauseMedia);

        mediaPlayer.pause();
        if (bigNotification != true || isInCall == true) {
            if (smallMediaPlayer != null)
                smallMediaPlayer.pause();
            Log.d("MEDIA_PLAYER", "pause small");
        } else {
            if (largeMediaPlayer != null)
                largeMediaPlayer.pause();
            Log.d("MEDIA_PLAYER", "pause large");
        }

    }


    /**
     * Stop media
     */
    public synchronized void stopMedia() {

        Intent stopMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
        stopMedia.putExtra(AudioAppConstants.MediaPlayer.STOP, true);
        LocalBroadcastManager.getInstance(context).sendBroadcast(stopMedia);
        cancelMediaPlayerNotification();

        AppPreference.setSelectionVerse(null);
        finished = false;
        if (telephoneManger != null)
            telephoneManger.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);



    }

    /**
     * Resume media
     */
    public synchronized void resumeMedia() {

        Intent resumeMedia = new Intent(AudioAppConstants.MediaPlayer.INTENT);
        resumeMedia.putExtra(AudioAppConstants.MediaPlayer.RESUME, false);
        LocalBroadcastManager.getInstance(context).sendBroadcast(resumeMedia);

        mediaPlayer.start();
        if (bigNotification != true || isInCall == true) {
            if (smallMediaPlayer != null)
                smallMediaPlayer.resume();
            Log.d("MEDIA_PLAYER", "resume small");
        } else {
            if (largeMediaPlayer != null)
                largeMediaPlayer.resume();
            Log.d("MEDIA_PLAYER", "resume large");
        }

    }



    /* You can write such method somewhere in utility class and call it NetworkChangeReceiver like below */
    public class NetworkStateChangeReceiver extends BroadcastReceiver {
        public static final String NETWORK_AVAILABLE_ACTION = "com.ajit.singh.NetworkAvailable";
        public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent networkStateIntent = new Intent(NETWORK_AVAILABLE_ACTION);
            networkStateIntent.putExtra(IS_NETWORK_AVAILABLE,  isConnectedToInternet(context));
            LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);
        }

        private boolean isConnectedToInternet(Context context) {
            try {
                if (context != null) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    return networkInfo != null && networkInfo.isConnected();
                }
                return false;
            } catch (Exception e) {
                Log.e(NetworkStateChangeReceiver.class.getName(), e.getMessage());
                return false;
            }
        }
    }



    /**
     * Listener to check incoming call
     */
    private void initPhoneListener() {

        final PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    pauseMedia();
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {

                    isInCall = false;

                    if (isFirstStart == false) {
                        if (Build.VERSION.SDK_INT >= 17.0) {
                            bigNotification = true;
                            largeMediaPlayer = LargeMediaPlayer.getInstance(context);
                        } else {
                            bigNotification = false;
                            smallMediaPlayer = SmallMediaPlayer.getInstance(context);
                        }
                        resumeMedia();
                    }

                    isFirstStart = false;
                }
                super.onCallStateChanged(state, incomingNumber);
            }

        };

        telephoneManger = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephoneManger != null) {
            telephoneManger.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    /**
     * Broadcast receiver to check outgoing call
     */
    private void initOutgoingBroadcastReceiver() {
        if (OutgoingBroadcastReceiver == null) {
            OutgoingBroadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {

                        isInCall = true;

                        if (isInCall) {
                            smallMediaPlayer = SmallMediaPlayer.getInstance(context);
                            bigNotification = false;
                            pauseMedia();
                        }

                    }
                }
            };
            IntentFilter filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
            context.registerReceiver(OutgoingBroadcastReceiver, filter);
        }
    }



    /**
     * Function to show media player  notification according to the mobile version
     */
    public synchronized void showMediaPlayerNotification() {

        bigNotification = false;
        smallMediaPlayer = SmallMediaPlayer.getInstance(context);
        smallMediaPlayer.resume();
    }


    public synchronized void cancelMediaPlayerNotification() {

        smallMediaPlayer.dismiss();

    }


    private void requestAudioFocus(android.media.AudioManager audioManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes playbackAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build();
            AudioFocusRequest focusRequest =
                    new AudioFocusRequest.Builder(android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(
                                    new android.media.AudioManager.OnAudioFocusChangeListener() {
                                        @Override
                                        public void onAudioFocusChange(int i) {
                                        }
                                    })
                            .build();
            audioManager.requestAudioFocus(focusRequest);
        } else {
            audioManager.requestAudioFocus(null, android.media.AudioManager.STREAM_VOICE_CALL,
                    android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }
    }
}
