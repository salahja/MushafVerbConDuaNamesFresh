package com.example.mushafconsolidated.Activity;

import static android.text.TextUtils.concat;
import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.MAKKI_MADANI;
import static com.example.Constant.MUSLIMMATE;
import static com.example.Constant.ORANGE100;
import static com.example.Constant.PURPLES;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.RUKUCOUNT;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.VERSESCOUNT;
import static com.example.mushafconsolidated.R.*;
import static com.example.mushafconsolidated.R.drawable.custom_search_box;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.collection.ArraySet;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.JustJava.InputFilterMinMax;
import com.example.JustJava.WbwSurah;
import com.example.mushafconsolidated.Adapters.FlowAyahWordAdapter;
import com.example.mushafconsolidated.Adapters.FlowAyahWordAdapterPassage;
import com.example.mushafconsolidated.Entities.BadalErabNotesEnt;
import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.CorpusExpandWbwPOJO;
import com.example.mushafconsolidated.Entities.HalEnt;
import com.example.mushafconsolidated.Entities.LiajlihiEnt;
import com.example.mushafconsolidated.Entities.MafoolBihi;
import com.example.mushafconsolidated.Entities.MafoolMutlaqEnt;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.TameezEnt;
import com.example.mushafconsolidated.BottomOptionDialog;
import com.example.mushafconsolidated.NamesDetail;
import com.example.mushafconsolidated.ParticleColorScheme;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.SurahSummary;
import com.example.mushafconsolidated.Utils;

import com.example.mushafconsolidated.fragments.BookMarkCreateFrag;
import com.example.mushafconsolidated.fragments.BookmarkFragment;
import com.example.mushafconsolidated.fragments.GrammerFragmentsBottomSheet;
import com.example.mushafconsolidated.fragments.NewSurahDisplayFrag;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.intrface.PassdataInterface;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.mushafconsolidated.settings.Constants;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;


import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.sj.conjugator.activity.ConjugatorAct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.GridImageAct;
import mm.prayer.muslimmate.Activity.MainTwoActivityPrayer;
import sj.hisnul.activity.HisnulBottomACT;
import wheel.OnWheelChangedListener;
import wheel.WheelView;
//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;

public class QuranGrammarAct extends BaseActivity implements PassdataInterface, OnItemClickListenerOnLong {
    private int lastpageShown = 1;
    private List<Integer> quraterSStart;
    /**
     * hold num of pages that read today
     * will be update(in db) with every exit from activity
     */
    ArraySet<Integer> pagesReadLogNumber;

    private static final String TAG = "fragment";
    Handler handler;
    List<Page> pageList;

    private final ArrayList<String> det = new ArrayList<>();
    FloatingActionButton btnBottomSheet;
    String surahArabicName;
    boolean jumptostatus = false;
    int surah_id = 0;
    int verseNumber, suraNumber;
    int rukucount;
    String surahname;
    int mudhafColoragainstBlack, mausofColoragainstBlack, sifatColoragainstBlack, brokenPlurarColoragainstBlack, shartagainstback;
    int surahorpart = 0;
    TextView tvsurah, tvayah, tvrukus;
    int currentSelectSurah;
    RelativeLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    View suralistlayout;
    int versescount = 0;
    boolean chapterorpart;
    // --Commented out by Inspection (14/08/21, 7:26 PM):ChipNavigationBar chipNavigationBar;
    int verse_no = 0;
    //  CoordinatorLayout coordinatorLayout;
    CoordinatorLayout coordinatorLayout;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    // ChipNavigationBar chipNavigationBar;
    private Toolbar materialToolbar;
    private FlowAyahWordAdapter flowAyahWordAdapter;
    private FlowAyahWordAdapterPassage flowAyahWordAdapterpassage;
    // private UpdateMafoolFlowAyahWordAdapter flowAyahWordAdapter;
    private boolean mausoof, mudhaf, harfnasb, shart;
    private ArrayList<ChaptersAnaEntity> soraList;
    private EditText ayahIndex;
    private boolean kana;
    private List<QuranEntity> allofQuran;
    private SharedPreferences shared;
    //  private OnClickListener onClickListener;
    private ArrayList<CorpusAyahWord> corpusayahWordArrayList;
    private final LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> passage = new LinkedHashMap<>();
    private ArrayList<MafoolBihi> mafoolbihiwords;
    private ArrayList<HalEnt> Jumlahaliya;
    private ArrayList<TameezEnt> Tammezent;
    private ArrayList<MafoolMutlaqEnt> Mutlaqent;
    private ArrayList<LiajlihiEnt> Liajlihient;
    private ArrayList<BadalErabNotesEnt> BadalErabNotesEnt;
    private Utils utils;
    private int isMakkiMadani;
    private int chapterno;
    private RecyclerView parentRecyclerView;
    private RecyclerView surahRecView;
    private boolean mushafview = false;
    private boolean mushafcoloredview = false;



    public int getRukucount() {
        return rukucount;
    }

    public void setRukucount(int rukucount) {
        this.rukucount = rukucount;
    }

    public void setSurahorpart(int surahorpart) {
        this.surahorpart = surahorpart;
    }

    public int getCurrentSelectSurah() {
        return currentSelectSurah;
    }

    public void setCurrentSelectSurah(int currentSelectSurah) {
        this.currentSelectSurah = currentSelectSurah;
    }

    public String getSurahArabicName() {
        return surahArabicName;
    }

    public void setSurahArabicName(String surahArabicName) {
        this.surahArabicName = surahArabicName;
    }

    public void setSurah_id(int surah_id) {
        this.surah_id = surah_id;
    }

    public int getVersescount() {
        return versescount;
    }

    public void setVersescount(int versescount) {
        this.versescount = versescount;
    }

    public void setVerse_no(int verse_no) {
        this.verse_no = verse_no;
    }

    public void setIsMakkiMadani(int isMakkiMadani) {
        this.isMakkiMadani = isMakkiMadani;
    }

    public void setChapterorpart(boolean chapterorpart) {
        this.chapterorpart = chapterorpart;
    }

    public int getChapterno() {
        return chapterno;
    }

    public void setChapterno(int chapterno) {
        this.chapterno = chapterno;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(id.frame_container);
        if (!(fragment instanceof com.example.mushafconsolidated.fragments.IOnBackPressed) || !((com.example.mushafconsolidated.fragments.IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
        //  finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dua_group, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(id.search).getActionView();
        searchView.setQueryHint("Type something…");
        Drawable sear = ContextCompat.getDrawable(this, custom_search_box);
        searchView.setClipToOutline(true);
        searchView.setBackgroundDrawable(sear);
        searchView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        searchView.setMaxWidth(Integer.MAX_VALUE);


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bookmark) {
        } else if (id == R.id.jumpto) {
            SurahAyahPicker();

        } else if (id == R.id.settings) {
            Intent settingint = new Intent(this, ActivitySettings.class);
            startActivity(settingint);
            navigationView.setCheckedItem(R.id.Names);
        } else if (id == R.id.mushafview) {
            Intent settingint = new Intent(this, WordbywordMushafAct.class);
            startActivity(settingint);
         //   mushafview = !mushafview;
          //  ReloadActivity();
        }

        return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shared =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this);
        String preferences = shared.getString("themepref", "dark");
        super.onCreate(savedInstanceState);
        setContentView(layout.new_fragment_reading);
        materialToolbar = findViewById(id.toolbarmain);
        setSupportActionBar(materialToolbar);
   /*     AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) materialToolbar.getLayoutParams();
        params.setScrollFlags(0);*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        if (preferences.equals("dark") || preferences.equals("blue") ||preferences.equals("green")) {
            shartagainstback = prefs.getInt("shartback", Color.GREEN);
            mausofColoragainstBlack = prefs.getInt("mausoofblack", Color.RED);
            mudhafColoragainstBlack = prefs.getInt("mudhafblack", Color.CYAN);
            sifatColoragainstBlack = prefs.getInt("sifatblack", Color.YELLOW);
            brokenPlurarColoragainstBlack = prefs.getInt("brokenblack", Color.GREEN);

        } else {
            shartagainstback = prefs.getInt("shartback", Color.DKGRAY);
            mudhafColoragainstBlack = prefs.getInt("mausoofwhite", 0xFF000000);
            mausofColoragainstBlack = prefs.getInt("mudhafwhite", 0xFF000000);
            sifatColoragainstBlack = prefs.getInt("sifatwhite", 0xFF000000);
            brokenPlurarColoragainstBlack = prefs.getInt("brokenwhite", 0xFF000000);

        }
        if (isFirstTime()) {
            Intent intents = new Intent(QuranGrammarAct.this, ActivitySettings.class);
            startActivity(intents);

        }


        PreferenceManager.setDefaultValues(this, xml.preferences, false);
        mausoof = shared.getBoolean("mausoof", true);
        mudhaf = shared.getBoolean("mudhaf", true);
        harfnasb = shared.getBoolean("harfnasb", true);
        shart = shared.getBoolean("shart", true);
        kana = shared.getBoolean("kana", true);
        getpreferences();
        Intent bundle = getIntent();
        if (!(bundle.getExtras() == null)) {
            Bundle bundles = getIntent().getExtras();
            //   if (bundle != null) {
            String lastread = bundles.getString(QURAN_VERB_ROOT);
            if (null != lastread && lastread.equals("quran")) {
                getpreferences();
                setChapterorpart(true);
            } else {
                int chapter = bundle.getIntExtra(CHAPTER, 1);
                mushafview = bundles.getBoolean("passages", false);
                Utils util = new Utils(this);
                ArrayList<ChaptersAnaEntity> list = util.getAllAnaChapters();
                //    final boolean chapterorpartb = bundle.getBooleanExtra(CHAPTERORPART, true);
                initView();
                initnavigation();
                setChapterno(chapter);
                //  setChapterorpart(chapterorpartb);
                setSurahArabicName(list.get(chapter - 1).getAbjadname());
                //   setChapterno( bundle.etIntExtra(SURAH_ID,2));
                setVerse_no(bundle.getIntExtra(AYAH_ID, 1));
                setVersescount(list.get(chapter - 1).getVersescount());
                setIsMakkiMadani(list.get(chapter - 1).getIsmakki());
                setRukucount(list.get(chapter - 1).getRukucount());

                SetTranslation();
            }

        } else {
            initView();
            initnavigation();
            Utils util = new Utils(this);
            ArrayList<ChaptersAnaEntity> list = util.getAllAnaChapters();
            //    final boolean chapterorpartb = bundle.getBooleanExtra(CHAPTERORPART, true);
            initView();
            initnavigation();
            setChapterno(chapterno);
            //  setChapterorpart(chapterorpartb);

            //   setChapterno( bundle.etIntExtra(SURAH_ID,2));
            setVerse_no(verse_no);
            setVersescount(list.get(chapterno - 1).getVersescount());
            setIsMakkiMadani(list.get(chapterno - 1).getIsmakki());
            setRukucount(list.get(chapterno - 1).getRukucount());


            setSurahArabicName(surahname);

            SetTranslation();

/*
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(anim.slide_down, anim.slide_up);
            NewSurahDisplayFrag newCustomFragment = NewSurahDisplayFrag.newInstance();
            transaction.replace(id.frame_container, newCustomFragment);
            transaction.addToBackStack(null);
            transaction.commit();*/

        }

    }

    private void initnavigation() {

        btnBottomSheet = findViewById(id.fab);
        drawerLayout = findViewById(id.drawer);
        navigationView = findViewById(id.navigationView);
        bottomNavigationView = findViewById(id.bottomNavView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, (string.drawer_open), (string.drawer_close));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        btnBottomSheet.setOnClickListener(v -> {
            toggleBottomSheets();
            //  toggleHideSeek();
        });

       bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
           @Override
           public void onNavigationItemReselected(@NonNull MenuItem item) {



               switch (item.getItemId()) {
                   case id.surahnav:
                       materialToolbar.setTitle("Surah");
                       FragmentManager fragmentManager = getSupportFragmentManager();
                       FragmentTransaction transaction = fragmentManager.beginTransaction();
                       transaction.setCustomAnimations(anim.slide_down, anim.slide_up);
                       NewSurahDisplayFrag newCustomFragment = NewSurahDisplayFrag.newInstance();
                       transaction.add(id.frame_container, newCustomFragment);
                       transaction.addToBackStack(null);
                       transaction.commit();
                       navigationView.setCheckedItem(id.surahnav);

                       break;
                   case id.conjugationnav:

                       materialToolbar.setTitle("Conjugator");

                       Intent conjugatorintent = new Intent(QuranGrammarAct.this, ConjugatorAct.class);
                       startActivity(conjugatorintent);
                       break;
                   case id.dua:
                     materialToolbar.setTitle("Hisnul Muslim-Dua;s");

                        Intent searchintent = new Intent(QuranGrammarAct.this, HisnulBottomACT.class);
                        startActivity(searchintent);







                       break;
                   case id.names:

                       materialToolbar.setTitle("Quran Audio");

                       Intent settingint = new Intent(QuranGrammarAct.this, GridImageAct.class);
                       settingint.putExtra(Constants.SURAH_INDEX, getChapterno());
                       startActivity(settingint);

                       break;
                   case id.mushafview:
                       materialToolbar.setTitle("Mushaf");
                    //   Intent searchs = new Intent(QuranGrammarAct.this, MainTwoActivityPrayer.class);

                     //  startActivity(searchs);

                       Intent settingints = new Intent(QuranGrammarAct.this, ShowMushafActivity.class);
             //      settingints.putExtra(Constants.SURAH_INDEX, getChapterno());
                       startActivity(settingints);






                       break;
                   default:
                       break;
               }

           }
       });


        navigationView.setNavigationItemSelectedListener(item ->
        {
            Fragment fragment;
            switch (item.getItemId()) {

                case id.bookmark:
                    drawerLayout.closeDrawers();
                    BookmarkFragment bookmarkFragment = new BookmarkFragment();
                    //  TameezDisplayFrag bookmarkFragment=new TameezDisplayFrag();
                    FragmentTransaction transactions = getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transactions.replace(id.frame_container, bookmarkFragment).addToBackStack("mujarrad");
                    transactions.commit();
                    break;
                case id.jumptoverse:
                    drawerLayout.closeDrawers();
                    Intent grammar = new Intent(this, GrammarRuleDetailHostActivity.class);
                    startActivity(grammar);
                    break;
                case id.search:
                    drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Root Word Search");
                    //  Intent conjugatorintent = new Intent(newreadactivity.this, VerbConjugationAct.class);
                    Intent search = new Intent(this, SearchKeyBoardAct.class);
                    startActivity(search);
                    break;
                case id.setting:
                    drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Prayer");
               /*     Intent settingint = new Intent(this, ActivitySettings.class);
                    startActivity(settingint);
                    navigationView.setCheckedItem(id.Names);*/
                      Intent pray = new Intent(QuranGrammarAct.this, MainTwoActivityPrayer.class);

                       startActivity(pray);
                    break;
            case id.searchtopic:
                drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Topics");
                    Intent searchs = new Intent(this, QuranTopicSearchActivity.class);
                    startActivity(searchs);
                 break;


                default:
                    break;
            }
            return false;
        });
    }


    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.apply();
        }
        return !ranBefore;
    }

    private void getpreferences() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("lastread", MODE_PRIVATE);
        chapterno = pref.getInt(CHAPTER, 1);
        verse_no = pref.getInt(AYAH_ID, 1);
        surahname = pref.getString(SURAH_ARABIC_NAME, "");
        setSurahArabicName(surahname);

    }


    public void initDialogComponents(int readposition) {
        QuranEntity quranEntity = allofQuran.get(readposition-1);
        Dialog jumpDialog;
        Spinner suraNames, verses;
        EditText surahIndex;
        Button ok;
        TextView ayahlabel;
        Utils util;
        List<String> sorasShow;
        //   jumpDialog = new Dialog(this,R.style.Base_Theme_AppCompat_Dialog);
        jumpDialog = new Dialog(this);
        jumpDialog.setContentView(layout.backupjumb_to_popup);
        suraNames = jumpDialog.findViewById(id.suras);
        verses = jumpDialog.findViewById(id.verses);
        util = new Utils(this);
        surahIndex = jumpDialog.findViewById(id.suraIndex);
        ayahIndex = jumpDialog.findViewById(id.ayahInput);
        ayahlabel = jumpDialog.findViewById(id.ayahlabel);
        jumpDialog.show();
        int maxLengthofEditText = 3;
        surahIndex.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        surahIndex.setFilters(new InputFilter[]{new InputFilterMinMax(1, 114)});
        ayahIndex.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        ok = jumpDialog.findViewById(id.ok);
        //    ArrayList<ChaptersAnaEntity> surahArray = utils.getSingleChapter(surah_id);
        int currentsurah = quranEntity.getSurah();
        verseNumber = quranEntity.getAyah();
        sorasShow = new ArrayList<>();
        int count = 0;
        soraList = util.getAllAnaChapters();
        for (ChaptersAnaEntity entity : soraList) {
            //  sorasShow.add(((++count) + " - " + (Locale.getDefault().getDisplayLanguage().equals("العربية") ? entity.getNamearabic() : entity.getAbjadname()).replace("$$$", "'")));
            String english = entity.getNameenglish();
            String abjad = entity.getAbjadname();
            sorasShow.add(((++count) + " - " + english + "-" + abjad));

        }
        final String[] show = sorasShow.toArray(new String[0]);
        ArrayAdapter<String> adapter;
        ArrayAdapter<String> verseAdapter;
        adapter = new ArrayAdapter<>(this,
                layout.myspinner, show);
        verseAdapter = new ArrayAdapter<>(this,
                layout.spinner_layout_larg, verse_no);
        suraNames.setAdapter(adapter);
        suraNames.setSelection(currentsurah - 1);
        setSurahArabicName(show[getCurrentSelectSurah()]);
        verses.setAdapter(verseAdapter);
        //   verses.setSelection(verseNumber);
        suraNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ChaptersAnaEntity sora;
                suraNumber = position + 1;
                sora = soraList.get(position);
                surahIndex.setInputType(suraNumber);
                ArrayAdapter<String> verseAdapter;
                int versesNumbers = suraNumber == 1 ? sora.versescount + 1 : sora.versescount;
                String[] numbers = new String[versesNumbers];
                for (int i = 1; i <= versesNumbers; i++) {
                    numbers[i - 1] = (i + "");
                }
                verseAdapter = new ArrayAdapter<>(QuranGrammarAct.this,
                        layout.spinner_layout_larg, numbers);
                verses.setAdapter(verseAdapter);
                if (verseNumber <= numbers.length) {
                    verses.setSelection(verseNumber - 1);
                } else {
                    verses.setSelection(numbers.length - 1);
                }
                ayahIndex.setFilters(new InputFilter[]{new InputFilterMinMax(1, numbers.length)});
                ayahlabel.setText("Max (" + (numbers.length - 1) + ")");
                surahIndex.setHint((suraNumber) + "");
                //   sora.getNamearabic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        verses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                verseNumber = position + 1;
                ayahIndex.setInputType(verseNumber);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ok.setOnClickListener(view -> {
            if (!ayahIndex.getText().toString().trim().equals("")) {
                jumpDialog.dismiss();
                //    soraList.get(suraNumber).getAbjadname();
                setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
                setSurahArabicName(soraList.get(suraNumber - 1).getAbjadname());
                //  ayahIndex.getInputType();
                Editable text = ayahIndex.getText();
                setVerse_no(Integer.parseInt(String.valueOf(text)));
                setVersescount(soraList.get(suraNumber - 1).getVersescount());
                setIsMakkiMadani(soraList.get(suraNumber - 1).getIsmakki());
                setRukucount(soraList.get(suraNumber - 1).getRukucount());
                setCurrentSelectSurah(suraNumber);
                //  setVerse_no(verseNumber);
                setChapterno(suraNumber);
                parentRecyclerView = findViewById(id.overlayViewRecyclerView);
                if (currentSelectSurah == surah_id) {
                    parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

                } else {
                    jumptostatus = true;
                    setSurahorpart(currentSelectSurah);
                    setSurah_id(currentSelectSurah);
                    ExecuteSurahWordByWord();
                    //     asyncTaskcorpus = new refactoringcurrentSurahSyncWordByWord().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            } else if (surahIndex.getText().toString().trim().equals("") || surahIndex.getText() == null) {
                jumpDialog.dismiss();
                //     soraList.get(suraNumber).getAbjadname();
                setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
                setSurahArabicName(soraList.get(suraNumber - 1).getAbjadname());
                setVerse_no(verseNumber);
                setVersescount(soraList.get(suraNumber - 1).getVersescount());
                setIsMakkiMadani(soraList.get(suraNumber - 1).getIsmakki());
                setRukucount(soraList.get(suraNumber - 1).getRukucount());
                setCurrentSelectSurah(suraNumber);
                setChapterno(suraNumber);
                parentRecyclerView = findViewById(id.overlayViewRecyclerView);
                if (currentSelectSurah == surah_id) {
                    parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

                } else {
                    jumptostatus = true;
                    setSurahorpart(currentSelectSurah);
                    setSurah_id(currentSelectSurah);
                    ExecuteSurahWordByWord();
                    //     asyncTaskcorpus = new refactoringcurrentSurahSyncWordByWord().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            }

        });

    }
    public void SurahAyahPicker() {
        TextView mTextView;
        WheelView chapterArray, versesArray, wvDay;

        final String[][] mYear = {new String[1]};
        String[] mMonth = new String[1];
        final String[] nyear = {""};
        final String[] nmonth = {""};
        final ArrayList<String>[] current = new ArrayList[]{new ArrayList<>()};
        int mDay;
        final int[] chapterno = new int[1];
        final int[] verseno = new int[1];
        final String[] surahArrays = getResources().getStringArray(array.surahdetails);
        final String[] versearrays = getResources().getStringArray(array.versescounts);
        final int[] intarrays = getResources().getIntArray(array.versescount);
        //     final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        Dialog dlg = new Dialog(this);
        //  AlertDialog dialog = builder.create();
        soraList = utils.getAllAnaChapters();
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(layout.activity_wheel_t, null);
        //  View view = inflater.inflate(R.layout.activity_wheel, null);
        dialogPicker.setView(view);
        mTextView = view.findViewById(id.textView2);
        chapterArray = view.findViewById(id.wv_year);
        versesArray = view.findViewById(id.wv_month);
//        wvDay = (WheelView) view.findViewById(R.id.wv_day);
        final String[] currentsurahVersescount = null;
        chapterArray.setEntries(surahArrays);
        int vcount = Integer.parseInt(versearrays[1]);
        versesArray.setEntries("7");
        for (int i = 1; i <= 7; i++) {
            current[0].add(String.valueOf(i));
        }

        versesArray.setEntries(current[0]);

        dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {




            //
            //   setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
          try {
              setSurahArabicName(suraNumber + "-" + soraList.get(chapterno[0] - 1).getNameenglish() + "-" + soraList.get(chapterno[0] - 1).getAbjadname());
              setSurahArabicName(soraList.get(chapterno[0]).getAbjadname());
              setVerse_no(verseno[0]);
              setVersescount(soraList.get(chapterno[0] - 1).getVersescount());
              setIsMakkiMadani(soraList.get(chapterno[0] - 1).getIsmakki());
              setRukucount(soraList.get(chapterno[0] - 1).getRukucount());
              setCurrentSelectSurah(soraList.get(chapterno[0] - 1).getChapterid());

              setChapterno(soraList.get(chapterno[0] - 1).getChapterid());
          } catch (ArrayIndexOutOfBoundsException e){
              setSurahArabicName(suraNumber + "-" + soraList.get(chapterno[0]).getNameenglish() + "-" + soraList.get(chapterno[0]).getAbjadname());
              setSurahArabicName(soraList.get(chapterno[0]).getAbjadname());
              setVerse_no(1);
              setVersescount(soraList.get(chapterno[0]).getVersescount());
              setIsMakkiMadani(soraList.get(chapterno[0]).getIsmakki());
              setRukucount(soraList.get(chapterno[0]).getRukucount());
              setCurrentSelectSurah(soraList.get(chapterno[0]).getChapterid());

              setChapterno(soraList.get(chapterno[0]).getChapterid());

          }
            parentRecyclerView = findViewById(id.overlayViewRecyclerView);
            //
            if (currentSelectSurah == surah_id) {
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

            } else {
                jumptostatus = true;
                setSurahorpart(currentSelectSurah);
                setSurah_id(currentSelectSurah);
                ExecuteSurahWordByWord();
                //     asyncTaskcorpus = new refactoringcurrentSurahSyncWordByWord().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
        });



        AlertDialog alertDialog = dialogPicker.create();
        String preferences = shared.getString("themepref", "dark");
        int db = ContextCompat.getColor(QuranGrammarApplication.getContext(), color.odd_item_bg_dark_blue_light);

        if(preferences.equals("light")) {
           alertDialog.getWindow().setBackgroundDrawableResource(color.md_theme_dark_onSecondary);
         //   alertDialog.getWindow().setBackgroundDrawableResource(R.color.md_theme_dark_onTertiary);

       //
        }else   if(preferences.equals("brown")) {
            alertDialog.getWindow().setBackgroundDrawableResource(color.background_color_light_brown);
          //  cardview.setCardBackgroundColor(ORANGE100);
        }else   if(preferences.equals("blue")){
            alertDialog.getWindow().setBackgroundDrawableResource(color.prussianblue);
          //  cardview.setCardBackgroundColor(db);
        }else   if(preferences.equals("green")){
            alertDialog.getWindow().setBackgroundDrawableResource(color.mdgreen_theme_dark_onPrimary);
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
        buttonPositive.setTextColor(ContextCompat.getColor(this, color.green));
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(this, color.red));
        if(preferences.equals("light")) {
            buttonPositive.setTextColor(ContextCompat.getColor(this, color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(this, color.red));
        }else   if(preferences.equals("brown")) {
            buttonPositive.setTextColor(ContextCompat.getColor(this, color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(this, color.red));
            //  cardview.setCardBackgroundColor(ORANGE100);
        }else   if(preferences.equals("blue")){
            buttonPositive.setTextColor(ContextCompat.getColor(this, color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(this, color.Goldenrod));
            //  cardview.setCardBackgroundColor(db);
        }else   if(preferences.equals("green")){
            buttonPositive.setTextColor(ContextCompat.getColor(this, color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(this, color.cyan_light));
            //  cardview.setCardBackgroundColor(MUSLIMMATE);
        }

        //  wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().setGravity(Gravity.TOP);



        chapterArray.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text = (String) chapterArray.getItem(newIndex);
                nyear[0] = (text);
                String[] chapno = text.split(" ");
               chapterno[0] = Integer.parseInt(chapno[0]);
                 verseno[0] =1;


                updateVerses(newIndex);
                updateTextView();
                //    updateTextView();
            }

            private void updateVerses(int newIndex) {
                current[0] = new ArrayList<>();
                int intarray;
                if (newIndex != 0) {
                    intarray = intarrays[newIndex ];
                } else {
                    intarray=7;
                }
                for (int i = 1; i <= intarray; i++) {
                    current[0].add(String.valueOf(i));
                }

                versesArray.setEntries(current[0]);
                updateTextView();


            }

            private void updateTextView() {
                String text = nyear[0].concat("/").concat(nmonth[0]);
                //   = mYear[0]+ mMonth[0];
                mTextView.setText(text);
            }
        });
        versesArray.setOnWheelChangedListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldIndex, int newIndex) {
                String text = (String) versesArray.getItem(newIndex);
                nmonth[0] = (text);
                verseno[0] = Integer.parseInt(text);
            }
        });
    }
    public void SurahAyahNumberpicker() {
        // final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        final String[] surahArrays = getResources().getStringArray(array.surahdetails);
        final int[] intArray = getResources().getIntArray(array.versescount);
        //     final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        Dialog dlg = new Dialog(this);
        //  AlertDialog dialog = builder.create();
        soraList = utils.getAllAnaChapters();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(layout.wheelview, null);
        List<String> sorasShow = new ArrayList<>();
        int count = 0;
        dialogPicker.setView(dialogView);
        final NumberPicker surahPicker = dialogView.findViewById(id.surahpicker);
        final NumberPicker ayahPicker = dialogView.findViewById(id.ayahpicker);
        TextView surahname = dialogView.findViewById(id.etSura);
        TextView ayanum = dialogView.findViewById(id.etAyah);
     CardView cardview=   dialogView.findViewById(id.cardview);
        surahPicker.setMaxValue(114);
        surahPicker.setMinValue(1);
        surahPicker.setWrapSelectorWheel(false);
        ayahPicker.setMaxValue(getVersescount());
        ayahPicker.setMinValue(1);
        ayahPicker.setWrapSelectorWheel(false);
        surahPicker.setDisplayedValues(surahArrays);
        surahPicker.setValue(getChapterno());
        surahPicker.setOnValueChangedListener((numberPicker, i, i1) -> {
            Log.d(TAG, "onValueChange: ");
            int valuePicker1 = surahPicker.getValue();
            int vcount = intArray[valuePicker1 - 1];
            ayahPicker.setMaxValue(vcount);
            surahname.setText(surahArrays[valuePicker1 - 1]);
            ayanum.setText(Integer.toString(ayahPicker.getValue()));
            //     ayanum.setText(ayahPicker.getValue());
        });
        ayahPicker.setOnValueChangedListener((numberPicker, i, i1) -> ayanum.setText(Integer.toString(ayahPicker.getValue())));
        dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {
            Log.d(TAG, "onClick: " + surahPicker.getValue());
            Log.d(TAG, "onClick: " + ayahPicker.getValue());
            int currentSelectSurah1 = getCurrentSelectSurah();
            ayanum.setText(Integer.toString(ayahPicker.getValue()));
            //
            //   setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
            setSurahArabicName(suraNumber + "-" + soraList.get(surahPicker.getValue() - 1).getNameenglish() + "-" + soraList.get(surahPicker.getValue() - 1).getAbjadname());
            setSurahArabicName(soraList.get(surahPicker.getValue() - 1).getAbjadname());
            setVerse_no(ayahPicker.getValue() - 1);
            setVersescount(soraList.get(surahPicker.getValue() - 1).getVersescount());
            setIsMakkiMadani(soraList.get(surahPicker.getValue() - 1).getIsmakki());
            setRukucount(soraList.get(surahPicker.getValue() - 1).getRukucount());
            setCurrentSelectSurah(surahPicker.getValue());
            setCurrentSelectSurah(soraList.get(surahPicker.getValue() - 1).getChapterid());
            setChapterno(soraList.get(surahPicker.getValue() - 1).getChapterid());
            parentRecyclerView = findViewById(id.overlayViewRecyclerView);
            //
            if (currentSelectSurah == surah_id) {
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

            } else {
                jumptostatus = true;
                setSurahorpart(currentSelectSurah);
                setSurah_id(currentSelectSurah);
                ExecuteSurahWordByWord();
                //     asyncTaskcorpus = new refactoringcurrentSurahSyncWordByWord().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
        dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
        });
        //   Dialog d = adb.setView(new View(this)).create();
        AlertDialog alertDialog = dialogPicker.create();

        String preferences = shared.getString("themepref", "dark");
        int db = ContextCompat.getColor(QuranGrammarApplication.getContext(), color.odd_item_bg_dark_blue_light);

        if(preferences.equals("light")) {
            alertDialog.getWindow().setBackgroundDrawableResource(color.md_theme_dark_onSecondary);
            cardview.setCardBackgroundColor(PURPLES);
        }else   if(preferences.equals("brown")) {
            alertDialog.getWindow().setBackgroundDrawableResource(color.background_color_light_brown);
            cardview.setCardBackgroundColor(ORANGE100);
        }else   if(preferences.equals("blue")){
            alertDialog.getWindow().setBackgroundDrawableResource(color.prussianblue);
            cardview.setCardBackgroundColor(db);
        }else   if(preferences.equals("green")){
            alertDialog.getWindow().setBackgroundDrawableResource(color.mdgreen_theme_dark_onPrimary);
            cardview.setCardBackgroundColor(MUSLIMMATE);
        }


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //   alertDialog.show();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

        alertDialog.show();

        //  wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().setGravity(Gravity.TOP);
    }

    private void SetTranslation() {
        //     allofQuran = Utils.getQuranbySurah(chapterno);
        shared.getBoolean("prefs_show_erab", true);
        ExecuteSurahWordByWord();

    }

    public void ExecuteSurahWordByWord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, style.ThemeOverlay_Material3_Dialog);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(layout.layout_loading_dialog);
        AlertDialog dialog = builder.create();
        corpusayahWordArrayList = new ArrayList<>();
        mafoolbihiwords = new ArrayList<>();
        Jumlahaliya = new ArrayList<>();
        Tammezent = new ArrayList<>();
        Liajlihient = new ArrayList<>();
        Jumlahaliya = utils.getHaliaErabBysurah(chapterno);
        Liajlihient = utils.getMafoolLiajlihisurah(chapterno);
        //  mafoolbihiwords =utils.getMafoolBihiErabSurah(chapterno);
        mafoolbihiwords = utils.getMafoolBySurah(chapterno);
        Tammezent = utils.getTameezsurah(chapterno);
        Mutlaqent = utils.getMutlaqsurah(chapterno);
        BadalErabNotesEnt = utils.getBadalrabSurah(chapterno);
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(() -> {
            //do inbackground
            QuranGrammarAct.this.bysurah(dialog, ex);

        });

    }

    private void bysurah(AlertDialog dialog, ExecutorService ex) {
        runOnUiThread(dialog::show);
        WbwSurah wbwSurah=new WbwSurah(QuranGrammarAct.this, chapterno, corpusayahWordArrayList,passage);
        wbwSurah.getWordbyword();
        CorpusUtilityorig corpus = new CorpusUtilityorig(this);
        //      corpus.highLightVerbs(corpusayahWordArrayList,surah_id);
        if (kana) {
            corpus.setKana(corpusayahWordArrayList, chapterno);

        }
        if (shart) {
            corpus.setShart(corpusayahWordArrayList, chapterno);
        }
        if (mudhaf) {
            corpus.setMudhafFromDB(corpusayahWordArrayList, chapterno);

        }
        if (mausoof) {
            corpus.SetMousufSifaDB(corpusayahWordArrayList, chapterno);
            //  corpus.NewMAOUSOOFSIFA(corpusayahWordArrayList);
        }
        if (harfnasb) {
            corpus.newnewHarfNasbDb(corpusayahWordArrayList, chapterno);
        }
        //     corpus.highLightVerbs(corpusayahWordArrayList,surah_id);
        //post
        runOnUiThread(() -> {
            dialog.dismiss();
            ex.shutdown();
            parentRecyclerView = findViewById(id.overlayViewRecyclerView);
            allofQuran = Utils.getQuranbySurah(chapterno);
            if (jumptostatus) {
                setSurahorpart(chapterno);

            }
            OnItemClickListenerOnLong listener = this;
            ArrayList<String> header = new ArrayList<>();
            header.add(String.valueOf(getRukucount()));
            header.add(String.valueOf(getVersescount()));
            header.add(String.valueOf(getChapterno()));
            header.add(getSurahArabicName());
            HightLightKeyWord();
            if (!mushafview) {
                flowAyahWordAdapter = new FlowAyahWordAdapter(false, passage, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, QuranGrammarAct.this, surah_id, surahArabicName, isMakkiMadani, listener);
                flowAyahWordAdapter.addContext(QuranGrammarAct.this);
                parentRecyclerView.setHasFixedSize(true);
                parentRecyclerView.setAdapter(flowAyahWordAdapter);
                flowAyahWordAdapter.notifyDataSetChanged();
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

            } else if(mushafview){




               flowAyahWordAdapterpassage = new FlowAyahWordAdapterPassage(passage, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, QuranGrammarAct.this, surah_id, surahArabicName, isMakkiMadani, listener);
                flowAyahWordAdapterpassage.addContext(QuranGrammarAct.this);
                parentRecyclerView.setHasFixedSize(true);
                parentRecyclerView.setAdapter(flowAyahWordAdapterpassage);
                flowAyahWordAdapterpassage.notifyDataSetChanged();
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

            }/*else if(mushafcoloredview){

                      loadData();
                LinearLayoutManager manager = new LinearLayoutManager(this);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                int pos = getStartPageFromIndex(getChapterno());
                parentRecyclerView.setLayoutManager(manager);
                parentRecyclerView.setHasFixedSize(true);
                PageAdapter pageAdapter = new PageAdapter(pageList,this);
                parentRecyclerView.setAdapter(pageAdapter);
                parentRecyclerView.setItemAnimator(new DefaultItemAnimator());
                pageAdapter.setPageList(pageList);
                parentRecyclerView.scrollToPosition(pos - 1);
                pageAdapter.setPageShown(new PageAdapter.PageShown() {
                    @Override
                    public void onDiplayed(int pos, PageAdapter.ViewHolder holder) {
                        // items start from 0 increase 1 to get real page num,
                        // will be used in bookmark
                        lastpageShown = pos + 1;
                        // add page to read log
                     //  addToReadLog(lastpageShown);

                       // holder.topLinear.setVisibility(View.INVISIBLE);
                      //  holder.BottomLinear.setVisibility(View.INVISIBLE);

                        // calculate Hizb info.
                        Page page = pageAdapter.getPage(pos);

                        if (quraterSStart.contains(page.getPageNum())) {
                            // get last ayah to extract info from it
                            QuranMetaEntity ayahItem = page.getAyahItems().get(page.getAyahItems().size() - 1);
                            int rub3Num = ayahItem.getHizbQuarter();
                            rub3Num--; // as first one must be 0
                            if (rub3Num % 8 == 0) {
                            //    showMessage(getString(R.string.juz_to_display, ayahItem.getJuz()));
                            } else if (rub3Num % 4 == 0) {
                           //     showMessage(getString(R.string.hizb_to_display, rub3Num / 4));
                            } else {
                                int part = rub3Num % 4;
                                part--; // 1/4 is first element which is 0
                             //   String[] parts = getResources().getStringArray(R.array.parts);
                          //      showMessage(getString(R.string.part_to_display, parts[part], (rub3Num / 4) + 1));
                            }
                        }



                    }
                });


            }*/

        });
    }

    private int getStartPageFromIndex(int pos) {
        return utils.getSuraStartpage(pos);
    }

 /*   private void loadData() {
        pageList = ((QuranGrammarApplication) getApplication()).getFullQuranPages();
        if (pageList != null && pageList.size() >= 50) {
         //   handler.sendEmptyMessage(0);
            Log.d(TAG, "loadData: %%% ");
        } else {
            Log.d(TAG, "loadData: @@@@");
            new Thread(() -> {
                List<Page> pages = new ArrayList<>();
                Page page;
                List<QuranMetaEntity> ayahItems;
                for (int i = 1; i <= 604; i++) {
                    ayahItems = utils.getAyahsByPage(i);
                    if (ayahItems.size() > 0) {
                        page = new Page();
                        page.setAyahItems(ayahItems);
                        page.setPageNum(i);
                        page.setJuz(ayahItems.get(0).getJuz());
                        pages.add(page);
                    }
                }

                pageList = new ArrayList<>(pages);

            //    handler.sendEmptyMessage(0);

            }).start();
        }
      new Thread(this::generateListOfPagesStartWithHizbQurater).start();
    }*/

    private void generateListOfPagesStartWithHizbQurater() {
      quraterSStart = utils.getHizbQuaterStart();
        // logData(quraterSStart);
    }
    private void GetSelectedPhrases() {
        List<QuranEntity> quran = Utils.getQuran();
        ArrayList<String> ajilihi = new ArrayList<>();
        // ajilihi.add("مفعول لأجله");
        ajilihi.add("لأجله");
        // ajilihi.add("لأجله.");
        ArrayList<String> tameez = new ArrayList<>();
        tameez.add("تمييز");
        tameez.add("تمييز.");
        tameez.add("التمييز");
        tameez.add("تمييز:");
        ArrayList<String> mafoolbihi = new ArrayList<>();
        mafoolbihi.add("مفعول به");
        mafoolbihi.add("مفعول به.");
        mafoolbihi.add("ومفعول به.");
        mafoolbihi.add("ومفعول به");
        ArrayList<String> hal = new ArrayList<>();
        ArrayList<String> halnew = new ArrayList<>();
        halnew.add("في محل نصب حال");
        halnew.add("في محل نصب حال.");
        halnew.add("والجملة حالية");
        halnew.add("والجملة حالية.");
        hal.add("حالية");
        hal.add("حالية.");
        hal.add("حالية:");
        hal.add("حال");
        hal.add("حال:");
        hal.add("حال.");
        hal.add("الواو حالية");
        ArrayList<String> badal = new ArrayList<>();
        badal.add("بدل");
        badal.add("بدل.");
        ArrayList<String> mutlaq = new ArrayList<>();
        mutlaq.add("مفعولا  مطلق");
        mutlaq.add("مفعولا مطلقا");
        mutlaq.add("مفعولا مطلقا،");
        mutlaq.add("مطلق.");
        mutlaq.add("");
        Trie ajilihitrie = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(mafoolbihi).build();
        //     Collection<Emit> ajilihiemit = ajilihitrie.parseText(ar_irab_two);
        for (QuranEntity quranEntity : quran) {
            Collection<Emit> ajilihiemit = ajilihitrie.parseText(quranEntity.getAr_irab_two());
            for (Emit emit : ajilihiemit) {
                String sbs = quranEntity.getSurah() + "|" +
                        quranEntity.getAyah() + "|" +
                        emit.getStart() + "|" +
                        emit.getEnd() + "|" + emit.getKeyword();
                det.add(sbs);

            }

        }

    }

    private void HightLightKeyWord() {
        String inshartiastr = "«إِنْ» شرطية";
        String izazarfshartsrt = "وإذا ظرف يتضمن معنى الشرط";
        String izashartiastr = "«إِذا» ظرف يتضمن معنى الشرط";
        String jawabshartstr = "جواب شرط";
        String jawabsharttwostr = "لجواب الشرط";
        String jawabalshart = "جواب الشرط";
        String jawab = "جواب";
        ArrayList<String> shart = new ArrayList<>();
        ArrayList<String> mutlaq = new ArrayList<>();
        mutlaq.add("مطلق");
        mutlaq.add("مفعولا مطلقا");
        mutlaq.add("مفعولا مطلقا،");
        mutlaq.add("مطلق.");
        mutlaq.add("");
        shart.add(inshartiastr);
        shart.add(izazarfshartsrt);
        shart.add(izashartiastr);
        shart.add(jawabshartstr);
        shart.add(jawabsharttwostr);
        shart.add(jawabalshart);
        shart.add(jawab);
        shart.add("شرطية");
        shart.add("شرطية.");
        shart.add("ظرف متضمن معنى الشرط");
        shart.add("وإذا ظرف زمان يتضمن معنى الشرط");
        shart.add("ظرف زمان يتضمن معنى الشرط");
        shart.add("ولو حرف شرط غير جازم");
        shart.add("حرف شرط غير جازم");
        shart.add("اللام واقعة في جواب لو");
        shart.add("حرف شرط جازم");
        shart.add("الشرطية");
        String mudhafilahistr = "مضاف إليه";
        String sifastr = "صفة";
        int mudhaflenght = mudhafilahistr.length();
        int sifalength = sifastr.length();
        ArrayList<String> hal = new ArrayList<>();
        hal.add("في محل نصب حال");
        hal.add("في محل نصب حال.");
        hal.add("والجملة حالية");
        hal.add("والجملة حالية.");
        hal.add("حالية");
        hal.add("حالية.");
        hal.add("حالية:");
        hal.add("حال");
        hal.add("حال:");
        hal.add("حال.");
        hal.add("الواو حالية");
        ArrayList<String> tameez = new ArrayList<>();
        tameez.add("تمييز");
        tameez.add("تمييز.");
        tameez.add("التمييز");
        ArrayList<String> badal = new ArrayList<>();
        badal.add("بدل");
        badal.add("بدل.");
        ArrayList<String> ajilihi = new ArrayList<>();
        ajilihi.add("مفعول لأجله");
        ajilihi.add("لأجله");
        ajilihi.add("لأجله.");
        ArrayList<String> mafoolbihi = new ArrayList<>();
        mafoolbihi.add("مفعول به");
        mafoolbihi.add("مفعول به.");
        mafoolbihi.add("مفعول به.(");
        mafoolbihi.add("في محل نصب مفعول");
        mafoolbihi.add("مفعول");
        for (QuranEntity pojo : allofQuran) {
            //  String ar_irab_two = pojo.getAr_irab_two();
            String ar_irab_two = pojo.getAr_irab_two();
            SpannableStringBuilder str = new SpannableStringBuilder(ar_irab_two);
            Trie mudhaftrie = Trie.builder().onlyWholeWords().addKeywords(mudhafilahistr).build();
            Collection<Emit> mudhafemit = mudhaftrie.parseText(ar_irab_two);
            Trie sifatrie = Trie.builder().onlyWholeWords().addKeywords(sifastr).build();
            Collection<Emit> sifaemit = sifatrie.parseText(ar_irab_two);
            Trie jawabsharttwotrie = Trie.builder().onlyWholeWords().addKeywords(jawabsharttwostr).build();
            Collection<Emit> jawabsharttwoemit = jawabsharttwotrie.parseText(ar_irab_two);
            Trie trieBuilder = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(shart).build();
            Collection<Emit> emits = trieBuilder.parseText(ar_irab_two);
            Trie haltrie = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(hal).build();
            Collection<Emit> halemits = haltrie.parseText(ar_irab_two);
            Trie tameeztrie = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(tameez).build();
            Collection<Emit> tameezemit = tameeztrie.parseText(ar_irab_two);
            Trie badaltrie = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(badal).build();
            Collection<Emit> badalemit = badaltrie.parseText(ar_irab_two);
            Trie ajilihitrie = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(ajilihi).build();
            Collection<Emit> ajilihiemit = ajilihitrie.parseText(ar_irab_two);
            Trie mafoolbihitri = Trie.builder().onlyWholeWordsWhiteSpaceSeparated().addKeywords(mafoolbihi).build();
            Collection<Emit> mafoolbihiemit = mafoolbihitri.parseText(ar_irab_two);
            for (Emit emit : mafoolbihiemit) {
                str.setSpan(new ForegroundColorSpan(sifatColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : ajilihiemit) {
                str.setSpan(new ForegroundColorSpan(sifatColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : tameezemit) {
                str.setSpan(new ForegroundColorSpan(sifatColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : badalemit) {
                str.setSpan(new ForegroundColorSpan(sifatColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : halemits) {
                str.setSpan(new ForegroundColorSpan(shartagainstback),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : emits) {
                str.setSpan(new ForegroundColorSpan(shartagainstback),
                        emit.getStart(),
                        emit.getStart() + emit.getKeyword().length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : mudhafemit) {
                str.setSpan(new ForegroundColorSpan(mausofColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + mudhaflenght,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            for (Emit emit : sifaemit) {
                str.setSpan(new ForegroundColorSpan(mudhafColoragainstBlack),
                        emit.getStart(),
                        emit.getStart() + sifalength,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            }
            //    colorerab.get(0).setErabspnabble(str);
            pojo.setErabspnabble(str);

        }
    }

    private void LoadItemList(Bundle dataBundle, QuranEntity word) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(layout.layout_loading_dialog);
        GrammerFragmentsBottomSheet item = new GrammerFragmentsBottomSheet();
        FragmentManager fragmentManager = getSupportFragmentManager();
        item.setArguments(dataBundle);
        String[] data = {String.valueOf(word.getSurah()), String.valueOf(word.getAyah()), word.getTranslation(), String.valueOf((1))};
        FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(anim.abc_slide_in_top, android.R.anim.fade_out);
        transactions.show(item);
        GrammerFragmentsBottomSheet.newInstance(data).show(getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);

    }

    @Override
    public void onItemClick(View view, int position) {
        qurangrammarmenu(view, position);
        //  popupWindow(view);


    }

    @SuppressLint("RestrictedApi")
    void qurangrammarmenu(View view, int position) {
        Object tag = view.getTag();
        QuranEntity quranEntity;
        View overflow = view.findViewById(id.ivActionOverflow);
        try {
            quranEntity = allofQuran.get(position);
        } catch (IndexOutOfBoundsException e) {
            quranEntity = allofQuran.get(position - 1);
        }
        SwitchCompat colorsentence = view.findViewById(id.colorized);
        boolean colortag = shared.getBoolean("colortag", true);
        View qurantext = view.findViewById(id.quran_textView);

        if(tag.equals("bookmarfb")){
            bookMarkSelected(position);

        }else
         if(tag.equals("collection"))
         {

             Bundle dataBundle = new Bundle();
             int chapter_no = corpusayahWordArrayList.get(position).getWord().get(0).getSurahId();
             int verse = corpusayahWordArrayList.get(position).getWord().get(0).getVerseId();
             BookMarks en = new BookMarks();




             dataBundle.putInt(SURAH_ID, chapter_no);
             dataBundle.putInt(AYAHNUMBER, verse);

             dataBundle.putString(SURAH_ARABIC_NAME, getSurahArabicName());


             BookMarkCreateFrag item = new BookMarkCreateFrag();
             item.setArguments(dataBundle);
             String[] data = new String[]{String.valueOf(chapter_no), String.valueOf(verse), getSurahArabicName()};
             //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
             FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
             String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
             //   transactions.show(item);
             BookMarkCreateFrag.newInstance(data).show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);



         }
        if (tag.equals("arrowforward")) {
            int currentsurah = quranEntity.getSurah();
            if (currentsurah != 114) {
                soraList = utils.getSingleChapter(currentsurah + 1);
                verseNumber = quranEntity.getAyah();
                final Intent intent = getIntent().putExtra("chapter", soraList.get(0).getChapterid()).putExtra("chapterorpart", chapterorpart).
                        putExtra(SURAH_ARABIC_NAME, soraList.get(0).abjadname)
                        .putExtra(VERSESCOUNT, soraList.get(0).getVersescount()).putExtra(RUKUCOUNT, soraList.get(0).getRukucount()).putExtra(MAKKI_MADANI, soraList.get(0).getIsmakki());
                overridePendingTransition(0, 0);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            }
        } else if (tag.equals("arrowback")) {
            int currentsurah = quranEntity.getSurah();
            if (currentsurah != 1) {
                soraList = utils.getSingleChapter(currentsurah - 1);
                verseNumber = quranEntity.getAyah();
                final Intent intent = getIntent().putExtra("chapter", soraList.get(0).getChapterid()).putExtra("chapterorpart", chapterorpart).
                        putExtra(SURAH_ARABIC_NAME, soraList.get(0).abjadname)
                        .putExtra(VERSESCOUNT, soraList.get(0).getVersescount()).putExtra(RUKUCOUNT, soraList.get(0).getRukucount()).putExtra(MAKKI_MADANI, soraList.get(0).getIsmakki());
                overridePendingTransition(0, 0);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            }
        } else if (tag.equals("colorize")) {
            if (colortag) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                editor.putBoolean("colortag", false);
                editor.apply();
                ReloadActivity(colorsentence);

            } else {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                editor.putBoolean("colortag", true);
                editor.apply();
                ReloadActivity(colorsentence);

            }


        } else if (tag.equals("overflowbottom")) {
            int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
            int verse = corpusayahWordArrayList.get(position - 1).getWord().get(0).getVerseId();
            String name = getSurahArabicName();
            String[] data = new String[]{String.valueOf(chapter_no), String.valueOf(verse), name};
            BottomOptionDialog.newInstance(data).show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);


        } else if (tag.equals("jumptofb")) {

            initDialogComponents(position);

        } else if (tag.equals("sharefb")) {

            takeScreenShot(getWindow().getDecorView());

        } else if (tag.equals("helpfb")) {

            int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
            Bundle dataBundle = new Bundle();
            dataBundle.putInt(SURAH_ID, chapter_no);
            SurahSummary item = new SurahSummary();
            item.setArguments(dataBundle);
            SurahSummary.newInstance((chapter_no)).show(getSupportFragmentManager(), NamesDetail.TAG);


        } else if (tag.equals("overflow_img")) {

            @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(this);
            MenuInflater inflater = new MenuInflater(this);
            inflater.inflate(menu.popup_menu, menuBuilder);
            @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, view);
            optionsMenu.setForceShowIcon(true);

// Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case id.actionTafsir: // Handle option1 Click
                            Intent readingintent = new Intent(QuranGrammarAct.this, TafsirFullscreenActivity.class);
                            //  flowAyahWordAdapter.getItem(position);
                            int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
                            int verse = corpusayahWordArrayList.get(position - 1).getWord().get(0).getVerseId();
                            String name = getSurahArabicName();
                            readingintent.putExtra(SURAH_ID, chapter_no);
                            readingintent.putExtra(AYAH_ID, verse);
                            readingintent.putExtra(SURAH_ARABIC_NAME, name);
                            startActivity(readingintent);
                            optionsMenu.dismiss();
                            return true;
                        case id.bookmark: // Handle option2 Click
                            bookMarkSelected(position);
                            optionsMenu.dismiss();
                            return true;

                        case id.jumpto: // Handle option2 Click
                            //  SurahAyahPicker();
                            initDialogComponents(position);
                            optionsMenu.dismiss();
                            return true;
                        case id.action_share:


                            takeScreenShot(getWindow().getDecorView());
                            optionsMenu.dismiss();
                            return true;
                        case id.ivHelp: // Handle option2 Click
                            String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
                            ParticleColorScheme.newInstance().show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                            optionsMenu.dismiss();
                            return true;
                        case id.colorized: // Handle option2 Click
                            if (colortag) {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                                editor.putBoolean("colortag", false);
                                editor.apply();
                                ReloadActivity();
                            } else {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                                editor.putBoolean("colortag", true);
                                editor.apply();
                                ReloadActivity();
                            }
                            optionsMenu.dismiss();

                            return true;


                        default:
                            return false;
                    }
                }

                @Override
                public void onMenuModeChange(@NonNull MenuBuilder menu) {
                }
            });

            optionsMenu.show();


        } else if (tag.equals("overflow_img")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QuranGrammarAct.this);
            LayoutInflater factory = LayoutInflater.from(QuranGrammarAct.this);
            view = factory.inflate(layout.popup_layout, null);
            View tafsirtag = view.findViewById(id.tafsir);
            View fopen = view.findViewById(id.fileopen);
            View bookmarkview = view.findViewById(id.bookmark);
            SwitchCompat colorized = view.findViewById(id.colorized);
            View helpview = view.findViewById(id.help);

            //  View surahsummary = view.findViewById(R.id.surahsummary);
            builder.setView(view);
            colorized.setChecked(colortag);
            int[] location = new int[2];
            overflow.getLocationOnScreen(location);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //   dialog.setTitle("my dialog");
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.TOP | Gravity.LEFT;
            wmlp.x = location[0];   //x position
            wmlp.y = location[1];  //y position
            dialog.getWindow().getAttributes().windowAnimations = style.WindowAnimationTransition; //style id
            dialog.show();
            helpview.setOnClickListener(v -> {
                ParticleColorScheme item = new ParticleColorScheme();
                //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
                String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
                FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(anim.abc_slide_in_top, android.R.anim.fade_out);
                //   transactions.show(item);
                ParticleColorScheme.newInstance().show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);

            });
            tafsirtag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent readingintent = new Intent(QuranGrammarAct.this, TafsirFullscreenActivity.class);
                    //  flowAyahWordAdapter.getItem(position);
                    int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
                    int verse = corpusayahWordArrayList.get(position - 1).getWord().get(0).getVerseId();
                    String name = getSurahArabicName();
                    readingintent.putExtra(SURAH_ID, chapter_no);
                    readingintent.putExtra(AYAH_ID, verse);
                    readingintent.putExtra(SURAH_ARABIC_NAME, name);
                    startActivity(readingintent);
                    dialog.dismiss();

                }
            });
            fopen.setOnClickListener(view12 -> {
                SurahAyahPicker();
                //    initDialogComponents(position);
                dialog.dismiss();
            });
            bookmarkview.setOnClickListener(view1 -> {
                bookMarkSelected(position);
                dialog.dismiss();
            });
            colorized.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //colortag
                    if (colortag) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                        //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                        editor.putBoolean("colortag", false);
                        editor.apply();
                        ReloadActivity();
                    } else {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                        //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                        editor.putBoolean("colortag", true);
                        editor.apply();
                        ReloadActivity();
                    }
                    dialog.dismiss();
                }

            });
            System.out.println("check");
        } else if (tag.equals("help_img")) {
            System.out.println("check");
            ParticleColorScheme item = new ParticleColorScheme();
            //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
            FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
            String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
            FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(anim.abc_slide_in_top, android.R.anim.fade_out);
            //   transactions.show(item);
            ParticleColorScheme.newInstance().show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
        }
        //   if (overflow != null) {
        //  popupMenu(overflow);
        //  popupWindow(overflow);
        //    }  else
        else if (tag.equals("qurantext")) {
            QuranEntity word;
            if (position != 0) {
                word = allofQuran.get(position - 1);
            } else {
                word = allofQuran.get(position);

            }
            Bundle dataBundle = new Bundle();
            dataBundle.putInt(SURAH_ID, word.getSurah());
            dataBundle.putInt(AYAHNUMBER, Math.toIntExact(word.getAyah()));
            LoadItemList(dataBundle, word);
        }
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            String path = mainDir + "/" + "Mushafapplication" + "-" + format + ".jpeg";
            //    File zipfile = new File(getExternalFilesDir(null).getAbsolutePath() + getString(R.string.app_folder_path) + File.separator + DATABASEZIP);

            view.setDrawingCacheEnabled(true);
            int color = Color.RED;
            Bitmap bitmap = getBitmapFromView(view, color);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Bitmap getBitmapFromView(View view, int defaultColor) {
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(defaultColor);
        view.draw(canvas);
        return bitmap;
    }

    private void shareScreenShot(File imageFile) {

        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", imageFile);


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Application from Instagram");
        intent.putExtra(Intent.EXTRA_STREAM, uri);


        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        //  startActivity(Intent.createChooser(intent, "Share PDF using.."));
        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void ReloadActivity() {
        Log.e(TAG, "onClick called");
        final Intent intent = getIntent().putExtra("chapter", chapterno).putExtra("chapterorpart", chapterorpart).putExtra(SURAH_ARABIC_NAME, surahArabicName).putExtra("passages", mushafview)
                .putExtra(VERSESCOUNT, getVersescount()).putExtra(RUKUCOUNT, rukucount).putExtra(MAKKI_MADANI, isMakkiMadani);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    private void bookMarkSelected(int position) {
        //  position = flowAyahWordAdapter.getAdapterposition();
        int chapter_no = corpusayahWordArrayList.get(position-1).getWord().get(0).getSurahId();
        int verse = corpusayahWordArrayList.get(position-1).getWord().get(0).getVerseId();
        BookMarks en = new BookMarks();
        en.setHeader("pins");
        en.setChapterno(String.valueOf(chapter_no));
        en.setVerseno(String.valueOf(verse));
        en.setSurahname(getSurahArabicName());
        //     Utils utils = new Utils(ReadingSurahPartActivity.this);
        utils.insertBookMark(en);
        View view = findViewById(id.bookmark);
        Snackbar snackbar = Snackbar
                .make(view, "BookMark Created", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.setTextColor(Color.CYAN);
        snackbar.setBackgroundTint(Color.BLACK);
        snackbar.show();
    }

    private void popupWindow(View overflow) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(layout.popup_filter_layout, null);
        SwitchCompat colorSwitch = popupView.findViewById(id.mySwitch);
        final View jumpto = popupView.findViewById(id.jumpto);
        View bookmarview = popupView.findViewById(id.bookmark);
        boolean colortag = shared.getBoolean("colortag", true);
        SwitchCompat colorized = popupView.findViewById(id.mySwitch);
        colorized.setChecked(colortag);
        colorSwitch.setOnClickListener(v -> {
            //   Toast.makeText(QuranGrammarAct.this, "You Clicked " , Toast.LENGTH_SHORT).show();
            boolean colortag1 = shared.getBoolean("colortag", true);
            boolean issentencesanalysis = shared.getBoolean("grammarsentence", true);
            if (colortag1) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                editor.putBoolean("colortag", false);
                editor.apply();
                //     ReloadActivity(colorsentence);
            } else {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this).edit();
                //     SharedPreferences.Editor editor = getActivity().getSharedPreferences("properties", 0).edit();
                editor.putBoolean("colortag", true);
                editor.apply();
                //     ReloadActivity(colorsentence);
            }

        });
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(() -> {
            //TODO do sth here on dismiss
        });
        popupWindow.showAsDropDown(overflow);

    }


    private void ReloadActivity(SwitchCompat colorsentence) {
        Log.e(TAG, "onClick called");
        final Intent intent = getIntent().putExtra("chapter", chapterno).putExtra("chapterorpart", chapterorpart).putExtra(SURAH_ARABIC_NAME, surahArabicName)
                .putExtra(VERSESCOUNT, getVersescount()).putExtra(RUKUCOUNT, rukucount).putExtra(MAKKI_MADANI, isMakkiMadani);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    @Override
    public void onItemLongClick(int position, View v) {
        //    Toast.makeText(this, "longclick", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        utils = new Utils(getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        btnBottomSheet = findViewById(id.fab);
        layoutBottomSheet = findViewById(id.bottom_sheet);
        //    btnBottomSheet=findViewById(R.id.btn_bottom_sheet);
        tvsurah = findViewById(id.tvRukus);
        tvayah = findViewById(id.tvVerses);
        tvrukus = findViewById(id.tvSura);
        coordinatorLayout = findViewById(id.coordinatorLayout);
        RecyclerView verlayViewRecyclerView = findViewById(id.overlayViewRecyclerView);
        verlayViewRecyclerView.setLayoutManager(linearLayoutManager);
        // bookmarkchip.setOnClickListener(v -> CheckStringLENGTHS());
    }

    public void toggleBottomSheets() {
        if (bottomNavigationView.getVisibility() == View.VISIBLE) {
            bottomNavigationView.setVisibility(View.GONE);
            //    btnBottomSheet.setText("Close sheet");
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            //    btnBottomSheet.setText("Expand sheet");
        }
    }

    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //    btnBottomSheet.setText("Close sheet");
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            //    btnBottomSheet.setText("Expand sheet");
        }
    }

    @Override
    public void ondatarecevied(int chapterno, String partname, int totalverses, int rukucount, int makkimadani) {
        Log.e(TAG, "onClick called");
        final Intent intent = getIntent().putExtra("chapter", chapterno).putExtra("chapterorpart", chapterorpart).putExtra(SURAH_ARABIC_NAME, partname)
                .putExtra(VERSESCOUNT, totalverses).putExtra(RUKUCOUNT, rukucount).putExtra(MAKKI_MADANI, makkimadani);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);

    }

}

