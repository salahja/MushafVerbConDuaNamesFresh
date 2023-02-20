package com.example.mushafconsolidated.Activity;

import static android.text.TextUtils.concat;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.google.android.exoplayer2.util.RepeatModeUtil.REPEAT_TOGGLE_MODE_ONE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.JustJava.WbwSurah;
import com.example.mushafconsolidated.Adapters.FlowAyahWordAdapter;
import com.example.mushafconsolidated.Adapters.FlowAyahWordAdapterPassage;
import com.example.mushafconsolidated.Adapters.LineMushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.PageMushaAudioAdapter;
import com.example.mushafconsolidated.Adapters.vtwoMushaAudioAdapter;
import com.example.mushafconsolidated.Entities.AudioPlayed;
import com.example.mushafconsolidated.Entities.BadalErabNotesEnt;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.CorpusExpandWbwPOJO;
import com.example.mushafconsolidated.Entities.HalEnt;
import com.example.mushafconsolidated.Entities.LiajlihiEnt;
import com.example.mushafconsolidated.Entities.MafoolBihi;
import com.example.mushafconsolidated.Entities.MafoolMutlaqEnt;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.Qari;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.TameezEnt;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.mushafconsolidated.receivers.AudioAppConstants;
import com.example.mushafconsolidated.receivers.DownloadService;
import com.example.mushafconsolidated.receivers.FileManager;
import com.example.mushafconsolidated.receivers.QuranValidateSources;
import com.example.mushafconsolidated.receivers.Settingsss;
import com.example.mushafconsolidated.settings.Constants;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.FlowLayout;
import com.example.utility.MovableFloatingActionButton;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mm.prayer.muslimmate.model.AudioPositionSaved;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import wheel.OnWheelChangedListener;
import wheel.WheelView;

public class WordbywordMushafAct extends BaseActivity implements

        OnItemClickListenerOnLong, View.OnClickListener, StyledPlayerView.FullscreenButtonClickListener {


    // private UpdateMafoolFlowAyahWordAdapter flowAyahWordAdapter;
    private boolean mausoof, mudhaf, harfnasb, shart;
    private ArrayList<ChaptersAnaEntity> soraList;
    private EditText ayahIndex;
    private boolean kana;
    private List<QuranEntity> allofQuran;
    private SharedPreferences shared;
    //  private OnClickListener onClickListener;
    private ArrayList<CorpusAyahWord> corpusayahWordArrayList;

    private ArrayList<MafoolBihi> mafoolbihiwords;
    private ArrayList<HalEnt> Jumlahaliya;
    private ArrayList<TameezEnt> Tammezent;
    private ArrayList<MafoolMutlaqEnt> Mutlaqent;
    private ArrayList<LiajlihiEnt> Liajlihient;
    private ArrayList<com.example.mushafconsolidated.Entities.BadalErabNotesEnt> BadalErabNotesEnt;
    private Utils utils;
    private int isMakkiMadani;
    private int chapterno;
    private RecyclerView parentRecyclerView;
    private RecyclerView surahRecView;

    private static final String KEY_TRACK_SELECTION_PARAMETERS = "track_selection_parameters";
    private static final String KEY_SERVER_SIDE_ADS_LOADER_STATE = "server_side_ads_loader_state";
    ImageButton exo_settings, exo_close, exo_bottom_bar;
    private static final String KEY_ITEM_INDEX = "item_index";
    private static final String KEY_POSITION = "position";
    private String[] surahWheelDisplayData;
    private String[] ayahWheelDisplayData;
    int versestartrange, verseendrange;
    private int currenttrack;
    private int resumelastplayed;
    private boolean onClickOrRange = false;
    LinearLayout llStartRange, llEndRange;
    //  private LinkedHashMap<Integer, Integer> hlights;

    private final ArrayList<AyahCoordinate> Coordinates = new ArrayList<>();
    private final LinkedHashMap<Integer, ArrayList<AyahCoordinate>> hlights = new LinkedHashMap<>();
    boolean flow = false;
    boolean singleline,rangeRecitation;
    private ArrayList<Page> fullQuranPages;
    private long resumetrackposition;
    private boolean resume;

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
    PageMushaAudioAdapter passageadapter;
    private final LinkedHashMap<Integer, String> passage = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, String> pages = new LinkedHashMap<>();
    int audioType;
    String prevqari = "";
    private final Handler handler = new Handler();
    private static final String KEY_AUTO_PLAY = "auto_play";
    private List<MediaItem> marray;
    private List<MediaItem> marrayrange=new ArrayList<>();
    private String singleverse;

    private boolean isSingle;
    private boolean isStartFrom = false;
    private List<QuranEntity> quranbySurahadapter;
    private MaterialButton resetplayer;
    private SharedPreferences sharedPreferences;
    private String selectedqari;
    MaterialTextView qariname,ayaprogress;
    ImageView buffering;
    MaterialButton canceldownload;

    public void setPrevqari(String prevqari) {
        this.prevqari = prevqari;
    }

    //  FrameLayout eqContainer;

    public void setAudioType(int audioType) {
        this.audioType = audioType;
    }
    // protected StyledPlayerView playerView;

    //    protected StyledPlayerControlView playerView;

    protected PlayerControlView playerView;

    protected @Nullable ExoPlayer player;

    private TrackSelectionParameters trackSelectionParameters;

    private Tracks lastSeenTracks;
    private boolean startAutoPlay;
    private int startItemIndex;
    private long startPosition;

    private final LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> ruku = new LinkedHashMap<>();
    // For ad playback only.
    public static final String BROADCAST_SEEKBAR = "com.example.mushafconsolidated.Activity.sendseekbar";
    public static int readerID;

    public String getSurahNameArabic() {
        return surahNameArabic;
    }

    public void setSurahNameArabic(String surahNameArabic) {
        this.surahNameArabic = surahNameArabic;
    }

    private boolean pausePlayFlag;
    public static String downloadLink, readerName;
    public static boolean startBeforeDownload;

    private static final String TAG = "ShowMushafActivity";

    int surahselected, verselected, versescount;
    String surahNameEnglish, surahNameArabic;
    private String isNightmode;

    public int getVersescount() {
        return versescount;
    }

    public void setVersescount(int versescount) {
        this.versescount = versescount;
    }

    public String getSurahNameEnglish() {
        return surahNameEnglish;
    }

    public void setSurahNameEnglish(String surahNameEnglish) {
        this.surahNameEnglish = surahNameEnglish;
    }
// LinearLayout fabLayout1, fabLayout2,fabLayout3;

    //  FloatingActionButton fab, fab1, fab2, fab3;
    MovableFloatingActionButton playfb;

    // Use the ExtendedFloatingActionButton to handle the

    // These TextViews are taken to make visible and
    // invisible along with FABs except parent FAB's action
    // name
    TextView resetfbtxt;

    public int getVerselected() {
        return verselected;
    }

    public void setVerselected(int verselected) {
        this.verselected = verselected;
    }

    @Override
    public void onBackPressed() {

        //unregister broadcast for download ayat
        LocalBroadcastManager.getInstance(WordbywordMushafAct.this).unregisterReceiver(downloadPageAya);
        //stop flag of auto start
        startBeforeDownload = false;


        if (player != null) {
            player.release();
        }
        SharedPreferences pref = getSharedPreferences("lastaya", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
    //    editor.putInt("lastaya", currenttrack);
//        editor.putInt("trackposition", hlights.get(currenttrack).get(0).getPassage());
        editor.apply();


        super.onBackPressed();
    }

    boolean isMusicplaying = false;
    private int surah;

    public int getSurahselected() {
        return surahselected;
    }

    public void setSurahselected(int surahselected) {
        this.surahselected = surahselected;
    }

    RecyclerView recyclerView;

    Utils repository;

    LineMushaAudioAdapter lineMushaAudioAdapter;
    vtwoMushaAudioAdapter vtwoMushaAudioAdapter;
    Typeface typeface;
    TextView txtView;
    //   int pos;

    private static final int songEnded = 0;

    private Spinner translationBooks, readers;
    private RelativeLayout downloadFooter;
    private LinearLayout normalFooter;
    private RelativeLayout playerFooter, audio_settings_bottom;

    //  TextView startrange, startimage, endrange, endimage;
    MaterialTextView startrange, endrange;

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    //  private List<TranslationBook> booksInfo;
    private List<Qari> readersList;

    private ProgressBar mediaPlayerDownloadProgress;
    private BottomSheetBehavior exoplayerBottomBehaviour, audioSettingBottomBehaviour;
    FloatingActionButton resetfab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wbwaudio);
        //    ButterKnife.bind(this);
        //    QuranGrammarApplication.appContext = ShowMushafActivity.this;
        //  intentmyservice = new Intent(this, AudioService.class);
        Intent intent = new Intent(BROADCAST_SEEKBAR);
        getpreferences();
        getLastPlayed();
        sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        isNightmode = sharedPreferences.getString("themepref", "dark");
        //  repository = Utils.getInstance(getApplication());
        repository = new Utils(this);
        typeface = Typeface.createFromAsset(getAssets(), "me_quran.ttf");
        selectedqari = sharedPreferences.getString("qari", "35");

        //region Description
        if (getIntent().hasExtra(Constants.SURAH_INDEX)) {
            surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
            singleline = getIntent().getBooleanExtra(Constants.MUSHAFDISPLAY, false);

            setSurahselected(surah);
            //   getIntent().getIntExtra(Constants.SURAH_GO_INDEX, 1);
            int ayahtrack = getIntent().getIntExtra(Constants.AYAH_GO_INDEX, 0);
            if (ayahtrack > 0) {
                setStartPosition(ayahtrack);
            }
            Log.d(TAG, "onCreate: ayah  " + ayah);

            //       showMessage(String.valueOf(pos));D

        }
        //endregion

        playerView = findViewById(R.id.player_view);

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
        RelativeLayout bottomsheetexoplayer = findViewById(R.id.footerplayer);
        exoplayerBottomBehaviour = BottomSheetBehavior.from(bottomsheetexoplayer);
        exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);


        RelativeLayout playerbottomsheet = findViewById(R.id.audio_settings_bottom);
        audioSettingBottomBehaviour = BottomSheetBehavior.from(playerbottomsheet);
        audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        recyclerView = (RecyclerView) findViewById(R.id.rvAyahsPages);



        initSpinner();
        if (!singleline) {
            loadFullQuran();
            prepares();
            System.out.println("check");
        }

        initRV();


    }

    private void getpreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("lastreadmushaf", MODE_PRIVATE);
        surah = pref.getInt(CHAPTER, 20);
        int pagenum = pref.getInt("page", 1);
        setSurahselected(surah);

    }

    private void getLastPlayed() {
        AudioPositionSaved aplayed = ConfigPreferences.getLastPlayedAudio(this, String.valueOf(surah));
        if(aplayed!=null) {
            resumelastplayed = aplayed.getAudiopsaved().get(0).getAyah();
            resumetrackposition = aplayed.getAudiopsaved().get(0).getTrackposition();


        }
    }

    private void loadFullQuran() {
        List<Page> pages = new ArrayList<>();
        List<QuranEntity> quranEntities = Utils.getQuranbySurah(surah);
        int firstpage = quranEntities.get(0).getPage();

        Page page;
        List<QuranEntity> ayahItems;
        for (int i = firstpage; i <= quranEntities.get(quranEntities.size() - 1).getPage(); i++) {
            ayahItems = repository.getAyahsByPageQuran(surah, i);
            if (ayahItems.size() > 0) {
                page = new Page();
                page.setAyahItemsquran(ayahItems);
                //    page.se(ayahItems);
                page.setPageNum(i);
                page.setJuz(ayahItems.get(0).getJuz());
                pages.add(page);
            }
        }

        fullQuranPages = new ArrayList<>(pages);


    }

    private void prepares() {
        int counter = 1;
        for (int i = 0; i < fullQuranPages.size(); i++) {
            Page page = fullQuranPages.get(i);

            String aya = "";
            StringBuilder builder = new StringBuilder();
            ArrayList<Integer> ayahmat = new ArrayList<>();
            for (QuranEntity ayahItem : page.getAyahItemsquran()) {
                aya = ayahItem.getQurantext();

                builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", aya, ayahItem.getAyah()));
                ayahmat.add(ayahItem.getAyah());
            }
            preparehighlightsNew(counter, builder, ayahmat);
            ayahmat = new ArrayList<>();
            builder = new StringBuilder();
            counter++;

        }


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
                ArrayAdapter<String> spinnerReaderAdapter = new ArrayAdapter<>(WordbywordMushafAct
                        .this, R.layout.spinner_layout_larg, R.id.spinnerText, readersNames);
                readers.setAdapter(spinnerReaderAdapter);

                for (int counter = 0; counter < readersNames.size(); counter++) {
                    if (readersNames.get(counter).trim().equals(selectedqari.trim())) {

                        readers.setSelection(counter);
                        break;

                    }


                }

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
        Utils utils = new Utils(WordbywordMushafAct.this);
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
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(WordbywordMushafAct.this);
        Dialog dlg = new Dialog(WordbywordMushafAct.this);
        //  AlertDialog dialog = builder.create();
        ArrayList<ChaptersAnaEntity> soraList = utils.getAllAnaChapters();
        LayoutInflater inflater = WordbywordMushafAct.this.getLayoutInflater();
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
                setSurahNameEnglish(soraList.get(chapterno[0] - 1).getNameenglish());
                setSurahNameArabic(soraList.get(chapterno[0] - 1).getNamearabic());

                SharedPreferences pref = getSharedPreferences("lastreadmushaf", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(CHAPTER, Integer.parseInt(sura));
                //  editor.putInt("page", page.getAyahItemsquran().get(0).getPage());

                editor.putString(SURAH_ARABIC_NAME, soraList.get(chapterno[0] - 1).getNamearabic());
                editor.apply();
            }

            int verse = verseno[0];

            // setSurahselected(Integer.parseInt(sura));
            setAyah(verse);

            String aya = String.valueOf(verseno[0]);

            if (isrefresh && starttrue) {
                releasePlayer();
                RefreshActivity(sura, aya, false);
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
        int db = ContextCompat.getColor(this, R.color.odd_item_bg_dark_blue_light);

        if (preferences.equals("light")) {
        //    alertDialog.getWindow().setBackgroundDrawableResource(R.color.md_theme_dark_onSecondary);
            alertDialog.getWindow().setBackgroundDrawableResource(R.color.background_color_light_brown);
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
        buttonPositive.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.green));
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.red));
        if (preferences.equals("light")) {
            buttonPositive.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.red));;

        } else if (preferences.equals("brown")) {
            buttonPositive.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.red));
            //  cardview.setCardBackgroundColor(ORANGE100);
        } else if (preferences.equals("blue")) {
            buttonPositive.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.Goldenrod));
            //  cardview.setCardBackgroundColor(db);
        } else if (preferences.equals("green")) {
            buttonPositive.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.cyan_light));
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

        st.append(getSurahNameEnglish()).append("-").append(getSurahselected()).append(":").append(getAyah());
        setVerseendrange(verse);
        endrange.setText(st.toString());
        rangeRecitation=true;
    }

    private void updateStartRange(int verse) {

        StringBuilder st = new StringBuilder();
        StringBuilder stt = new StringBuilder();
        st.append(getSurahNameEnglish()).append("-").append(getSurahselected()).append(":").append(getAyah());
        setVersestartrange(verse);
        startrange.setText(st.toString());
        rangeRecitation=true;

    }

    private void RefreshActivity(String s, String aya, boolean b) {
        Log.e(TAG, "onClick called");
        final Intent intent = this.getIntent();
        //  surah = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        String parentActivityRef = intent.getStringExtra("PARENT_ACTIVITY_REF");
        if (b) {

            intent.putExtra(Constants.MUSHAFDISPLAY, true);
            intent.putExtra(Constants.SURAH_INDEX, surah);
        } else if (s.isEmpty() && !b) {
            intent.putExtra(Constants.MUSHAFDISPLAY, false);
            intent.putExtra(Constants.SURAH_INDEX, surah);
        } else if (s.isEmpty()) {
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





    private final Runnable SinglesendUpdatesToUI = new Runnable() {
        final boolean trackchange = false;

        public void run() {

            RecyclerView.ViewHolder holder = null;

            holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(currenttrack);
            recyclerView.post(() -> recyclerView.scrollToPosition(currenttrack));
            StringBuilder ab=new StringBuilder();
            ab.append("Aya").append(":").append(currenttrack).append(" ").append("of").append(getVersescount());
            ayaprogress.setText(ab.toString());

            if (null != holder) {
                try {

                    if (holder.itemView.findViewById(R.id.flow_word_by_word) != null) {

                        if(isNightmode.equals("light")){
                            TextView textViews =      holder.itemView.findViewById(R.id.flow_word_by_word).findViewById(R.id.word_arabic_textView);
                            holder.itemView.findViewById(R.id.flow_word_by_word).setBackgroundColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.horizontalview_color_ab));


                        }
                     else    if (isNightmode.equals("brown")) {
                            TextView textViews =      holder.itemView.findViewById(R.id.flow_word_by_word).findViewById(R.id.word_arabic_textView);
                            holder.itemView.findViewById(R.id.flow_word_by_word).setBackgroundColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.bg_surface_brown));

                        } else {
                            TextView textViews =      holder.itemView.findViewById(R.id.flow_word_by_word).findViewById(R.id.word_arabic_textView);
                                holder.itemView.findViewById(R.id.flow_word_by_word).setBackgroundColor(ContextCompat.getColor(WordbywordMushafAct.this, R.color.bg_surface_dark_blue));
                            //   holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Constant.MUSLIMMATE);
                        }

                    } else if (holder.itemView.findViewById(R.id.rukuview) != null) {
                        System.out.println("rukuvue");

                    }
                } catch (NullPointerException exception) {
                    Toast.makeText(WordbywordMushafAct.this, "null pointer udapte", Toast.LENGTH_SHORT).show();
                }
            }

            int temp = 2;

            if (currenttrack > 1) {
                RecyclerView.ViewHolder holderp = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(currenttrack-1);
                if (null != holderp) {
                    try {
                        int drawingCacheBackgroundColor = holderp.itemView.findViewById(R.id.flow_word_by_word).getDrawingCacheBackgroundColor();



                        if (holderp.itemView.findViewById(R.id.flow_word_by_word) != null) {
                            //    holder.itemView.findViewById(R.id.quran_textView).setBackgroundColor(Color.CYAN);
                            holderp.itemView.findViewById(R.id.flow_word_by_word).setBackgroundColor(drawingCacheBackgroundColor);

                        }
                    } catch (NullPointerException exception) {
                        Toast.makeText(WordbywordMushafAct.this, "UPDATE HIGHLIGHTED", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            //  rvAyahsPages.post(() -> rvAyahsPages.scrollToPosition((ayah)));

            handler.postDelayed(this, 1000);


        }

        private void setVerseHighLight(TextView textView, int foreGroundcoloer) {
            String str = String.valueOf(textView.getText());
            SpannableStringBuilder span = new SpannableStringBuilder(str);
            try {

                span.setSpan(new ForegroundColorSpan(foreGroundcoloer), hlights.get(currenttrack).get(0).getStart(), hlights.get(currenttrack).get(0).getEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(span);
                String[] split = str.split("\n");
            } catch (IndexOutOfBoundsException exception) {
                System.out.println(exception);
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
                System.out.println(exception);
            }
        }


    };


    private void setupHandlerSingle() {
        handler.removeCallbacks(SinglesendUpdatesToUI);

        handler.postDelayed(SinglesendUpdatesToUI, 1000);

    }



    protected void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            player.release();
            player = null;
            playerView.setPlayer(/* player= */ null);
            List<MediaItem> mediaItems = Collections.emptyList();
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
            playerFooter.setVisibility(View.VISIBLE);
          //  normalFooter.setVisibility(View.GONE);
            downloadFooter.setVisibility(View.GONE);
            boolean stream = false;
            long playbackPosition = 0L;
            if (stream) {

                String streamUrl = "https://ia800204.us.archive.org/17/items/AbdAlrahman-Al3osy/009.mp3";
                player =
                        new ExoPlayer.Builder(WordbywordMushafAct.this)
                                .setMediaSourceFactory(
                                        new DefaultMediaSourceFactory(WordbywordMushafAct.this).setLiveTargetOffsetMs(5000))
                                .build();

// Per MediaItem settings.
                MediaItem mediaItem = MediaItem.fromUri(streamUrl);
                player.addMediaItem(mediaItem);
                //  player = new ExoPlayer.Builder(this).build();
                lastSeenTracks = Tracks.EMPTY;

                player.addListener(new WordbywordMushafAct.PlayerEventListener());
                player.setTrackSelectionParameters(trackSelectionParameters);
                player.addListener(new WordbywordMushafAct.PlayerEventListener());
                player.addAnalyticsListener(new EventLogger());
                player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
                player.setPlayWhenReady(startAutoPlay);
                player.setRepeatMode(Player.REPEAT_MODE_ALL);
                player.getBufferedPercentage();

                playerView.setRepeatToggleModes(REPEAT_TOGGLE_MODE_ONE);

                player.seekTo(getAyah(), playbackPosition);
                if (getVersestartrange() != 0) {
                    setAyah(getVersestartrange());
                }

                playerView.setPlayer(player);
                player.prepare();
                String str = getSurahNameEnglish().concat(":").concat(readerName);
                qariname.setText(str);
                player.play();


            } else {
          //      myPlayer mp=new myPlayer(ShowMushafActivity.this,surah);
                
                marray = createMediaItems();
                if (marray.isEmpty()) {
                    return false;
                }

                player = new ExoPlayer.Builder(this).build();
                lastSeenTracks = Tracks.EMPTY;

                player.addListener(new WordbywordMushafAct.PlayerEventListener());
                player.setTrackSelectionParameters(trackSelectionParameters);
                player.addListener(new WordbywordMushafAct.PlayerEventListener());
                player.addAnalyticsListener(new EventLogger());
                player.setAudioAttributes(AudioAttributes.DEFAULT, /* handleAudioFocus= */ true);
                player.setPlayWhenReady(startAutoPlay);
                player.setRepeatMode(Player.REPEAT_MODE_ALL);

                playerView.setRepeatToggleModes(REPEAT_TOGGLE_MODE_ONE);

                player.seekTo(getAyah(), playbackPosition);
                if (getVersestartrange() != 0) {
                    setAyah(getVersestartrange());
                }

                playerView.setPlayer(player);
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

                boolean haveStartPosition = startItemIndex != C.INDEX_UNSET;
                if (haveStartPosition) {
                    //    player.seekTo(startItemIndex, startPosition);
                }

                if(rangeRecitation) {
                    marrayrange = marray.subList(getVersestartrange(), getVerseendrange());
                    player.setMediaItems(marrayrange, /* resetPosition= */ !haveStartPosition);
                }else {
                    player.setMediaItems(marray, /* resetPosition= */ !haveStartPosition);
                }

                String str = ("(").concat(getSurahNameArabic().concat(")").concat("(").concat(getSurahNameEnglish().concat(")").concat(":")
                        .concat(readerName)));

                qariname.setText(str);
                //   qariname.setText(readerName);
                player.prepare();

                if (resume) {
                    player.seekToDefaultPosition(resumelastplayed);
                }

                if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                if (exoplayerBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                    player.play();
                }

             /*  player.getCurrentAdGroupIndex();
                player.setTrackSelectionParameters(
                        player.getTrackSelectionParameters()
                                .buildUpon()
                                .setOverrideForType(
                                        new TrackSelectionOverride(
                                                audioTrackGroup.getMediaTrackGroup(),
                                                *//* trackIndex= *//* 0))
                                .build());*/
            }
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
   /*     if (getVersestartrange() != 0 && getVerseendrange() != 0) {
            onClickOrRange = true;
            List<QuranEntity> quranbySurah = Utils.getQuranbySurahAyahrange(surah, getVersestartrange(), getVerseendrange());
            for (QuranEntity ayaItem : quranbySurah) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }
        } else */if (isSingle) {
            List<QuranEntity> sngleverseplay = Utils.getsurahayahVerses(getSurahselected(), getVerselected());
            //Create files locations for the all page ayas
            for (QuranEntity ayaItem : sngleverseplay) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));

            }

        } else if (isStartFrom) {
            onClickOrRange = true;
            List<QuranEntity> fromrange = Utils.getQuranbySurahAyahrange(getSurahselected(), getVerselected(), chap.get(0).getVersescount());

            for (QuranEntity ayaItem : fromrange) {
                ayaLocations.add(FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah()));
                String location = FileManager.createAyaAudioLinkLocation(this, readerID, ayaItem.getAyah(), ayaItem.getSurah());
                marray.add(MediaItem.fromUri(location));
            }


        } else {
            List<QuranEntity> quranbySurah = Utils.getQuranbySurah(getSurahselected());
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

    public void pauseplay() {
        if(player!=null) {
            player.pause();
        }
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
                assert player != null;
                player.seekToDefaultPosition();
                player.prepare();
            } else {
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
            System.out.println("oldpostion" + " " + oldPosition + "newpostion " + " " + newPosition + "reason" + " " + reason);
            System.out.println("check");
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(Tracks tracks) {
            //   updateButtonVisibility();
            Tracks currentTracks = player.getCurrentTracks();

            currenttrack = player.getCurrentMediaItemIndex();
            //     currenttrack=resumelastplayed;
            boolean resume = true;

            System.out.println("Ayah" + "" + ayah);


            if(rangeRecitation) {
                currenttrack += getVersestartrange();
                currenttrack++;
            }else if (onClickOrRange) {
                currenttrack += getAyah();
            } else {

                currenttrack++;

            }
        singleline=true;
            //    NewsendUpdatesToUIPassage.run();

                if (player.isPlaying() || player.getPlayWhenReady()) {
                    SinglesendUpdatesToUI.run();
                 //   sendUpdatesToUI.run();
                } else {
                    handler.removeCallbacks(SinglesendUpdatesToUI);
                }



            if (tracks == lastSeenTracks) {
                return;
            }

            if (tracks.containsType(C.TRACK_TYPE_AUDIO)
                    && !tracks.isTypeSupported(C.TRACK_TYPE_AUDIO, /* allowExceedsCapabilities= */ true)) {
                //showToast(R.string.error_unsupported_audio);
            }
            lastSeenTracks = tracks;
        }
    }

    private void preparehighlightsNew(int passageno, StringBuilder str, ArrayList<Integer> ayahmat) {

        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

        int ayahindex = ayahmat.get(0);
        int ayahmaz = ayahmat.size();

        String[] split1 = str.toString().split("﴿");
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

        RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) recyclerView.findViewHolderForAdapterPosition(1);

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

    protected void clearStartPosition() {
        startAutoPlay = true;
        startItemIndex = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

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
        ExecuteSurahWordByWord();
        canceldownload = (MaterialButton) findViewById(R.id.canceldownload);
        canceldownload.setOnClickListener(this);
        ayaprogress= (MaterialTextView) findViewById(R.id.ayaprogress);
        qariname = (MaterialTextView) findViewById(R.id.lqari);
        buffering = (ImageView) findViewById(R.id.exo_buffering);
        SwitchCompat chooseDisplaytype = findViewById(R.id.chooseDisplaytype);
        chooseDisplaytype.setOnClickListener(this);
        playfb = (MovableFloatingActionButton) findViewById(R.id.playfb);
        playfb.setOnClickListener(this);
        exo_settings = findViewById(R.id.exo_settings);
        exo_settings.setOnClickListener(this);
        exo_close = (ImageButton) findViewById(R.id.exo_close);
        exo_bottom_bar = (ImageButton) findViewById(R.id.exo_bottom_bar);
        //  private ImageView playbutton;
        MaterialButton playbutton = findViewById(R.id.playbutton);
        MaterialButton playresume = (MaterialButton) findViewById(R.id.playresume);
        playresume.setOnClickListener(this);
        MaterialButton surahselection = (MaterialButton) findViewById(R.id.surahselection);
        surahselection.setOnClickListener(this);
        exo_close.setOnClickListener(this);
        playbutton.setOnClickListener(this);
        exo_bottom_bar.setOnClickListener(this);
        chooseDisplaytype.setChecked(singleline);

        startrange = findViewById(R.id.start_range);
        endrange = findViewById(R.id.endrange);

        startrange.setOnClickListener(this);

        llStartRange = (LinearLayout) findViewById(R.id.llStartRange);
        llStartRange.setOnClickListener(this);
        endrange.setOnClickListener(this);
        llEndRange = (LinearLayout) findViewById(R.id.llEndRange);
        llEndRange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean starttrue = false;
                SurahAyahPicker(false, starttrue);
            }
        });

        llStartRange.setOnClickListener(new View.OnClickListener() {

           final boolean starttrue = true;


            @Override
            public void onClick(View v) {
              marrayrange=null;
                SurahAyahPicker(false, starttrue);
            }
        });

        ListView listView = (ListView) findViewById(R.id.ayahlist);


        SeekBar seekBar = findViewById(R.id.SeekBar01);

        RelativeLayout footerContainer = (RelativeLayout) findViewById(R.id.footerbar);

        audio_settings_bottom = findViewById(R.id.audio_settings_bottom);
      //  normalFooter = (LinearLayout) findViewById(R.id.normalfooter);
        downloadFooter = (RelativeLayout) findViewById(R.id.footerdownload);
        playerFooter = (RelativeLayout) findViewById(R.id.footerplayer);

        mediaPlayerDownloadProgress = (ProgressBar) findViewById(R.id.downloadProgress);
        chooseDisplaytype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    singleline = true;
                    RefreshActivity("", "", true);
                } else {
                    singleline = false;
                    RefreshActivity("", "", false);
                }
            }
        });
        startrange.setOnClickListener(new View.OnClickListener() {
            final boolean starttrue = true;

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
        surahselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurahAyahPicker(true, true);
            }
        });
        playfb.setOnClickListener(new View.OnClickListener() {
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
                    if (player != null)
                        player.play();
                } else {
                    if (player != null)
                        player.pause();
                    exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

                StringBuilder st = new StringBuilder();
                StringBuilder stt = new StringBuilder();
                st.append(getSurahNameEnglish()).append("-").append(getSurahselected()).append(":").append("1");
                stt.append(getSurahNameEnglish()).append("-").append(getSurahselected()).append(":").append(getVersescount());
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



        exo_bottom_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SurahAyahPicker(true, true);
            }
        });
        exo_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //reset player
                setVerselected(1);
                setVersestartrange(0);
                setVerseendrange(0);
                setAyah(0);
                marrayrange=null;
                resume=false;
                rangeRecitation=false;

                handler.removeCallbacks(SinglesendUpdatesToUI);
                recyclerView.post(() -> recyclerView.scrollToPosition((0)));
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
                    assert player != null;
                    player.play();
                } else {
                    if(null!=player) {
                        player.pause();
                        exoplayerBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

            }
        });
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadIfnotPlay();
            }
        });

        playresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resume = true;
                DownloadIfnotPlay();


            }
        });

        canceldownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFooter.setVisibility(View.GONE);
              //  normalFooter.setVisibility(View.VISIBLE);
                //stop flag of auto start audio after download
                startBeforeDownload = false;
                //stop download service
                stopService(new Intent(WordbywordMushafAct.this, DownloadService.class));

            }
        });

        // to preserver quran direction from right to left
        recyclerView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

    }

    public void ExecuteSurahWordByWord() {
        utils=new Utils(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ThemeOverlay_Material3_Dialog);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();
        corpusayahWordArrayList = new ArrayList<>();
        mafoolbihiwords = new ArrayList<>();
        Jumlahaliya = new ArrayList<>();
        Tammezent = new ArrayList<>();
        Liajlihient = new ArrayList<>();
        Jumlahaliya = utils.getHaliaErabBysurah(surah);
        Liajlihient = utils.getMafoolLiajlihisurah(surah);
        //  mafoolbihiwords =utils.getMafoolBihiErabSurah(surah);
        mafoolbihiwords = utils.getMafoolBySurah(surah);
        Tammezent = utils.getTameezsurah(surah);
        Mutlaqent = utils.getMutlaqsurah(surah);
        BadalErabNotesEnt = utils.getBadalrabSurah(surah);
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            //do inbackground
            WordbywordMushafAct.this.bysurah(dialog, ex);

        });

    }

    private void bysurah(AlertDialog dialog, ExecutorService ex) {
        runOnUiThread(dialog::show);
        WbwSurah wbwSurah=new WbwSurah(WordbywordMushafAct.this, surah, corpusayahWordArrayList,ruku);
        wbwSurah.getWordbyword();
        CorpusUtilityorig corpus = new CorpusUtilityorig(this);
        //      corpus.highLightVerbs(corpusayahWordArrayList,surah_id);
        if (kana) {
            corpus.setKana(corpusayahWordArrayList, surah);

        }
        if (shart) {
            corpus.setShart(corpusayahWordArrayList, surah);
        }
        if (mudhaf) {
            corpus.setMudhafFromDB(corpusayahWordArrayList, surah);

        }
        if (mausoof) {
            corpus.SetMousufSifaDB(corpusayahWordArrayList, surah);
            //  corpus.NewMAOUSOOFSIFA(corpusayahWordArrayList);
        }
        if (harfnasb) {
            corpus.newnewHarfNasbDb(corpusayahWordArrayList, surah);
        }
        //     corpus.highLightVerbs(corpusayahWordArrayList,surah_id);
        //post
        runOnUiThread(() -> {
            dialog.dismiss();
            ex.shutdown();
            recyclerView = findViewById(R.id.rvAyahsPages);
            allofQuran = Utils.getQuranbySurah(surah);
            ArrayList<ChaptersAnaEntity> chapter = repository.getSingleChapter(surah);
            //  initlistview(quranbySurah, chapter);

            OnItemClickListenerOnLong listener = this;
            ArrayList<String> header = new ArrayList<>();
            header.add(String.valueOf(chapter.get(0).getRukucount()));
            header.add(String.valueOf(chapter.get(0).getVersescount()));
            header.add(String.valueOf(chapter.get(0).getChapterid()));
            header.add(chapter.get(0).getAbjadname());
            header.add(chapter.get(0).getNameenglish());
            setVersescount(chapter.get(0).getVersescount());
            setSurahNameEnglish(chapter.get(0).getNameenglish());
            setSurahNameArabic(chapter.get(0).getNamearabic());
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);

            recyclerView.setHasFixedSize(true);

            manager.setOrientation(LinearLayoutManager.VERTICAL);

/*
            recyclerView.setLayoutManager(manager);
            flowAyahWordAdapterpassage = new FlowAyahWordAdapterPassage(ruku, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, WordbywordMushafAct.this, surah, getSurahNameEnglish(), isMakkiMadani, listener);
            flowAyahWordAdapterpassage.addContext(WordbywordMushafAct.this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(flowAyahWordAdapterpassage);
            flowAyahWordAdapterpassage.notifyDataSetChanged();
*/



           // recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(manager);
            FlowAyahWordAdapter flowAyahWordAdapter = new FlowAyahWordAdapter(true,ruku, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, WordbywordMushafAct.this, surah, getSurahNameArabic(), isMakkiMadani, listener);
            flowAyahWordAdapter.addContext(WordbywordMushafAct.this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(flowAyahWordAdapter);
            flowAyahWordAdapter.notifyDataSetChanged();
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        //    parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));




        });

    }
    public void getReaderAudioLink(String readerName) {
        for (Qari reader : readersList) {

            if (reader.getName_english().equals(readerName) && Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                downloadLink = reader.getUrl();
                readerID = reader.getId();
                audioType = reader.getAudiotype();
                break;
            } else if (reader.getName_english().equals(readerName)) {
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
        //    normalFooter.setVisibility(View.GONE);
        } else {
            if (downloadFooter.getVisibility() != View.VISIBLE) {
                playerFooter.setVisibility(View.VISIBLE);
            } else {
                playerFooter.setVisibility(View.GONE);
            }
         //   normalFooter.setVisibility(View.GONE);
        }

        if (audioSettingBottomBehaviour.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if (exoplayerBottomBehaviour.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            audioSettingBottomBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
            player.play();
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
                  //  normalFooter.setVisibility(View.GONE);
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
                    if (startBeforeDownload) {
                        //change views
                        downloadFooter.setVisibility(View.GONE);
                    //    normalFooter.setVisibility(View.GONE);
                        playerFooter.setVisibility(View.VISIBLE);
                        initializePlayer();

                    } else {
                        downloadFooter.setVisibility(View.GONE);
                     //   normalFooter.setVisibility(View.GONE);
                        playerFooter.setVisibility(View.GONE);
                    }

                }

            }
        }
    };

    public List<String> createDownloadLink() {
        List<ChaptersAnaEntity> chap = repository.getSingleChapter(surah);

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
        return downloadLin;
    }

    public List<String> createDownloadLinks() {

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
        return downloadLinks;
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.play)) {

            DownloadIfnotPlay();
        } else if (v == findViewById(R.id.canceldownload)) {
            downloadFooter.setVisibility(View.GONE);
        //    normalFooter.setVisibility(View.VISIBLE);
            //stop flag of auto start audio after download
            startBeforeDownload = false;
            //stop download service
            stopService(new Intent(this, DownloadService.class));
        }


    }

    private void DownloadIfnotPlay() {

        String filePath = "";

        int internetStatus = Settingsss.checkInternetStatus(this);

//https://cdn.islamic.network/quran/audio-surah/128/ar.alafasy/
        //check if there is other download in progress
        if (!Settingsss.isMyServiceRunning(this, DownloadService.class)) {
            //internal media play
            List<String> Links;
            boolean everyayah = true;
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

                playerFooter.setVisibility(View.VISIBLE);
                audio_settings_bottom.setVisibility(View.GONE);

            }
        } else {
            //Other thing in download
            Toast.makeText(this, getString(R.string.download_busy), Toast.LENGTH_SHORT).show();
        }

    }

    private void DownLoadIfNot(int internetStatus, ArrayList<String> Links) {
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

            // String app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Audio/" + readerID;
            String app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/audio/" + readerID;

            File f = new File(app_folder_path);
            String path = f.getAbsolutePath();
            File file = new File(path);

            if (!file.exists())
                file.mkdirs();

            startBeforeDownload = true;

            Intent intent = new Intent(WordbywordMushafAct.this, DownloadService.class);
            intent.putStringArrayListExtra(AudioAppConstants.Download.DOWNLOAD_LINKS, Links);
            intent.putExtra(AudioAppConstants.Download.DOWNLOAD_LOCATION, app_folder_path);
            startService(intent);

        }
    }

    @Override
    public void onItemClick(View v, int position) {
        Object istag = v.getTag();
        if (istag.equals("verse") && singleline) {
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
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        handler.removeCallbacks(SinglesendUpdatesToUI);

      if(currenttrack!=0) {
          SharedPreferences pref = getSharedPreferences("lastaya", MODE_PRIVATE);
          SharedPreferences.Editor editor = pref.edit();
          editor.putInt("lastaya", currenttrack);

          editor.putInt("trackposition", hlights.get(currenttrack).get(0).getPassage());
          ArrayList<AudioPlayed> ap = new ArrayList<>();
          AudioPlayed audioPlayed = new AudioPlayed();
          audioPlayed.setSurah(surah);
          audioPlayed.setAyah(currenttrack);
          audioPlayed.setTrackposition(hlights.get(currenttrack).get(0).getPassage());
          ap.add(audioPlayed);

          editor.apply();

          ConfigPreferences.setLastPlayedAudio(this, ap, String.valueOf(surah));
      }
        //unregister broadcast for download ayat
        LocalBroadcastManager.getInstance(WordbywordMushafAct.this).unregisterReceiver(downloadPageAya);
        //stop flag of auto start
        startBeforeDownload = false;

        if (player != null) {
            player.release();
        }

        // finish();

    }

    @Override
    public void onItemLongClick(int position, View v) {

        Object istag = v.getTag();
        if (istag.equals("verse") && singleline) {
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


}

