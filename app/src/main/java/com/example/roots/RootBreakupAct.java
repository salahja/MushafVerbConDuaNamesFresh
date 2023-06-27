package com.example.roots;

import static com.example.Constant.ARABICWORD;
import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.WORDDETAILS;
import static com.example.Constant.WORDMEANING;
import static com.example.Constant.WORDNUMBER;
import static com.example.Constant.particlespanDark;

import static org.sj.conjugator.utilities.ArabicLiterals.AlifMaksuraString;
import static org.sj.conjugator.utilities.ArabicLiterals.Hamza;
import static org.sj.conjugator.utilities.ArabicLiterals.LALIF;
import static org.sj.conjugator.utilities.ArabicLiterals.Ya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.Constant;
import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.Activity.LughatWordDetailsAct;
import com.example.mushafconsolidated.Activity.TopicDetailAct;
import com.example.mushafconsolidated.Adapters.NounVerbOccuranceListAdapter;
import com.example.mushafconsolidated.Entities.CorpusNounWbwOccurance;
import com.example.mushafconsolidated.Entities.CorpusVerbWbwOccurance;
import com.example.mushafconsolidated.Entities.NounCorpusBreakup;
import com.example.mushafconsolidated.Entities.RootVerbDetails;
import com.example.mushafconsolidated.Entities.RootWordDetails;
import com.example.mushafconsolidated.Entities.VerbCorpusBreakup;
import com.example.mushafconsolidated.Entities.hanslexicon;
import com.example.mushafconsolidated.Entities.lanelexicon;
import com.example.mushafconsolidated.Entities.lughat;
import com.example.mushafconsolidated.Entities.qurandictionary;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.fragments.QuranMorphologyDetails;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.chip.Chip;

import androidx.appcompat.app.AlertDialog;

import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mushafconsolidated.databinding.ActivityRootBreakupBinding;

import com.example.mushafconsolidated.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RootBreakupAct extends BaseActivity implements OnItemClickListener,View.OnClickListener {
    private SharedPreferences shared;
    int firstcolortat, maincolortag, pronouncolortag, fourcolortag;
    private AppBarConfiguration appBarConfiguration;
    private ActivityRootBreakupBinding binding;
     private String root,wordorverb;
    private ArrayList<VerbCorpusBreakup> verbCorpusArrayList;
    private ArrayList<CorpusNounWbwOccurance> occurances;
    private ArrayList<NounCorpusBreakup> nounCorpusArrayList;
    public ArrayList<CorpusNounWbwOccurance> corpusNounWbwOccurances;
    ExpandableListView expandableListView;
    ViewPager2 viewPager2;
    boolean harf;
    List<String> expandNounTitles;
    List<String> expandVerbTitles;
    int mPageNo = 0;
    ImageView imgPg;
    TextView link;

    int counter = 0;
    AlertDialog dialog;
    LinkedHashMap<String, List<SpannableString>> updatechild = new LinkedHashMap<>();
    LinkedHashMap<String, List<SpannableString>> expandNounVerses = new LinkedHashMap<>();
    LinkedHashMap<String, List<SpannableString>> expandVerbVerses = new LinkedHashMap<>();
    private Utils utils;
    Chip lanes,hanes;
    private ArrayList<RootWordDetails> rootdetails;
    private ArrayList<RootVerbDetails> verbdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRootBreakupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent bundle = getIntent();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        String preferences = prefs.getString("theme", "dark");
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        lanes= findViewById(R.id.lanelexicon);
        TextView rootoccurance = binding.rootoccurance;
        rootoccurance.setText(root);
        lanes.setOnClickListener(this);
        lanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                //   Intent intent = new Intent(getActivity(), NounOccuranceAsynKAct.class);
                Intent intent = new Intent(RootBreakupAct.this, LughatWordDetailsAct.class);
                //   getTypedValues();
                bundle.putString(QURAN_VERB_ROOT, root);
                bundle.putBoolean("dictionary",true);
                intent.putExtras(bundle);
                //   intent.putExtra(QURAN_VERB_ROOT,vb.getRoot());
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog);
        dialog = builder.create();
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
        TextView lanelexicon = binding.lanelexicon;
        RecyclerView recyclerView = binding.rootbreakup;
        if (!(bundle.getExtras() == null)) {
            Bundle bundles = getIntent().getExtras();
            //   if (bundle != null) {
             root = bundles.getString(QURAN_VERB_ROOT);
             wordorverb=bundles.getString(WORDDETAILS);
        }

       utils=new Utils(this);
        verbdetails=    utils.getRootVerbDetails(root);
       rootdetails=                                 utils.getRootDetails(root);
        ArrayList<lughat> rootDictionary = utils.getRootDictionary(root);
        ArrayList<lanelexicon>  lanes= utils.getLanesDifinition(root);
        ArrayList<hanslexicon> hans=   utils.getHansDifinition(root);
        StringBuilder sb=new StringBuilder();
        sb.append(root).append(" ").append("Ocurrance").append(" ").append(rootdetails.size());

        rootoccurance.setText(sb.toString());
        ArrayList<qurandictionary> allroots=   utils.getQuranDictionarybyroot(root);
        if (root.equals("ACC") || root.equals("LOC") || root.equals("T")) {
            occurances = utils.getnounoccuranceHarfNasbZarf(root);
            harf = true;
            ExecuteNounOcurrance();

        } else {
            harf = false;
            ExecuteNounOcurrance();

        }
        lanelexicon.setText("Lanes");
        MyRootBreakRecyclerViewAdapter adapter=null;
        VerbDetailsRecAdapter verbDetailsRecAdapter=null;
        if(wordorverb.equals("word")) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


         adapter = new MyRootBreakRecyclerViewAdapter(rootdetails);
            recyclerView.setAdapter(adapter);
            adapter.SetOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    RootWordDetails wordDetails = rootdetails.get(position);
                    HashMap<String, String> datas = new HashMap<>();

                    Bundle newbundle=new Bundle();
                    newbundle.putInt(SURAH_ID,wordDetails.getSurah());
                    newbundle.putInt(AYAH_ID,wordDetails.getAyah());
                    newbundle.putString(SURAH_ARABIC_NAME,wordDetails.getNamearabic());
                    newbundle.putString(ARABICWORD,wordDetails.getArabic());
                    newbundle.putString(WORDMEANING,wordDetails.getEn());


                    newbundle.putSerializable("map", datas);
                    Intent intents = new Intent(RootBreakupAct.this, TopicDetailAct.class);
                    intents.putExtras(newbundle);
                    startActivity(intents);

                    //   Fragment ayahfragment=new Fragment();
                    //   ayahfragment.setArguments(newbundle);
                    //  SelectedWordAyah item = new SelectedWordAyah();
                    //  item.setArguments(newbundle);
                    //  String[] data = {String.valueOf(wordDetails.getSurah()), String.valueOf(wordDetails.getAyah()), wordDetails.getNamearabic(), String.valueOf((wordDetails.getArabic())), wordDetails.getEn()};
                    //   SelectedWordAyah.newInstance(data).show(getSupportFragmentManager(), SelectedWordAyah.TAG);

                    //   Toast.makeText(RootBreakupAct.this, "chipclecked", Toast.LENGTH_LONG).show();
                }
            });
        } else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            recyclerView.setAdapter(adapter);

            verbDetailsRecAdapter=new VerbDetailsRecAdapter(utils.getRootVerbDetails(root));
            recyclerView.setAdapter(verbDetailsRecAdapter);

        }
    //    rootDictionary.get(0).getHansweir();





    }

    void ExecuteNounOcurrance() {
        shared =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
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
                String Lemma = "";
                int incexofgroup = 0;
                List alist = new ArrayList();
                if (harf) {
                    for (CorpusNounWbwOccurance vers : occurances) {
                        //    alist.add("");
                        StringBuilder sb = new StringBuilder();
                        SpannableString spanDark = new SpannableString(vers.getQurantext());
                        Spannable spannableVerses = CorpusUtilityorig.getSpannableVerses(vers.getAraone() + vers.getAratwo() + vers.getArathree() + vers.getArafour() + vers.getArafive(),
                                vers.getQurantext());
                        sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-").append(vers.getEn()).append("-");
                        SpannableString ref = new SpannableString(sb.toString());
                        ref.setSpan(particlespanDark, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        String which = shared.getString("selecttranslation", "en_sahih");
                        SpannableString trans = null;
                        if (which.equals("en_sahih")) {
                            trans = SpannableString.valueOf(vers.getTranslation());
                        } else if (which.equals("ur_jalalayn")) {
                            trans = SpannableString.valueOf(vers.getUr_jalalayn());
                        } else if (which.equals("en_jalalayn")) {
                            trans = SpannableString.valueOf(vers.getEn_jalalayn());
                        }
                        CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                        alist.add(charSequence);
                        alist.add(trans);
                        expandNounVerses.put(sb.toString(), alist);

                    }

                }
                for (NounCorpusBreakup noun : nounCorpusArrayList) {
                    List list = new ArrayList();
                    list.add("");
                    Lemma = noun.getLemma_a();
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
                    List list = new ArrayList();
                    list.add("");
                    Lemma = verbCorpusBreakup.getLemma_a();
                    if (verbCorpusBreakup.getForm().equals("I")) {
                        StringBuilder sb = new StringBuilder();
                        String mujarrad = String.valueOf(QuranMorphologyDetails.getThulathiName(verbCorpusBreakup.getThulathibab()));
                        sb.append(verbCorpusBreakup.getCount()).append("-").append("times").append(" ").append(verbCorpusBreakup.getLemma_a()).append(" ").append("occurs as the").append(" ").append(mujarrad);
                        expandVerbVerses.put(sb.toString(), list);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        String s = QuranMorphologyDetails.expandTags(verbCorpusBreakup.getTense());
                        String s1 = QuranMorphologyDetails.expandTags(verbCorpusBreakup.getVoice());
                        //  String s1 = QuranMorphologyDetails.expandTags(noun.getProptwo());
                        //   String s2 = QuranMorphologyDetails.expandTags(noun.get);
                        String mazeed = String.valueOf(QuranMorphologyDetails.getFormName(verbCorpusBreakup.getForm()));
                        sb.append(verbCorpusBreakup.getCount()).append("-").append("times").append(" ").append(verbCorpusBreakup.getLemma_a()).append(" ").append("occurs as the").append(" ").append(mazeed)
                                .append(" ").append(s).append(" ").append(" ").append(s1);
                        expandVerbVerses.put(sb.toString(), list);

                    }

                }
                expandNounTitles = new ArrayList<String>(expandNounVerses.keySet());
                expandVerbTitles = new ArrayList<String>(expandVerbVerses.keySet());
                expandNounVerses.putAll(expandVerbVerses);
                expandNounTitles.addAll(expandVerbTitles);
                //post
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        // Intent intent = new Intent();
                        // intent.putExtra("result", 1);
                        //  setResult(RESULT_OK, intent);
                        NounVerbOccuranceListAdapter listAdapter;
                        listAdapter = new NounVerbOccuranceListAdapter(RootBreakupAct.this, expandNounTitles, expandNounVerses, expandVerbVerses, expandVerbTitles);
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
                                                List list = new ArrayList();
                                                //   ArrayList<CorpusNounWbwOccurance> verses = utils.getNounOccuranceBreakVerses(split[1]);
                                                ArrayList<hanslexicon> lanesDifinition = utils.getHansDifinition(verbroot);
                                                //    ArrayList<SpannableString> lanesdifinition;
                                                //   StringBuilder lanessb = new StringBuilder();
                                                for (hanslexicon hans : lanesDifinition) {
                                                    //  <p style="margin-left:200px; margin-right:50px;">
                                                    //    list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
                                                    //  list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
                                                    list.add(hans.getDefinition());
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
                                                List lists = new ArrayList();
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
                                                        sb.setSpan(particlespanDark, indexof, m.group(0).length() + indexof, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        lists.add(sb);
                                                        //   System.out.println("Found value: " + m.group(2) );
                                                    } else {
                                                        lists.add(replaceAll);
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
                                                List list = new ArrayList();
                                                ArrayList<lanelexicon> lanesDifinition = utils.getLanesDifinition(root);
                                                for (lanelexicon lanes : lanesDifinition) {
                                                    //  <p style="margin-left:200px; margin-right:50px;">
                                                    //    list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
                                                    //  list.add("<p style=\"margin-left:200px; margin-right:50px;\">");
                                                    list.add(lanes.getDefinition());
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
                                                List lists = new ArrayList();
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
                                                        sb.setSpan(particlespanDark, indexof, m.group(0).length() + indexof, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                        lists.add(sb);
                                                        //   System.out.println("Found value: " + m.group(2) );
                                                    } else {
                                                        lists.add(replaceAll);
                                                    }

                                                }
                                                return lists;
                                            }
                                        });

                                    } else if (expandNounTitles.get(groupPosition).contains("Noun") || expandNounTitles.get(groupPosition).contains("Adverb") || expandNounTitles.get(groupPosition).contains("Adjective")) {
                                        ExecutorService ex = Executors.newSingleThreadExecutor();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(RootBreakupAct.this);
                                        builder.setCancelable(false); // if you want user to wait for some process to finish,
                                        builder.setView(R.layout.layout_loading_dialog);
                                        AlertDialog dialog = builder.create();
                                        ex.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(() -> {
                                                    dialog.show();
                                                });
                                                List list = new ArrayList();
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
                                                    if (which.equals("en_sahih")) {
                                                        trans = SpannableString.valueOf(vers.getTranslation());
                                                    } else if (which.equals("ur_jalalayn")) {
                                                        trans = SpannableString.valueOf(vers.getUr_jalalayn());
                                                    } else if (which.equals("en_jalalayn")) {
                                                        trans = SpannableString.valueOf(vers.getEn_jalalayn());
                                                    } else if (which.equals("en_arberry")) {
                                                        trans = SpannableString.valueOf(vers.getEn_arberry());
                                                    }
                                                    CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                                                    list.add(charSequence);
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
                                                List list = new ArrayList();
                                                ArrayList<CorpusVerbWbwOccurance> verses = utils.getVerbOccuranceBreakVerses((split[1]));
                                                for (CorpusVerbWbwOccurance vers : verses) {
                                                    StringBuilder sb = new StringBuilder();
                                                    SpannableString spanDark = new SpannableString(vers.getQurantext());
                                                    Spannable spannableVerses = CorpusUtilityorig.getSpannableVerses(vers.getAraone() + vers.getAratwo() + vers.getArathree() + vers.getArafour() + vers.getArafive(),
                                                            vers.getQurantext());
                                                    //  SpannableString spannableString = CorpusUtilityorig.SetWordSpanNew(vers.getTagone(), vers.getTagtwo(), vers.getTagthree(), vers.getTagfour(), vers.getTagfive(),
                                                    //     vers.getAraone(), vers.getAratwo(), vers.getArathree(), vers.getArafour(), vers.getArafive());
                                                    sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-").append(vers.getEn()).append("-");
                                                    //       sb.append(vers.getSurah()).append(":").append(vers.getAyah()).append(":").append(vers.getWordno()).append("-");
                                                    vers.getWordno();
                                                    SpannableString ref = new SpannableString(sb.toString());
                                                    ref.setSpan(maincolortag, 0, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                                    String which = shared.getString("selecttranslation", "en_sahih");
                                                    SpannableString trans = null;
                                                    if (which.equals("en_sahih")) {
                                                        trans = SpannableString.valueOf(vers.getTranslation());
                                                    } else if (which.equals("ur_jalalayn")) {
                                                        trans = SpannableString.valueOf(vers.getUr_jalalayn());
                                                    } else if (which.equals("en_jalalayn")) {
                                                        trans = SpannableString.valueOf(vers.getEn_jalalayn());
                                                    } else if (which.equals("en_arberry")) {
                                                        trans = SpannableString.valueOf(vers.getEn_arberry());
                                                    }
                                                    CharSequence charSequence = TextUtils.concat(ref, "\n ", spannableVerses);
                                                    list.add(charSequence);
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
                                //   final String selected = String.valueOf((SpannableString) listAdapter.getChild(
                                //      groupPosition, childPosition));
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
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                item.setArguments(dataBundle);
                                String[] data = {surah, ayah, "", wordno};
                                FragmentTransaction transactions = fragmentManager.beginTransaction().setCustomAnimations(R.anim.abc_slide_in_top, android.R.anim.fade_out);
                                //     transactions.show(item);
                                WordAnalysisBottomSheet.newInstance(data).show(getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                                //   WordAnalysisBottomSheet.newInstance(data).show(WordOccuranceAsynKAct.this).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                            }
                        });
                    }
                });

            }

        });
        //  ExpandableRecAdapter expandableRecAdapter=new ExpandableRecAdapter(WordOccuranceAsynKAct.this,expandNounVerses,expandNounTitles);
        //  recview.setAdapter(expandableRecAdapter);
    }

    @Override
    public void onItemClick(View v, int position) {
        Toast.makeText(this, "itemclicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, "itemclicked", Toast.LENGTH_SHORT).show();
    }
}