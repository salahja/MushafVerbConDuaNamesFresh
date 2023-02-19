package com.example.JustJava;

import static android.content.Context.MODE_PRIVATE;
import static android.text.TextUtils.concat;

import static com.example.Constant.CHAPTER;
import static com.example.Constant.SURAH_ARABIC_NAME;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.Entities.CorpusExpandWbwPOJO;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

import wheel.OnWheelChangedListener;
import wheel.WheelView;

public class WbwSurah {
    private final Context context;
    private  int surah;
    private String[] surahWheelDisplayData;
    private String[] ayahWheelDisplayData;
    private  LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> ruku=new LinkedHashMap<>();
    boolean isrefresh, starttrue;
    int surahselected, ayahselected;
    ArrayList<CorpusAyahWord> corpusayahWordArrayList = new ArrayList<>();

    public WbwSurah(Context context, int surah, ArrayList<CorpusAyahWord> corpusayahWordArrayList, LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> ruku) {
        this.context = context;
        this.surah = surah;
        this.corpusayahWordArrayList=corpusayahWordArrayList;
        this.ruku=ruku;

    }

    public WbwSurah(Context context ,boolean isrefresh, boolean starttrue, int surahselected, int ayahselected) {
        this.context=context;
        this.isrefresh=isrefresh;this.starttrue=starttrue;this.surahselected=surahselected;this.ayahselected=ayahselected;
        SurahAyahPicker();
    }

    public void getWordbyword() {
        Utils utils = new Utils(context);

        int versesnumbers;
        //    versesnumbers = getVersescount();
        ArrayList<CorpusExpandWbwPOJO> wbw = utils.getCorpusWbwBySurah(surah);
        //  ArrayList<MafoolBihi> mafoolbihiquran = utils.getMafoolbihiquran();
        int verseglobal = 0;
        int tempVerseWord;
        int verseexit = wbw.size();
        int verseno = 0;
        int surahid = 0;
        final int[] intArray = context.getResources().getIntArray(R.array.versescount);
        versesnumbers=    intArray[surah-1];
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
                ruku.put(ispassage, wordArrayListpassage);
                wordArrayListpassage = new ArrayList<>();

            }
            corpusayahWordArrayList.add(ayahWord);

        }





    }

 

    public int SurahAyahPicker() {
        ArrayList<Integer>rangevalues=new ArrayList<>();
        TextView mTextView;
        final int[] verseselected = {0};
        WheelView chapterWheel, verseWheel, wvDay;
        Utils utils = new Utils(context);
        final String[][] mYear = {new String[1]};
        String[] mMonth = new String[1];
        surahWheelDisplayData = new String[]{""};
        ayahWheelDisplayData = new String[]{""};
        final ArrayList<String>[] current = new ArrayList[]{new ArrayList<>()};
        int mDay = 0;
        final int[] chapterno = new int[1];
        final int[] verseno = new int[1];
        final String[] surahArrays = context.getResources().getStringArray(R.array.surahdetails);
        final String[] versearrays = context.getResources().getStringArray(R.array.versescounts);
        final int[] intarrays = context.getResources().getIntArray(R.array.versescount);
        //     final AlertDialog.Builder dialogPicker = new AlertDialog.Builder(this);
        AlertDialog.Builder dialogPicker = new AlertDialog.Builder(context);
        Dialog dlg = new Dialog(context);
        //  AlertDialog dialog = builder.create();
        ArrayList<ChaptersAnaEntity> soraList = utils.getAllAnaChapters();
        LayoutInflater inflater =((AppCompatActivity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_wheel_t, null);
        //  View view = inflater.inflate(R.layout.activity_wheel, null);
        dialogPicker.setView(view);
        mTextView = view.findViewById(R.id.textView2);
        chapterWheel = view.findViewById(R.id.wv_year);
        verseWheel = view.findViewById(R.id.wv_month);
        chapterWheel.setEntries(surahArrays);
        chapterWheel.setCurrentIndex(surahselected - 1);
        //set wheel initial state
        boolean initial = true;
        if (initial) {
            String text = (String) chapterWheel.getItem(surahselected - 1);
            surahWheelDisplayData[0] = (text);
            String[] chapno = text.split(" ");
            chapterno[0] = Integer.parseInt(chapno[0]);
            verseno[0] = 1;
            current[0] = new ArrayList<>();
            int intarray;
            if (surahselected != 0) {
                intarray = intarrays[surahselected - 1];
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

        int vcount = Integer.parseInt(versearrays[surahselected - 1]);

        for (int i = 1; i <= vcount; i++) {
            current[0].add(String.valueOf(i));
        }

        verseWheel.setEntries(current[0]);
        verseWheel.setCurrentIndex(ayahselected);

        dialogPicker.setPositiveButton("Done", (dialogInterface, i) -> {

            String sura = "";

            //   setSurahArabicName(suraNumber + "-" + soraList.get(suraNumber - 1).getNameenglish() + "-" + soraList.get(suraNumber - 1).getAbjadname());
            if (chapterno[0] == 0) {
                //setSurahselected(surah);

            } else {
                sura = String.valueOf(soraList.get(chapterno[0] - 1).getChapterid());

              //  setSurahselected(soraList.get(chapterno[0] - 1).getChapterid());
             //   setSurahNameEnglish(soraList.get(chapterno[0] - 1).getNameenglish());
            //    setSurahNameArabic(soraList.get(chapterno[0] - 1).getNamearabic());

                SharedPreferences pref = context.getSharedPreferences("lastreadmushaf", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(CHAPTER, Integer.parseInt(sura));
                //  editor.putInt("page", page.getAyahItemsquran().get(0).getPage());

                editor.putString(SURAH_ARABIC_NAME, soraList.get(chapterno[0] - 1).getNamearabic());
                editor.apply();
            }

           verseselected[0] = verseno[0];
         //   setAyah(verseselected);
            String aya = String.valueOf(verseno[0]);
            if (isrefresh && starttrue) {
              //  releasePlayer();
              //  RefreshActivity(sura, aya, false);
            } else if (starttrue) {
               // updateStartRange(verseselected);
                rangevalues.add(verseselected[0]);
            } else {
              //  updateEndRange(verseselected);
                rangevalues.add(verseselected[0]);
            }


        });

        dialogPicker.setNegativeButton("Cancel", (dialogInterface, i) -> {
        });
        SharedPreferences sharedPreferences;
        sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        AlertDialog alertDialog = dialogPicker.create();
        String preferences = sharedPreferences.getString("themepref", "dark");
        int db = ContextCompat.getColor(context, R.color.odd_item_bg_dark_blue_light);

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
        buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.green));
        Button buttonNegative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.red));
        if (preferences.equals("purple")) {
            buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.Goldenrod));

        } else if (preferences.equals("brown")) {
            buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.colorMuslimMate));
            buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.red));
            //  cardview.setCardBackgroundColor(ORANGE100);
        } else if (preferences.equals("blue")) {
            buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.Goldenrod));
            //  cardview.setCardBackgroundColor(db);
        } else if (preferences.equals("green")) {
            buttonPositive.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            buttonNegative.setTextColor(ContextCompat.getColor(context, R.color.cyan_light));
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
        return verseselected[0];
    }
}
