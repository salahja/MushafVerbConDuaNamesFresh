package com.example.JustJava;

import static android.text.TextUtils.concat;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;

import com.example.mushafconsolidated.Entities.CorpusExpandWbwPOJO;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;

public class WbwSurah {
    private final Context context;
    private  int surah;
    private  LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> ruku=new LinkedHashMap<>();

    ArrayList<CorpusAyahWord> corpusayahWordArrayList = new ArrayList<>();

    public WbwSurah(Context context, int surah, ArrayList<CorpusAyahWord> corpusayahWordArrayList, LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> ruku) {
        this.context = context;
        this.surah = surah;
        this.corpusayahWordArrayList=corpusayahWordArrayList;
        this.ruku=ruku;

    }

    public void getWordbyword() {
        Utils utils = new Utils(context);

        int versesnumbers;
        //    versesnumbers = getVersescount();
         ArrayList<CorpusExpandWbwPOJO> wbw = utils.getCorpusWbwBySurah(surah);

      //  ArrayList<CorpusExpandWbwPOJO> wbw = utils.getCorpusWbwBySurahAyahtopic(2,1);


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
            //   word.setWordsAr(ayanno);
            words.setAraone(s);
            words.setSurahId(surahid);
            words.setVerseId(verseno);
            wordArrayListpassage.add(words);
            ayahWord.setWord(wordArrayList);
            int asize = wordArrayList.size();
            if(asize>=1) {
                int ispassage = wordArrayList.get(asize - 1).getPassage_no();

                if (ispassage != 0) {
                    ruku.put(ispassage, wordArrayListpassage);
                    wordArrayListpassage = new ArrayList<>();

                }
            }
            corpusayahWordArrayList.add(ayahWord);

        }





    }


}
