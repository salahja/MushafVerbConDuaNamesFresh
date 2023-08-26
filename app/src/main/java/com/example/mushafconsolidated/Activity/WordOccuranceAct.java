package com.example.mushafconsolidated.Activity;

import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.WORDNUMBER;
import static com.example.Constant.particlespanDark;
import static org.sj.conjugator.utilities.ArabicLiterals.AlifMaksuraString;
import static org.sj.conjugator.utilities.ArabicLiterals.Hamza;
import static org.sj.conjugator.utilities.ArabicLiterals.LALIF;
import static org.sj.conjugator.utilities.ArabicLiterals.Ya;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.Constant;
import com.example.mushafconsolidated.Adapters.NounVerbOccuranceListAdapter;
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance;
import com.example.mushafconsolidated.Entities.CorpusVerbWbwOccurance;
import com.example.mushafconsolidated.Entities.NounCorpusBreakup;
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup;
import com.example.mushafconsolidated.Entities.hanslexicon;
import com.example.mushafconsolidated.Entities.lanelexicon;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.fragments.QuranMorphologyDetails;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.QuranGrammarApplication;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class WordOccuranceAct extends BaseActivity {

    ExpandableListView expandableListView;

    boolean harf;
    List<String> expandNounTitles;
    List<String> expandVerbTitles;

    String root;
    AlertDialog dialog;

    final LinkedHashMap<String, List<SpannableString>> expandNounVerses = new LinkedHashMap<>();
    final LinkedHashMap<String, List<SpannableString>> expandVerbVerses = new LinkedHashMap<>();
    Utils utils;

    int firstcolortat, maincolortag, pronouncolortag, fourcolortag;

    private ArrayList<VerbCorpusBreakup> verbCorpusArrayList;
    private ArrayList<CorpusNounWbwOccurance> occurances;
    private ArrayList<NounCorpusBreakup> nounCorpusArrayList;

    private SharedPreferences shared;

    public WordOccuranceAct() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expand_list);
        Bundle bundle = getIntent().getExtras();
        root = bundle.getString(QURAN_VERB_ROOT);
        utils = new Utils(WordOccuranceAct.this);
        // String preferences = SharedPref.themePreferences();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
        shared =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        String whichtranslation = shared.getString("selecttranslation", "en_sahih");
        Boolean showtranslation = shared.getBoolean("prefs_show_translation", true);
        expandableListView = findViewById(R.id.expandableListView);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        String preferences = prefs.getString("theme", "dark");
        if (preferences.equals("dark")||preferences.equals("blue")||preferences.equals("green")) {
            firstcolortat = Constant.BCYAN;
            maincolortag = Constant.BYELLOW;
            pronouncolortag = Constant.BBLUE;
            fourcolortag = Constant.BWHITE;

        } else {
            firstcolortat = Constant.WBURNTUMBER;
            maincolortag = ContextCompat.getColor(QuranGrammarApplication.getContext(), R.color.prussianblue);
            pronouncolortag = Constant.WMIDNIHTBLUE;
            fourcolortag = Constant.GREENDARK;

        }
        FloatingTextButton callBackbutton = findViewById(R.id.action_button);
    String pref = "dark";
        if (pref.equals("dark")) {
            int color = getResources().getColor(R.color.color_background_overlay);
            callBackbutton.setBackgroundColor(color);
        } else {
            callBackbutton.setBackgroundColor(getResources().getColor(R.color.color_background));

        }
        callBackbutton.setOnClickListener(view -> {
            //  Intent quranintnt = new Intent(VerbOccuranceAsynKAct.this, ReadingSurahPartActivity.class);
            finish();
            //     startActivity(quranintnt);
        });
        if (root.equals("ACC") || root.equals("LOC") || root.equals("T")) {
            occurances = utils.getnounoccuranceHarfNasbZarf(root);
            harf = true;
            ExecuteNounOcurrance();

        } else {
            harf = false;
            ExecuteNounOcurrance();

        }

    }

    void ExecuteNounOcurrance() {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });
                //  occurances = utils.getNounOccuranceBreakVerses(root);
                int vroot = root.indexOf("ء");
                String nounroot = "";
                int verb=root.indexOf("ا");

                String verbroot="";
                if (vroot != -1) {
                    nounroot = root.replace("ء", "ا");

                } else {
                    nounroot = root;
                }
                if(verb!=-1){
                    verbroot=root.replace("ا","ء");
                }else{
                    verbroot=root;
                }
                nounCorpusArrayList = utils.getNounBreakup(nounroot);
                verbCorpusArrayList = utils.getVerbBreakUp(verbroot);
                ArrayList<SpannableString> alist = new ArrayList<>();
                if (harf) {
                    for (CorpusNounWbwOccurance vers : occurances) {
                        //    alist.add("");
                        StringBuilder sb = new StringBuilder();
                        Spannable spannableVerses = CorpusUtilityorig.getSpannableVerses(vers.getAraone() + vers.getAratwo() + vers.getArathree() + vers.getArafour() + vers.getArafive(),
                                vers.getQurantext());
                        sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-").append(vers.getEn()).append("-");
                        SpannableString ref = new SpannableString(sb.toString());
                        ref.setSpan(particlespanDark, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        String which = shared.getString("selecttranslation", "en_sahih");
                        SpannableString trans = null;
                        switch (which) {
                            case "en_sahih":
                                trans = SpannableString.valueOf(vers.getTranslation());
                                break;
                            case "ur_jalalayn":
                                trans = SpannableString.valueOf(vers.getUr_jalalayn());
                                break;
                            case "en_jalalayn":
                                trans = SpannableString.valueOf(vers.getEn_jalalayn());
                                break;
                        }
                        CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                        alist.add(SpannableString.valueOf(charSequence));
                        alist.add(trans);
                        expandNounVerses.put(sb.toString(), alist);

                    }

                }
                for (NounCorpusBreakup noun : nounCorpusArrayList) {
                    List<SpannableString> list =  new ArrayList<SpannableString>();
                    list.add(SpannableString.valueOf(""));
                    if (noun.getForm().equals("null")) {
                        StringBuilder sb = new StringBuilder();
                        String nounexpand = QuranMorphologyDetails.expandTags(noun.getTag());
                        String times = "";
                        if (noun.getCount() == 1) {
                            times = "Once";
                        } else {
                            int count = noun.getCount();
                            String timess = String.valueOf(count);
                            times = timess.concat("-").concat("times");
                        }
                        sb.append(times).append(" ").append(noun.getLemma_a()).append(" ").append("occurs as the").append(" ").append(nounexpand);
                        expandNounVerses.put(sb.toString(), list);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        String s = QuranMorphologyDetails.expandTags(noun.getPropone() + noun.getProptwo());
                        //  String s1 = QuranMorphologyDetails.expandTags(noun.getProptwo());
                        String s2 = QuranMorphologyDetails.expandTags(noun.getTag());
                        String times = "";
                        if (noun.getCount() == 1) {
                            times = "Once";
                        } else {
                            int count = noun.getCount();
                            String timess = String.valueOf(count);
                            times = timess.concat("-").concat("times");
                        }
                        sb.append(times).append(" ").append(noun.getLemma_a()).append(" ").append("occurs as the").append(" ").append(noun.getForm())
                                .append(" ");
                        if (!s.equals("null")) {
                            sb.append(s).append(" ").append(" ");
                        }
                        sb.append(s2);
                        expandNounVerses.put(sb.toString(), list);

                    }

                }
                for (VerbCorpusBreakup verbCorpusBreakup : verbCorpusArrayList) {
                    ArrayList<SpannableString> list = new ArrayList<>();
                    list.add(SpannableString.valueOf(""));
                    if (verbCorpusBreakup.getForm().equals("I")) {
                        StringBuilder sb = new StringBuilder();
                        String mujarrad = String.valueOf(QuranMorphologyDetails.getThulathiName(verbCorpusBreakup.getThulathibab()));
                        sb.append(verbCorpusBreakup.getCount()).append("-").append("times").append(" ").append(verbCorpusBreakup.getLemma_a()).append(" ").append("occurs as the").append(" ").append(mujarrad);
                        expandVerbVerses.put(sb.toString(), list);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        String s = QuranMorphologyDetails.expandTags(verbCorpusBreakup.getTense());
                        String s1 = QuranMorphologyDetails.expandTags(verbCorpusBreakup.getVoice());
                        String mazeed = String.valueOf(QuranMorphologyDetails.getFormName(verbCorpusBreakup.getForm()));
                        sb.append(verbCorpusBreakup.getCount()).append("-").append("times").append(" ").append(verbCorpusBreakup.getLemma_a()).append(" ").append("occurs as the").append(" ").append(mazeed)
                                .append(" ").append(s).append(" ").append(" ").append(s1);
                        expandVerbVerses.put(sb.toString(), list);

                    }

                }
                expandNounTitles = new ArrayList<>(expandNounVerses.keySet());
                expandVerbTitles = new ArrayList<>(expandVerbVerses.keySet());
                expandNounVerses.putAll(expandVerbVerses);
                expandNounTitles.addAll(expandVerbTitles);
                //post
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        NounVerbOccuranceListAdapter listAdapter;
                     //   listAdapter = new NounVerbOccuranceListAdapter(WordOccuranceAct.this, expandNounTitles, expandNounVerses);
                        listAdapter = new NounVerbOccuranceListAdapter(WordOccuranceAct.this, expandNounTitles, expandNounVerses, expandVerbVerses, expandVerbTitles);
                        expandableListView.setAdapter(listAdapter);
                        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                            @Override
                            public void onGroupExpand(int groupPosition) {
                                String[] split = expandNounTitles.get(groupPosition).split("\\s");
                                if (!harf) {
                                    if (expandNounTitles.get(groupPosition).contains("Hans")) {
                                        ExecutorService ex = Executors.newSingleThreadExecutor();
                                        ex.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(() -> {
                                                    dialog.show();
                                                });
                                                String verbroot = "";
                                                int indexOfHamza = root.indexOf(Hamza);
                                                int indexofAlifMaksura = root.indexOf(Ya);
                                                if (indexOfHamza != -1) {
                                                    verbroot = root.replaceAll(Hamza, LALIF);
                                                } else {
                                                    verbroot = root;
                                                }
                                                if (indexofAlifMaksura != -1) {
                                                    verbroot = verbroot.replaceAll(Ya, AlifMaksuraString);
                                                }
                                                List<SpannableString> list =  new ArrayList<>();
                                                //   ArrayList<CorpusNounWbwOccurance> verses = utils.getNounOccuranceBreakVerses(split[1]);
                                                ArrayList<hanslexicon> lanesDifinition = utils.getHansDifinition(verbroot);
                                                for (hanslexicon hans : lanesDifinition) {
                                                    //  <p style="margin-left:200px; margin-right:50px;">
                                                    list.add(SpannableString.valueOf(hans.getDefinition()));
//
                                                }
                                                list = highLightParadigm(list);
                                                List<SpannableString> finalList = list;
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ex.shutdown();
                                                        dialog.dismiss();
                                                        expandNounVerses.put(expandNounTitles.get(groupPosition), finalList);
                                                        listAdapter.notifyDataSetChanged();
                                                    }
                                                });

                                            }

                                            private List highLightParadigm(List list) {
                                                List<SpannableString> lists = new ArrayList<>();
                                                String REGEX = "aor.([\\s\\S]){3}";
                                                Pattern pattern = Pattern.compile(REGEX);
                                                for (Object l : list) {
                                                    String replaceAll = l.toString().replaceAll("<br></br>", "");
                                                    Matcher m = pattern.matcher(replaceAll);
                                                    SpannableString sb = null;
                                                    int indexof = 0;
                                                    if (m.find()) {
                                                        System.out.println("Found value: " + m.group(0));
                                                        System.out.println("Found value: " + m.group(1));
                                                        indexof = l.toString().indexOf(m.group(0));
                                                        sb = new SpannableString(l.toString());
                                                        sb.setSpan(particlespanDark, indexof, Objects.requireNonNull(m.group(0)).length() + indexof, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        lists.add(sb);
                                                        //   System.out.println("Found value: " + m.group(2) );
                                                    } else {
                                                        lists.add(SpannableString.valueOf(replaceAll));
                                                    }

                                                }
                                                return lists;
                                            }
                                        });

                                    } else if (expandNounTitles.get(groupPosition).contains("lanes")) {
                                        ExecutorService ex = Executors.newSingleThreadExecutor();
                                        ex.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(() -> {
                                                    dialog.show();
                                                });
                                                List<SpannableString> list = new ArrayList<>();
                                                ArrayList<lanelexicon> lanesDifinition = utils.getLanesDifinition(root);
                                                for (lanelexicon lanes : lanesDifinition) {
                                                    //  <p style="margin-left:200px; margin-right:50px;">
                                                    list.add(SpannableString.valueOf(lanes.getDefinition()));
//
                                                }
                                                list = highLightParadigm(list);
                                                List finalList = list;
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ex.shutdown();
                                                        dialog.dismiss();
                                                        expandNounVerses.put(expandNounTitles.get(groupPosition), finalList);
                                                        listAdapter.notifyDataSetChanged();
                                                    }
                                                });

                                            }

                                            private List highLightParadigm(List list) {
                                                List<SpannableString> lists = new ArrayList<>();
                                                String REGEX = "aor.([\\s\\S]){3}";
                                                Pattern pattern = Pattern.compile(REGEX);
                                                for (Object l : list) {
                                                    String replaceAll = l.toString().replaceAll("<br></br>", "");
                                                    Matcher m = pattern.matcher(replaceAll);
                                                    SpannableString sb = null;
                                                    int indexof = 0;
                                                    if (m.find()) {
                                                        System.out.println("Found value: " + m.group(0));
                                                        System.out.println("Found value: " + m.group(1));
                                                        indexof = l.toString().indexOf(Objects.requireNonNull(m.group(0)));
                                                        sb = new SpannableString(l.toString());
                                                        sb.setSpan(particlespanDark, indexof, Objects.requireNonNull(m.group(0)).length() + indexof, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        lists.add(sb);
                                                        //   System.out.println("Found value: " + m.group(2) );
                                                    } else {
                                                        lists.add(SpannableString.valueOf(replaceAll));
                                                    }

                                                }
                                                return lists;
                                            }
                                        });

                                    } else if (expandNounTitles.get(groupPosition).contains("Noun") || expandNounTitles.get(groupPosition).contains("Adverb") || expandNounTitles.get(groupPosition).contains("Adjective")) {
                                        ExecutorService ex = Executors.newSingleThreadExecutor();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(WordOccuranceAct.this);
                                        builder.setCancelable(false); // if you want user to wait for some process to finish,
                                        builder.setView(R.layout.layout_loading_dialog);
                                        AlertDialog dialog = builder.create();
                                        ex.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(dialog::show);
                                                List<SpannableString> list = new ArrayList<>();
                                                ArrayList<CorpusNounWbwOccurance> verses = utils.getNounOccuranceBreakVerses(split[1]);
                                                for (CorpusNounWbwOccurance vers : verses) {
                                                    StringBuilder sb = new StringBuilder();
                                                    SpannableString spanDark = new SpannableString(vers.getQurantext());
                                                    Spannable spannableVerses = CorpusUtilityorig.getSpannableVerses(vers.getAraone() + vers.getAratwo() + vers.getArathree() + vers.getArafour() + vers.getArafive(),
                                                            vers.getQurantext());
                                                    sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-").append(vers.getEn()).append("-");
                                                    SpannableString ref = new SpannableString(sb.toString());
                                                    ref.setSpan(particlespanDark, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    String which = shared.getString("selecttranslation", "en_sahih");
                                                    SpannableString trans = null;
                                                    switch (which) {
                                                        case "en_sahih":
                                                            trans = SpannableString.valueOf(vers.getTranslation());
                                                            break;
                                                        case "ur_jalalayn":
                                                            trans = SpannableString.valueOf(vers.getUr_jalalayn());
                                                            break;
                                                        case "en_jalalayn":
                                                            trans = SpannableString.valueOf(vers.getEn_jalalayn());
                                                            break;
                                                        case "en_arberry":
                                                            trans = SpannableString.valueOf(vers.getEn_arberry());
                                                            break;
                                                    }
                                                    CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                                                    list.add(SpannableString.valueOf(charSequence));
                                                    list.add(trans);

                                                }
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ex.shutdown();
                                                        dialog.dismiss();
                                                        expandNounVerses.put(expandNounTitles.get(groupPosition), list);
                                                        listAdapter.notifyDataSetChanged();
                                                    }
                                                });

                                            }
                                        });

                                    } else {
                                        ExecutorService ex = Executors.newSingleThreadExecutor();
                                        ex.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dialog.show();

                                                    }
                                                });
                                                List<SpannableString> list = new ArrayList<>();
                                                ArrayList<CorpusVerbWbwOccurance> verses = utils.getVerbOccuranceBreakVerses((split[1]));
                                                for (CorpusVerbWbwOccurance vers : verses) {
                                                    StringBuilder sb = new StringBuilder();
                                                    Spannable spannableVerses = CorpusUtilityorig.getSpannableVerses(vers.getAraone() + vers.getAratwo() + vers.getArathree() + vers.getArafour() + vers.getArafive(),
                                                            vers.getQurantext());
                                                    sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-").append(vers.getEn()).append("-");
                                                    //       sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-");
                                                 //   vers.getWordno();
                                                    SpannableString ref = new SpannableString(sb.toString());
                                                    ref.setSpan(maincolortag, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    String which = shared.getString("selecttranslation", "en_sahih");
                                                    SpannableString trans = null;
                                                    switch (which) {
                                                        case "en_sahih":
                                                            trans = SpannableString.valueOf(vers.getTranslation());
                                                            break;
                                                        case "ur_jalalayn":
                                                            trans = SpannableString.valueOf(vers.getUr_jalalayn());
                                                            break;
                                                        case "en_jalalayn":
                                                            trans = SpannableString.valueOf(vers.getEn_jalalayn());
                                                            break;
                                                        case "en_arberry":
                                                            trans = SpannableString.valueOf(vers.getEn_arberry());
                                                            break;
                                                    }
                                                    CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                                                    list.add(SpannableString.valueOf(charSequence));
                                                    list.add(trans);

                                                }
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        ex.shutdown();
                                                        dialog.dismiss();
                                                        expandNounVerses.put(expandNounTitles.get(groupPosition), list);
                                                        listAdapter.notifyDataSetChanged();
                                                    }
                                                });

                                            }
                                        });

                                    }
                                }

                            }
                        });
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                                CharSequence child = (CharSequence) listAdapter.getChild(groupPosition, childPosition);
                                String[] split = child.toString().split("-");
                                String[] surahaya = split[0].split(":");
                                if (surahaya.length > 1) {
                                    try {
                                        Integer.valueOf(surahaya[2]);
                                    } catch (NumberFormatException e) {
                                        return false;
                                    }
                                    try {
                                        Integer.valueOf(surahaya[1]);
                                    } catch (NumberFormatException e) {
                                        return false;
                                    }
                                    try {
                                        Integer.valueOf(surahaya[0]);
                                    } catch (NumberFormatException e) {
                                        return false;
                                    }
                                    String wordno = surahaya[2];
                                    String ayah = surahaya[1];
                                    String surah = surahaya[0];
                                    Bundle dataBundle = new Bundle();
                                    dataBundle.putInt(SURAH_ID, Integer.parseInt(surah));
                                    dataBundle.putInt(AYAHNUMBER, Integer.parseInt(ayah));
                                    dataBundle.putInt(WORDNUMBER, Integer.parseInt(wordno));
                                    dataBundle.putString(SURAH_ARABIC_NAME, "SurahName");
                                    LoadItemList(dataBundle, surah, ayah, wordno);
                                    return true;
                                } else {
                                    return false;
                                }
                            }

                            private void LoadItemList(Bundle dataBundle, String surah, String ayah, String wordno) {
                                WordAnalysisBottomSheet item = new WordAnalysisBottomSheet();
                                //    item.setdata(rootWordMeanings,wbwRootwords,grammarRootsCombined);
                                item.setArguments(dataBundle);
                                String[] data = {surah, ayah, "", wordno};
                                WordAnalysisBottomSheet.newInstance(data).show(getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                                //   WordAnalysisBottomSheet.newInstance(data).show(WordOccuranceAsynKAct.this).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                            }
                        });
                    }
                });

            }

        });
    }

}












