package com.example.mushafconsolidated.Activity;


import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Adapters.Data;
import com.example.mushafconsolidated.Adapters.IOnClick;
import com.example.mushafconsolidated.Adapters.PageAdapter;
import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.Qari;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.QuranMetaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.receivers.AppPreference;
import com.example.mushafconsolidated.receivers.AudioAppConstants;
import com.example.mushafconsolidated.receivers.AudioService;
import com.example.mushafconsolidated.receivers.DownloadService;
import com.example.mushafconsolidated.receivers.QuranValidateSources;
import com.example.mushafconsolidated.receivers.Settingsss;
import com.example.mushafconsolidated.settings.Constants;
import com.example.utility.QuranGrammarApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowMushafActivityorig extends AppCompatActivity implements View.OnClickListener{
    public static int selectPage, readerID, repeatCounter, repeateValue;
    private boolean flagHideShowTool, tafseerMood, pausePlayFlag, repeatNotRepeat;
    public static String lastSoraName, downloadLink, readerName;
    public static boolean startBeforeDownload, nextPage;
    private  Intent player;
    private static final String TAG = "ShowMushafActivity";
 int surahselected,versescount;

    public int getSurahselected() {
        return surahselected;
    }

    public void setSurahselected(int surahselected) {
        this.surahselected = surahselected;
    }

    @BindView(R.id.rvAyahsPages)
    RecyclerView rvAyahsPages;
    @BindView(R.id.tv_no_quran_data)
    TextView tvNoQuranData;
    private RelativeLayout myToolbarContainer, footerContainer;


    Utils repository;
    PageAdapter pageAdapter;
    Typeface typeface;
    int pos;
    Handler handler;
    List<Page> pageList;
    int ayahsColor, scrollorColor;
    private int lastpageShown = 1;

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
    private LinearLayout normalFooter, downloadFooter, playerFooter;
    private List<String> bookNames;
    private List<Integer> bookIDs;
  //  private List<TranslationBook> booksInfo;
    private List<Qari> readersList;
    private static final int REQUEST_WRITE_Settings = 113;
    private Intent playForward, pause, playBack, play, stop, repeat, notRepeat;
    private ImageView playForwardiv, pauseiv, playBackiv, playiv, stopiv, repeativ, notRepeativ;
    private AudioManager audioManager;
    private int screenHeight;
    private ProgressBar mediaPlayerDownloadProgress;
    private List<QuranMetaEntity> ayaList;
    ObjectAnimator toolbarAnimY;
    private PendingIntent notificationPending;
    private boolean aboveLollipopFlag;
    /**
     * hold current readLog item used to retrive pages and also with updating
     */
   // private ReadLog readLog;
    Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ayahs);
        ButterKnife.bind(this);
      //  MaterialToolbar materialToolbar = findViewById(R.id.toolbarmain);
      //  setSupportActionBar(materialToolbar);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // if (materialToolbar != null) {
         //   setSupportActionBar(materialToolbar);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
       // }
        repository = Utils.getInstance(getApplication());
        typeface = Typeface.createFromAsset(getAssets(), "me_quran.ttf");
        pos = getIntent().getIntExtra(Constants.SURAH_INDEX, 1);
        pos = getStartPageFromIndex(pos);

        //region Description
        if (getIntent().hasExtra(Constants.SURAH_GO_INDEX)) {
            int surah = getIntent().getIntExtra(Constants.SURAH_GO_INDEX, 1);
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

        initRV();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //region handle
                if (pageList != null) {
                    if (pageList.size() > 0) {
                        rvAyahsPages.setVisibility(View.VISIBLE);
                        tvNoQuranData.setVisibility(View.GONE);
                        pageAdapter.setPageList(pageList);
                        rvAyahsPages.scrollToPosition(pos - 1);
                        // pos represent Mushaf representation (i.e start from 1)
                        //addToReadLog(pos);
                        foundState();
                    } else {
                        notFound();
                    }
                } else {
                    finish();
                }
                //endregion
            }
        };
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

     //   myToolbarContainer = (RelativeLayout) findViewById(R.id.appbar);
        footerContainer = (RelativeLayout) findViewById(R.id.footerbar);
        playForward = new Intent(AudioAppConstants.MediaPlayer.FORWARD);
        pause = new Intent(AudioAppConstants.MediaPlayer.PAUSE);
        playBack = new Intent(AudioAppConstants.MediaPlayer.BACK);
        playiv= (ImageView) findViewById(R.id.play);
        pauseiv= (ImageView) findViewById(R.id.pause_play);
        playBackiv= (ImageView) findViewById(R.id.before);
        playForwardiv= (ImageView) findViewById(R.id.forward);
        stopiv= (ImageView) findViewById(R.id.play);
        repeativ= (ImageView) findViewById(R.id.repeat);

        playiv.setOnClickListener(this);
        pauseiv.setOnClickListener(this);
        playBackiv.setOnClickListener(this);
        playForwardiv.setOnClickListener(this);
        stopiv.setOnClickListener(this);
        repeativ.setOnClickListener(this);



     //   playForwardiv, pauseiv, playBackiv, playiv, stopiv, repeativ, notRepeativ;
        play = new Intent(AudioAppConstants.MediaPlayer.PLAY);
        stop = new Intent(AudioAppConstants.MediaPlayer.STOP);
        repeat = new Intent(AudioAppConstants.MediaPlayer.REPEAT_ON);
        notRepeat = new Intent(AudioAppConstants.MediaPlayer.REPEAT_OFF);

     //   sorahTitle = (TextView) findViewById(R.id.soraTitle);
      //  back = (ImageView) findViewById(R.id.back);
//        check1=(CheckBox)findViewById(R.id.check2);
     //   soraName = (TextView) findViewById(R.id.textView15);
      //  partNumber = (TextView) findViewById(R.id.textView14);
       // pageNumber = (TextView) findViewById(R.id.pageNumber);
        normalFooter = (LinearLayout) findViewById(R.id.normalfooter);
        downloadFooter = (LinearLayout) findViewById(R.id.footerdownload);
        playerFooter = (LinearLayout) findViewById(R.id.footerplayer);
       // translationBooks = (Spinner) findViewById(R.id.tafaseer);
        readers = (Spinner) findViewById(R.id.selectReader);
       // sorahInfo = (TextView) findViewById(R.id.soraInfo);
        mediaPlayerDownloadProgress = (ProgressBar) findViewById(R.id.downloadProgress);
    //    Typeface type = Typeface.createFromAsset(getAssets(), "simple.otf");
      //  flagHideShowTool = false;
     //   tafseerMood = false;
        //prepareColors();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvAyahsPages.setLayoutManager(manager);
        rvAyahsPages.setHasFixedSize(true);
        pageAdapter = new PageAdapter(typeface, ayahsColor, scrollorColor,this);
        rvAyahsPages.setAdapter(pageAdapter);
        rvAyahsPages.setItemAnimator(new DefaultItemAnimator());
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
                List<String> readersNames = new ArrayList<String>();
                readersList = repository.getQaris();
                for (Qari reader : readersList) {
                    if (reader.getAudiotype() == 0) {
                        if (Locale.getDefault().getDisplayLanguage().equals("العربية"))
                            readersNames.add(reader.getName_english());
                        else
                            readersNames.add(reader.getName_english());
                    }
                }

                //add custom spinner adapter for readers
            ArrayAdapter<String> spinnerReaderAdapter = new ArrayAdapter<String>(ShowMushafActivityorig
                        .this, R.layout.spinner_layout_larg, R.id.spinnerText, readersNames);
                readers.setAdapter(spinnerReaderAdapter);
              readers.setSelection(10);
    /*
                //load books names
                bookNames = new ArrayList<String>();
                bookIDs = QuranValidateSources.getDownloadedTransaltions();
                booksInfo = new DatabaseAccess().getAllTranslations();
                for (int bookID : bookIDs) {
                    for (TranslationBook bookInfo : booksInfo) {
                        if (bookID == bookInfo.bookID) bookNames.add(bookInfo.bookName);
                    }
                }

                //add custom spinner adapter for books
                ArrayAdapter<String> spinnerBooksAdapter = new ArrayAdapter<String>(QuranPageReadActivity
                        .this, R.layout.spinner_layout, R.id.spinnerText, bookNames);
                translationBooks.setAdapter(spinnerBooksAdapter);*/
            }
        });
        pageAdapter.setPageShown(new PageAdapter.PageShown() {
            @Override
            public void onDiplayed(int pos, PageAdapter.ViewHolder holder) {
                // items start from 0 increase 1 to get real page num,
                // will be used in bookmark
                lastpageShown = pos + 1;
                // add page to read log
                //addToReadLog(lastpageShown);

            //    holder.topLinear.setVisibility(View.INVISIBLE);
              //  holder.BottomLinear.setVisibility(View.INVISIBLE);

                // calculate Hizb info.
                Page page = pageAdapter.getPage(pos);
                if (quraterSStart.contains(page.getPageNum())) {
                    // get last ayah to extract info from it
                    QuranMetaEntity ayahItem = page.getAyahItems().get(page.getAyahItems().size() - 1);
                    int rub3Num = ayahItem.getHizbQuarter();
                    rub3Num--; // as first one must be 0
                    if (rub3Num % 8 == 0) {
                        showMessage(getString(R.string.juz_to_display, ayahItem.getJuz()));
                    } else if (rub3Num % 4 == 0) {
                        showMessage(getString(R.string.hizb_to_display, rub3Num / 4));
                    } else {
                        int part = rub3Num % 4;
                        part--; // 1/4 is first element which is 0
                        String[] parts = getResources().getStringArray(R.array.parts);
                        showMessage(getString(R.string.part_to_display, parts[part], (rub3Num / 4) + 1));
                    }
                }


            }
        });
/*
        pageAdapter.setiBookmark(new PageAdapter.IBookmark() {
            @Override
            public void onBookmarkClicked(Page item) {
                BookmarkItem bookmarkItem = new BookmarkItem();

                bookmarkItem.setTimemills(new Date().getTime());
                // get ayah to retrieve info from it
                AyahItem ayahItem = item.getAyahItems().get(0);
                bookmarkItem.setSuraName(getSuraNameFromIndex(ayahItem.getSurahIndex()));
                bookmarkItem.setPageNum(item.getPageNum());
                Log.d(TAG, "onBookmarkClicked: " + bookmarkItem.getPageNum());


                repository.addBookmark(bookmarkItem);

                showMessage("Saved");
            }
        });*/

        // to preserver quran direction from right to left
        rvAyahsPages.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        pageAdapter.setiOnClick(new IOnClick() {
            @Override
            public void onClick(int pos) {
                // pos represent page and need to be updated by 1 to be as recyclerview
                // +2 to be as Mushaf
                rvAyahsPages.scrollToPosition(pos + 1);
                //   addToReadLog(pos + 2);
            }
        });

    }

    public void getReaderAudioLink(String readerName) {
        for (Qari reader : readersList) {

            if (reader.getName_english() == readerName && Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                downloadLink = reader.getUrl();
                readerID = reader.getId();
                break;
            } else if (reader.getName_english() == readerName) {
                downloadLink = reader.getUrl();
                readerID = reader.getId();
                break;
            }

        }
    }
    //</editor-fold>

    /**
     * @param surahIndex in quran
     * @return
     */
    private String getSuraNameFromIndex(int surahIndex) {
        return Data.SURA_NAMES[surahIndex - 1];
    }
    @Override
    protected void onPause() {
//        mSensorManager.unregisterListener(this);
        super.onPause();
//        sendBroadcast(stop);
        //unregister broadcast for media player
        LocalBroadcastManager.getInstance(this).unregisterReceiver(MediaPlayer);
        //unregister broadcast for download ayat
        LocalBroadcastManager.getInstance(this).unregisterReceiver(downloadPageAya);
        //stop flag of auto start
        startBeforeDownload = false;

    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //volume down and check volume key for navigation
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) && AppPreference.isVolumeKeyNavigation()) {
            if (selectPage > 604) selectPage = 604;
          //  mViewPager.setCurrentItem((604 - (++selectPage)), true);
            //generate silent tone to disable volume sound
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 0);
            toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
            //volume up and check volume key for navigation
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && AppPreference.isVolumeKeyNavigation()) {
            if (selectPage < 0) selectPage = 1;
          //  mViewPager.setCurrentItem((604 - (--selectPage)), true);
            //generate silent tone to disable volume sound
            ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 0);
            toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //call on back button
            onBackPressed();

        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && !AppPreference.isVolumeKeyNavigation()) {
            //volume down
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 1);
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP && !AppPreference.isVolumeKeyNavigation()) {
            //volume up
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 1);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        LocalBroadcastManager.getInstance(this).registerReceiver(MediaPlayer, new IntentFilter(AudioAppConstants.MediaPlayer.INTENT));
        //register broadcast for download ayat
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadPageAya, new IntentFilter(AudioAppConstants.Download.INTENT));

        //make footer change to normal if audio end in pause
        if (!Settingsss.isMyServiceRunning(this, AudioService.class)) {
            playerFooter.setVisibility(View.GONE);
            normalFooter.setVisibility(View.VISIBLE);
        } else {
            if (downloadFooter.getVisibility() != View.VISIBLE){
                playerFooter.setVisibility(View.VISIBLE);
            }else{
                playerFooter.setVisibility(View.GONE);
            }
            normalFooter.setVisibility(View.GONE);
        }

        //check if the service paused in background
        if (Settingsss.isMyServiceRunning(this, AudioService.class) && AudioService.mediaPaused == true) {
            ((ImageButton) findViewById(R.id.pause_play)).setImageResource(R.drawable.ic_play);
            pausePlayFlag = true;
        }

        //check the selected page
        if (Settingsss.isMyServiceRunning(this, AudioService.class) && AudioService.pageNumber != 0) {
        //    mViewPager.setCurrentItem(604 - (AudioService.pageNumber));
        }


        switch (repeatCounter) {

            case 1:
                repeatNotRepeat = true;
                sendBroadcast(repeat);
                ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("1"));

                break;
            case 2:
                repeatNotRepeat = true;
                sendBroadcast(repeat);
                ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("2"));


                break;
            case 3:
                repeatNotRepeat = true;
                sendBroadcast(repeat);
                ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("3"));

                break;
            case 4:
                repeatNotRepeat = true;
                sendBroadcast(repeat);
                ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("∞"));

                repeatCounter = -1;
                break;
            default:
                repeatNotRepeat = false;
                sendBroadcast(notRepeat);
                ((ImageButton) findViewById(R.id.repeat)).setImageResource(R.drawable.ic_repeat);


                repeatCounter = 0;
                break;
        }
        //loadPagesReadLoge();
    }



    public Bitmap drawNumbers(String number) {
        //Toast.makeText(QuranPageReadActivity.this,number,Toast.LENGTH_LONG).show();
        //GlobalAttributeUtil.setRepeatCounter(getApplicationContext(), number);
        final String numbers;
        numbers = number;

//        final MyApplication globalVariable = (MyApplication) getApplicationContext();
        float fontAndPadding = getResources().getDimension(R.dimen.draw_number);
        Paint paint = new Paint();
        paint.setTextSize(fontAndPadding);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setFakeBoldText(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_repeat);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        canvas.drawText(numbers, bitmap.getWidth() - fontAndPadding, fontAndPadding, paint);
        return mutableBitmap;
    }
    private BroadcastReceiver downloadPageAya = new BroadcastReceiver() {
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

                        startService(new Intent(ShowMushafActivityorig.this, AudioService.class)
                                .putExtra(AudioAppConstants.MediaPlayer.SURAH, getSurahselected())
                                .putExtra(AudioAppConstants.MediaPlayer.READER, readerID));
                    } else {
                        downloadFooter.setVisibility(View.GONE);
                        normalFooter.setVisibility(View.VISIBLE);
                        playerFooter.setVisibility(View.GONE);
                    }

                }

            }
        }
    };

    private BroadcastReceiver MediaPlayer = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //media player intent receive
            if (intent.getBooleanExtra(AudioAppConstants.MediaPlayer.STOP, false) == true) {
                ((ImageButton) findViewById(R.id.pause_play)).setImageResource(R.drawable.ic_pause);
                ((ImageButton) findViewById(R.id.repeat)).setImageResource(R.drawable.ic_repeat);
                playerFooter.setVisibility(View.GONE);
                normalFooter.setVisibility(View.VISIBLE);
            } else if (intent.getBooleanExtra(AudioAppConstants.MediaPlayer.PLAY, true) == true) {
                normalFooter.setVisibility(View.GONE);
                playerFooter.setVisibility(View.VISIBLE);
            } else if (intent.getBooleanExtra(AudioAppConstants.MediaPlayer.PAUSE, false) == true) {
                ((ImageButton) findViewById(R.id.pause_play)).setImageResource(R.drawable.ic_play);
            } else if (intent.getBooleanExtra(AudioAppConstants.MediaPlayer.RESUME, false) == false) {
                ((ImageButton) findViewById(R.id.pause_play)).setImageResource(R.drawable.ic_pause);
            }
/*
            if (intent.getIntExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, -1) == 1) {
                int currentPage = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(--currentPage);
            } else if (intent.getIntExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, -1) == 0) {
                int currentPage = mViewPager.getCurrentItem();
                mViewPager.setCurrentItem(++currentPage);
            } else if (intent.getIntExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, -1) == 2) {
                mViewPager.setCurrentItem(604);
            } else if (intent.getIntExtra(AudioAppConstants.MediaPlayer.OTHER_PAGE, -1) == 3) {
                mViewPager.setCurrentItem(0);
            }*/

        }
    };
    private void footerPlay() throws PendingIntent.CanceledException {
        String filePath="";
        //check if other instance of Audio service run
        if (Settingsss.isMyServiceRunning(this, AudioService.class)) return;

        //get all ayat information of the page
      //  ayaList = new DatabaseAccess().getPageAyat((604 - mViewPager.getCurrentItem()));
        //       pageList.get(surai)

        //check the internet statue
        int internetStatus = Settingsss.checkInternetStatus(this);
        if (AppPreference.isStreamMood()) {
            //check internet is opened or not to start stream
            if (internetStatus <= 0) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this, R.style.MaterialAlertDialog_Material3);
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
                //start stream mood
                normalFooter.setVisibility(View.GONE);
                playerFooter.setVisibility(View.VISIBLE);
                Intent player =       new Intent(this, AudioService.class);
                player.putExtra("streamLink", downloadLink);
                player.putExtra(AudioAppConstants.MediaPlayer.SURAH, getSurahselected());
                player.putExtra(AudioAppConstants.MediaPlayer.READER, readerID);
                startService(player);


            }

        } else {

            //check if there is other download in progress
            if (!Settingsss.isMyServiceRunning(this, DownloadService.class)) {
                //internal media play
                List<String> Links = createDownloadLinks();
                if (Links.size() != 0) {
                    //check if the internet is opened
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
                        normalFooter.setVisibility(View.GONE);
                        playerFooter.setVisibility(View.GONE);
                        downloadFooter.setVisibility(View.VISIBLE);

                        //check audio folders
                         filePath = Environment
                                .getExternalStorageDirectory()
                                .getAbsolutePath()
                                + getString(R.string.app_folder_path)
                                + "/Audio/" + readerID;


                       // String app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Audio/" + readerID;
                        String app_folder_path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()+ "/audio/"+readerID;


                            File f=new File(app_folder_path);
                         //   File f = new File(Environment.getExternalStoragePublicDirectory
                               //     (Environment.DIRECTORY_DOCUMENTS), "audio/"+readerID);
                            String path=       f.getAbsolutePath();
                            File file = new File(path);


                            if (!file.exists())
                                file.mkdirs();



                        startBeforeDownload = true;

                       Intent intent = new Intent(ShowMushafActivityorig.this, DownloadService.class);
                        intent.putStringArrayListExtra(AudioAppConstants.Download.DOWNLOAD_LINKS, (ArrayList<String>) Links);
                        intent.putExtra(AudioAppConstants.Download.DOWNLOAD_LOCATION, app_folder_path);
                        startService(intent);


                    //    PendingIntent pendingIntent = PendingIntent.getService(ShowMushafActivity.this, 0, intent, FLAG_IMMUTABLE);
                      //  pendingIntent.send();


                        //start download service
                  /*      startService(new Intent(ShowMushafActivity.this, DownloadService.class)
                                .putStringArrayListExtra(AudioAppConstants.Download.DOWNLOAD_LINKS, (ArrayList<String>) Links)
                                .putExtra(AudioAppConstants.Download.DOWNLOAD_LOCATION, app_folder_path));*/
                    }

                } else {
                    //Start media player service
                    startService(new Intent(ShowMushafActivityorig.this, AudioService.class)
                            .putExtra(AudioAppConstants.MediaPlayer.SURAH, getSurahselected())
                            .putExtra(AudioAppConstants.MediaPlayer.READER, readerID));



                }
            } else {
                //Other thing in download
                Toast.makeText(this, getString(R.string.download_busy), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission((ShowMushafActivityorig.this) ,  android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public List<String> createDownloadLinks() {
  List<ChaptersAnaEntity>   chap=    repository.getSingleChapter(pageList.get(pos-1).getAyahItems().get(0).getSurahIndex());
        int versescount = chap.get(0).getVersescount();

        List<QuranEntity> quranbySurah = repository.getQuranbySurah(pageList.get(pos-1).getAyahItems().get(0).getSurahIndex());
        setSurahselected(pageList.get(pos-1).getAyahItems().get(0).getSurahIndex());
        //   int ayaID=0;
        int counter=0;
     //   quranbySurah.add(0, new QuranEntity(1, 1, 1));
        List<String> downloadLinks = new ArrayList<>();
     //   ayaList.add(0, new Aya(1, 1, 1));
        //loop for all page ayat


        for (QuranEntity ayaItem : quranbySurah) {
            //validate if aya download or not
            if (!QuranValidateSources.validateAyaAudio(this, readerID, ayaItem.getAyah(),  ayaItem.getSurah())) {

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
                } else if (ayaLength == 2){

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
    private void loadData() {
        pageList = ((QuranGrammarApplication) getApplication()).getFullQuranPages();
        if (pageList != null && pageList.size() >= 50) {
            handler.sendEmptyMessage(0);
            Log.d(TAG, "loadData: %%% ");
        } else {
            Log.d(TAG, "loadData: @@@@");
            new Thread(() -> {
                List<Page> pages = new ArrayList<>();
                Page page;
                List<QuranMetaEntity> ayahItems;
                for (int i = 1; i <= 604; i++) {
                    ayahItems = repository.getAyahsByPage(i);
                    if (ayahItems.size() > 0) {
                        page = new Page();
                        page.setAyahItems(ayahItems);
                        page.setPageNum(i);
                        page.setJuz(ayahItems.get(0).getJuz());
                        pages.add(page);
                    }
                }

                pageList = new ArrayList<>(pages);

                handler.sendEmptyMessage(0);

            }).start();
        }
        new Thread(this::generateListOfPagesStartWithHizbQurater).start();
    }

/*    private void loadPagesReadLoge() {
        currentDate = DateOperation.getCurrentDateExact().getTime();
        currentDateStr = DateOperation.getCurrentDateAsString();

        readLog = repository.getLReadLogByDate(currentDateStr);
        if (readLog == null) {
            readLog = new ReadLog();
            readLog.setDate(currentDate);
            readLog.setStrDate(currentDateStr);
            readLog.setPages(new ArraySet<>());
        }
        pagesReadLogNumber = readLog.getPages();

        for (Integer integer : pagesReadLogNumber) {
            Log.d(TAG, "loadPagesReadLoge: " + integer);
        }
    }*/

    /**
     * retrieve list of pages that contain start of hizb Quaters.
     */
    private void generateListOfPagesStartWithHizbQurater() {
        quraterSStart = repository.getHizbQuaterStart();
        // logData(quraterSStart);
    }

    private void foundState() {
      //  spShowAyahs.setVisibility(View.GONE);
        tvNoQuranData.setVisibility(View.GONE);
        rvAyahsPages.setVisibility(View.VISIBLE);
    }

    private void notFound() {
     //   spShowAyahs.setVisibility(View.GONE);
        tvNoQuranData.setVisibility(View.VISIBLE);
        rvAyahsPages.setVisibility(View.GONE);
    }

    private void logData(List<Integer> quraterSStart) {
        for (Integer integer : quraterSStart) {
            Log.d(TAG, "logData: " + integer);
        }
    }

    private void showMessage(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();

    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.play)) {
            try {
                footerPlay();
            } catch (PendingIntent.CanceledException e) {
                throw new RuntimeException(e);
            }
        } else if (v == findViewById(R.id.stop)) {
            //Stop media player
            ((ImageButton) findViewById(R.id.pause_play)).setImageResource(R.drawable.ic_pause);
            playerFooter.setVisibility(View.GONE);
            normalFooter.setVisibility(View.VISIBLE);
            sendBroadcast(stop);
            //      stopService(player);
            //   stopService(new Intent(this, AudioService.class));

        } else if (v == findViewById(R.id.pause_play)) {
            //Pause and play media
            if (pausePlayFlag != true) {
                ((ImageButton) findViewById(R.id.pause_play))
                        .setImageResource(R.drawable.ic_play);
                sendBroadcast(pause);
                pausePlayFlag = true;
            } else {
                ((ImageButton) findViewById(R.id.pause_play))
                        .setImageResource(R.drawable.ic_pause);
                sendBroadcast(play);
                pausePlayFlag = false;
            }
        } else if (v == findViewById(R.id.repeat)) {

            repeatCounter++;
            repeateValue = repeatCounter;

            switch (repeatCounter) {

                case 1:
                    repeatNotRepeat = true;
                    sendBroadcast(repeat);
                    ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("1"));
                    break;
                case 2:
                    repeatNotRepeat = true;
                    sendBroadcast(repeat);
                    ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("2"));

                    break;
                case 3:
                    repeatNotRepeat = true;
                    sendBroadcast(repeat);
                    ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("3"));

                    break;
                case 4:
                    repeatNotRepeat = true;
                    sendBroadcast(repeat);
                    ((ImageButton) findViewById(R.id.repeat)).setImageBitmap(drawNumbers("∞"));

                    repeatCounter = -1;
                    break;
                default:
                    repeatNotRepeat = false;
                    sendBroadcast(notRepeat);
                    ((ImageButton) findViewById(R.id.repeat)).setImageResource(R.drawable.ic_repeat);

                    repeatCounter = 0;
                    break;
            }


        } else if (v == findViewById(R.id.before)) {
            //play the before aya
            sendBroadcast(playBack);
        } else if (v == findViewById(R.id.forward)) {
            //play forward aya
            sendBroadcast(playForward);
        } else if (v == findViewById(R.id.canceldownload)) {
            downloadFooter.setVisibility(View.GONE);
            normalFooter.setVisibility(View.VISIBLE);
            //stop flag of auto start audio after download
            startBeforeDownload = false;
            //stop download service
            stopService(new Intent(this, DownloadService.class));
        }

    }

/*    @OnClick(R.id.tv_no_quran_data)
    public void onBOClicked() {
        Intent openAcivity = new Intent(ShowMushafActivity.this, DownloadActivity.class);
        startActivity(openAcivity);
    }*/
/*
    @Override
    protected void onStop() {
        super.onStop();
        repository.addLatestread(lastpageShown);
        saveReadLog();
    }

    private void saveReadLog() {
        readLog.setPages(pagesReadLogNumber);
        try {
            repository.addReadLog(readLog);
            //       Log.d(TAG, "saveReadLog: added");
        } catch (Exception e) {
            repository.updateReadLog(readLog);
            //      Log.d(TAG, "saveReadLog: updated");
        }


    }*/
}
