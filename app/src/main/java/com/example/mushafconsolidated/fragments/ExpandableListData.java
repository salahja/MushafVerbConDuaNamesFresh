package com.example.mushafconsolidated.fragments;

import static android.graphics.Color.CYAN;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.YELLOW;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static com.example.Constant.BCYAN;
import static com.example.Constant.CYANLIGHTEST;
import static com.example.Constant.FORESTGREEN;
import static com.example.Constant.GOLD;
import static com.example.Constant.GREENDARK;
import static com.example.Constant.GREENYELLOW;
import static com.example.Constant.KASHMIRIGREEN;
import static com.example.Constant.MIDNIGHTBLUE;
import static com.example.Constant.ORANGE400;
import static com.example.Constant.WBURNTUMBER;
import static com.example.Constant.WHOTPINK;
import static com.example.Constant.deepburnsienna;
import static com.example.Constant.harfinnaspanDark;
import static com.example.Constant.harfismspanDark;
import static com.example.Constant.harfkhabarspanDark;
import static com.example.Constant.harfshartspanDark;
import static com.example.Constant.jawabshartspanDark;
import static com.example.Constant.mudhafspansDark;
import static com.example.Constant.prussianblue;
import static com.example.Constant.shartspanDark;
import static com.example.Constant.sifaspansDark;
import static Utility.ArabicLiterals.QALA;
import static Utility.ArabicLiterals.SALA;
import static Utility.ArabicLiterals.SMALLJEEM;
import static Utility.ArabicLiterals.SMALLLAM;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.example.mushafconsolidated.Activity.SplitQuranVerses;
import com.example.mushafconsolidated.Entities.NewCorpusExpandWbwPOJO;
import com.example.mushafconsolidated.Entities.NewKanaEntity;
import com.example.mushafconsolidated.Entities.NewMudhafEntity;
import com.example.mushafconsolidated.Entities.NewNasbEntity;
import com.example.mushafconsolidated.Entities.NewShartEntity;
import com.example.mushafconsolidated.Entities.SifaEntity;
import com.example.mushafconsolidated.Entities.wbwentity;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.model.Word;
import com.example.utility.QuranGrammarApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListData {
    private final int chapterid;
    private final int ayanumber;
    private final ArrayList<NewCorpusExpandWbwPOJO> corpusSurahWord;
    private final Utils utils;
    private String whichwbw;
    private boolean dark;

    public ExpandableListData(int chapterid, int ayanumber, ArrayList<NewCorpusExpandWbwPOJO> corpusSurahWord, Utils utils) {
        this.chapterid = chapterid;
        this.ayanumber = ayanumber;
        this.corpusSurahWord = corpusSurahWord;
        this.utils = utils;
    }

    public LinkedHashMap<String, List<SpannableStringBuilder>> getData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        String preferences = prefs.getString("theme", "dark");
        dark = preferences.equals("dark")
                || preferences.equals("blue")

                || preferences.equals("green");
        String whichtranslation = prefs.getString("selecttranslation", "en_sahih");
        whichwbw = prefs.getString("wbw", "en_sahih");
        LinkedHashMap<String, List<SpannableStringBuilder>> expandableListDetail = new LinkedHashMap<>();
        List<SpannableStringBuilder> verse = new ArrayList<>();
        List<SpannableStringBuilder> translation = new ArrayList<>();
        verse.add(SpannableStringBuilder.valueOf(corpusSurahWord.get(0).getSurah() + ":" + corpusSurahWord.get(0).getSurah() + ":-" + corpusSurahWord.get(0).getQurantext()));
        if (whichtranslation.equals("en_sahih")) {
            translation.add(SpannableStringBuilder.valueOf(corpusSurahWord.get(0).getTranslation()));
        } else {
            translation.add(SpannableStringBuilder.valueOf(corpusSurahWord.get(0).getUr_junagarhi()));

        }
        List<SpannableStringBuilder> shartarray = new ArrayList<>();
        newSetShart(shartarray);
        List<SpannableStringBuilder> harfnasbarray = new ArrayList<>();
        setNewNasb(harfnasbarray);
        List<SpannableStringBuilder> mausoofsifaarray = new ArrayList<>();
        setMausoof(mausoofsifaarray);
        List<SpannableStringBuilder> mudhafarray = new ArrayList<>();
        setMudhaf(mudhafarray);
        List<SpannableStringBuilder> kanaarray = new ArrayList<>();
        newsetKana(kanaarray);
        expandableListDetail.put("Verse", verse);
        expandableListDetail.put("Translation", translation);
        expandableListDetail.put("Conditional/جملة شرطية\"", shartarray);
        expandableListDetail.put("Accusative/ ", harfnasbarray);
        expandableListDetail.put("Verb kāna/كان واخواتها", kanaarray);
        expandableListDetail.put("Adjectival Phrases/مرکب توصیفی", mausoofsifaarray);
        expandableListDetail.put("Possessive/إضافَة", mudhafarray);
        return expandableListDetail;
    }

    private void setNewNasb(List<SpannableStringBuilder> hasbarray) {
        ArrayList<NewNasbEntity> kanaSurahAyahnew = utils.getHarfNasbIndSurahAyahSnew(chapterid, ayanumber);

        for (NewNasbEntity nasbEntity : kanaSurahAyahnew) {
            //System.out.println("CHECK");
            if (dark) {
                harfinnaspanDark = new ForegroundColorSpan(GREEN);
                harfismspanDark = new ForegroundColorSpan(BCYAN);
                harfkhabarspanDark = new ForegroundColorSpan(YELLOW);
            } else {
                harfinnaspanDark = new ForegroundColorSpan(KASHMIRIGREEN);
                harfismspanDark = new ForegroundColorSpan(prussianblue);
                harfkhabarspanDark = new ForegroundColorSpan(deepburnsienna);
            }



            String harfofverse;
            String ismofverse;
            String khabarofverse;
            int indexstart = nasbEntity.getIndexstart();
            int indexend = nasbEntity.getIndexend();
            int ismstartindex = nasbEntity.getIsmstart();
            int ismendindex = nasbEntity.getIsmend();
            int khabarstartindex = nasbEntity.getKhabarstart();
            int khabarendindex = nasbEntity.getKhabarend();
            String quranverses = corpusSurahWord.get(0).getQurantext();
            harfofverse = quranverses.substring(indexstart, indexend);
            ismofverse = quranverses.substring(ismstartindex, ismendindex);
            khabarofverse = quranverses.substring(khabarstartindex, khabarendindex);
            boolean isharfb = indexstart >= 0 && indexend > 0;
            boolean isism = ismstartindex >= 0 && ismendindex > 0;
            boolean iskhabar = khabarstartindex >= 0 && khabarendindex > 0;
            boolean a = isharfb && isism && iskhabar;
            boolean d = isharfb && iskhabar;
            boolean b = isharfb && isism;
            int harfword = nasbEntity.getHarfwordno();
            int shartSword = nasbEntity.getIsmstartwordno();
            int shartEword = nasbEntity.getIsmendwordno();
            int jawbSword = nasbEntity.getKhabarstartwordno();
            int jawabEword = nasbEntity.getKhabarendwordno();
            StringBuilder sb = new StringBuilder();
            StringBuilder khabarsb = new StringBuilder();
            SpannableStringBuilder harfspannble;
            SpannableStringBuilder harfismspannable;
            SpannableStringBuilder khabarofversespannable;
            if (a) {
                int isismkhabarconnected = nasbEntity.getKhabarstart() - nasbEntity.getIsmend();
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfismspannable = new SpannableStringBuilder(ismofverse);
                khabarofversespannable = new SpannableStringBuilder(khabarofverse);

                harfspannble.setSpan(harfinnaspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                harfismspannable.setSpan(harfismspanDark, 0, ismofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                khabarofversespannable.setSpan(harfkhabarspanDark, 0, khabarofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                if (nasbEntity.getIsmstart() > nasbEntity.getKhabarstart()) {
                    CharSequence charSequence = TextUtils.concat(harfspannble, " ", khabarofversespannable, " ", harfismspannable);
                    hasbarray.add(SpannableStringBuilder.valueOf(charSequence));
                } else {
                    CharSequence charSequence = TextUtils.concat(harfspannble, " ", harfismspannable, " ", khabarofversespannable);
                    hasbarray.add(SpannableStringBuilder.valueOf(charSequence));

                }
                if (isismkhabarconnected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(),
                            corpusSurahWord.get(0).getAyah(), harfword, jawabEword);
                    for (wbwentity w : list) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");
                    }

                } else {
                    ArrayList<wbwentity> wbwayah = utils.getwbwQuranBySurahAyah(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah());
                    for (wbwentity w : wbwayah) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        if (w.getWordno() == harfword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= shartSword && w.getWordno() <= shartEword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= jawbSword && w.getWordno() <= jawabEword) {
                            //     sb. append("... ");
                            khabarsb.append(temp).append(" ");

                        }

                    }
                    sb.append(".....");
                    sb.append(khabarsb);
                    hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));

                }
                hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                //  CharSequence first = TextUtils.concat(harfspannble," ",shartofverse);
            } else if (d) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                khabarofversespannable = new SpannableStringBuilder(khabarofverse);
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                khabarofversespannable.setSpan(jawabshartspanDark, 0, khabarofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble, " ", khabarofversespannable);
                hasbarray.add(SpannableStringBuilder.valueOf(charSequence));
                //     StringBuilder sb = new StringBuilder();
                int wordfrom = nasbEntity.getHarfwordno();
                int wordto;
                String[] split = khabarofverse.split("\\s");
                if (split.length == 1) {
                    wordto = nasbEntity.getKhabarstartwordno();
                } else {
                    wordto = nasbEntity.getKhabarendwordno();

                }
                int isconnected = nasbEntity.getKhabarstart() - nasbEntity.getIndexend();
                if (isconnected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
                    for (wbwentity w : list) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                } else {
                    int wordfroms = nasbEntity.getHarfwordno();
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordfroms);
                    int from = nasbEntity.getKhabarstartwordno();
                    int to = nasbEntity.getKhabarendwordno();
                    if (to == 0) {
                        to = from;
                    }
                    sb.append(list.get(0).getEn()).append(".......");
                    switch (whichwbw) {
                        case "en":
                            sb.append(list.get(0).getEn()).append(".......");
                            break;
                        case "ur":
                            sb.append(list.get(0).getUr()).append(".......");

                            break;
                        case "bn":
                            sb.append(list.get(0).getBn()).append(".......");
                            break;
                        case "id":
                            sb.append(list.get(0).getId()).append(".......");
                            break;
                    }
                    ArrayList<wbwentity> lists = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), from, to);
                    for (wbwentity w : lists) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                }

            } else if (b) {
                harfshartspanDark = new ForegroundColorSpan(GOLD);
                shartspanDark = new ForegroundColorSpan(GREEN);
                jawabshartspanDark = new ForegroundColorSpan(CYAN);
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfismspannable = new SpannableStringBuilder(ismofverse);
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                harfismspannable.setSpan(shartspanDark, 0, ismofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequences = TextUtils.concat(harfspannble, " " + harfismspannable);
                hasbarray.add(SpannableStringBuilder.valueOf(charSequences));
                //    kanaarray.add(SpannableStringBuilder.valueOf(charSequence));
                int ismconnected = nasbEntity.getIsmstart() - nasbEntity.getIndexend();
                int wordfrom = nasbEntity.getHarfwordno();
                int wordto = nasbEntity.getIsmendwordno();
                if (ismconnected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
                    for (wbwentity w : list) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                } else {
                    //    kanaarray.add(SpannableStringBuilder.valueOf(list.get(0).getEn()));
                    int from = nasbEntity.getHarfwordno();
                    int ismfrom = nasbEntity.getIsmstartwordno();
                    int ismto = nasbEntity.getIsmendwordno();
                    //     sb.append(list.get(0).getEn()).append("----");
                    ArrayList<wbwentity> harf = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), from, from);
                    ArrayList<wbwentity> ism = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), ismfrom, ismto);
                    for (wbwentity w : harf) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    sb.append(".....");
                    for (wbwentity w : ism) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    hasbarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                }

            } else if (isharfb) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble);
                hasbarray.add(SpannableStringBuilder.valueOf(charSequence));
                int wordfroms = nasbEntity.getHarfwordno();
                ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfroms, wordfroms);
                StringBuffer sbss = new StringBuffer();
                switch (whichwbw) {
                    case "en":
                        sbss.append(list.get(0).getEn()).append(".......");
                        break;
                    case "ur":
                        sbss.append(list.get(0).getUr()).append(".......");

                        break;
                    case "bn":
                        sbss.append(list.get(0).getBn()).append(".......");
                        break;
                    case "id":
                        sbss.append(list.get(0).getId()).append(".......");
                        break;
                }
                hasbarray.add(SpannableStringBuilder.valueOf(sbss));

            }
            // kanaarray.add(spannable);
        }

    }

    private StringBuilder getSelectedTranslation(wbwentity tr) {
        StringBuilder sb = new StringBuilder();
        switch (whichwbw) {
            case "en":
                sb.append(tr.getEn());
                break;
            case "ur":
                sb.append(tr.getUr());

                break;
            case "bn":
                sb.append(tr.getBn());
                break;
            case "id":
                sb.append(tr.getId());
                break;
        }
        sb.append(" ");
        return sb;
    }

    private void newsetKana(List<SpannableStringBuilder> kanaarray) {
        ArrayList<NewKanaEntity> kanaSurahAyahnew = utils.getKanaSurahAyahnew(chapterid, ayanumber);
        ForegroundColorSpan harfkana;
        ForegroundColorSpan kanaism;
        ForegroundColorSpan kanakhbar;
        if (dark) {
            harfkana = new ForegroundColorSpan(GOLD);
            kanaism = new ForegroundColorSpan(ORANGE400);
            kanakhbar = new ForegroundColorSpan(CYAN);
        } else {
            harfkana = new ForegroundColorSpan(FORESTGREEN);
            kanaism = new ForegroundColorSpan(KASHMIRIGREEN);
            kanakhbar = new ForegroundColorSpan(WHOTPINK);
        }

        for (NewKanaEntity kana : kanaSurahAyahnew) {
            //System.out.println("CHECK");
            String harfofverse;
            String ismofverse;
            String khabarofverse;
            int start = kana.getIndexstart();
            int end = kana.getIndexend();
            int isstart = kana.getIsmkanastart();
            int issend = kana.getIsmkanaend();
            int khabarstart = kana.getKhabarstart();
            int khabarend = kana.getKhabarend();
            String quranverses = corpusSurahWord.get(0).getQurantext();
            harfofverse = quranverses.substring(start, end);
            if(issend>isstart) {
                ismofverse = quranverses.substring(isstart, issend);
            }else {
                ismofverse="";
            }
            khabarofverse = quranverses.substring(khabarstart, khabarend);
            boolean isharfb = start >= 0 && end > 0;
            boolean isism = isstart >= 0 && issend > 0;
            boolean isjawab = khabarstart >= 0 && khabarend > 0;
            boolean a = isharfb && isism && isjawab;
            boolean d = isharfb && isjawab;
            boolean b = isharfb && isism;

            int harfword = kana.getHarfwordno();
            int ismSword = kana.getIsmwordo();
            int ismEword = kana.getIsmendword();
            int khabarSword = kana.getKhabarstartwordno();
            int habarEword = kana.getKhabarendwordno();
            SpannableStringBuilder harfspannble;
            SpannableStringBuilder harfismspannable;
            SpannableStringBuilder khabarofversespannable;
            if (a) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfismspannable = new SpannableStringBuilder(ismofverse);
                khabarofversespannable = new SpannableStringBuilder(khabarofverse);
                harfspannble.setSpan(harfkana, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                harfismspannable.setSpan(kanaism, 0, ismofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                khabarofversespannable.setSpan(kanakhbar, 0, khabarofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                if (kana.getIsmkanastart() > kana.getKhabarstart()) {
                    CharSequence charSequence = TextUtils.concat(harfspannble, " ", khabarofversespannable, " ", harfismspannable);
                    kanaarray.add(SpannableStringBuilder.valueOf(charSequence));
                } else {
                    CharSequence charSequence = TextUtils.concat(harfspannble, " ", harfismspannable, " ", khabarofversespannable);
                    kanaarray.add(SpannableStringBuilder.valueOf(charSequence));

                }
                StringBuilder sb = new StringBuilder();
                StringBuilder ismorkhabarsb = new StringBuilder();
                ArrayList<wbwentity> wbwayah = utils.getwbwQuranBySurahAyah(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah());
                for (wbwentity w : wbwayah) {
                    new StringBuilder();
                    StringBuilder temp;
                    temp = getSelectedTranslation(w);
                    if (w.getWordno() == harfword) {
                        sb.append(temp.append(" "));
                    } else if (w.getWordno() >= ismSword && w.getWordno() <= ismEword) {
                        sb.append(temp).append(" ");
                    } else if (w.getWordno() >= khabarSword && w.getWordno() <= habarEword) {
                        ismorkhabarsb.append(temp).append(" ");

                    }

                }
                sb.append("... ");
                sb.append(ismorkhabarsb);
                kanaarray.add(SpannableStringBuilder.valueOf(sb.toString()));

                //  CharSequence first = TextUtils.concat(harfspannble," ",shartofverse);
            } else if (d) {
                if (dark) {
                    harfkana = new ForegroundColorSpan(GOLD);
                    kanaism = new ForegroundColorSpan(ORANGE400);
                    kanakhbar = new ForegroundColorSpan(CYAN);
                } else {
                    harfkana = new ForegroundColorSpan(FORESTGREEN);
                    kanaism = new ForegroundColorSpan(KASHMIRIGREEN);
                    kanakhbar = new ForegroundColorSpan(WHOTPINK);
                }
                harfspannble = new SpannableStringBuilder(harfofverse);
                khabarofversespannable = new SpannableStringBuilder(khabarofverse);
                harfspannble.setSpan(harfkana, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                khabarofversespannable.setSpan(kanakhbar, 0, khabarofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble, " ", khabarofversespannable);
                kanaarray.add(SpannableStringBuilder.valueOf(charSequence));
                StringBuilder sb = new StringBuilder();
                int wordfrom = kana.getHarfwordno();
                int wordto;
                String[] split = khabarofverse.split("\\s");
                if (split.length == 1) {
                    wordto = kana.getKhabarstartwordno();
                } else {
                    wordto = kana.getKhabarendwordno();

                }
                int isconnected = kana.getKhabarstartwordno() - kana.getHarfwordno();
                if (isconnected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
                    for (wbwentity w : list) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    kanaarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                } else {
                    int wordfroms = kana.getHarfwordno();
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordfroms);
                    int from = kana.getKhabarstartwordno();
                    int to = kana.getKhabarendwordno();
                    if (to == 0) {
                        to = from;
                    }
                    switch (whichwbw) {
                        case "en":
                            sb.append(list.get(0).getEn()).append(".......");
                            break;
                        case "ur":
                            sb.append(list.get(0).getUr()).append(".......");

                            break;
                        case "bn":
                            sb.append(list.get(0).getBn()).append(".......");
                            break;
                        case "id":
                            sb.append(list.get(0).getId()).append(".......");
                            break;
                    }
                    //    sb.append(list).append("----");
                    ArrayList<wbwentity> lists = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), from, to);
                    for (wbwentity w : lists) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    kanaarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                }

            } else if (b) {
                if (dark) {
                    harfkana = new ForegroundColorSpan(GOLD);
                    kanaism = new ForegroundColorSpan(ORANGE400);
                    kanakhbar = new ForegroundColorSpan(CYAN);
                } else {
                    harfkana = new ForegroundColorSpan(FORESTGREEN);
                    kanaism = new ForegroundColorSpan(KASHMIRIGREEN);
                    kanakhbar = new ForegroundColorSpan(WHOTPINK);
                }
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfismspannable = new SpannableStringBuilder(ismofverse);
                harfspannble.setSpan(harfkana, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                harfismspannable.setSpan(kanaism, 0, ismofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequences = TextUtils.concat(harfspannble, " " + harfismspannable);
                kanaarray.add(SpannableStringBuilder.valueOf(charSequences));
                //    kanaarray.add(SpannableStringBuilder.valueOf(charSequence));
                StringBuilder sb = new StringBuilder();
                StringBuilder ismorkhabarsb = new StringBuilder();
                int ismconnected = kana.getIsmkanastart() - kana.getIndexend();
                int wordfrom = kana.getHarfwordno();
                int wordto = kana.getIsmwordo();
                if (ismconnected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
                    for (wbwentity w : list) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }
                    kanaarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                } else {
                    //  ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfroms, wordfroms);
                    ArrayList<wbwentity> wbwayah = utils.getwbwQuranBySurahAyah(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah());
                    for (wbwentity w : wbwayah) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        if (w.getWordno() == harfword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= ismSword && w.getWordno() <= ismEword) {
                            ismorkhabarsb.append(temp).append(" ");
                        }

                    }
                    sb.append(".....");
                    sb.append(ismorkhabarsb);
                    kanaarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                }

            } else if (isharfb) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfspannble.setSpan(harfkana, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble);
                kanaarray.add(SpannableStringBuilder.valueOf(charSequence));
                int wordfroms = kana.getHarfwordno();
                ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfroms, wordfroms);
                StringBuffer sb = new StringBuffer();
                switch (whichwbw) {
                    case "en":
                        sb.append(list.get(0).getEn()).append(".......");
                        break;
                    case "ur":
                        sb.append(list.get(0).getUr()).append(".......");

                        break;
                    case "bn":
                        sb.append(list.get(0).getBn()).append(".......");
                        break;
                    case "id":
                        sb.append(list.get(0).getId()).append(".......");
                        break;
                }
                kanaarray.add(SpannableStringBuilder.valueOf(sb));

            }
            // kanaarray.add(spannable);
        }

    }

    private void setMausoof(List<SpannableStringBuilder> mausoofsifaarray) {
        ArrayList<SifaEntity> sifabySurahAyah = utils.getSifabySurahAyah(chapterid, ayanumber);
        for (SifaEntity shartEntity : sifabySurahAyah) {

            if (dark) {
                sifaspansDark = new BackgroundColorSpan(WBURNTUMBER);
            } else {
                sifaspansDark = new BackgroundColorSpan(CYANLIGHTEST);
            }

            //   sifaspansDark = new BackgroundColorSpan(WBURNTUMBER);
            String quranverses = corpusSurahWord.get(0).getQurantext();
            SpannableStringBuilder spannable = new SpannableStringBuilder(quranverses);
            try {
                spannable.setSpan(sifaspansDark, shartEntity.getStartindex(), shartEntity.getEndindex(), SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch (IndexOutOfBoundsException e) {
                System.out.println(shartEntity.getSurah() + "  " + shartEntity.getAyah() + "  " + quranverses);
            }
            CharSequence sequence = spannable.subSequence(shartEntity.getStartindex(), shartEntity.getEndindex());
            mausoofsifaarray.add((SpannableStringBuilder) sequence);
            int wordfrom = shartEntity.getWordno() - 1;
            int wordto = shartEntity.getWordno();
            StringBuilder ssb = new StringBuilder();
            ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
            for (wbwentity w : list) {
                new StringBuilder();
                StringBuilder temp;
                temp = getSelectedTranslation(w);
                ssb.append(temp).append(" ");

            }
            mausoofsifaarray.add(SpannableStringBuilder.valueOf(ssb.toString()));
        }
    }

    private void setMudhaf(List<SpannableStringBuilder> mudhafarray) {
        //   ArrayList<MudhafEntity> mudhafSurahAyah = utils.getMudhafSurahAyah(chapterid, ayanumber);
        ArrayList<NewMudhafEntity> mudhafSurahAyah = utils.getMudhafSurahAyahNew(chapterid, ayanumber);
        for (NewMudhafEntity mudhafEntity : mudhafSurahAyah) {


            if (dark) {
                mudhafspansDark = new BackgroundColorSpan(MIDNIGHTBLUE);

            } else {
                mudhafspansDark = new BackgroundColorSpan(GREENYELLOW);

            }



            String quranverses = corpusSurahWord.get(0).getQurantext();
            SpannableStringBuilder spannable = new SpannableStringBuilder(quranverses);
            spannable.setSpan(mudhafspansDark, mudhafEntity.getStartindex(), mudhafEntity.getEndindex(), SPAN_EXCLUSIVE_EXCLUSIVE);
            CharSequence sequence = spannable.subSequence(mudhafEntity.getStartindex(), mudhafEntity.getEndindex());
            mudhafarray.add((SpannableStringBuilder) sequence);
            StringBuilder sb = new StringBuilder();
            int wordfrom = mudhafEntity.getWordfrom();
            int wordto = mudhafEntity.getWordto();
            String[] strings = sequence.toString().split("\\s");
            StringBuilder ssb = new StringBuilder();
            if (strings.length == 2) {
                ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordfrom, wordto);
                for (wbwentity w : list) {
                    new StringBuilder();
                    StringBuilder temp;
                    temp = getSelectedTranslation(w);
                    ssb.append(temp).append(" ");

                }
                mudhafarray.add(SpannableStringBuilder.valueOf(ssb.toString()));
            } else {
                ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), wordto, wordto);
                switch (whichwbw) {
                    case "en":
                        sb.append(list.get(0).getEn()).append(".......");
                        break;
                    case "ur":
                        sb.append(list.get(0).getUr()).append(".......");

                        break;
                    case "bn":
                        sb.append(list.get(0).getBn()).append(".......");
                        break;
                    case "id":
                        sb.append(list.get(0).getId()).append(".......");
                        break;
                }
                mudhafarray.add(SpannableStringBuilder.valueOf(sb));

            }
        }

    }

    private void newSetShart(List<SpannableStringBuilder> shartarray) {
        String quranverses = corpusSurahWord.get(0).getQurantext();
        ArrayList<NewShartEntity> shart = utils.getShartSurahAyahNew(chapterid, ayanumber);
        // String quranverses = corpusSurahWord.get(0).getQurantext();
        StringBuilder sb;
        StringBuilder sbjawab = new StringBuilder();
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        for (NewShartEntity shartEntity : shart) {
            String harfofverse;
            String shartofverse;
            String jawabofverrse;
            sb = new StringBuilder();
            int indexstart = shartEntity.getIndexstart();
            int indexend = shartEntity.getIndexend();
            int shartindexstart = shartEntity.getShartindexstart();
            int shartindexend = shartEntity.getShartindexend();
            int jawabstart = shartEntity.getJawabshartindexstart();
            int jawabend = shartEntity.getJawabshartindexend();
            int harfword = shartEntity.getHarfwordno();
            int shartSword = shartEntity.getShartstatwordno();
            int shartEword = shartEntity.getShartendwordno();
            int jawbSword = shartEntity.getJawabstartwordno();
            int jawabEword = shartEntity.getJawabendwordno();
            harfofverse = quranverses.substring(indexstart, indexend);
            shartofverse = quranverses.substring(shartindexstart, shartindexend);
            jawabofverrse = quranverses.substring(jawabstart, jawabend);
            boolean isharfb = indexstart >= 0 && indexend > 0;
            boolean isshart = shartindexstart >= 0 && shartindexend > 0;
            boolean isjawab = jawabstart >= 0 && jawabend > 0;
            boolean a = isharfb && isshart && isjawab;
            boolean b = isharfb && isshart;
            SpannableStringBuilder harfspannble;
            SpannableStringBuilder shartspoannable;
            SpannableStringBuilder jawabshartspannable;

            if (dark) {
                harfshartspanDark = new ForegroundColorSpan(GOLD);
                shartspanDark = new ForegroundColorSpan(ORANGE400);
                jawabshartspanDark = new ForegroundColorSpan(CYAN);
            } else {
                harfshartspanDark = new ForegroundColorSpan(FORESTGREEN);
                shartspanDark = new ForegroundColorSpan(KASHMIRIGREEN);

                jawabshartspanDark = new ForegroundColorSpan(WHOTPINK);
            }



            if (a) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                shartspoannable = new SpannableStringBuilder(shartofverse);
                jawabshartspannable = new SpannableStringBuilder(jawabofverrse);
                if (dark) {
                //    harfshartspanDark = new ForegroundColorSpan(MIDNIGHTBLUE);
                     shartspanDark = new ForegroundColorSpan(ORANGE400);
                    jawabshartspanDark = new ForegroundColorSpan(CYAN);
                } else {
                 //   harfshartspanDark = new ForegroundColorSpan(FORESTGREEN);
                    shartspanDark = new ForegroundColorSpan(GREENDARK);
                    jawabshartspanDark = new ForegroundColorSpan(WHOTPINK);

                }
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                shartspoannable.setSpan(shartspanDark, 0, shartofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                jawabshartspannable.setSpan(jawabshartspanDark, 0, jawabofverrse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble, " ", shartspoannable, " ", jawabshartspannable);
                shartarray.add(SpannableStringBuilder.valueOf(charSequence));
                int connected = jawabstart - shartindexend;
                if (connected == 1) {
                    ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(),
                            corpusSurahWord.get(0).getAyah(), harfword, jawabEword);
                    for (wbwentity w : list) {
                        StringBuilder temp ;
                        temp = getSelectedTranslation(w);
                        sb.append(temp).append(" ");
                    }
                //    trstr1 = getFragmentTranslations(quranverses, sb, charSequence, false);
                    shartarray.add(SpannableStringBuilder.valueOf(sb.toString()));
                } else {
                    ArrayList<wbwentity> wbwayah = utils.getwbwQuranBySurahAyah(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah());
                    for (wbwentity w : wbwayah) {
                        StringBuilder temp ;
                        temp = getSelectedTranslation(w);
                        if (w.getWordno() == harfword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= shartSword && w.getWordno() <= shartEword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= jawbSword && w.getWordno() <= jawabEword) {
                            //     sb. append("... ");
                            sbjawab.append(temp).append(" ");

                        }

                    }
                    sb.append(".....");
                    sb.append(sbjawab);
                    shartarray.add(SpannableStringBuilder.valueOf(sb.toString()));

                }
            } else if (b) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                shartspoannable = new SpannableStringBuilder(shartofverse);
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                shartspoannable.setSpan(shartspanDark, 0, shartofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble, " ", shartspoannable);
                shartarray.add(SpannableStringBuilder.valueOf(charSequence));
                //    shartarray.add(trstr);
                if ((shartindexstart - indexend) == 1) {
                    ArrayList<wbwentity> harfnshart = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), harfword, shartEword);
                    for (wbwentity w : harfnshart) {
                        new StringBuilder();
                        StringBuilder temp;
                        temp = getSelectedTranslation(w);
                        getSelectedTranslation(w);
                        sb.append(temp).append(" ");

                    }

                } else {
                    ArrayList<wbwentity> wbwayah = utils.getwbwQuranBySurahAyah(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah());
                    for (wbwentity w : wbwayah) {
                        StringBuilder temp ;
                        temp = getSelectedTranslation(w);
                        if (w.getWordno() == harfword) {
                            sb.append(temp).append(" ");
                        } else if (w.getWordno() >= shartSword && w.getWordno() <= shartEword) {
                            sb.append(temp).append(" ");
                        }

                    }
                    sb.append(".....");
                    sb.append(sbjawab);
                    //   shartarray.add(SpannableStringBuilder.valueOf(sb.toString()));


                }
                shartarray.add(SpannableStringBuilder.valueOf(sb.toString()));

            } else if (isharfb) {
                harfspannble = new SpannableStringBuilder(harfofverse);
                harfspannble.setSpan(harfshartspanDark, 0, harfofverse.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
                CharSequence charSequence = TextUtils.concat(harfspannble);
                SpannableStringBuilder trstr = getFragmentTranslations(quranverses, charSequence);
                shartarray.add(SpannableStringBuilder.valueOf(charSequence));
                shartarray.add(trstr);
            }

        }
    }

    @NonNull
    private SpannableStringBuilder getFragmentTranslations(String quranverses, CharSequence charSequence) {
        //get the string firs wordno and last wordno
        StringBuilder sb = new StringBuilder();
        int firstwordindex = 0;
        int lastwordindex = 0;
        SplitQuranVerses split = new SplitQuranVerses();
        ArrayList<Word> words = split.splitSingleVerse(quranverses);
        String trim = charSequence.toString().trim();
        String[] strings = trim.split("\\s");
        int length = strings.length;
        String firstword = strings[0];
        String lastword = strings[length - 1];
        for (Word w : words) {
            String wordsAr = w.getWordsAr();
            if (w.getWordsAr().equals(firstword)) {
                firstwordindex = w.getWordno();

            }
            if (wordsAr.equals(lastword)) {
                lastwordindex = w.getWordno();
                break;
            }

        }
//if the agove is false incase of the punctutaion marks,strip the punctuation and reloop
        if (lastwordindex == 0) {
            for (Word ww : words) {
                String wwordsAr = ww.getWordsAr();
                int sala = wwordsAr.indexOf(SALA);
                int qala = wwordsAr.indexOf(QALA);
                int smalllam = wwordsAr.indexOf(SMALLLAM);
                int smalljeem = wwordsAr.indexOf(SMALLJEEM);//small high geem
                boolean b = sala != -1 || qala != -1 || smalljeem != -1 || smalllam != -1;
                if (b) {
                    wwordsAr = wwordsAr.substring(0, wwordsAr.length() - 1);
                }
                if (wwordsAr.equals(firstword)) {
                    firstwordindex = ww.getWordno();
                }
                if (wwordsAr.equals(lastword)) {
                    lastwordindex = ww.getWordno();
                    break;
                }

            }
        }
        ArrayList<wbwentity> list = utils.getwbwQuranbTranslation(corpusSurahWord.get(0).getSurah(), corpusSurahWord.get(0).getAyah(), firstwordindex, lastwordindex);
        for (wbwentity tr : list) {
            getSelectedTranslation(tr);
            sb.append(tr.getEn()).append(" ");

        }
        return new SpannableStringBuilder(sb);
    }

    public List<SpannableStringBuilder> getKana() {
        List<SpannableStringBuilder> kanaarray = new ArrayList<>();
        newsetKana(kanaarray);
        return kanaarray;
    }
}
