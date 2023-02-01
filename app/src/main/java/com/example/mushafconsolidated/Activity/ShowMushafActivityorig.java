package com.example.mushafconsolidated.Activity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.collection.ArraySet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.Constant;
import com.example.mushafconsolidated.Adapters.FlowMushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.MushaAudioAdapter;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.Qari;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.receivers.AudioAppConstants;
import com.example.mushafconsolidated.receivers.DownloadService;
import com.example.mushafconsolidated.receivers.FileManager;
import com.example.mushafconsolidated.receivers.QuranValidateSources;
import com.example.mushafconsolidated.receivers.Settingsss;
import com.example.mushafconsolidated.settings.Constants;
import com.example.utility.FlowLayout;
import com.example.utility.MovableFloatingActionButton;
import com.example.utility.QuranGrammarApplication;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.DebugTextViewHelper;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowMushafActivityorig extends BaseActivity implements

        OnItemClickListenerOnLong, View.OnClickListener, StyledPlayerView.FullscreenButtonClickListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    private static final String KEY_SERVER_SIDE_ADS_LOADER_STATE = "server_side_ads_loader_state";
    private static final String KEY_ITEM_INDEX = "item_index";
    private static final String KEY_POSITION = "position";
    private int ayah;

    int audioType;
    String prevqari="";
    private final Handler handler = new Handler();
    private static final String KEY_AUTO_PLAY = "auto_play";
    private List<MediaItem> marray;
    private String singleverse;


    private boolean isSingle=false;
    private List<QuranEntity> quranbySurahadapter;
    private MaterialButton resetplayer;
    private FloatingActionButton fab1,fab2;
    private LinearLayout  fabLayout1,fabLayout2;
    private   MovableFloatingActionButton  fab;


    public String getPrevqari() {
        return prevqari;
    }

    public void setPrevqari(String prevqari) {
        this.prevqari = prevqari;
    }

    private RelativeLayout bottomsheet;
    FrameLayout eqContainer;

    public int getAudioType() {
        return audioType;
    }

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }
//  protected StyledPlayerView playerView;

    protected StyledPlayerControlView playerView;

    //  protected  StyledPlayerView playerView;
    protected LinearLayout debugRootView;
    protected TextView debugTextView;
    protected @Nullable ExoPlayer player;

    private boolean isShowingTrackSelectionDialog;
    private Button selectTracksButton;
    private DataSource.Factory dataSourceFactory;
    private List<MediaItem> mediaItems;
    private TrackSelectionParameters trackSelectionParameters;
    private DebugTextViewHelper debugViewHelper;
    private Tracks lastSeenTracks;
    private boolean startAutoPlay;
    private int startItemIndex;
    private long startPosition;
    private int currentItem = 0;
    private long playbackPosition = 0L;
    ImageView playiv;
    // For ad playback only.
    public static final String BROADCAST_SEEKBAR = "com.example.mushafconsolidated.Activity.sendseekbar";
    public static int selectPage, readerID, repeatCounter, repeateValue;
    private boolean flagHideShowTool, tafseerMood, pausePlayFlag, repeatNotRepeat;
    public static String lastSoraName, downloadLink, readerName;
    public static boolean startBeforeDownload, nextPage;

    private static final String TAG = "ShowMushafActivity";
    private Intent intentmyservice;
    private TextView ayadet;
    private SeekBar seekBar;
    int surahselected, verselected;
    private String isNightmode;
   // LinearLayout fabLayout1, fabLayout2,fabLayout3;


  //  FloatingActionButton fab, fab1, fab2, fab3;
    FloatingActionButton resetfb, playlistfb;

    // Use the ExtendedFloatingActionButton to handle the
    // parent FAB
    ExtendedFloatingActionButton mAddFab;

    // These TextViews are taken to make visible and
    // invisible along with FABs except parent FAB's action
    // name
    TextView resetfbtxt, playlistfbtxt;

    // to check whether sub FABs are visible or not
    Boolean isAllFabsVisible;

    boolean isFABOpen = false;
    public int getVerselected() {
        return verselected;
    }

    public void setVerselected(int verselected) {
        this.verselected = verselected;
    }

    @Override
    public void onBackPressed() {

        //unregister broadcast for download ayat
        LocalBroadcastManager.getInstance(ShowMushafActivityorig.this).unregisterReceiver(downloadPageAya);
        //stop flag of auto start
        startBeforeDownload = false;

        if (player != null) {
            player.release();
        }

        // finish();
        super.onBackPressed();
    }

    boolean isMusicplaying = false;
    private boolean musicplaying;
    private Intent intent;
    private boolean everyayah;
    private int surah;
    private ListView listView;

    public int getSurahselected() {
        return surahselected;
    }

    public void setSurahselected(int surahselected) {
        this.surahselected = surahselected;
    }

    @BindView(R.id.rvAyahsPages)
    RecyclerView rvAyahsPages;

    private RelativeLayout myToolbarContainer, footerContainer;


    Utils repository;
    FlowMushaAudioAdapter flowMushaAudioAdapter;
    MushaAudioAdapter mushaAudioAdapter;
    Typeface typeface;
    TextView txtView;
    int pos;

    List<Page> pageList;
    int ayahsColor, scrollorColor;
    private int lastpageShown = 1;
    private int seekMax;
    private static int songEnded = 0;
    boolean mBroadcastIsRegistered;
    /**
     * list of pages num that contain start of HizbQurater
     */
    private List<Integer> quraterSStart;
    /**
     * hold num of pages that read today
     * will be update(in db) with every exit from activity
     */
    ArraySet<Integer> pagesReadLogNumber;

    /**
     * hold current date used to retrive pages and also with updating
     */
    private long currentDate;


    /**
     * hold current date used to retrive pages and also with updating
     */
    private String currentDateStr;

    private Spinner translationBooks, readers;
    private ConstraintLayout  downloadFooter;
    private  LinearLayout normalFooter;
    private RelativeLayout playerFooter;
    private List<String> bookNames;
    private List<Integer> bookIDs;
    //  private List<TranslationBook> booksInfo;
    private List<Qari> readersList;
    private static final int REQUEST_WRITE_Settings = 113;


    private AudioManager audioManager;
    private int screenHeight;
    private ProgressBar mediaPlayerDownloadProgress;
    private BottomSheetBehavior mBottomSheetBehavior;
    FloatingActionButton resetfab,playlistfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newactivity_show_ayahs);
        ButterKnife.bind(this);
        QuranGrammarApplication.appContext = ShowMushafActivityorig.this;
        //  intentmyservice = new Intent(this, AudioService.class);
        intent = new Intent(BROADCAST_SEEKBAR);
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        isNightmode = sharedPreferences.getString("themepref", "dark");
        repository = Utils.getInstance(getApplication());
        typeface = Typeface.createFromAsset(getAssets(), "me_quran.ttf");
        pos = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        pos = getStartPageFromIndex(pos);

        //region Description
        if (getIntent().hasExtra(Constants.SURAH_INDEX)) {
            surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
            setSurahselected(surah);
            //   getIntent().getIntExtra(Constants.SURAH_GO_INDEX, 1);
            int ayah = getIntent().getIntExtra(Constants.AYAH_GO_INDEX, 1);
            Log.d(TAG, "onCreate: ayah  " + ayah);
            pos = getPosFromSurahAndAyah(surah, ayah);
            //       showMessage(String.valueOf(pos));D
        } else if (getIntent().hasExtra(Constants.LAST_INDEX)) {
            //    pos = repository.getLatestRead(); // as it will be decreased

        } else if (getIntent().hasExtra(Constants.PAGE_INDEX)) {  // case bookmark, go to by page
            pos = getIntent().getIntExtra(Constants.PAGE_INDEX, 1);
        } else if (getIntent().hasExtra(Constants.JUZ_INDEX)) {
            pos = getIntent().getIntExtra(Constants.JUZ_INDEX, 1);
            pos = getPageFromJuz(pos);
        }
        //endregion

        Log.d(TAG, "onCreate: " + pos);
        // debugRootView = findViewById(R.id.controls_root);
        // debugTextView = findViewById(R.id.debug_text_view);
        // selectTracksButton = findViewById(R.id.select_tracks_button);
        //  selectTracksButton.setOnClickListener(this);

        playerView = findViewById(R.id.player_view);
        //  playerView.setControllerVisibilityListener((StyledPlayerView.ControllerVisibilityListener) this);
        // playerView.setErrorMessageProvider(new ShowMushafActivity.PlayerErrorMessageProvider());

        playerView.requestFocus();


        if (savedInstanceState != null) {
            trackSelectionParameters =
                    TrackSelectionParameters.fromBundle(
                            savedInstanceState.getBundle(KEY_TRACK_SELECTION_PARAMETERS));
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startItemIndex = savedInstanceState.getInt(KEY_ITEM_INDEX);
            startPosition = savedInstanceState.getLong(KEY_POSITION);

        } else {
            trackSelectionParameters = new TrackSelectionParameters.Builder(/* context= */ this).build();
            clearStartPosition();
        }
        bottomsheet = findViewById(R.id.footerplayer);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        initRV();
        initfabmenu();


    }

    private void initfabmenu() {
        fabLayout1 = (LinearLayout) findViewById(R.id.fabLayout1);
         fabLayout2 = (LinearLayout) findViewById(R.id.fabLayout2);



      fab =  findViewById(R.id.fab);
      
        fab1 = (FloatingActionButton) findViewById(R.id.resetfab);
        fab2= (FloatingActionButton) findViewById(R.id.playlistfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

    }
    private void showFABMenu(){



        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);


        fab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.vstandard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.vstandard_145));


    }

    private void closeFABMenu(){


        isFABOpen = false;


        fab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout1.setVisibility(View.GONE);
        fabLayout2.setVisibility(View.GONE);



/*        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {

                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });*/





    }
    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {


            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(ayah);
      /*      if(position!=1) {
                RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(position - 1);

                if (null != holderp) {
                    int drawingCacheBackgroundColor = holderp.itemView.getDrawingCacheBackgroundColor();
                    holderp.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                }
            }
*/
            //     ((FlowMushaAudioAdapter.ItemViewAdapter) holder).flow_word_by_word.findViewById(R.id.word_arabic_textView).mText
            if (null != holder) {
                try {
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown"))

                            holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                        else {
                            holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivityorig.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(ayah - 1);
            if (ayah > 1) {
                if (null != holderp) {
                    try {
                        ArrayList<String> arrayList = new ArrayList<>();
                        FlowLayout fl = new FlowLayout(ShowMushafActivityorig.this, arrayList);
                        ArrayList<String> arrayList1 = fl.getArrayList();
                        fl.getChildAt(ayah);
                        int drawingCacheBackgroundColor = holderp.itemView.findViewById(R.id.quran_textView).getDrawingCacheBackgroundColor();

                        if (holderp.itemView.findViewById(R.id.quran_textView) != null) {
                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            holderp.itemView.findViewById(R.id.quran_textView).setBackgroundColor(drawingCacheBackgroundColor);

                        }
                    } catch (NullPointerException exception) {
                        Toast.makeText(ShowMushafActivityorig.this, "UPDATE HIGHLIGHTED", Toast.LENGTH_SHORT).show();
                    }


                }

            }


            rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));


            handler.postDelayed(this, 500);


        }


    };

    private void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000);
    }

    protected void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            //   releaseServerSideAdsLoader();
            //   debugViewHelper.stop();
            //  debugViewHelper = null;
            player.release();
            player = null;
            playerView.setPlayer(/* player= */ null);
            mediaItems = Collections.emptyList();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT > 23) {
            if (playerView != null) {
                //  playerView.onPause();


            }
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT > 23) {
            //    initializePlayer(isSingle);
            if (playerView != null) {
                //   playerView.onResume();
            }
        }
    }

    protected boolean initializePlayer(boolean isSingle) {
        if(isMusicplaying){
            releasePlayer();
        }
        if (player == null) {
            Intent intent = getIntent();
            playerFooter.setVisibility(View.VISIBLE);
            normalFooter.setVisibility(View.GONE);
            downloadFooter.setVisibility(View.GONE);



                marray = createMediaItems();

                if (marray.isEmpty()) {
                    return false;
                }

            player = new ExoPlayer.Builder(this).build();
            lastSeenTracks = Tracks.EMPTY;


            player.addListener(new ShowMushafActivityorig.PlayerEventListener());
            player.setTrackSelectionParameters(trackSelectionParameters);
            player.addListener(new ShowMushafActivityorig.PlayerEventListener());
            player.addAnalyticsListener(new EventLogger());
            player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
            player.setPlayWhenReady(startAutoPlay);
            player.setRepeatMode(Player.REPEAT_MODE_ALL);

            AudioAttributes audioAttributes = player.getAudioAttributes();
            player.seekTo(currentItem, playbackPosition);

            playerView.setPlayer(player);
            AudioAttributes audioAttributess = player.getAudioAttributes();
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (player.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                        isMusicplaying=true;// media actually playing
                    } else if (player.getPlayWhenReady()) {
                        isMusicplaying=false;
                        // might be idle (plays after prepare()),
                        // buffering (plays when data available)
                        // or ended (plays when seek away from end)
                    } else {
                        pausePlayFlag=true;
                        // player paused in any state
                    }
                    Player.Listener.super.onPlaybackStateChanged(playbackState);
                }
            });

            //   debugViewHelper = new DebugTextViewHelper(player, debugTextView);
            //   debugViewHelper.start();
        }
        boolean haveStartPosition = startItemIndex != C.INDEX_UNSET;
        if (haveStartPosition) {
            player.seekTo(startItemIndex, startPosition);
        }

            player.setMediaItems(marray, /* resetPosition= */ !haveStartPosition);
            player.prepare();
            setupHandler();

        //updateButtonVisibility();
        return true;
    }

    private List<MediaItem> createMediaItems() {
        Utils repository = new Utils(this);
        List<ChaptersAnaEntity> chap = repository.getSingleChapter(getSurahselected());


        List<String> ayaLocations = new ArrayList<>();

        marray = new ArrayList<>();


        if(isSingle) {
            List<QuranEntity> sngleverseplay = repository.getsurahayahVerses(getSurahselected(), getVerselected());
            //Create files locations for the all page ayas
            for (QuranEntity ayaItem : sngleverseplay) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }

        }else{
            List<QuranEntity> quranbySurah = repository.getQuranbySurah(getSurahselected());
            for (QuranEntity ayaItem : quranbySurah) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }
        }





        return marray;
    }


    private void updateTrackSelectorParameters() {
        if (player != null) {
            trackSelectionParameters = player.getTrackSelectionParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startItemIndex = player.getCurrentMediaItemIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    @Override
    public void onFullscreenButtonClick(boolean isFullScreen) {

    }


    private class PlayerEventListener implements Player.Listener {


        @Override
        public void onPlaybackStateChanged(@Player.State int playbackState) {
            if (playbackState == Player.STATE_ENDED) {
                System.out.println("playbackstate");
            }
            //     updateButtonVisibility();
        }

        @Override
        public void onPlayerError(PlaybackException error) {
            if (error.errorCode == PlaybackException.ERROR_CODE_BEHIND_LIVE_WINDOW) {
                player.seekToDefaultPosition();
                player.prepare();
            } else {
                //     updateButtonVisibility();
                //showControls();
            }
        }


        @Override
        public void onMediaItemTransition(
                @Nullable MediaItem mediaItem, @Player.MediaItemTransitionReason int reason) {
            //  listener.onMediaItemTransition(mediaItem, reason);
            System.out.println("check");
        }

        @Override
        public void onPositionDiscontinuity(
                Player.PositionInfo oldPosition, Player.PositionInfo newPosition, @Player.DiscontinuityReason int reason) {
            //  listener.onPositionDiscontinuity(oldPosition, newPosition, reason);
// ayah=newPosition.mediaItemIndex;
// sendUpdatesToUI.run();
            System.out.println("oldpostion" + " " + oldPosition + "newpostion " + " " + newPosition + "reason" + " " + reason);
            System.out.println("check");
        }


        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(Tracks tracks) {
            //   updateButtonVisibility();
            Tracks currentTracks = player.getCurrentTracks();
            ayah = player.getCurrentMediaItemIndex();
            System.out.println("Ayah" + "" + ayah);
            ayah++;
            if (ayah >= 1) {
                sendUpdatesToUI.run();
            }
            if (tracks == lastSeenTracks) {
                return;
            }

            if (tracks.containsType(C.TRACK_TYPE_AUDIO)
                    && !tracks.isTypeSupported(C.TRACK_TYPE_AUDIO, /* allowExceedsCapabilities= */ true)) {
                showToast(R.string.error_unsupported_audio);
            }
            lastSeenTracks = tracks;
        }
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void clearStartPosition() {
        startAutoPlay = true;
        startItemIndex = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<PlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(PlaybackException e) {
            String errorString = getString(R.string.error_generic);
            Throwable cause = e.getCause();
            if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                // Special case for decoder initialization failures.
                MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                        (MediaCodecRenderer.DecoderInitializationException) cause;
                if (decoderInitializationException.codecInfo == null) {
                    if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                        errorString = getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString =
                                getString(
                                        R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                    } else {
                        errorString =
                                getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    errorString =
                            getString(
                                    R.string.error_instantiating_decoder,
                                    decoderInitializationException.codecInfo.name);
                }
            }
            return Pair.create(0, errorString);
        }
    }
/*
    private void addToReadLog(int pos) {
        pagesReadLogNumber.add(pos);
    }
*/

    private int getPosFromSurahAndAyah(int surah, int ayah) {
        return repository.getPageFromSurahAndAyah(surah, ayah);
    }

    private int getPageFromJuz(int pos) {
        return repository.getPageFromJuz(pos);
    }

    private int getStartPageFromIndex(int pos) {
        return repository.getSuraStartpage(pos);
    }

    private void initRV() {

        Animation openfab =    AnimationUtils.loadAnimation(this, R.anim.rotate_open_animation) ;
        Animation closefab = AnimationUtils.loadAnimation(this, R.anim.rotate_close_animation);
        Animation frombottomfab =      AnimationUtils.loadAnimation(this, R.anim.from_bottom_animation);
        Animation fromtopfab =         AnimationUtils.loadAnimation(this, R.anim.to_bottom_animation);












       //  resetplayer = findViewById(R.id.resetplayer);






        listView = (ListView) findViewById(R.id.ayahlist);
        playiv = (ImageView) findViewById(R.id.play);
        playiv.setOnClickListener(this);
        ;

        //   myToolbarContainer = (RelativeLayout) findViewById(R.id.appbar);
        seekBar = findViewById(R.id.SeekBar01);
        //   ayadet = (TextView) findViewById(R.id.details);
        footerContainer = (RelativeLayout) findViewById(R.id.footerbar);


        normalFooter = (LinearLayout) findViewById(R.id.normalfooter);
        downloadFooter = (ConstraintLayout) findViewById(R.id.footerdownload);
        playerFooter = (RelativeLayout) findViewById(R.id.footerplayer);

        readers = (Spinner) findViewById(R.id.selectReader);

        mediaPlayerDownloadProgress = (ProgressBar) findViewById(R.id.downloadProgress);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);


        layoutManager.setFlexDirection(FlexDirection.ROW);
        //   layoutManager.setAlignItems(AlignItems.STRETCH);

        layoutManager.setJustifyContent(JustifyContent.FLEX_START);


        layoutManager.setFlexWrap(FlexWrap.WRAP);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        quranbySurahadapter = Utils.getQuranbySurah(surah);
        ArrayList<ChaptersAnaEntity> chapter = repository.getSingleChapter(surah);
        //  initlistview(quranbySurah, chapter);


        OnItemClickListenerOnLong listener = this;
        ArrayList<String> header = new ArrayList<>();
        header.add(String.valueOf(chapter.get(0).getRukucount()));
        header.add(String.valueOf(chapter.get(0).getVersescount()));
        header.add(String.valueOf(chapter.get(0).getChapterid()));
        header.add(chapter.get(0).getAbjadname());
        eqContainer.setVisibility(View.GONE);
        rvAyahsPages.setLayoutManager(manager);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        //  rvAyahsPages.setLayoutManager(mLayoutManager);

        //  rvAyahsPages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvAyahsPages.setHasFixedSize(true);
        boolean flow = false;
        if (flow) {
            flowMushaAudioAdapter = new FlowMushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            rvAyahsPages.setAdapter(flowMushaAudioAdapter);

        } else {
            mushaAudioAdapter = new MushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            rvAyahsPages.setAdapter(mushaAudioAdapter);
        }


        rvAyahsPages.setItemAnimator(new DefaultItemAnimator());
/*

        resetfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releasePlayer();

            }
        });
        resetfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //    releasePlayer();
               player.stop();
            //   initializePlayer(false);
                Toast.makeText(ShowMushafActivity.this, "PlayList Reset done", Toast.LENGTH_SHORT).show();
            }
        });

        playlistfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isMusicplaying) {

                    playerFooter.setVisibility(View.GONE);
                    normalFooter.setVisibility(View.VISIBLE);
                }
            }
        });
*/






        readers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                readerName = readers.getSelectedItem().toString();
                setPrevqari(readerName);
                getReaderAudioLink(readerName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //check language to load readers arabic or english
                List<String> readersNames = new ArrayList<>();
                readersList = repository.getQaris();
                for (Qari reader : readersList) {
                    if (reader.getAudiotype() == 0 || reader.getAudiotype() == 2) {
                        readersNames.add(reader.getName_english());



                    } /*else {
                        readersNames.add(reader.getName_english());


                    }*/
                }

                //add custom spinner adapter for readers
                ArrayAdapter<String> spinnerReaderAdapter = new ArrayAdapter<>(ShowMushafActivityorig
                        .this, R.layout.spinner_layout_larg, R.id.spinnerText, readersNames);
                readers.setAdapter(spinnerReaderAdapter);

                // readers.setSelection(10);

            }
        });


        // to preserver quran direction from right to left
        rvAyahsPages.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }



    private void initlistview(List<QuranEntity> quranbySurah, ArrayList<ChaptersAnaEntity> chapter) {
        String[] verserarray = new String[chapter.get(0).getVersescount()];
        int i = 0;
        for (QuranEntity entity : quranbySurah) {
            StringBuilder builder = new StringBuilder();
            builder.append(entity.getQurantext());
            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", "", entity.getAyah()));
            verserarray[i++] = builder.toString();
        }


        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.activity_listview, verserarray);
        listView.setAdapter(adapter);
    }

    public void getReaderAudioLink(String readerName) {
        for (Qari reader : readersList) {

            if (reader.getName_english() == readerName && Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                downloadLink = reader.getUrl();
                readerID = reader.getId();
                audioType = reader.getAudiotype();
                break;
            } else if (reader.getName_english() == readerName) {
                downloadLink = reader.getUrl();
                readerID = reader.getId();
                break;
            }

        }
    }


    @Override
    protected void onPause() {
//        mSensorManager.unregisterListener(this);
        super.onPause();

        //unregister broadcast for download ayat
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadPageAya);
        //stop flag of auto start
        startBeforeDownload = false;

    }


    @Override
    protected void onResume() {
        super.onResume();


        //register broadcast for download ayat
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadPageAya, new IntentFilter(AudioAppConstants.Download.INTENT));

        //make footer change to normal if audio end in pause
        if (!Settingsss.isMyServiceRunning(this, DownloadService.class)) {
            playerFooter.setVisibility(View.GONE);
            normalFooter.setVisibility(View.VISIBLE);
        } else {
            if (downloadFooter.getVisibility() != View.VISIBLE) {
                playerFooter.setVisibility(View.VISIBLE);
            } else {
                playerFooter.setVisibility(View.GONE);
            }
            normalFooter.setVisibility(View.GONE);
        }


        //loadPagesReadLoge();
    }


    private final BroadcastReceiver downloadPageAya = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int value = (int) intent.getLongExtra(AudioAppConstants.Download.NUMBER, 0);
            int max = (int) intent.getLongExtra(AudioAppConstants.Download.MAX, 0);
            String status = intent.getStringExtra(AudioAppConstants.Download.DOWNLOAD);

            if (status != null) {
                if (status.equals(AudioAppConstants.Download.IN_DOWNLOAD)) {
                    downloadFooter.setVisibility(View.VISIBLE);
                    normalFooter.setVisibility(View.GONE);
                    playerFooter.setVisibility(View.GONE);
                    mediaPlayerDownloadProgress.setMax(max);
                    mediaPlayerDownloadProgress.setProgress(value);
                } else if (status.equals(AudioAppConstants.Download.FAILED)) {
                    mediaPlayerDownloadProgress.setMax(1);
                    mediaPlayerDownloadProgress.setProgress(1);
                } else if (status.equals(AudioAppConstants.Download.SUCCESS)) {
                    mediaPlayerDownloadProgress.setMax(1);
                    mediaPlayerDownloadProgress.setProgress(1);
                    //check if you auto play after download
                    if (startBeforeDownload == true) {
                        //change views
                        downloadFooter.setVisibility(View.GONE);
                        normalFooter.setVisibility(View.GONE);
                        playerFooter.setVisibility(View.VISIBLE);
                        initializePlayer(isSingle);

                    } else {
                        downloadFooter.setVisibility(View.GONE);
                        normalFooter.setVisibility(View.VISIBLE);
                        playerFooter.setVisibility(View.GONE);
                    }

                }

            }
        }
    };


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission((ShowMushafActivityorig.this), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public List<String> createDownloadLink() {
        List<ChaptersAnaEntity> chap = repository.getSingleChapter(surah);
        int versescount = chap.get(0).getVersescount();

        List<QuranEntity> quranbySurah = Utils.getQuranbySurah(surah);
        setSurahselected(surah);
        //   int ayaID=0;
        int counter = 0;
        //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        List<String> downloadLinks = new ArrayList<>();


        //validate if aya download or not
        if (!QuranValidateSources.validateSurahAudio(this, readerID, getSurahselected())) {

            //create aya link

            int suraLength = String.valueOf(chap.get(0).getChapterid()).trim().length();
            String suraID = chap.get(0).getChapterid() + "";

            //   int ayaLength = String.valueOf(ayaItem.ayaID).trim().length();

            if (suraLength == 1)
                suraID = "00" + chap.get(0).getChapterid();
            else if (suraLength == 2)
                suraID = "0" + chap.get(0).getChapterid();

            counter++;


            downloadLinks.add(downloadLink + chap.get(0).getChapterid() + AudioAppConstants.Extensions.MP3);
            Log.d("DownloadLinks", downloadLink + suraID + AudioAppConstants.Extensions.MP3);


        }
        //    ayaList.remove(0);
        //    quranbySurah.remove(0);
        return downloadLinks;
    }

    public List<String> createDownloadLinks() {
        List<ChaptersAnaEntity> chap = repository.getSingleChapter(surah);
        int versescount = chap.get(0).getVersescount();

        List<QuranEntity> quranbySurah = Utils.getQuranbySurah(surah);
        setSurahselected(surah);
        //   int ayaID=0;
        int counter = 0;
        //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        List<String> downloadLinks = new ArrayList<>();
        //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat


        for (QuranEntity ayaItem : quranbySurah) {
            //validate if aya download or not
            if (!QuranValidateSources.validateAyaAudio(this, readerID, ayaItem.getAyah(), ayaItem.getSurah())) {

                //create aya link
                int suraLength = String.valueOf(ayaItem.getSurah()).trim().length();
                String suraID = ayaItem.getSurah() + "";
                int ayaLength = String.valueOf(ayaItem.getAyah()).trim().length();
                //   int ayaLength = String.valueOf(ayaItem.ayaID).trim().length();
                StringBuilder ayaID = new StringBuilder(new StringBuilder().append(ayaItem.getAyah()).append("").toString());
                if (suraLength == 1)
                    suraID = "00" + ayaItem.getSurah();
                else if (suraLength == 2)
                    suraID = "0" + ayaItem.getSurah();

                if (ayaLength == 1) {
                    ayaID = new StringBuilder("00" + ayaItem.getAyah());
                } else if (ayaLength == 2) {

                    ayaID = new StringBuilder("0" + ayaItem.getAyah());
                }
                counter++;
                //add aya link to list

                downloadLinks.add(downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3);
                Log.d("DownloadLinks", downloadLink + suraID + ayaID + AudioAppConstants.Extensions.MP3);

            }
        }
        //    ayaList.remove(0);
        //    quranbySurah.remove(0);
        return downloadLinks;
    }


    /**
     * retrieve list of pages that contain start of hizb Quaters.
     */
    private void generateListOfPagesStartWithHizbQurater() {
        quraterSStart = repository.getHizbQuaterStart();
        // logData(quraterSStart);
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.play)) {

            DownloadIfnotPlay();
        }


    }

    private void DownloadIfnotPlay() {

        String filePath = "";

        int internetStatus = Settingsss.checkInternetStatus(this);

//https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/
        //check if there is other download in progress
        if (!Settingsss.isMyServiceRunning(this, DownloadService.class)) {
            //internal media play
            List<String> Links = new ArrayList<>();
            everyayah = true;
            if (everyayah && !(audioType == 2)) {
                Links = createDownloadLinks();
            } else {
                Links = createDownloadLink();
            }

            if (Links.size() != 0) {
                //check if the internet is opened
                DownLoadIfNot(internetStatus, (ArrayList<String>) Links);

            } else {
                initializePlayer(isSingle);

                playerFooter.setVisibility(View.VISIBLE);



            }
        } else {
            //Other thing in download
            Toast.makeText(this, getString(R.string.download_busy), Toast.LENGTH_SHORT).show();
        }

    }

    private void DownLoadIfNot(int internetStatus, ArrayList<String> Links) {
        String filePath;
        if (internetStatus <= 0) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setCancelable(false);
            builder.setTitle(getResources().getString(R.string.Alert));
            builder.setMessage(getResources().getString(R.string.no_internet_alert));
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else {
            //change view of footer to media
      //      footerContainer.setVisibility(View.VISIBLE);
            playerFooter.setVisibility(View.GONE);
            //  mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            downloadFooter.setVisibility(View.VISIBLE);

            //check audio folders
            filePath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath()
                    + getString(R.string.app_folder_path)
                    + "/Audio/" + readerID;


            // String app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Audio/" + readerID;
            String app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/audio/" + readerID;


            File f = new File(app_folder_path);
            //   File f = new File(Environment.getExternalStoragePublicDirectory
            //     (Environment.DIRECTORY_DOCUMENTS), "audio/"+readerID);
            String path = f.getAbsolutePath();
            File file = new File(path);


            if (!file.exists())
                file.mkdirs();


            startBeforeDownload = true;

            Intent intent = new Intent(ShowMushafActivityorig.this, DownloadService.class);
            intent.putStringArrayListExtra(AudioAppConstants.Download.DOWNLOAD_LINKS, Links);
            intent.putExtra(AudioAppConstants.Download.DOWNLOAD_LOCATION, app_folder_path);
            startService(intent);

        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent serviceIntent) {
            updateUI(serviceIntent);
        }

        private void updateUI(Intent serviceIntent) {


            String ayahplaying = serviceIntent.getStringExtra("ayaplaying");
            int position = Integer.parseInt(ayahplaying);
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(position);

            if (null != holder) {
                holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);


            }
            if (position != 0) {
                RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(position - 1);
                int drawingCacheBackgroundColor = holderp.itemView.findViewById(R.id.quran_textView).getDrawingCacheBackgroundColor();
                holderp.itemView.findViewById(R.id.quran_textView).setBackgroundColor(drawingCacheBackgroundColor);

            }
            mushaAudioAdapter.getItemId(Integer.parseInt(ayahplaying));


            String counter = serviceIntent.getStringExtra("counter");
            String mediamax = serviceIntent.getStringExtra("mediamax");
            String strSongEnded = serviceIntent.getStringExtra("song_ended");
            long seekProgress = Long.parseLong(ayahplaying);
            //  seekProgress=1272539-seekProgress;
            seekMax = Integer.parseInt(mediamax);
            songEnded = Integer.parseInt(strSongEnded);
            seekBar.setMax(seekMax);
            // ayadet.setText("Playing"+ayahplaying);
            seekBar.setProgress(Integer.parseInt(counter));
            rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(Integer.parseInt(ayahplaying + 2)));
            QuranEntity entity = (QuranEntity) mushaAudioAdapter.getItem(Integer.parseInt(ayahplaying));
            //   entity.getQurantext();
            //    pageAdapter.notifyItemInserted(Integer.parseInt(ayahplaying));

            // listscroll(ayahplaying);


        }


    };


    @Override
    public void onItemClick(View v, int position) {
     Object istag=   v.getTag();
    if(istag.equals("verse")){

        int ayah1 = quranbySurahadapter.get(position).getAyah();
        isSingle=true;
        setVerselected(ayah1-1);
        DownloadIfnotPlay();

    }

    }

    @Override
    public void onItemLongClick(int position, View v) {

    }


}

