package com.example.mushafconsolidated.Activity;

import static android.text.TextUtils.concat;
import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.MAKKI_MADANI;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.RUKUCOUNT;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.VERSESCOUNT;
import static com.example.mushafconsolidated.R.drawable.custom_search_box;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.JustJava.InputFilterMinMax;
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
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.TameezEnt;
import com.example.mushafconsolidated.BottomOptionDialog;
import com.example.mushafconsolidated.NamesDetail;
import com.example.mushafconsolidated.ParticleColorScheme;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.SurahSummary;
import com.example.mushafconsolidated.Utils;

import com.example.mushafconsolidated.fragments.BookmarkFragment;
import com.example.mushafconsolidated.fragments.GrammerFragmentsBottomSheet;
import com.example.mushafconsolidated.fragments.NewSurahDisplayFrag;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.intrface.PassdataInterface;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;

public class QuranGrammarAct extends BaseActivity implements PassdataInterface, OnItemClickListenerOnLong {


    private static final String TAG = "fragment";

    //  FloatingActionButton fab1, fab2, fab3, fab;
    private boolean isFABOpen = false;
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
    DrawerLayout coordinatorLayout;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;
    // ChipNavigationBar chipNavigationBar;
    private MaterialToolbar materialToolbar;
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
    private boolean passages = false;
    private FloatingActionButton fabmenu;
    private FloatingActionButton fab1, fab2, fab3;
    FloatingActionButton tafsirfb, jumptofb, bookmarfb, helpfb, summbaryfb;

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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (!(fragment instanceof com.example.mushafconsolidated.fragments.IOnBackPressed) || !((com.example.mushafconsolidated.fragments.IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
        //  finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dua_group, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
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

            passages = !passages;
            ReloadActivity();
        }
        return super.onOptionsItemSelected(item);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shared =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarAct.this);
        String preferences = shared.getString("themePref", "dark");
        switch (preferences) {
            case "white":
                switchTheme("purple");
                break;
            case "dark":
                switchTheme("dark");
                break;
            case "blue":
                switchTheme("blue");
                break;
            case "green":
                switchTheme("light");
                break;
            case "brwon":
                switchTheme("brown");
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_fragment_reading);
        materialToolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(materialToolbar);
  /*      materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("check");
            }
        });
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.settings:
                        Intent settingint = new Intent(QuranGrammarAct.this, ActivitySettings.class);
                        startActivity(settingint);
                        return false;

                }
                return false;
            }
        });*/
         // setSupportActionBar(toolbar);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        if (preferences.equals("dark") || preferences.equals("blue") || preferences.equals("purple")||preferences.equals("green")) {
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
        getpreferences();

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
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
                passages = bundles.getBoolean("passages", false);
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
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
            NewSurahDisplayFrag newCustomFragment = NewSurahDisplayFrag.newInstance();
            transaction.replace(R.id.frame_container, newCustomFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }

    }

    private void initnavigation() {

        btnBottomSheet = findViewById(R.id.fab);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, materialToolbar, (R.string.drawer_open), (R.string.drawer_close));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
/*        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        String isNightmode = shared.getString("theme", "dark");
        final int color = ContextCompat.getColor(this, R.color.color_background_overlay);
        final int colorsurface = ContextCompat.getColor(this, R.color.color_surface);
        final int coloronbackground = ContextCompat.getColor(this, R.color.neutral0);
        if (isNightmode.equals("dark") || isNightmode.equals("blue")) {
            bottomNavigationView.setBackgroundColor(coloronbackground);
            bottomNavigationView.setBackgroundColor(color);
        } else {
            bottomNavigationView.setBackgroundColor(colorsurface);
        }
        if (isNightmode.equals("dark") || isNightmode.equals("blue")) {
            materialToolbar.setBackgroundColor(coloronbackground);
            materialToolbar.setBackgroundColor(color);
        } else {
            materialToolbar.setBackgroundColor(colorsurface);
        }*/
  /*     fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (!isFABOpen) {
                   showFABMenu();
               } else {
                   closeFABMenu();
               }
           }
       });
*/


        btnBottomSheet.setOnClickListener(v -> {
            toggleBottomSheets();
            //  toggleHideSeek();
        });
        bottomNavigationView.setOnNavigationItemReselectedListener(item ->
        {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.surahnav:
                    materialToolbar.setTitle("Surah");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
                    NewSurahDisplayFrag newCustomFragment = NewSurahDisplayFrag.newInstance();
                    transaction.add(R.id.frame_container, newCustomFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    navigationView.setCheckedItem(R.id.surahnav);
                   //  this.getSupportActionBar().hide();
                    break;
                case R.id.conjugationnav:
                    //   drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Conjugator");
                    //  Intent conjugatorintent = new Intent(newreadactivity.this, VerbConjugationAct.class);
                    Intent conjugatorintent = new Intent(this, ConjugatorAct.class);
                    startActivity(conjugatorintent);
                    break;
                case R.id.dua:
                    materialToolbar.setTitle("Hisnul Muslim-Dua;s");
                    //        Intent searchintent = new Intent(this, HisnulMainAct.class);
                    Intent searchintent = new Intent(this, HisnulBottomACT.class);
                    startActivity(searchintent);
                    navigationView.setCheckedItem(R.id.duanav);
                    break;
                case R.id.Names:
                    materialToolbar.setTitle("Aallah(SWT) Namess");
                    Intent searchintents = new Intent(this, GridImageAct.class);
                    startActivity(searchintents);
                    navigationView.setCheckedItem(R.id.Names);
                    break;
                case R.id.prayers:
                    materialToolbar.setTitle("Topics");
                 Intent searchs = new Intent(this, MainTwoActivityPrayer.class);
                 //   Intent searchs = new Intent(this, ActivitySettings.class);
                    startActivity(searchs);
                    break;
                default:
                    break;
            }

        });
        navigationView.setNavigationItemSelectedListener(item ->
        {
            Fragment fragment;
            switch (item.getItemId()) {

                case R.id.bookmark:
                    drawerLayout.closeDrawers();
                    BookmarkFragment bookmarkFragment = new BookmarkFragment();
                    //  TameezDisplayFrag bookmarkFragment=new TameezDisplayFrag();
                    FragmentTransaction transactions = getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transactions.replace(R.id.frame_container, bookmarkFragment).addToBackStack("mujarrad");
                    transactions.commit();
                    break;
                case R.id.jumptoverse:
                    drawerLayout.closeDrawers();
                    Intent grammar = new Intent(this, GrammarRuleDetailHostActivity.class);
                    startActivity(grammar);
                    break;
                case R.id.search:
                    drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Root Word Search");
                    //  Intent conjugatorintent = new Intent(newreadactivity.this, VerbConjugationAct.class);
                    Intent search = new Intent(this, SearchKeyBoardAct.class);
                    startActivity(search);
                    break;
                case R.id.setting:
                    drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Settings");
                    Intent settingint = new Intent(this, ActivitySettings.class);
                    startActivity(settingint);
                    navigationView.setCheckedItem(R.id.Names);
                    //  Intent conjugatorintent = new Intent(newreadactivity.this, VerbConjugationAct.class);
                    break;
            case R.id.searchtopic:
                drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Topics");
                    Intent searchs = new Intent(this, QuranTopicSearchActivity.class);
                    startActivity(searchs);
                 break;
           /*     case R.id.mudhaf:
                    drawerLayout.closeDrawers();
                    materialToolbar.setTitle("Mudhaf");
                    MudhafDisplayFrag mudhafDisplayFrag = new MudhafDisplayFrag();
                    //  TameezDisplayFrag bookmarkFragment=new TameezDisplayFrag();
                    FragmentTransaction transactionsmudhaf = getSupportFragmentManager().beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transactionsmudhaf.replace(R.id.frame_container, mudhafDisplayFrag).addToBackStack("mujarrad");
                    transactionsmudhaf.commit();
                    break;
*/


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
        QuranEntity quranEntity = allofQuran.get(readposition);
        Dialog jumpDialog;
        Spinner suraNames, verses;
        EditText surahIndex;
        Button ok;
        TextView ayahlabel;
        Utils util;
        List<String> sorasShow;
        //   jumpDialog = new Dialog(this,R.style.Base_Theme_AppCompat_Dialog);
        jumpDialog = new Dialog(this);
        jumpDialog.setContentView(R.layout.backupjumb_to_popup);
        suraNames = jumpDialog.findViewById(R.id.suras);
        verses = jumpDialog.findViewById(R.id.verses);
        util = new Utils(this);
        surahIndex = jumpDialog.findViewById(R.id.suraIndex);
        ayahIndex = jumpDialog.findViewById(R.id.ayahInput);
        ayahlabel = jumpDialog.findViewById(R.id.ayahlabel);
        jumpDialog.show();
        int maxLengthofEditText = 3;
        surahIndex.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        surahIndex.setFilters(new InputFilter[]{new InputFilterMinMax(1, 114)});
        ayahIndex.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLengthofEditText)});
        ok = jumpDialog.findViewById(R.id.ok);
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
                R.layout.myspinner, show);
        verseAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_layout_larg, verse_no);
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
                        R.layout.spinner_layout_larg, numbers);
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
                parentRecyclerView = findViewById(R.id.overlayViewRecyclerView);
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
                parentRecyclerView = findViewById(R.id.overlayViewRecyclerView);
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
        // final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        final String[] surahArrays = getResources().getStringArray(R.array.suraharabic);
        final int[] intArray = getResources().getIntArray(R.array.versescount);
        //     final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        Dialog dlg = new Dialog(this);
        //  AlertDialog dialog = builder.create();
        soraList = utils.getAllAnaChapters();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.wheelview, null);
        List<String> sorasShow = new ArrayList<>();
        int count = 0;
        dialogPicker.setView(dialogView);
        final NumberPicker surahPicker = dialogView.findViewById(R.id.surahpicker);
        final NumberPicker ayahPicker = dialogView.findViewById(R.id.ayahpicker);
        TextView surahname = dialogView.findViewById(R.id.etSura);
        TextView ayanum = dialogView.findViewById(R.id.etAyah);
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
//            Log.d("picker value", surahArrays[valuePicker1]);
      //      Log.d(TAG, "surah: " + surahPicker.getValue());
      //      Log.d(TAG, "ayah: " + ayahPicker.getValue());
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
            parentRecyclerView = findViewById(R.id.overlayViewRecyclerView);
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
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //   alertDialog.show();
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        alertDialog.show();
        alertDialog.getWindow().setAttributes(lp);
    }

    private void SetTranslation() {
        //     allofQuran = Utils.getQuranbySurah(chapterno);
        shared.getBoolean("prefs_show_erab", true);
        ExecuteSurahWordByWord();

    }

    public void ExecuteSurahWordByWord() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.ThemeOverlay_Material3_Dialog);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
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
        int versesnumbers;
        versesnumbers = getVersescount();
        ArrayList<CorpusExpandWbwPOJO> wbw = utils.getCorpusWbwBySurah(chapterno);
        //  ArrayList<MafoolBihi> mafoolbihiquran = utils.getMafoolbihiquran();
        int verseglobal = 0;
        int tempVerseWord;
        int verseexit = wbw.size();
        int verseno = 0;
        int surahid = 0;
        ArrayList<CorpusWbwWord> wordArrayListpassage = new ArrayList<>();
        for (int indexv = 1; indexv <= versesnumbers; indexv++) {
            tempVerseWord = indexv;
            CorpusAyahWord ayahWord = new CorpusAyahWord();
            ArrayList<CorpusWbwWord> wordArrayList = new ArrayList<>();
            while (tempVerseWord == indexv) {
                if (verseexit == verseglobal) {
                    break;
                }
                for (; verseglobal < wbw.size(); verseglobal++) {
                    CorpusWbwWord word = new CorpusWbwWord();
                    tempVerseWord = wbw.get(verseglobal).getAyah();
                    if (tempVerseWord != indexv) {
                        break;
                    }
                    //    final Object o6 = wbwa.get(verseglobal).get(0);
                    StringBuilder sb = new StringBuilder();
                    sb.append(wbw.get(verseglobal).getAraone()).append(wbw.get(verseglobal).getAratwo());
                    CharSequence sequence = concat(wbw.get(verseglobal).getAraone() + wbw.get(verseglobal).getAratwo() +
                            wbw.get(verseglobal).getArathree() + wbw.get(verseglobal).getArafour());
                    //   Object o4 = wbw.get(verseglobal).getWord();
                    Object en = wbw.get(verseglobal).getEn();
                    Object bn = wbw.get(verseglobal).getBn();
                    Object ind = wbw.get(verseglobal).getIn();
                    String ur = wbw.get(verseglobal).getUr();
                    word.setRootword(wbw.get(verseglobal).getRoot_a());
                    word.setSurahId(wbw.get(verseglobal).getSurah());
                    word.setVerseId(wbw.get(verseglobal).getAyah());
                    word.setWordno(wbw.get(verseglobal).getWordno());
                    word.setWordcount(wbw.get(verseglobal).getWordcount());
                    word.setWordsAr(sequence.toString());
                    //  word.setWordindex(getIndex(wbw.get(verseglobal).getQuranverses()));
                    word.setTranslateEn(en.toString());
                    word.setTranslateBn(bn.toString());
                    word.setTranslateIndo(ind.toString());
                    word.setTranslationUrdu(ur);
                    word.setAraone(wbw.get(verseglobal).getAraone());
                    word.setAratwo(wbw.get(verseglobal).getAratwo());
                    word.setArathree(wbw.get(verseglobal).getArathree());
                    word.setArafour(wbw.get(verseglobal).getArafour());
                    word.setArafive(wbw.get(verseglobal).getArafive());
                    word.setTagone(wbw.get(verseglobal).getTagone());
                    word.setTagtwo(wbw.get(verseglobal).getTagtwo());
                    word.setTagthree(wbw.get(verseglobal).getTagthree());
                    word.setTagfour(wbw.get(verseglobal).getTagfour());
                    word.setTagfive(wbw.get(verseglobal).getTagfive());
                    word.setPassage_no(wbw.get(verseglobal).getPassage_no());
                    word.setDetailsone(wbw.get(verseglobal).getDetailsone());
                    word.setDetailstwo(wbw.get(verseglobal).getDetailstwo());
                    word.setDetailsthree(wbw.get(verseglobal).getDetailsthree());
                    word.setDetailsfour(wbw.get(verseglobal).getDetailsfour());
                    word.setDetailsfive(wbw.get(verseglobal).getDetailsfive());
                    word.setCorpusSpnnableQuranverse(SpannableStringBuilder.valueOf(wbw.get(verseglobal).getQurantext()));
                    //    word.setQuranversestr(wbw.get(verseglobal).getQuranverses());
                    word.setQuranversestr(wbw.get(verseglobal).getQurantext());
                    word.setTranslations(wbw.get(verseglobal).getTranslation());
                    word.setSurahId((wbw.get(verseglobal).getSurah()));
                    word.setVerseId((wbw.get(verseglobal).getAyah()));
                    word.setWordno(wbw.get(verseglobal).getWordno());
                    word.setWordcount((wbw.get(verseglobal).getWordcount()));
                    verseno = wbw.get(verseglobal).getAyah();
                    surahid = wbw.get(verseglobal).getSurah();
                    //  ayahWord.setSpannableverse(SpannableStringBuilder.valueOf(wbw.get(verseglobal).getQuranverses()));
                    ayahWord.setSpannableverse(SpannableString.valueOf(wbw.get(verseglobal).getQurantext()));
                    ayahWord.setPassage_no(wbw.get(verseglobal).getPassage_no());
                    wordArrayList.add(word);
                    wordArrayListpassage.add(word);
                    //


                }

            }
            CorpusWbwWord words = new CorpusWbwWord();
            NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
            String s = "\uFD3E" + nf.format(verseno) + "\uFD3F";
            String ayanno = String.valueOf(verseno);
            //   word.setWordsAr(ayanno);
            words.setAraone(s);
            words.setSurahId(surahid);
            words.setVerseId(verseno);
            wordArrayListpassage.add(words);
            ayahWord.setWord(wordArrayList);
            int asize = wordArrayList.size();
            int ispassage = wordArrayList.get(asize - 1).getPassage_no();
            if (ispassage != 0) {
                passage.put(ispassage, wordArrayListpassage);
                wordArrayListpassage = new ArrayList<>();

            }
            corpusayahWordArrayList.add(ayahWord);

        }
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
            parentRecyclerView = findViewById(R.id.overlayViewRecyclerView);
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
            if (!passages) {
                flowAyahWordAdapter = new FlowAyahWordAdapter(passage, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, QuranGrammarAct.this, surah_id, surahArabicName, isMakkiMadani, listener);
                flowAyahWordAdapter.addContext(QuranGrammarAct.this);
                parentRecyclerView.setHasFixedSize(true);
                parentRecyclerView.setAdapter(flowAyahWordAdapter);
                flowAyahWordAdapter.notifyDataSetChanged();
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));
            } else {
                flowAyahWordAdapterpassage = new FlowAyahWordAdapterPassage(passage, Mutlaqent, Tammezent, BadalErabNotesEnt, Liajlihient, Jumlahaliya, mafoolbihiwords, header, allofQuran, corpusayahWordArrayList, QuranGrammarAct.this, surah_id, surahArabicName, isMakkiMadani, listener);
                flowAyahWordAdapterpassage.addContext(QuranGrammarAct.this);
                parentRecyclerView.setHasFixedSize(true);
                parentRecyclerView.setAdapter(flowAyahWordAdapterpassage);
                flowAyahWordAdapterpassage.notifyDataSetChanged();
                parentRecyclerView.post(() -> parentRecyclerView.scrollToPosition(verse_no));

            }

        });
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
        builder.setView(R.layout.layout_loading_dialog);
        GrammerFragmentsBottomSheet item = new GrammerFragmentsBottomSheet();
        FragmentManager fragmentManager = getSupportFragmentManager();
        item.setArguments(dataBundle);
        String[] data = {String.valueOf(word.getSurah()), String.valueOf(word.getAyah()), word.getTranslation(), String.valueOf((1))};
        FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
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
        View overflow = view.findViewById(R.id.ivActionOverflow);
        try {
            quranEntity = allofQuran.get(position);
        } catch (IndexOutOfBoundsException e) {
            quranEntity = allofQuran.get(position - 1);
        }
        SwitchCompat colorsentence = view.findViewById(R.id.colorized);
        boolean colortag = shared.getBoolean("colortag", true);
        View qurantext = view.findViewById(R.id.quran_textView);

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
          /*  FragmentManager fragmentManager =  getSupportFragmentManager();
            int data = 3;
    ;
            bottomoptionsListDialogFragment.newInstance(data).show(getSupportFragmentManager(), FontQuranListDialogFragment.TAG);
          */
            int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
            int verse = corpusayahWordArrayList.get(position - 1).getWord().get(0).getVerseId();
            String name = getSurahArabicName();
            FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
            String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
            String[] data = new String[]{String.valueOf(chapter_no), String.valueOf(verse), name};
            //    FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
            //   transactions.show(item);
            BottomOptionDialog.newInstance(data).show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);


        } else if (tag.equals("jumptofb")) {

            initDialogComponents(position);

        } else if (tag.equals("sharefb")) {

            takeScreenShot(getWindow().getDecorView());

        } else if (tag.equals("helpfb")) {

            int chapter_no = corpusayahWordArrayList.get(position - 1).getWord().get(0).getSurahId();
            int verse = corpusayahWordArrayList.get(position - 1).getWord().get(0).getVerseId();
            String name = getSurahArabicName();
            Bundle dataBundle = new Bundle();
            dataBundle.putInt(SURAH_ID, chapter_no);
            SurahSummary item = new SurahSummary();
            item.setArguments(dataBundle);
            int data = (chapter_no);
            //  FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
            //   transactions.show(item);
            SurahSummary.newInstance(data).show(getSupportFragmentManager(), NamesDetail.TAG);


        } else if (tag.equals("overflow_img")) {

            @SuppressLint("RestrictedApi") MenuBuilder menuBuilder = new MenuBuilder(this);
            MenuInflater inflater = new MenuInflater(this);
            inflater.inflate(R.menu.popup_menu, menuBuilder);
            @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, view);
            optionsMenu.setForceShowIcon(true);

// Set Item Click Listener
            menuBuilder.setCallback(new MenuBuilder.Callback() {
                @Override
                public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.actionTafsir: // Handle option1 Click
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
                        case R.id.bookmark: // Handle option2 Click
                            bookMarkSelected(position);
                            optionsMenu.dismiss();
                            return true;

                        case R.id.jumpto: // Handle option2 Click
                            //  SurahAyahPicker();
                            initDialogComponents(position);
                            optionsMenu.dismiss();
                            return true;
                        case R.id.action_share:


                            takeScreenShot(getWindow().getDecorView());
                            optionsMenu.dismiss();
                            return true;
                        case R.id.ivHelp: // Handle option2 Click
                            //  SurahAyahPicker();
                            //   ParticleColorScheme item = new ParticleColorScheme();
                            //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                            FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
                            String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
                            String[] data = {sample, sample, sample};
                            //    FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
                            //   transactions.show(item);
                            ParticleColorScheme.newInstance().show(QuranGrammarAct.this.getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                            optionsMenu.dismiss();
                            return true;
                        case R.id.colorized: // Handle option2 Click
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
                public void onMenuModeChange(MenuBuilder menu) {
                }
            });

            optionsMenu.show();


        } else if (tag.equals("overflow_img")) {
            boolean issentencesanalysis = shared.getBoolean("grammarsentence", true);
            AlertDialog.Builder builder = new AlertDialog.Builder(QuranGrammarAct.this);
            LayoutInflater factory = LayoutInflater.from(QuranGrammarAct.this);
            view = factory.inflate(R.layout.popup_layout, null);
            View tafsirtag = view.findViewById(R.id.tafsir);
            View fopen = view.findViewById(R.id.fileopen);
            View bookmarkview = view.findViewById(R.id.bookmark);
            SwitchCompat colorized = view.findViewById(R.id.colorized);
            View helpview = view.findViewById(R.id.help);

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
            dialog.getWindow().getAttributes().windowAnimations = R.style.WindowAnimationTransition; //style id
            dialog.show();
            helpview.setOnClickListener(v -> {
                ParticleColorScheme item = new ParticleColorScheme();
                //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                FragmentManager fragmentManager = QuranGrammarAct.this.getSupportFragmentManager();
                String sample = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ";
                String[] data = {sample, sample, sample};
                FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
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
            String[] data = {sample, sample, sample};
            FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
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
            //  Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            //  view.setDrawingCacheEnabled(false);

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
 /*      CropImage.activity(uri)
                .start(this);*/


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
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                System.out.println(resultUri);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Application from Instagram");
                intent.putExtra(Intent.EXTRA_STREAM, resultUri);


                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    grantUriPermission(packageName, resultUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivity(Intent.createChooser(intent, "Share PDF using.."));
                try {
                    this.startActivity(Intent.createChooser(intent, "Share With"));
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }*/

    private void ReloadActivity() {
        Log.e(TAG, "onClick called");
        final Intent intent = getIntent().putExtra("chapter", chapterno).putExtra("chapterorpart", chapterorpart).putExtra(SURAH_ARABIC_NAME, surahArabicName).putExtra("passages", passages)
                .putExtra(VERSESCOUNT, getVersescount()).putExtra(RUKUCOUNT, rukucount).putExtra(MAKKI_MADANI, isMakkiMadani);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
    }

    private void bookMarkSelected(int position) {
        //  position = flowAyahWordAdapter.getAdapterposition();
        int chapter_no = corpusayahWordArrayList.get(position).getWord().get(0).getSurahId();
        int verse = corpusayahWordArrayList.get(position).getWord().get(0).getVerseId();
        BookMarks en = new BookMarks();
        en.setChapterno(String.valueOf(chapter_no));
        en.setVerseno(String.valueOf(verse));
        en.setSurahname(getSurahArabicName());
        //     Utils utils = new Utils(ReadingSurahPartActivity.this);
        utils.insertBookMark(en);
        View view = findViewById(R.id.bookmark);
        Snackbar snackbar = Snackbar
                .make(view, "BookMark Created", Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.BLUE);
        snackbar.setTextColor(Color.CYAN);
        snackbar.setBackgroundTint(Color.BLACK);
        snackbar.show();
    }

    private void popupWindow(View overflow) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_filter_layout, null);
        SwitchCompat colorSwitch = popupView.findViewById(R.id.mySwitch);
        final View jumpto = popupView.findViewById(R.id.jumpto);
        View bookmarview = popupView.findViewById(R.id.bookmark);
        boolean colortag = shared.getBoolean("colortag", true);
        SwitchCompat colorized = popupView.findViewById(R.id.mySwitch);
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
        btnBottomSheet = findViewById(R.id.fab);
        layoutBottomSheet = findViewById(R.id.bottom_sheet);
        //    btnBottomSheet=findViewById(R.id.btn_bottom_sheet);
        tvsurah = findViewById(R.id.tvRukus);
        tvayah = findViewById(R.id.tvVerses);
        tvrukus = findViewById(R.id.tvSura);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        RecyclerView verlayViewRecyclerView = findViewById(R.id.overlayViewRecyclerView);
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

