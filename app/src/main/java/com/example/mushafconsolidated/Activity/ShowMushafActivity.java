package com.example.mushafconsolidated.Activity;

import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE;

import android.annotation.SuppressLint;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
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
import androidx.appcompat.widget.SwitchCompat;
import androidx.collection.ArraySet;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import com.example.utility.MovableFloatingActionButton;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import wheel.OnWheelChangedListener;
import wheel.WheelView;

public class ShowMushafActivity extends BaseActivity implements

        OnItemClickListenerOnLong, View.OnClickListener, StyledPlayerView.FullscreenButtonClickListener {
    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    private static final String KEY_SERVER_SIDE_ADS_LOADER_STATE = "server_side_ads_loader_state";
    ImageButton exo_settings, exo_close, exo_bottom_bar;
    private static final String KEY_ITEM_INDEX = "item_index";
    private static final String KEY_POSITION = "position";
    private String[] surahWheelDisplayData;
    private String[] ayahWheelDisplayData;
    //  private ImageView playbutton;
    private MaterialButton playbutton;
    int versestartrange, verseendrange;
    private int currenttrack;
    private boolean onClickOrRange = false;
    //  private LinkedHashMap<Integer, Integer> hlights;

    private ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();
    private LinkedHashMap<Integer, ArrayList<AyahCoordinate>> hlights = new LinkedHashMap<>();
    boolean flow = false;
    boolean singleline;
    private SwitchCompat chooseDisplaytype;

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
    //  FrameLayout eqContainer;

    public int getAudioType() {
        return audioType;
    }

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }
    // protected StyledPlayerView playerView;

    //    protected StyledPlayerControlView playerView;

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
    MovableFloatingActionButton playfb;

    // Use the ExtendedFloatingActionButton to handle the

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
    //  TextView startrange, startimage, endrange, endimage;
    MaterialTextView startrange, endrange;

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    //  private List<TranslationBook> booksInfo;
    private List<Qari> readersList;

    private ProgressBar mediaPlayerDownloadProgress;
    private BottomSheetBehavior exoplayerBottomBehaviour, audioSettingBottomBehaviour;
    FloatingActionButton resetfab, playlistfab;
    Player.Listener playbackStateListener;

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
            singleline=        getIntent().getBooleanExtra(Constants.MUSHAFDISPLAY,true);
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
        exoplayerBottomBehaviour = BottomSheetBehavior.from(bottomsheetexoplayer);
        exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);

        playerbottomsheet = findViewById(R.id.audio_settings_bottom);
        audioSettingBottomBehaviour = BottomSheetBehavior.from(playerbottomsheet);
        audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);

        initSpinner();
        if(!singleline) {
            initpassage();
        }
        //  initQuranPages();
        initRV();


    }

    private void initQuranPages() {
//todo
        List<QuranEntity> quranEntities = Utils.getQuranbySurah(surah);

        StringBuilder builder = new StringBuilder();
        ArrayList<Integer> ayahmat = new ArrayList<>();
        int counter = 1;
        int outerloop = 0;
        int pageloop;
        int nextpage;
        int indexsize = quranEntities.size();
        for (; outerloop <= quranEntities.size(); ) {
            int page = quranEntities.get(outerloop).getPage();
            // try {
            nextpage = quranEntities.get(outerloop + 1).getPage();
            //    } catch (IndexOutOfBoundsException e){
            //       break;
            //   }

            boolean whileloop = false;

            do {

                String aya = quranEntities.get(outerloop).getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntities.get(outerloop).getAyah()).append("} ﴾");

                aya = quranEntities.get(outerloop + 1).getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntities.get(outerloop + 1).getAyah()).append("} ﴾");
                outerloop++;
                whileloop = true;
                try {
                    boolean b = page == quranEntities.get(outerloop + 1).getPage();
                } catch (IndexOutOfBoundsException e) {
                    break;
                }

            } while (page == quranEntities.get(outerloop + 1).getPage());
     /*        while (page==quranEntities.get(outerloop+1).getPage()){
                 String aya = quranEntities.get(outerloop).getQurantext();
                 builder.append(aya).append("﴿ { ").append(quranEntities.get(outerloop).getAyah()).append("} ﴾");

                     aya = quranEntities.get(outerloop+1).getQurantext();
                     builder.append(aya).append("﴿ { ").append(quranEntities.get(outerloop+1).getAyah()).append("} ﴾");
                 outerloop++;
                 whileloop=true;

            }*/

            outerloop++;

            if (builder.toString().trim().length() != 0) {
                passage.put(page, builder.toString());
            }
            //    preparehighlightsNew(   quranEntities.get(outerloop).getPage()-1,builder, ayahmat);
            ayahmat = new ArrayList<>();
            builder = new StringBuilder();
        }

        System.out.println("CHECK");
    }

    private void initSpinner() {
        readers = (Spinner) findViewById(R.id.selectReaders);

        readers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                readerName = readers.getSelectedItem().toString();
                getReaderAudioLink(readerName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        readers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                readerName = readers.getSelectedItem().toString();
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


    }

    private void initpassage() {
        List<QuranEntity> quranEntities = Utils.getQuranbySurah(surah);
        StringBuilder builder = new StringBuilder();
        ArrayList<Integer> ayahmat = new ArrayList<>();
        int counter = 1;
        for (QuranEntity quranEntity : quranEntities) {
            if (quranEntity.getPassage_no() == 0) {
                String aya = quranEntity.getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntity.getAyah()).append("} ﴾");
                ayahmat.add(quranEntity.getAyah());
            } else if (quranEntity.getPassage_no() != 0) {
                String aya = quranEntity.getQurantext();
                builder.append(aya).append("﴿ { ").append(quranEntity.getAyah()).append("} ﴾");
                passage.put(counter, builder.toString());
                int ayah = quranEntity.getAyah();
                ayahmat.add(ayah + 1);

                preparehighlightsNew(quranEntity.getPassage_no(), builder, ayahmat);
                ayahmat = new ArrayList<>();
                builder = new StringBuilder();
                counter++;

            }
        }

        System.out.println("CHECK");
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
                 releasePlayer();
                RefreshActivity(sura, aya,false);
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

    private void RefreshActivity(String s, String aya, boolean b) {
        Log.e(TAG, "onClick called");
        final Intent intent = this.getIntent();
        //  surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        String parentActivityRef = intent.getStringExtra("PARENT_ACTIVITY_REF");
        if(b){

            intent.putExtra(Constants.MUSHAFDISPLAY, true);
            intent.putExtra(Constants.SURAH_INDEX, surah);
        }else if(s.isEmpty() && !b){
            intent.putExtra(Constants.MUSHAFDISPLAY, false);
            intent.putExtra(Constants.SURAH_INDEX, surah);
        }else       if (s.isEmpty()) {
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



            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack);

            if (null != holder) {
                try {
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown"))

                            holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                        else {
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);
                            holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.BLUE);
                            //for vtwoadapter
                           // Highlightverse(textView);

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack - 1);
            if (currenttrack > 1) {
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

            rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((currenttrack)));

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

    private Runnable NewsendUpdatesToUIPassage = new Runnable() {
        boolean trackchange = false;

        // int currentAdapterP=hlights.get(currenttrack-1).get(0).getPassage();
        public void run() {
  /*       if(currentAdapterP==0){
             currentAdapterP=1;
         }else if(currentAdapterP!=0){

             currentAdapterP++;
         }*/

            RecyclerView.ViewHolder holder = null;
            if (onClickOrRange) {
                if (hlights.get(currenttrack) != null) {
                    holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(getVersestartrange()).get(0).getPassage());
                    rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(hlights.get(getVersestartrange()).get(0).getPassage()));
                } else {
                    holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(currenttrack + 1).get(0).getPassage());
                    rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((hlights.get(currenttrack + 1).get(0).getPassage())));
                    currenttrack++;
                }

            } else {
                if (hlights.get(currenttrack) != null) {
                    holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(currenttrack).get(0).getPassage());
                    rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(hlights.get(currenttrack).get(0).getPassage()));
                } else {
                    holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(currenttrack + 1).get(0).getPassage());
                    rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((hlights.get(currenttrack + 1).get(0).getPassage())));
                    currenttrack++;
                }
            }

            if (null != holder) {
                try {
                    int drawingCacheBackgroundColor = holder.itemView.findViewById(R.id.rukuview).getDrawingCacheBackgroundColor();
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown")) {

                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.BLUE);
                        } else {
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.CYAN);

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    } else if (holder.itemView.findViewById(R.id.rukuview) != null) {
                        System.out.println("rukuvue");

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);
            int temp = 2;

            if (temp > 1) {
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

        private void setVerseHighLight(TextView textView, int foreGroundcoloer) {
            String str = String.valueOf(textView.getText());
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            try {

                span.setSpan(new ForegroundColorSpan(foreGroundcoloer), hlights.get(currenttrack).get(0).getStart(), hlights.get(currenttrack).get(0).getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception.toString());
            }
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

    private Runnable SinglesendUpdatesToUI = new Runnable() {
        boolean trackchange = false;


        public void run() {

            RecyclerView.ViewHolder holder = null;

                holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(currenttrack);
                rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((hlights.get(currenttrack + 1).get(0).getPassage())));





            if (null != holder) {
                try {
                    int drawingCacheBackgroundColor = holder.itemView.findViewById(R.id.rukuview).getDrawingCacheBackgroundColor();
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown")) {

                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.BLUE);

                            String str = String.valueOf(textView.getText());
                            SpannableStringBuilder span = new SpannableStringBuilder(str);
                            try {

                                span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                                textView.setText(span);
                                String[] split = str.split("\n");
                            } catch (IndexOutOfBoundsException exception) {
                                System.out.println(exception.toString());
                            }
                        } else {
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.BLUE);

                            String str = String.valueOf(textView.getText());
                            SpannableStringBuilder span = new SpannableStringBuilder(str);
                            span.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            textView.setText(span);

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    } else if (holder.itemView.findViewById(R.id.rukuview) != null) {
                        System.out.println("rukuvue");

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);
            int temp = 2;

            if (temp > 1) {
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

        private void setVerseHighLight(TextView textView, int foreGroundcoloer) {
            String str = String.valueOf(textView.getText());
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            try {

                span.setSpan(new ForegroundColorSpan(foreGroundcoloer), hlights.get(currenttrack).get(0).getStart(), hlights.get(currenttrack).get(0).getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception.toString());
            }
        }

        private void Highlightverse(TextView textView) {
            int start, end;
            String starta, endb;

            String str = String.valueOf(textView.getText());
            String[] split1 = str.split("﴿");

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
        boolean trackchange = false;

        // int currentAdapterP=hlights.get(currenttrack-1).get(0).getPassage();
        public void run() {
  /*       if(currentAdapterP==0){
             currentAdapterP=1;
         }else if(currentAdapterP!=0){

             currentAdapterP++;
         }*/

            RecyclerView.ViewHolder holder = null;
            if (hlights.get(currenttrack) != null) {
                holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(currenttrack).get(0).getPassage());
                rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((hlights.get(currenttrack + 1).get(0).getPassage())));
            } else {
                holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(hlights.get(currenttrack + 1).get(0).getPassage());
                rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((hlights.get(currenttrack + 1).get(0).getPassage())));
                currenttrack++;
            }


            if (null != holder) {
                try {
                    int drawingCacheBackgroundColor = holder.itemView.findViewById(R.id.rukuview).getDrawingCacheBackgroundColor();
                    if (holder.itemView.findViewById(R.id.quran_textView) != null) {
                        if (isNightmode.equals("brown")) {

                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.BLUE);
                        } else {
                            TextView textView = holder.itemView.findViewById(R.id.quran_textView);

                            setVerseHighLight(textView, Color.CYAN);

                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    } else if (holder.itemView.findViewById(R.id.rukuview) != null) {
                        System.out.println("rukuvue");

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(ShowMushafActivity.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }
            RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);
            int temp = 2;

            if (temp > 1) {
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

        private void setVerseHighLight(TextView textView, int foreGroundcoloer) {
            String str = String.valueOf(textView.getText());
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            try {

                span.setSpan(new ForegroundColorSpan(foreGroundcoloer), hlights.get(currenttrack).get(0).getStart(), hlights.get(currenttrack).get(0).getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception.toString());
            }
        }

        private void Highlightverse(TextView textView) {
            int start, end;
            String starta, endb;

            String str = String.valueOf(textView.getText());
            String[] split1 = str.split("﴿");

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
     //   handler.removeCallbacks(sendUpdatesToUIPassage);
        //  handler.postDelayed(sendUpdatesToUI, 1000);
    //    handler.postDelayed(sendUpdatesToUIPassage, 1000);
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

        if (Util.SDK_INT > 23) {
      //      this.releasePlayer();
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
        //    player.seekTo(startItemIndex, startPosition);
        }

        player.setMediaItems(marray, /* resetPosition= */ !haveStartPosition);
        player.prepare();
        setupHandler();
        if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (exoplayerBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
            player.play();
        }

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
            onClickOrRange = true;
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
                currenttrack += getAyah();
            }else {

                currenttrack++;
            }

        //    NewsendUpdatesToUIPassage.run();
                 if(!singleline) {
                     sendUpdatesToUIPassage.run();
                 }else{

              //     if(player.isPlaying()) {
                       sendUpdatesToUI.run();
                 //  }
                     //   handler.removeCallbacks(sendUpdatesToUI);
                       //   handler.removeCallbacks(sendUpdatesToUIPassage);
                       //  handler.postDelayed(sendUpdatesToUI, 1000);
                       //    handler.postDelayed(sendUpdatesToUIPassage, 1000);


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

    private void preparehighlightsNew(int passageno, StringBuilder str, ArrayList<Integer> ayahmat) {

        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);

        int ayahindex = ayahmat.get(0);
        int ayahmaz = ayahmat.size();

        String[] split1 = str.toString().split("﴿");
   /*     if(ayahindex==1) {
            int start = 0;
            int end = str.indexOf("1");
            AyahCoordinate ac = new AyahCoordinate(0, end,passageno);
            ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();


            Coordinates.add(ac);
            hlights.put(0, Coordinates);
          //  ayahindex++;
        }*/
        int start = 0;
        //  = str.indexOf("1");
        int end = str.indexOf(String.valueOf(ayahindex));
        AyahCoordinate acf = new AyahCoordinate(0, end, passageno);
        ArrayList<AyahCoordinate> Coordinatesf = new ArrayList<>();

        Coordinatesf.add(acf);
        hlights.put(ayahindex, Coordinatesf);
        //  ayahindex++;

        for (int i = 0; i < split1.length; i++) {
            int s = str.indexOf(String.valueOf(ayahindex));
            int e = str.indexOf(String.valueOf(ayahindex + 1));

            if (s != -1 && e != -1) {
                AyahCoordinate ac = new AyahCoordinate(s, e, passageno);
                ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();

                Coordinates.add(ac);
                hlights.put(++ayahindex, Coordinates);
            } else {
                System.out.println(s + " " + e);
            }
        }

        System.out.println("check");
    }

    private void preparehighlights(int passageno, StringBuilder str, ArrayList<Integer> ayahmat) {

        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) rvAyahsPages.findViewHolderForAdapterPosition(1);

        int ayahindex = ayahmat.get(0);

        String[] split1 = str.toString().split("﴿");
        if (ayahindex == 1) {
            int start = 0;
            int end = str.indexOf("1");
            AyahCoordinate ac = new AyahCoordinate(0, end, passageno);
            ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();

            Coordinates.add(ac);
            hlights.put(ayahindex, Coordinates);
            ayahindex++;
        }
        for (int i = 0; i < split1.length; i++) {
            int s = str.indexOf(String.valueOf(ayahindex));
            int e = str.indexOf(String.valueOf(ayahindex + 1));

            if (s != -1 && e != -1) {
                AyahCoordinate ac = new AyahCoordinate(s, e, passageno);
                ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();

                Coordinates.add(ac);
                hlights.put(ayahindex++, Coordinates);
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
        Coordinates.add(ac);
        hlights.put(0, Coordinates);
        for (int i = 1; i < split1.length; i++) {

            int s = str.indexOf(String.valueOf(i));
            int e = str.indexOf(String.valueOf(i + 1));
            if (s != -1 && e != -1) {
                ac = new AyahCoordinate(s, e);
                ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();

                Coordinates.add(ac);
                hlights.put(i, Coordinates);
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

    @SuppressLint("WrongViewCast")
    private void initRV() {
        chooseDisplaytype= findViewById(R.id.chooseDisplaytype);
        chooseDisplaytype.setOnClickListener(this);
        playfb = (MovableFloatingActionButton) findViewById(R.id.playfb);
        playfb.setOnClickListener(this);
        exo_settings = findViewById(R.id.exo_settings);
        exo_settings.setOnClickListener(this);
        exo_close = (ImageButton) findViewById(R.id.exo_close);
        exo_bottom_bar = (ImageButton) findViewById(R.id.exo_bottom_bar);
        playbutton = findViewById(R.id.playbutton);
        exo_close.setOnClickListener(this);
        playbutton.setOnClickListener(this);
        exo_bottom_bar.setOnClickListener(this);
        chooseDisplaytype.setChecked(singleline);

        startrange = findViewById(R.id.start_range);
        endrange = findViewById(R.id.endrange);

        startrange.setOnClickListener(this);

        endrange.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.ayahlist);
        playiv = (ImageView) findViewById(R.id.play);
        playiv.setOnClickListener(this);

        seekBar = findViewById(R.id.SeekBar01);

        footerContainer = (RelativeLayout) findViewById(R.id.footerbar);

        audio_settings_bottom = findViewById(R.id.audio_settings_bottom);
        normalFooter = (LinearLayout) findViewById(R.id.normalfooter);
        downloadFooter = (ConstraintLayout) findViewById(R.id.footerdownload);
        playerFooter = (RelativeLayout) findViewById(R.id.footerplayer);

        mediaPlayerDownloadProgress = (ProgressBar) findViewById(R.id.downloadProgress);
        chooseDisplaytype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    singleline=true;
                    RefreshActivity("","",true);
                }else{
                    singleline=false;
                    RefreshActivity("","",false);
                }
            }
        });
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
        playfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    //    audio_settings_bottom.setVisibility(View.VISIBLE);
                } else {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);

                }*/
                if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    audio_settings_bottom.setVisibility(View.VISIBLE);
                } else {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    //    audio_settings_bottom.setVisibility(View.GONE);
                }
                if (exoplayerBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    if(player!=null)
                    player.play();
                } else {
                    if(player!=null)
                  player.pause();
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }






                StringBuilder st = new StringBuilder();
                StringBuilder stt = new StringBuilder();
                st.append(getSurahName()).append("-").append(getSurahselected()).append(":").append("1");
                stt.append(getSurahName()).append("-").append(getSurahselected()).append(":").append(getVersescount());
                startrange.setText(st.toString());
                startrange.setText(stt.toString());


            }

        });

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
        // eqContainer.setVisibility(View.GONE);
        rvAyahsPages.setLayoutManager(manager);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        //  rvAyahsPages.setLayoutManager(mLayoutManager);

        //  rvAyahsPages.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvAyahsPages.setHasFixedSize(true);

        if (singleline) {
             mushaAudioAdapter = new MushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
             rvAyahsPages.setAdapter(mushaAudioAdapter);

        } else if (flow) {
            flowMushaAudioAdapter = new FlowMushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            rvAyahsPages.setAdapter(flowMushaAudioAdapter);

        } else {
            //    mushaAudioAdapter = new MushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            //  rvAyahsPages.setAdapter(mushaAudioAdapter);
            //   rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(ayah));
            //     vtwoMushaAudioAdapter = new vtwoMushaAudioAdapter(this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            //   rvAyahsPages.setAdapter(vtwoMushaAudioAdapter);
            passageadapter = new passagevtwoMushaAudioAdapter(passage, this, quranbySurahadapter, listener, chapter.get(0).getChapterid(), chapter.get(0).getNamearabic(), chapter.get(0).getIsmakki(), header);
            rvAyahsPages.setAdapter(passageadapter);
        }

        rvAyahsPages.setItemAnimator(new DefaultItemAnimator());

        exo_bottom_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurahAyahPicker(true, true);
            }
        });
        exo_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    player.stop();
              setVerselected(1);

                handler.removeCallbacks(sendUpdatesToUI);
                rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((0)));
              releasePlayer();
              initializePlayer();

         //    RefreshActivity("", " ", false);

            }
        });
        exo_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    audio_settings_bottom.setVisibility(View.VISIBLE);
                } else {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    //    audio_settings_bottom.setVisibility(View.GONE);
                }
                if (exoplayerBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    player.play();
                } else {
                    player.pause();
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
         /*       ThemeListPrefrence item = new ThemeListPrefrence();
                FragmentManager fragmentManager = ShowMushafActivity.this.getSupportFragmentManager();


                FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
                transactions.show(item);
                ThemeListPrefrence.newInstance().show(ShowMushafActivity.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);*/

            }
        });
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadIfnotPlay();
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
                    downloadLink = qari.getUrl();
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
                //chec

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

                initializePlayer();
                //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition(getVersestartrange()));
                //  mushaAudioAdapter.notifyDataSetChanged();
                playerFooter.setVisibility(View.VISIBLE);
                //  audio_settings_bottom.setVisibility(View.GONE);

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

            audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
         //   this.initializePlayer();
        }

    }

 /*   public void onResume() {
        super.onResume();

        if (Util.SDK_INT <= 23 || this.player == null) {
            this.initializePlayer();
        }

    }
*/



}

