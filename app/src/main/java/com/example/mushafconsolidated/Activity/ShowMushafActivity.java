package com.example.mushafconsolidated.Activity;


import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE;

import android.app.Dialog;
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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import com.bullhead.equalizer.EqualizerFragment;
import com.bullhead.equalizer.Settings;
import com.example.mushafconsolidated.Adapters.FlowMushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.MushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.passagevtwoMushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.vtwoMushaAudioAdapter;
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
import com.example.utility.MovableExtendedFloatingActionButton;
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
import com.google.android.exoplayer2.ui.PlayerControlView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import wheel.OnWheelChangedListener;
import wheel.WheelView;

public class ShowMushafActivity extends BaseActivity implements

        OnItemClickListenerOnLong, View.OnClickListener, StyledPlayerView.FullscreenButtonClickListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    private static final String KEY_SERVER_SIDE_ADS_LOADER_STATE = "server_side_ads_loader_state";
    private static final String KEY_ITEM_INDEX = "item_index";
    private static final String KEY_POSITION = "position";
    private String[] surahWheelDisplayData;
    private String[] ayahWheelDisplayData;
    private ImageView playbutton;
    int versestartrange, verseendrange;
    private int currenttrack;
    private boolean onClickOrRange = false;
    //  private LinkedHashMap<Integer, Integer> hlights;

    private ArrayList<AyahCoordinate> coordinates = new ArrayList<>();
    private LinkedHashMap<Integer, ArrayList<AyahCoordinate>> hlights;

    public int getVersestartrange() {
        return versestartrange;
    }

    public void setVersestartrange(int versestartrange) {
        this.versestartrange = versestartrange;
    }

    public int getVerseendrange() {
        return verseendrange;
    }

    public void setVerseendrange(int verseendrange) {
        this.verseendrange = verseendrange;
    }

    public int getAyah() {
        return ayah;
    }

    public void setAyah(int ayah) {
        this.ayah = ayah;
    }

    private int ayah;
    passagevtwoMushaAudioAdapter passageadapter;
    private final LinkedHashMap<Integer, String> passage = new LinkedHashMap<>();
    int audioType;
    String prevqari = "";
    private final Handler handler = new Handler();
    private static final String KEY_AUTO_PLAY = "auto_play";
    private List<MediaItem> marray;
    private String singleverse;


    private boolean isSingle = false;
    private boolean isStartFrom = false;
    private List<QuranEntity> quranbySurahadapter;
    private MaterialButton resetplayer;
    private SharedPreferences sharedPreferences;
    private int ayahtrack;
    private String selectedqari;
    private ArrayList<String> header = new ArrayList<>();

    public String getPrevqari() {
        return prevqari;
    }

    public void setPrevqari(String prevqari) {
        this.prevqari = prevqari;
    }

    private RelativeLayout bottomsheetexoplayer, playerbottomsheet;
    FrameLayout eqContainer;

    public int getAudioType() {
        return audioType;
    }

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }
//  protected StyledPlayerView playerView;

    //  protected StyledPlayerControlView playerView;

    protected PlayerControlView playerView;
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
    int surahselected, verselected, versescount;
    String surahName;
    private String isNightmode;

    public int getVersescount() {
        return versescount;
    }

    public void setVersescount(int versescount) {
        this.versescount = versescount;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }
// LinearLayout fabLayout1, fabLayout2,fabLayout3;


    //  FloatingActionButton fab, fab1, fab2, fab3;
    FloatingActionButton resetfb, playlistfb, jumpfb, playfb;

    // Use the ExtendedFloatingActionButton to handle the
    // parent FAB
    MovableExtendedFloatingActionButton mAddFab;

    // These TextViews are taken to make visible and
    // invisible along with FABs except parent FAB's action
    // name
    TextView resetfbtxt, playlistfbtxt, jumptv, playtxt;

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
        LocalBroadcastManager.getInstance(ShowMushafActivity.this).unregisterReceiver(downloadPageAya);
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
    vtwoMushaAudioAdapter vtwoMushaAudioAdapter;
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
    private ConstraintLayout downloadFooter;
    private LinearLayout normalFooter;
    private RelativeLayout playerFooter, audio_settings_bottom;
    private List<String> bookNames;
    private List<Integer> bookIDs;
    TextView startrange, startimage, endrange, endimage, qariname, qariimage;

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    //  private List<TranslationBook> booksInfo;
    private List<Qari> readersList;
    private static final int REQUEST_WRITE_Settings = 113;


    private AudioManager audioManager;
    private int screenHeight;
    private ProgressBar mediaPlayerDownloadProgress;
    private BottomSheetBehavior footerBottomSheetBehavior, playerBottomsheetBehaviour;
    FloatingActionButton resetfab, playlistfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vfour_expandable_newactivity_show_ayahs);
        ButterKnife.bind(this);
        QuranGrammarApplication.appContext = ShowMushafActivity.this;
        //  intentmyservice = new Intent(this, AudioService.class);
        intent = new Intent(BROADCAST_SEEKBAR);
        sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        isNightmode = sharedPreferences.getString("themepref", "dark");
        repository = Utils.getInstance(getApplication());
        typeface = Typeface.createFromAsset(getAssets(), "me_quran.ttf");
        selectedqari = sharedPreferences.getString("qari", "35");
        pos = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        pos = getStartPageFromIndex(pos);

        //region Description
        if (getIntent().hasExtra(Constants.SURAH_INDEX)) {
            surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
            setSurahselected(surah);
            //   getIntent().getIntExtra(Constants.SURAH_GO_INDEX, 1);
            ayahtrack = getIntent().getIntExtra(Constants.AYAH_GO_INDEX, 0);
            if (ayahtrack > 0) {
                currentItem = ayahtrack;
                setStartPosition(ayahtrack);
            }
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
        bottomsheetexoplayer = findViewById(R.id.footerplayer);
        footerBottomSheetBehavior = BottomSheetBehavior.from(bottomsheetexoplayer);
        footerBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        playerbottomsheet = findViewById(R.id.audio_settings_bottom);
        playerBottomsheetBehaviour = BottomSheetBehavior.from(playerbottomsheet);
        playerBottomsheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
     //   initpassage();
        initRV();
        registerFabmenu();
        initFabmenu();


    }

    private void initpassage() {
     List<QuranEntity> quranEntities= Utils.getQuranbySurah(surah);
        StringBuilder builder = new StringBuilder();
        int counter=1;
        for (QuranEntity quranEntity : quranEntities) {
            if(quranEntity.getPassage_no()==0) {
                String aya = quranEntity.getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntity.getAyah()).append("} ﴾");
            }else    if(quranEntity.getPassage_no()!=0){
               String  aya=     quranEntity.getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntity.getAyah()).append("} ﴾");
                passage.put(counter++,builder.toString());
            int ayah=     quranEntity.getAyah();

                preparehighlights(counter,builder,ayah+1);

                builder=new StringBuilder();

            }
        }

        System.out.println("CHECK");
    }

    private void initFabmenu() {

        playfb.setVisibility(View.GONE);
        playerFooter.setVisibility(View.GONE);
        jumpfb.setVisibility(View.GONE);
        resetfb.setVisibility(View.GONE);
        playlistfb.setVisibility(View.GONE);
        resetfbtxt.setVisibility(View.GONE);
        playlistfbtxt.setVisibility(View.GONE);
        jumptv.setVisibility(View.GONE);

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are
        // invisible
        isAllFabsVisible = false;
        mAddFab.setVisibility(View.VISIBLE);
        // Set the Extended floating action button to
        // shrinked state initially
        mAddFab.shrink();

        jumpfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExecutorService ex = Executors.newSingleThreadExecutor();

                SurahAyahPicker(true, true);


            }


        });
        playlistfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (normalFooter.getVisibility() == View.GONE) {
                    //   player.pause();
                    normalFooter.setVisibility(View.GONE);
                    footerBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {

                    normalFooter.setVisibility(View.GONE);
                }
            }
        });
        resetfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshActivity("", " ");
            }
        });
        playfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playerBottomsheetBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    playerBottomsheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    if (player != null)
                        player.pause();
                    if (footerBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        footerBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }


                audio_settings_bottom.setVisibility(View.VISIBLE);


                startrange.setText("Start Range");
                endrange.setText("End Range");
                StringBuilder st = new StringBuilder();
                StringBuilder stt = new StringBuilder();
                st.append(getSurahName()).append("-").append(getSurahselected()).append(":").append("1");
                stt.append(getSurahName()).append("-").append(getSurahselected()).append(":").append(getVersescount());
                startrange.setText(st.toString());
                startrange.setText(stt.toString());

                qariname.setText(selectedqari);

                startrange.setOnClickListener(new View.OnClickListener() {
                    boolean starttrue = true;

                    @Override
                    public void onClick(View v) {
                        SurahAyahPicker(false, starttrue);


                    }
                });

                endrange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean starttrue = false;
                        SurahAyahPicker(false, starttrue);


                    }
                });




            /*    audioplaysetting item = new audioplaysetting();
                Bundle bundle = new Bundle();

                bundle.putInt(Constants.SURAH_INDEX,surah);
                bundle.putString("qari",selectedqari);
                bundle.putInt(VERSESCOUNT, Integer.parseInt(header.get(1)));
                bundle.putString(SURAHNAME, header.get(4));
                //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                FragmentManager fragmentManager = ShowMushafActivity.this.getSupportFragmentManager();
                item.setArguments(bundle);
                item.show(fragmentManager,"audio");*/


            }
        });


        // We will make all the FABs and action name texts
        // visible only when Parent FAB button is clicked So
        // we have to handle the Parent FAB button first, by
        // using setOnClickListener you can see below
        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            jumpfb.animate().rotation(180);
                            resetfb.animate().rotation(180);

                            playlistfb.animate().rotation(180);
                            jumptv.setVisibility(View.VISIBLE);
                            resetfbtxt.setVisibility(View.VISIBLE);
                            playlistfbtxt.setVisibility(View.VISIBLE);
                            playtxt.setVisibility(View.VISIBLE);
                            resetfb.show();
                            playlistfb.show();
                            playfb.show();

                            jumpfb.show();

                            // Now extend the parent FAB, as
                            // user clicks on the shrinked
                            // parent FAB
                            mAddFab.extend();

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.

                            resetfb.animate().rotation(180);

                            playlistfb.animate().rotation(180);
                            resetfb.hide();
                            playlistfb.hide();
                            jumpfb.hide();
                            playfb.hide();
                            playtxt.setVisibility(View.GONE);
                            resetfbtxt.setVisibility(View.GONE);
                            playlistfbtxt.setVisibility(View.GONE);
                            jumptv.setVisibility(View.GONE);

                            // Set the FAB to shrink after user
                            // closes all the sub FABs
                            mAddFab.shrink();

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

    }

    private void registerFabmenu() {

        // This FAB button is the Parent
        mAddFab = findViewById(R.id.add_fab);

        // FAB button
        resetfb = findViewById(R.id.resetfb);
        playlistfb = findViewById(R.id.playlistfb);
        jumpfb = findViewById(R.id.jumptofb);
        jumpfb.setOnClickListener(this);

        playfb = findViewById(R.id.playfb);
        playfb.setOnClickListener(this);

        // Also register the action name text, of all the
        // FABs. except parent FAB action name text
        resetfb.setOnClickListener(this);
        ;
        playlistfb.setOnClickListener(this);
        resetfbtxt = findViewById(R.id.resetfbtxt);
        playlistfbtxt = findViewById(R.id.playlistfbtxt);
        jumptv = (TextView) findViewById(R.id.jumptv);
        playtxt = (TextView) findViewById(R.id.playtv);
        // Now set all the FABs and all the action name
        // texts as GONE


    }

    public void SurahAyahPicker(boolean isrefresh, boolean starttrue) {
        TextView mTextView;
        WheelView chapterWheel, verseWheel, wvDay;
        Utils utils = new Utils(ShowMushafActivity.this);
        final String[][] mYear = {new String[1]};
        String[] mMonth = new String[1];
        surahWheelDisplayData = new String[]{""};
        ayahWheelDisplayData = new String[]{""};
        final ArrayList<String>[] current = new ArrayList[]{new ArrayList<>()};
        int mDay;
        final int[] chapterno = new int[1];
        final int[] verseno = new int[1];
        final String[] surahArrays = getResources().getStringArray(R.array.surahdetails);
        final String[] versearrays = getResources().getStringArray(R.array.versescounts);
        final int[] intarrays = getResources().getIntArray(R.array.versescount);
        //     final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(ShowMushafActivity.this);
        Dialog dlg = new Dialog(ShowMushafActivity.this);
        //  AlertDialog dialog = builder.create();
        ArrayList<ChaptersAnaEntity> soraList = utils.getAllAnaChapters();
        LayoutInflater inflater = ShowMushafActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_wheel_t, null);
        //  View view = inflater.inflate(R.layout.activity_wheel, null);
        dialogPicker.setView(view);
        mTextView = view.findViewById(R.id.textView2);
        chapterWheel = view.findViewById(R.id.wv_year);
        verseWheel = view.findViewById(R.id.wv_month);
        chapterWheel.setEntries(surahArrays);
        chapterWheel.setCurrentIndex(getSurahselected() - 1);
        //set wheel initial state
        boolean initial = true;
        if (initial) {
            initial = false;
            String text = (String) chapterWheel.getItem(getSurahselected() - 1);
            surahWheelDisplayData[0] = (text);
            String[] chapno = text.split(" ");
            chapterno[0] = Integer.parseInt(chapno[0]);
            verseno[0] = 1;
            current[0] = new ArrayList<>();
            int intarray;
            if (getSurahselected() != 0) {
                intarray = intarrays[getSurahselected() - 1];
            } else {
                intarray = 7;
            }
            for (int i = 1; i <= intarray; i++) {
                current[0].add(String.valueOf(i));
            }

            verseWheel.setEntries(current[0]);
            String texts = surahWheelDisplayData[0].concat("/").concat(ayahWheelDisplayData[0]);
            //   = mYear[0]+ mMonth[0];
            mTextView.setText(texts);


        }

//        wvDay = (WheelView) view.findViewById(R.id.wv_day);
        final String[] currentsurahVersescount = null;

        int vcount = Integer.parseInt(versearrays[getSurahselected() - 1]);


        for (int i = 1; i <= vcount; i++) {
            current[0].add(String.valueOf(i));
        }

        verseWheel.setEntries(current[0]);
        verseWheel.setCurrentIndex(getAyah());

        dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {

            String sura = "";


            //   setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
            if (chapterno[0] == 0) {
                setSurahselected(surah);

            } else {
                sura = String.valueOf(soraList.get(chapterno[0] - 1).getChapterid());

                setSurahselected(soraList.get(chapterno[0] - 1).getChapterid());
                setSurahName(soraList.get(chapterno[0] - 1).getNameenglish());
            }


            int verse = verseno[0];

            // setSurahselected(Integer.parseInt(sura));
            setAyah(verse);

            String aya = String.valueOf(verseno[0]);

            if (isrefresh && starttrue) {


                RefreshActivity(sura, aya);
            } else if (starttrue) {
                updateStartRange(verse);
            } else {
                updateEndRange(verse);
            }


        });

        dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
        });


        AlertDialog alertDialog = dialogPicker.create();
        String preferences = sharedPreferences.getString("themepref", "dark");
        int db = ContextCompat.getColor(QuranGrammarApplication.getContext(), R.color.odd_item_bg_dark_blue_light);

        if (preferences.equals("purple")) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.md_theme_dark_onSecondary);
            //   alertDialog.getWindow().setBackgroundDrawableResource(R.color.md_theme_dark_onTertiary);

            //
        } else if (preferences.equals("brown")) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.background_color_light_brown);
            //  cardview.setCardBackgroundColor(ORANGE100);
        } else if (preferences.equals("blue")) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.prussianblue);
            //  cardview.setCardBackgroundColor(db);
        } else if (preferences.equals("green")) {
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.mdgreen_theme_dark_onPrimary);
            //  cardview.setCardBackgroundColor(MUSLIMMATE);
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //   alertDialog.show();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

        alertDialog.show();
        Button buttonPositive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.green));
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.red));
        if (preferences.equals("purple")) {
            buttonPositive.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.Goldenrod));

        } else if (preferences.equals("brown")) {
            buttonPositive.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.red));
            //  cardview.setCardBackgroundColor(ORANGE100);
        } else if (preferences.equals("blue")) {
            buttonPositive.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.Goldenrod));
            //  cardview.setCardBackgroundColor(db);
        } else if (preferences.equals("green")) {
            buttonPositive.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(ShowMushafActivity.this, R.color.cyan_light));
            //  cardview.setCardBackgroundColor(MUSLIMMATE);
        }

        //  wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().setGravity(Gravity.TOP);


        chapterWheel.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text = (String) chapterWheel.getItem(newIndex);
                surahWheelDisplayData[0] = (text);
                String[] chapno = text.split(" ");
                chapterno[0] = Integer.parseInt(chapno[0]);
                verseno[0] = 1;


                updateVerses(newIndex);
                updateTextView();
                //    updateTextView();
            }

            private void updateVerses(int newIndex) {
                current[0] = new ArrayList<>();
                int intarray;
                if (newIndex != 0) {
                    intarray = intarrays[newIndex];
                } else {
                    intarray = 7;
                }
                for (int i = 1; i <= intarray; i++) {
                    current[0].add(String.valueOf(i));
                }

                verseWheel.setEntries(current[0]);
                updateTextView();


            }

            private void updateTextView() {
                String text = surahWheelDisplayData[0].concat("/").concat(ayahWheelDisplayData[0]);
                //   = mYear[0]+ mMonth[0];
                mTextView.setText(text);
            }
        });
        verseWheel.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text = (String) verseWheel.getItem(newIndex);
                ayahWheelDisplayData[0] = (text);
                verseno[0] = Integer.parseInt(text);
            }
        });
    }

    private void updateEndRange(int verse) {


        StringBuilder st = new StringBuilder();

        st.append(getSurahName()).append("-").append(getSurahselected()).append(":").append(getAyah());
        setVerseendrange(verse);
        endrange.setText(st.toString());

    }

    private void updateStartRange(int verse) {
        startrange.setText("Start Range");


        StringBuilder st = new StringBuilder();
        StringBuilder stt = new StringBuilder();
        st.append(getSurahName()).append("-").append(getSurahselected()).append(":").append(getAyah());
        setVersestartrange(verse);
        startrange.setText(st.toString());

    }

    private void RefreshActivity(String s, String aya) {
        Log.e(TAG, "onClick called");
        final Intent intent = this.getIntent();
        //  surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        String parentActivityRef = intent.getStringExtra("PARENT_ACTIVITY_REF");
        if (s.isEmpty()) {
            intent.putExtra(Constants.SURAH_INDEX, surah);
        } else {
            intent.putExtra(Constants.SURAH_INDEX, Integer.parseInt(s));
            intent.putExtra(Constants.AYAH_GO_INDEX, Integer.parseInt(aya));

        }
        this.overridePendingTransition(0, 0);
        startActivity(intent);
        this.finish();
        this.overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);

    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));
///musincadapter
            // RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack);
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);
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
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            //for vtwoadapter
                            Highlightverse(textView);

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack - 1);
            if (ayah > 1) {
                if (null != holderp) {
                    try {
                        ArrayList<String> arrayList = new ArrayList<>();
                        FlowLayout fl = new FlowLayout(ShowMushafActivity.this, arrayList);
                        ArrayList<String> arrayList1 = fl.getArrayList();
                        fl.getChildAt(ayah);
                        int drawingCacheBackgroundColor = holderp.itemView.findViewById(R.id.quran_textView).getDrawingCacheBackgroundColor();

                        if (holderp.itemView.findViewById(R.id.quran_textView) != null) {
                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            holderp.itemView.findViewById(R.id.quran_textView).setBackgroundColor(drawingCacheBackgroundColor);

                        }
                    } catch (NullPointerException exception) {
                        Toast.makeText(ShowMushafActivity.this, "UPDATE HIGHLIGHTED", Toast.LENGTH_SHORT).show();
                    }


                }

            }


            rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));


            handler.postDelayed(this, 1500);


        }

        private void Highlightverse(TextView textView) {
            int start, end;
            String starta, endb;

            String str = String.valueOf(textView.getText());
            String[] split1 = str.split("﴿");
            //  String s1 = split1[currenttrack];
            //  int substringlen = s1.length();
            // int fullstrlength = str.length();
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            if (currenttrack == 1) {
                start = 0;
                endb = String.valueOf(currenttrack);

                end = str.indexOf(endb);
            } else {
                starta = String.valueOf(currenttrack);
                start = str.indexOf(starta);
                endb = String.valueOf(currenttrack + 1);

                end = str.indexOf(endb);

            }


            try {
                System.out.println(span.subSequence(start, end));
                span.setSpan(new ForegroundColorSpan(Color.CYAN), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception.toString());
            }
        }


    };
    private Runnable sendUpdatesToUIPassage = new Runnable() {
        public void run() {

            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);

            if (null != holder) {
                try {
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown"))

                            holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                        else {
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);


                            //    Highlightverse(textView);

                            String str = String.valueOf(textView.getText());
                            SpannableStringBuilder span = new SpannableStringBuilder(str);
                            try {
                                //     ArrayList<AyahCoordinate> cc  =                       hlights.get(currenttrack);
                                //  System.out.println(span.subSequence(start, end));
                                span.setSpan(new ForegroundColorSpan(Color.CYAN), hlights.get(0).get(currenttrack - 1).getStart(), hlights.get(0).get(currenttrack - 1).getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                textView.setText(span);
                                String[] split = str.split("\n");
                            } catch (IndexOutOfBoundsException exception) {
                                System.out.println(exception.toString());
                            }

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack - 1);
            if (ayah > 1) {
                if (null != holderp) {
                    try {
                        ArrayList<String> arrayList = new ArrayList<>();
                        FlowLayout fl = new FlowLayout(ShowMushafActivity.this, arrayList);
                        ArrayList<String> arrayList1 = fl.getArrayList();
                        fl.getChildAt(ayah);
                        int drawingCacheBackgroundColor = holderp.itemView.findViewById(R.id.quran_textView).getDrawingCacheBackgroundColor();

                        if (holderp.itemView.findViewById(R.id.quran_textView) != null) {
                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            holderp.itemView.findViewById(R.id.quran_textView).setBackgroundColor(drawingCacheBackgroundColor);

                        }
                    } catch (NullPointerException exception) {
                        Toast.makeText(ShowMushafActivity.this, "UPDATE HIGHLIGHTED", Toast.LENGTH_SHORT).show();
                    }


                }

            }


          //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));


            handler.postDelayed(this, 1500);


        }

        private void Highlightverse(TextView textView) {
            int start, end;
            String starta, endb;

            String str = String.valueOf(textView.getText());
            String[] split1 = str.split("﴿");
            //  String s1 = split1[currenttrack];
            //  int substringlen = s1.length();
            // int fullstrlength = str.length();
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            if (currenttrack == 1) {
                start = 0;
                endb = String.valueOf(currenttrack);

                end = str.indexOf(endb);
            } else {
                starta = String.valueOf(currenttrack);
                start = str.indexOf(starta);
                endb = String.valueOf(currenttrack + 1);

                end = str.indexOf(endb);

            }


            try {
                System.out.println(span.subSequence(start, end));
                span.setSpan(new ForegroundColorSpan(Color.CYAN), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception.toString());
            }
        }


    };

    private void setupHandler() {
        //   handler.removeCallbacks(sendUpdatesToUI);
        handler.removeCallbacks(sendUpdatesToUIPassage);
        //  handler.postDelayed(sendUpdatesToUI, 1000);
        handler.postDelayed(sendUpdatesToUIPassage, 1000);
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
            //    initializePlayer();
            if (playerView != null) {
                //   playerView.onResume();
            }
        }
    }

    protected boolean initializePlayer() {
        if (isMusicplaying) {
            releasePlayer();
        }
        if (player == null) {
            Intent intent = getIntent();
            playerFooter.setVisibility(View.VISIBLE);
            normalFooter.setVisibility(View.GONE);
            downloadFooter.setVisibility(View.GONE);


            marray = createMediaItems();
            preparehighlight();

            if (marray.isEmpty()) {
                return false;
            }

            player = new ExoPlayer.Builder(this).build();
            lastSeenTracks = Tracks.EMPTY;


            player.addListener(new ShowMushafActivity.PlayerEventListener());
            player.setTrackSelectionParameters(trackSelectionParameters);
            player.addListener(new ShowMushafActivity.PlayerEventListener());
            player.addAnalyticsListener(new EventLogger());
            player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
            player.setPlayWhenReady(startAutoPlay);
            player.setRepeatMode(Player.REPEAT_MODE_ALL);

            playerView.setRepeatToggleModes(REPEAT_TOGGLE_MODE_ONE);
            AudioAttributes audioAttributes = player.getAudioAttributes();

            player.seekTo(getAyah(), playbackPosition);
            if (getVersestartrange() != 0) {
                setAyah(getVersestartrange());
            }
            if (playerBottomsheetBehaviour.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                playerBottomsheetBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            if (footerBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                playerBottomsheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                player.play();
            }


            playerView.setPlayer(player);
            AudioAttributes audioAttributess = player.getAudioAttributes();
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (player.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                        isMusicplaying = true;// media actually playing
                    } else if (player.getPlayWhenReady()) {
                        isMusicplaying = false;
                        // might be idle (plays after prepare()),
                        // buffering (plays when data available)
                        // or ended (plays when seek away from end)
                    } else {
                        pausePlayFlag = true;
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

        System.out.println(getVersestartrange() + " " + getVerseendrange());
        List<String> ayaLocations = new ArrayList<>();

        marray = new ArrayList<>();
        if (getVersestartrange() != 0 && getVerseendrange() != 0) {
            List<QuranEntity> quranbySurah = Utils.getQuranbySurahAyahrange(surah, getVersestartrange(), getVerseendrange());
            for (QuranEntity ayaItem : quranbySurah) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }
        } else if (isSingle) {
            List<QuranEntity> sngleverseplay = repository.getsurahayahVerses(getSurahselected(), getVerselected());
            //Create files locations for the all page ayas
            for (QuranEntity ayaItem : sngleverseplay) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }

        } else if (isStartFrom) {
            onClickOrRange = true;
            List<QuranEntity> fromrange = repository.getQuranbySurahAyahrange(getSurahselected(), getVerselected(), chap.get(0).getVersescount());

            for (QuranEntity ayaItem : fromrange) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));
            }


        } else {
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

            currenttrack = player.getCurrentMediaItemIndex();
            System.out.println("Ayah" + "" + ayah);
            if (onClickOrRange) {
                currenttrack += ayah;
            } else {
                currenttrack++;
            }
            if (currenttrack == 0) {
                currenttrack++;
            }


            if (currenttrack >= 1) {
                //   sendUpdatesToUI.run();
                    sendUpdatesToUIPassage.run();
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

    private void preparehighlights(int mapindex, StringBuilder str, int ayahindex) {
        hlights = new LinkedHashMap<>();
        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);



        String[] split1 = str.toString().split("﴿");

       //  int start = 0;
       // int end = str.indexOf("1");
       // AyahCoordinate ac = new AyahCoordinate(0, end);
      //  coordinates.add(ac);
      //  hlights.put(0, coordinates);
        for (int i = 0; i < split1.length; i++) {

            int s = str.indexOf(String.valueOf(ayahindex));
            int e = str.indexOf(String.valueOf(ayahindex + 1));
            if (s != -1 && e != -1) {
                AyahCoordinate      ac = new AyahCoordinate(s, e);
                coordinates.add(ac);
                hlights.put(mapindex, coordinates);
            }
        }



        System.out.println("check");

    }

    private void preparehighlight() {
        hlights = new LinkedHashMap<>();
        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);
       TextView textView = holder.itemView.findViewById(R.id.quran_textView);
        String str = String.valueOf(textView.getText());
        String[] split1 = str.split("﴿");

        int start = 0;
        int end = str.indexOf("1");
        AyahCoordinate ac = new AyahCoordinate(0, end);
        coordinates.add(ac);
        hlights.put(0, coordinates);
        for (int i = 1; i < split1.length; i++) {

            int s = str.indexOf(String.valueOf(i));
            int e = str.indexOf(String.valueOf(i + 1));
            if (s != -1 && e != -1) {
                ac = new AyahCoordinate(s, e);
                coordinates.add(ac);
                hlights.put(i, coordinates);
            }
        }



        System.out.println("check");

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
        playbutton = findViewById(R.id.pplaybutton);
        playbutton.setOnClickListener(this);
        ;

        startrange = findViewById(R.id.start_range);
        endrange = findViewById(R.id.endrange);
        qariname = findViewById(R.id.qariname);
        startimage = findViewById(R.id.startimage);
        endimage = findViewById(R.id.endimage);
        qariname = findViewById(R.id.qariname);

        startrange.setOnClickListener(this);

        endrange.setOnClickListener(this);
        qariname.setOnClickListener(this);
        startimage.setOnClickListener(this);
        endimage.setOnClickListener(this);
        qariname.setOnClickListener(this);

        eqContainer = findViewById(R.id.eqFrame);
        listView = (ListView) findViewById(R.id.ayahlist);
        playiv = (ImageView) findViewById(R.id.play);
        playiv.setOnClickListener(this);
        ;

        //   myToolbarContainer = (RelativeLayout) findViewById(R.id.appbar);
        seekBar = findViewById(R.id.SeekBar01);
        //   ayadet = (TextView) findViewById(R.id.details);
        footerContainer = (RelativeLayout) findViewById(R.id.footerbar);

        audio_settings_bottom = findViewById(R.id.audio_settings_bottom);
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
        StringBuilder sb = new StringBuilder();
        for (QuranEntity entity : quranbySurahadapter) {
            if (entity.getPassage_no() != 0) {
                sb.append(entity.getQurantext()).append("﴿").append(entity.getAyah()).append("﴾");

            } else {

                passage.put(entity.getPassage_no(), sb.toString());
                sb = new StringBuilder();
            }


        }


        ArrayList<ChaptersAnaEntity> chapter = repository.getSingleChapter(surah);
        //  initlistview(quranbySurah, chapter);


        OnItemClickListenerOnLong listener = this;
        header = new ArrayList<>();
        header.add(String.valueOf(chapter.get(0).getRukucount()));
        header.add(String.valueOf(chapter.get(0).getVersescount()));
        header.add(String.valueOf(chapter.get(0).getChapterid()));
        header.add(chapter.get(0).getAbjadname());
        header.add(chapter.get(0).getNameenglish());
        setVersescount(chapter.get(0).getVersescount());
        setSurahName(chapter.get(0).getNameenglish());
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
            //    mushaAudioAdapter = new MushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            //  rvAyahsPages.setAdapter(mushaAudioAdapter);
            //   rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(ayah));
          vtwoMushaAudioAdapter = new vtwoMushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
         rvAyahsPages.setAdapter(vtwoMushaAudioAdapter);
          //  passageadapter = new passagevtwoMushaAudioAdapter(passage,this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
         //   rvAyahsPages.setAdapter(passageadapter);
        }


        rvAyahsPages.setItemAnimator(new DefaultItemAnimator());

        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFabmenu();
                DownloadIfnotPlay();
            }
        });

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
                    normalFooter.setVisibility(View.GONE);
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
                ArrayAdapter<String> spinnerReaderAdapter = new ArrayAdapter<>(ShowMushafActivity
                        .this, R.layout.spinner_layout_larg, R.id.spinnerText, readersNames);
                readers.setAdapter(spinnerReaderAdapter);

                for (int counter = 0; counter < readersNames.size(); counter++) {
                    if (readersNames.get(counter).trim().equals(selectedqari.trim())) {

                        readers.setSelection(counter);
                        break;

                    }


                }


                //      readersList.get(0).getId();
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
            normalFooter.setVisibility(View.GONE);
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
                        initializePlayer();

                    } else {
                        downloadFooter.setVisibility(View.GONE);
                        normalFooter.setVisibility(View.GONE);
                        playerFooter.setVisibility(View.GONE);
                    }

                }

            }
        }
    };


    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission((ShowMushafActivity.this), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
        List<String> downloadLin = new ArrayList<>();


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
            String s = downloadLink + chap.get(0).getChapterid() + AudioAppConstants.Extensions.MP3;

            downloadLin.add(s);
            Log.d("DownloadLinks", downloadLink + suraID + AudioAppConstants.Extensions.MP3);


        }
        //    ayaList.remove(0);
        //    quranbySurah.remove(0);
        return downloadLin;
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
//check if readerID is 0
        if (readerID == 0) {
            for (Qari qari : readersList) {
                if (qari.getName_english().equals(selectedqari)) {
                    readerID = qari.getId();
                    break;
                }

            }


        }

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
        if (v == findViewById(R.id.eqFrame)) {
            if (eqContainer.getVisibility() == View.GONE) {
                eqContainer.setVisibility(View.VISIBLE);
                final int sessionId = player.getAudioSessionId();
                Settings.isEditing = false;
                EqualizerFragment equalizerFragment = EqualizerFragment.newBuilder().setAccentColor(Color.CYAN)
                        .setAudioSessionId(sessionId).build();
                getSupportFragmentManager().beginTransaction().replace(R.id.eqFrame, equalizerFragment).commit();


            }
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

                initializePlayer();
                //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(getVersestartrange()));
                //  mushaAudioAdapter.notifyDataSetChanged();
                playerFooter.setVisibility(View.VISIBLE);
                audio_settings_bottom.setVisibility(View.GONE);
                mAddFab.shrink();


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

            Intent intent = new Intent(ShowMushafActivity.this, DownloadService.class);
            intent.putStringArrayListExtra(AudioAppConstants.Download.DOWNLOAD_LINKS, Links);
            intent.putExtra(AudioAppConstants.Download.DOWNLOAD_LOCATION, app_folder_path);
            startService(intent);

        }
    }


    @Override
    public void onItemClick(View v, int position) {
        Object istag = v.getTag();
        if (istag.equals("verse")) {
            onClickOrRange = true;
            int ayah1 = quranbySurahadapter.get(position).getAyah();

            isSingle = true;
            setVerselected(ayah1 - 1);
            setAyah(ayah1 - 1);
            DownloadIfnotPlay();
            isSingle = false;

        }

    }

    @Override
    public void onItemLongClick(int position, View v) {

        Object istag = v.getTag();
        if (istag.equals("verse")) {
            onClickOrRange = true;
            int ayah1 = quranbySurahadapter.get(position).getAyah();
            setAyah(ayah1);
            isStartFrom = true;
            setVerselected(ayah1 - 1);
            setAyah(ayah1 - 1);
            DownloadIfnotPlay();
            isStartFrom = false;

        }

    }


}

