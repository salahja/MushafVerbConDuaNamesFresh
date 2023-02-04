package com.example.mushafconsolidated.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.Constant.AJLIHI;
import static com.example.Constant.AJLIHIHEADER;
import static com.example.Constant.AYAHNUMBER;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.BADAL;
import static com.example.Constant.BADALHEADER;
import static com.example.Constant.BIHI;
import static com.example.Constant.BIHIHEADER;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.HAL;
import static com.example.Constant.HALHEADER;
import static com.example.Constant.MUTLAQ;
import static com.example.Constant.MUTLAQHEADER;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.Constant.SURAH_ID;
import static com.example.Constant.TAMEEZ;
import static com.example.Constant.TAMEEZHEADER;
import static com.example.Constant.WORDNUMBER;
import static com.example.utility.CorpusUtilityorig.getStringForegroundColorSpanMap;
import static com.example.utility.QuranGrammarApplication.getContext;
import static com.example.utility.QuranGrammarApplication.getInstance;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.mushafconsolidated.Activity.TafsirFullscreenActivity;
import com.example.mushafconsolidated.Config;
import com.example.mushafconsolidated.Entities.BadalErabNotesEnt;
import com.example.mushafconsolidated.Entities.BookMarks;
import com.example.mushafconsolidated.Entities.HalEnt;
import com.example.mushafconsolidated.Entities.LiajlihiEnt;
import com.example.mushafconsolidated.Entities.MafoolBihi;
import com.example.mushafconsolidated.Entities.MafoolMutlaqEnt;
import com.example.mushafconsolidated.Entities.NounCorpus;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.Entities.TameezEnt;
import com.example.mushafconsolidated.Entities.VerbCorpus;
import com.example.mushafconsolidated.NamesDetail;
import com.example.mushafconsolidated.ParticleColorScheme;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.SurahSummary;
import com.example.mushafconsolidated.Utils;
import com.example.mushafconsolidated.fragments.SentenceAnalysisBottomSheet;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;
import com.example.mushafconsolidated.fragments.WordMorphologyDetails;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.utility.AnimationUtility;
import com.example.utility.CorpusUtilityorig;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tooltip.Tooltip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;
//public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListenerOnLong {
public class FlowAyahWordAdapter extends RecyclerView.Adapter<FlowAyahWordAdapter.ItemViewAdapter>
        //implements OnItemClickListenerOnLong {
{


    private boolean isFABOpen=false;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    final long surah_id;
    private final ArrayList<MafoolBihi> mafoolBihis;
    private final ArrayList<HalEnt> jumlahaliya;
    private final ArrayList<TameezEnt> tameezEnts;
    private final ArrayList<BadalErabNotesEnt> badalErabNotesEnt;
    private final ArrayList<LiajlihiEnt> liajlihient;
    private final ArrayList<MafoolMutlaqEnt> mutlaqent;
    private final boolean issentence;
    private final ArrayList<CorpusAyahWord> ayahWordArrayList;
    private final List<QuranEntity> allofQuran;
    private final ArrayList<String> header;
    private final String SurahName;
    private final int isMakkiMadani;
    public Context context;
    public TextView arabic, rootword;
    final int arabicfontSize;
    final int translationfontsize;
    final OnItemClickListenerOnLong mItemClickListener;
    private String isNightmode;
    private int headercolor;
    private WebView kathir_translation;
    private Typeface colorwordfont;
    private CorpusAyahWord ayahWord;

    public FlowAyahWordAdapter(LinkedHashMap<Integer, ArrayList<CorpusWbwWord>> passage, ArrayList<MafoolMutlaqEnt> mutlaqent, ArrayList<TameezEnt> tameezEnts, ArrayList<BadalErabNotesEnt> badalErabNotesEnt, ArrayList<LiajlihiEnt> liajlihient, ArrayList<HalEnt> jumlahaliya, ArrayList<MafoolBihi> mafoolBihis, ArrayList<String> header, List<QuranEntity> allofQuran,
                               ArrayList<CorpusAyahWord> ayahWordArrayList, Context context, long surah_id, String surahName, int ismakki, OnItemClickListenerOnLong listener) {
        this.mutlaqent = mutlaqent;
        this.tameezEnts = tameezEnts;
        this.badalErabNotesEnt = badalErabNotesEnt;
        this.liajlihient = liajlihient;
        this.jumlahaliya = jumlahaliya;
        this.mafoolBihis = mafoolBihis;
        this.header = header;
        this.allofQuran = allofQuran;
        this.ayahWordArrayList = ayahWordArrayList;
        this.context = context;
        this.surah_id = surah_id;
        this.SurahName = surahName;
        this.isMakkiMadani = ismakki;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.getBoolean(Config.SHOW_Erab, Config.defaultShowErab);
        issentence = sharedPreferences.getBoolean("grammarsentence", false);
        arabicfontSize = sharedPreferences.getInt("pref_font_arabic_key", 18);
        translationfontsize = sharedPreferences.getInt("pref_font_englsh_key", 18);
        mItemClickListener = listener;

    }

    public void addContext(Context context) {
        this.context = context;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return ayahWordArrayList.size() + 1;
        //     return  quran.size();
    }

    @Override
    public long getItemId(int position) {
        CorpusAyahWord ayahWord = ayahWordArrayList.get(position);
        long itemId = 0;
        for (CorpusWbwWord word : ayahWord.getWord()) {
            itemId = word.getVerseId();
        }
        return itemId;
    }

    @NonNull
    @Override
    public ItemViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surah_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ayah_word, parent, false);
            //   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewer_aya_cardview, parent, false);
        }
        return new ItemViewAdapter(view, viewType);

    }

    public Object getItem(int position) {
        return ayahWordArrayList.get(0).getWord().get(position);
        //    return allofQuran.get(position);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ItemViewAdapter holder, int position) {
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        isNightmode = sharedPreferences.getString("themepref", "dark");
        //  String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", String.valueOf(MODE_PRIVATE));

        String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", "quranicfontregular.ttf");
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),
                arabic_font_selection);
        final String FONTS_LOCATION_PATH = "fonts/DejaVuSans.ttf";
        colorwordfont = Typeface.createFromAsset(QuranGrammarApplication.getContext().getAssets(),FONTS_LOCATION_PATH);
        boolean showrootkey = sharedPreferences.getBoolean("showrootkey", true);
        boolean showErab = sharedPreferences.getBoolean("showErabKey", true);
        boolean showWordColor = sharedPreferences.getBoolean("colortag", true);
        boolean showTransliteration = sharedPreferences.getBoolean("showtransliterationKey", true);
        boolean showJalalayn = sharedPreferences.getBoolean("showEnglishJalalayn", true);
        boolean showTranslation = sharedPreferences.getBoolean("showTranslationKey", true);
        boolean showWordByword = sharedPreferences.getBoolean("wordByWord", false);
        boolean showKathir = sharedPreferences.getBoolean("showKathir", false);
//bg_black
        if (position % 2 == 1) {
            switch (isNightmode) {

                case "brown":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_brown));
                    break;
                case "dark":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.odd_item_bg_black));
                    break;
                case "blue":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_dark_blue));
                    break;
            }
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            switch (isNightmode) {

                case "brown":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.odd_item_bg_brown));
                    break;
                case "dark":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_black));
                    break;
                case "blue":
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_dark_blue));
                    break;

            }
        }
        String whichtranslation = sharedPreferences.getString("selecttranslation", "en_sahih");
        if (getItemViewType(position) == 0) {
            TypedArray imgs = this.context.getResources().obtainTypedArray(R.array.sura_imgs);

            // You have to set your header items values with the help of model class and you can modify as per your needs
            holder.tvRukus.setText(String.format("Ruku's :%s", header.get(0)));
            holder.tvVerses.setText(String.format("Aya's :%s", header.get(1)));
            holder.tvSura.setText(header.get(3));
            String chapterno = header.get(2);
            int tauba = Integer.parseInt(chapterno);
            if (tauba == 9) {
                holder.ivBismillah.setVisibility(View.GONE);
            }
            if (isMakkiMadani == 1) {
                holder.ivLocationmakki.setVisibility(View.VISIBLE);
                holder.ivLocationmadani.setVisibility(View.GONE);
            } else {
                holder.ivLocationmadani.setVisibility(View.VISIBLE);
                holder.ivLocationmakki.setVisibility(View.GONE);
            }
            final Drawable drawable = imgs.getDrawable(Integer.parseInt(chapterno) - 1);
            imgs.recycle();
            holder.ivSurahIcon.setImageDrawable(drawable);
            if (isNightmode.equals("dark") || isNightmode.equals("blue") || isNightmode.equals("purple")||isNightmode.equals("green")) {
                headercolor = Color.YELLOW;
                holder.ivLocationmakki.setColorFilter(Color.CYAN);
                holder.ivSurahIcon.setColorFilter(Color.CYAN);
                holder.ivLocationmadani.setColorFilter(Color.CYAN);

            } else {
                headercolor = Color.BLUE;
                holder.ivLocationmakki.setColorFilter(Color.BLACK);
                holder.ivSurahIcon.setColorFilter(Color.BLACK);
                holder.ivLocationmadani.setColorFilter(Color.BLACK);
            }

        } else {
            displayAyats(showrootkey, holder, position - 1, sharedPreferences, custom_font, showErab, showWordColor, showTransliteration, showJalalayn, showTranslation, showWordByword, whichtranslation, showKathir);

        }

    }

    private void displayAyats(boolean showrootkey, FlowAyahWordAdapter.ItemViewAdapter holder, int position, SharedPreferences sharedPreferences, Typeface custom_font, boolean showErab, boolean showWordColor, boolean showTransliteration, boolean showJalalayn, boolean showTranslation, boolean showWordByword, String whichtranslation, boolean showKathir) {
        //   holder.flowwbw.setBackgroundColor(R.style.Theme_DarkBlue);
        QuranEntity entity = null;
        String wbw = sharedPreferences.getString("wbw", "en");

        //   String wbw = sharedPreferences.getString("wordByWord", String.valueOf(Context.MODE_PRIVATE));
        try {
            entity = allofQuran.get(position);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("check");
        }
        MafoolBihi mafoolBihi = null;
        try {
            mafoolBihi = mafoolBihis.get(position);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        ayahWord = ayahWordArrayList.get(position);
        if (null != entity) {
            storepreferences(entity);
        }
        SpannableString quranverses = ayahWordArrayList.get(position).getSpannableverse();
        holder.quran_textView.setText(quranverses);
        holder.quran_textView.setTextSize(arabicfontSize);
        holder.quran_textView.setTypeface(custom_font);
        holder.base_cardview.setVisibility(View.GONE);
        SpannableStringBuilder mf = new SpannableStringBuilder();
        StringBuilder halsb = new StringBuilder();
        StringBuilder tameezsb = new StringBuilder();
        StringBuilder badalsb = new StringBuilder();
        StringBuilder ajlihisb = new StringBuilder();
        StringBuilder mutlaqsb = new StringBuilder();
        holder.mafoolbihi.setVisibility(View.GONE);
        holder.base_cardview.setVisibility(View.GONE);
        Map<String, ForegroundColorSpan> spanhash = getStringForegroundColorSpanMap();
        CharSequence mfcharSequence;
        if (mafoolBihi != null) {
            assert entity != null;
            if (mafoolBihi.getAyah() == entity.getAyah()) {
                setUpMafoolbihistring(mf);
            }
        }
        //   finalstring.setSpan(FORESTGREEN,indexOfbihi, HAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        for (MafoolBihi ent : mafoolBihis) {
            assert entity != null;
            if (ent.getAyah() == entity.getAyah()) {
                boolean b = ent.getObjectpronoun() == null;
                if (!b) {
                    SpannableStringBuilder mafoolbihiverb = new SpannableStringBuilder();
                    SpannableStringBuilder objectpronoun = new SpannableStringBuilder();
                    mafoolbihiverb.append(ent.getWord()).append(" ");
                    objectpronoun = SpannableStringBuilder.valueOf(ent.getObjectpronoun());
                    //   objectpronoun.append("(").append("مفعول به").append(")");
                    ForegroundColorSpan colorSpan = spanhash.get("V");
                    ForegroundColorSpan proncolospan = spanhash.get("PRON");
                    assert colorSpan != null;
                    mafoolbihiverb.setSpan(new ForegroundColorSpan(colorSpan.getForegroundColor()), 0, mafoolbihiverb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    assert proncolospan != null;
                    objectpronoun.setSpan(new ForegroundColorSpan(proncolospan.getForegroundColor()), 0, objectpronoun.length(),
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mfcharSequence = TextUtils.concat(mafoolbihiverb, " ", objectpronoun);
                    mf.append(mfcharSequence).append("\n");

                } else {
                    SpannableStringBuilder mafoolbihiverb = new SpannableStringBuilder();
                    mafoolbihiverb.append(ent.getWord()).append(" ");
                    //      mafoolbihiverb.append("(").append("مفعول به").append(")");
                    mafoolbihiverb.setSpan(spanhash.get("N"), 0, mafoolbihiverb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mfcharSequence = TextUtils.concat(mafoolbihiverb);
                    mf.append(mfcharSequence).append("\n");
                }
                //    mf.append(ent.getWord().trim()).append("\n");
            }

        }
        //   if(!jumlahaliya.isEmpty()) {
        for (HalEnt ent : jumlahaliya) {
            if (entity != null && ent.getAyah() == entity.getAyah()) {
                halsb.append(ent.getText().trim()).append("\n");

            }

        }
        for (TameezEnt ent : tameezEnts) {
            if (entity != null && ent.getAyah() == entity.getAyah()) {
                tameezsb.append(ent.getWord().trim()).append("\n");

            }
        }
        for (LiajlihiEnt ent : liajlihient) {
            if (entity != null && ent.getAyah() == entity.getAyah()) {
                ajlihisb.append(ent.getWord().trim()).append("\n");

            }
        }
        for (BadalErabNotesEnt ent : badalErabNotesEnt) {
            if (entity != null && ent.getAyah() == entity.getAyah()) {
                badalsb.append(ent.getText().trim()).append("\n");

            }
        }
        for (MafoolMutlaqEnt ent : mutlaqent) {
            if (entity != null && ent.getAyah() == entity.getAyah()) {
                mutlaqsb.append(ent.getWord().trim()).append("\n");

            }
        }
        //CharSequence charSequence = TextUtils.concat(harfspannble, " ", khabarofversespannable, " ", harfismspannable);
        SpannableStringBuilder halspan = new SpannableStringBuilder();
        SpannableStringBuilder tameezspan = new SpannableStringBuilder();
        SpannableStringBuilder badalspan = new SpannableStringBuilder();
        SpannableStringBuilder mutlaqspan = new SpannableStringBuilder();
        SpannableStringBuilder ajlihispan = new SpannableStringBuilder();
        if (!halsb.toString().isEmpty()) {
            halsb.insert(0, HALHEADER);
            halspan = SpannableStringBuilder.valueOf(SpannableString.valueOf(halsb.toString()));
            int indexOfbihi1 = halspan.toString().indexOf(HAL);
            halspan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi1, HAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            halspan.setSpan(new RelativeSizeSpan(1f), indexOfbihi1, HAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            halspan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi1, HAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        if (!tameezsb.toString().isEmpty()) {
            tameezsb.insert(0, TAMEEZHEADER);
            tameezspan = SpannableStringBuilder.valueOf(SpannableString.valueOf(tameezsb.toString()));
            int indexOfbihi2 = tameezspan.toString().indexOf(TAMEEZ);
            tameezspan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi2, TAMEEZ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tameezspan.setSpan(new RelativeSizeSpan(1f), indexOfbihi2, TAMEEZ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tameezspan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi2, TAMEEZ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        if (!ajlihisb.toString().isEmpty()) {
            ajlihisb.insert(0, AJLIHIHEADER);
            ajlihispan = SpannableStringBuilder.valueOf(SpannableString.valueOf(ajlihisb.toString()));
            int indexOfbihi3 = ajlihispan.toString().indexOf(AJLIHI);
            ajlihispan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi3, AJLIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ajlihispan.setSpan(new RelativeSizeSpan(1f), indexOfbihi3, AJLIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ajlihispan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi3, AJLIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!badalsb.toString().isEmpty()) {
            badalsb.insert(0, BADALHEADER);
            badalspan = SpannableStringBuilder.valueOf(SpannableString.valueOf(badalsb.toString()));
            int indexOfbihi4 = badalspan.toString().indexOf(BADAL);
            badalspan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi4, BADAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            badalspan.setSpan(new RelativeSizeSpan(1f), indexOfbihi4, BADAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            badalspan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi4, BADAL.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (!mutlaqsb.toString().isEmpty()) {
            mutlaqsb.insert(0, MUTLAQHEADER);
            mutlaqspan = SpannableStringBuilder.valueOf(SpannableString.valueOf(mutlaqsb.toString()));
            int indexOfbihi5 = mutlaqspan.toString().indexOf(MUTLAQ);
            mutlaqspan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi5, MUTLAQ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mutlaqspan.setSpan(new RelativeSizeSpan(1f), indexOfbihi5, MUTLAQ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mutlaqspan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi5, MUTLAQ.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        CharSequence charSequence = TextUtils.concat(mf, " ", halspan, " ", tameezspan, " "
                , ajlihispan, " ", badalspan, " ", mutlaqspan);
        if (charSequence.toString().length() > 15) {
            holder.mafoolbihi.setText(charSequence);
            holder.mafoolbihi.setGravity(Gravity.CENTER);
            holder.base_cardview.setVisibility(View.VISIBLE);
            holder.mafoolbihi.setTextSize(arabicfontSize - 5);
            holder.mafoolbihi.setTypeface(custom_font);

        } else {
            holder.erabnotescardView.setVisibility(View.GONE);
            holder.mafoolbihi.setVisibility(View.GONE);
        }
        setChapterInfo(holder, ayahWord);
        //  setAdapterposition(position);
        wordBywordWithTranslation(showrootkey, holder, custom_font, showWordColor, wbw, ayahWord, showWordByword);

        if (showTransliteration) {
            if (entity != null) {
                holder.quran_transliteration.setText(Html.fromHtml(entity.getEn_transliteration(), Html.FROM_HTML_MODE_LEGACY));
            }
            holder.quran_transliteration.setTextSize(translationfontsize);
            holder.quran_transliteration.setTextSize(translationfontsize);
            holder.quran_transliteration.setVisibility(View.VISIBLE);

        }
        if (showJalalayn) {
            //   holder.quran_jalalaynnote.setText(enjalalayn.getAuthor_name());
            if (entity != null) {
                holder.quran_jalalayn.setText(entity.getEn_jalalayn());
            }
            holder.quran_jalalayn.setTextSize(translationfontsize);
            holder.quran_jalalayn.setTextSize(translationfontsize);
            holder.quran_jalalayn.setVisibility(View.VISIBLE);
            holder.quran_jalalaynnote.setVisibility(View.VISIBLE);
        }
        if (showTranslation) {
            if (whichtranslation.equals("en_arberry")) {
                if (entity != null) {
                    holder.translate_textView.setText(entity.getEn_arberry());
                }
                holder.translate_textViewnote.setText(R.string.arberry);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setVisibility(View.VISIBLE);
                holder.translate_textViewnote.setVisibility(View.VISIBLE);
            }
            if (whichtranslation.equals("en_sahih")) {
                if (entity != null) {
                    holder.translate_textView.setText(entity.getTranslation());
                }
                holder.translate_textViewnote.setText(R.string.ensahih);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setVisibility(View.VISIBLE);
                holder.translate_textViewnote.setVisibility(View.VISIBLE);
            }
            if (whichtranslation.equals("en_jalalayn")) {
                if (entity != null) {
                    holder.translate_textView.setText(entity.getEn_jalalayn());
                }
                holder.translate_textViewnote.setText(R.string.enjalalayn);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setVisibility(View.VISIBLE);
                holder.translate_textViewnote.setVisibility(View.VISIBLE);
            }
            if (whichtranslation.equals("ur_jalalayn")) {
                if (entity != null) {
                    holder.translate_textView.setText(entity.getUr_jalalayn());
                }
                holder.translate_textViewnote.setText(R.string.enjalalayn);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setVisibility(View.VISIBLE);
                holder.translate_textViewnote.setVisibility(View.VISIBLE);
            }
            if (whichtranslation.equals("ur_junagarhi")) {
                if (entity != null) {
                    holder.translate_textView.setText(entity.getUr_junagarhi());
                }
                holder.translate_textViewnote.setText(R.string.urjunagadi);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setTextSize(translationfontsize);
                holder.translate_textView.setVisibility(View.VISIBLE);
                holder.translate_textViewnote.setVisibility(View.VISIBLE);
            }
            holder.translate_textView.setTextSize(translationfontsize);
            holder.translate_textView.setTextSize(translationfontsize);
            holder.translate_textView.setVisibility(View.VISIBLE);
            holder.translate_textViewnote.setVisibility(View.VISIBLE);

        }
        if (showErab) {
            holder.erabexpand.setVisibility(View.VISIBLE);
            if (entity != null) {
                holder.erab_textView.setText(entity.getErabspnabble());
            }
            holder.erab_textView.setTextSize(translationfontsize);
            holder.erab_textView.setTypeface(custom_font);
            //     holder.erab_textView.setVisibility(View.VISIBLE);
            holder.erab_textViewnote.setVisibility(View.VISIBLE);


        } else {
            holder.erabexpand.setVisibility(View.GONE);
        }
    }

    private void setUpMafoolbihistring(SpannableStringBuilder mf) {
        SpannableStringBuilder mfspan;
        mf.append(BIHIHEADER);
        mfspan = SpannableStringBuilder.valueOf(SpannableString.valueOf(mf.toString()));
        int indexOfbihi = mfspan.toString().indexOf(BIHI);
        mfspan.setSpan(new ForegroundColorSpan(headercolor), indexOfbihi, BIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mfspan.setSpan(new RelativeSizeSpan(1f), indexOfbihi, BIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mfspan.setSpan(Typeface.DEFAULT_BOLD, indexOfbihi, BIHI.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mfspan.append("\n");
    }

    private void wordBywordWithTranslation(boolean showrootkey, ItemViewAdapter holder, Typeface custom_font, boolean showWordColor, String wbw, CorpusAyahWord ayahWord, boolean showWbwTranslation) {
        final LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        holder.flow_word_by_word.removeAllViews();
        for (final CorpusWbwWord word : ayahWord.getWord()) {
            final View view = inflater.inflate(R.layout.word_by_word, null);
            arabic = view.findViewById(R.id.word_arabic_textView);
            rootword = view.findViewById(R.id.root_word);
            //   wordno = view.findViewById(R.id.wordno);
            final TextView translation = view.findViewById(R.id.word_trans_textView);
            SpannableString spannedroot = null;
            StringBuilder sb = new StringBuilder();
            sb.append(word.getWordno());
            if (showrootkey) {
                if (!word.getRootword().isEmpty()) {
                    spannedroot = getSpannedRoots(word);
                } else {
                    spannedroot = SpannableString.valueOf(word.getRootword());
                }
                rootword.setText(spannedroot);
                rootword.setTextSize(arabicfontSize);
                rootword.setVisibility(View.VISIBLE);
            }
            if (showWordColor) {
                SpannableString spannedword;
                //        word.getRootword();
                spannedword = getSpannedWords(word);
                //   arabic.setText(fixArabic(String.valueOf(spannedword)));

                arabic.setText(spannedword);
            } else {
                arabic.setText(word.getWordsAr());
            }
            rootword.setText(spannedroot);
            rootword.setTextSize(arabicfontSize);
            //  arabic.setTextSize(18);
            arabic.setTextSize(arabicfontSize);
            arabic.setTypeface(colorwordfont);

            if (showWbwTranslation) {
                switch (wbw) {
                    case "en":
                        translation.setText(word.getTranslateEn());
                        translation.setPaintFlags(translation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case "bn":
                        translation.setText(word.getTranslateBn());
                        translation.setPaintFlags(translation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case "in":
                        translation.setText(word.getTranslateIndo());
                        translation.setPaintFlags(translation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                    case "ur":
                        translation.setText(word.getTranslationUrdu());
                        translation.setPaintFlags(translation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        break;
                }
                //  translation.setTextColor(ContextCompat.getColor(context,R.color.neutral2));
            }
            //    translation.setTextSize(forntSize + 4);
            translation.setTextSize(translationfontsize);
            holder.flow_word_by_word.addView(view);
            view.setLongClickable(true);
            view.setOnLongClickListener(v -> {
                Utils utils = new Utils(getContext());
                ArrayList<VerbCorpus> verbCorpusRootWords = utils.getQuranRoot(word.getSurahId(), word.getVerseId(), word.getWordno());
                if (verbCorpusRootWords.size() > 0 && verbCorpusRootWords.get(0).getTag().equals("V")) {
                    //    vbdetail = ams.getVerbDetails();
                    System.out.print("check");
                }
                ArrayList<NounCorpus> corpusNounWord = utils.getQuranNouns(word.getSurahId(), word.getVerseId(), word.getWordno());
                ArrayList<VerbCorpus> verbCorpusRootWord = utils.getQuranRoot(word.getSurahId(), word.getVerseId(), word.getWordno());
                WordMorphologyDetails qm = new WordMorphologyDetails(word, corpusNounWord, verbCorpusRootWord);
                SpannableString workBreakDown = qm.getWorkBreakDown();
                int color = ContextCompat.getColor(context,R.color.background_color_light_brown);
                switch (isNightmode) {
                    case "dark":
                    case "blue":
                    case "purple":
                    case "green":
                        color = ContextCompat.getColor(context,R.color.background_color);
                        break;
                    case "brown":
                        color = ContextCompat.getColor(context,R.color.neutral0);
                        break;
                    case "white":
                        color = ContextCompat.getColor(context,R.color.background_color_light_brown);
                        break;
                }
                Tooltip.Builder builder = new Tooltip.Builder(v, R.style.ayah_translation)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.TOP)
                        .setArrowEnabled(true)
                        .setBackgroundColor(color)
                        .setText(workBreakDown);
                builder.show();
                return true;
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setTitle(word.getWordsAr());
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt(SURAH_ID, word.getSurahId());
                    dataBundle.putInt(AYAHNUMBER, Math.toIntExact(word.getVerseId()));
                    dataBundle.putInt(WORDNUMBER, Math.toIntExact(word.getWordno()));
                    dataBundle.putString(SURAH_ARABIC_NAME, SurahName);
                    LoadItemList(dataBundle);

                }

                private void LoadItemList(Bundle dataBundle) {
                    if (issentence) {
                        SentenceAnalysisBottomSheet item = new SentenceAnalysisBottomSheet();
                        item.setArguments(dataBundle);
                        String[] data = {String.valueOf(word.getSurahId()), String.valueOf(word.getVerseId()), word.getTranslateEn(), String.valueOf((word.getWordno()))};
                        SentenceAnalysisBottomSheet.newInstance(data).show(((AppCompatActivity) context).getSupportFragmentManager(), SentenceAnalysisBottomSheet.TAG);

                    } else {
                        WordAnalysisBottomSheet item = new WordAnalysisBottomSheet();
                        item.setArguments(dataBundle);
                        String[] data = {String.valueOf(word.getSurahId()), String.valueOf(word.getVerseId()), word.getTranslateEn(), String.valueOf((word.getWordno())), SurahName};
                        WordAnalysisBottomSheet.newInstance(data).show(((AppCompatActivity) context).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);

                    }

                }

            });

        }
        holder.flow_word_by_word.setVisibility(View.VISIBLE);

    }

    private SpannableString getSpannedRoots(CorpusWbwWord corpus) {
        boolean b = corpus.getVerseId() == 20 && (corpus.getWordno() == 2 || corpus.getWordno() == 9);
        if (b) {
            System.out.println("check");
        }
        return CorpusUtilityorig.ColorizeRootword(corpus.getTagone(), corpus.getTagtwo(), corpus.getTagthree(), corpus.getTagfour(), corpus.getTagfive(),
                corpus.getRootword());
    }

    private SpannableString getSpannedWords(CorpusWbwWord corpus) {
        boolean b = corpus.getVerseId() == 20 && (corpus.getWordno() == 2 || corpus.getWordno() == 9);
        if (b) {
            System.out.println("check");
        }
        return CorpusUtilityorig.NewSetWordSpan(corpus.getTagone(), corpus.getTagtwo(), corpus.getTagthree(), corpus.getTagfour(), corpus.getTagfive(),
                corpus.getAraone(), corpus.getAratwo(), corpus.getArathree(), corpus.getArafour(), corpus.getArafive());
    }

    private void storepreferences(QuranEntity entity) {
        SharedPreferences pref = context.getSharedPreferences("lastread", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(CHAPTER, entity.getSurah());
        editor.putInt(AYAH_ID, entity.getAyah());
        editor.putString(SURAH_ARABIC_NAME, SurahName);
        editor.apply();
      //  editor.commit();

    }

    private void setChapterInfo(ItemViewAdapter holder, CorpusAyahWord verse) {
        StringBuilder surahInfo = new StringBuilder();
//        surahInfo.append(surahName+".");
        surahInfo.append(verse.getWord().get(0).getSurahId()).append(".");
        surahInfo.append(verse.getWord().get(0).getVerseId()).append("-");
        surahInfo.append(SurahName);
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String isNightmode = sharedPreferences.getString("theme", "dark");
        if (isMakkiMadani == 1) {
            holder.surah_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_makkah_48, 0, 0, 0);

        } else {
            holder.surah_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_madinah_48, 0, 0, 0);
        }
        if (isNightmode.equals("dark") || isNightmode.equals("blue")||isNightmode.equals("purple")||isNightmode.equals("green")) {
//TextViewCompat.setCompoundDrawableTintList()
            holder.surah_info.setCompoundDrawableTintList(ColorStateList.valueOf(Color.CYAN));
        } else {
            holder.surah_info.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLUE));

        }
        holder.surah_info.setText(surahInfo);
        holder.surah_info.setTextSize(16);
        //  holder.surah_info.setTextColor(ContextCompat.getColor(context,R.color.colorOnPrimary));
    }

    public class ItemViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, OnLongClickListener {
        public TextView quran_jalalayn, kathir_translation;
        public TextView quran_transliteration;
        public TextView translate_textView;
        //   public TextView erab_textView;
        public TextView erab_textView;
        public TextView surah_info, mafoolbihi;
        public TextView bismilla;
        public TextView quran_textView, erab_notes;
        public TextView quran_transliterationnote;
        public TextView quran_jalalaynnote;
        public TextView erab_textViewnote;
        public TextView translate_textViewnote;
        public ImageView bookmark, jumpto, makkimadaniicon, ivSummary;
        public ImageView expandImageButton, ivBismillah, erabexpand, erab_notes_expand;
        TextView tvSura, tvRukus, tvVerses;
        ImageView ivSurahIcon, ivLocationmakki, ivLocationmadani, ivhelp, ivoverflow, ivoverflow2,arrowforward, arrowback;
        //  public   com.nex3z.flowlayout.FlowLayout  flow_word_by_word;
        com.example.utility.FlowLayout flow_word_by_word;
        //   RelativeLayout colllayout;
        CardView erabnotescardView ;
        ImageView mafoolatarow;
        Group hiddenGroup, card_group;
        MaterialCardView base_cardview;
        FloatingActionButton tafsir, jumptofb, bookmarfb, fabmenu,helpfb,summbaryfb,sharescreenfb;
        ItemViewAdapter(View view, int viewType) {
            super(view);
            view.setTag(this);
            itemView.setOnClickListener(this);
            if (viewType == 0) {
                ivLocationmakki = view.findViewById(R.id.ivLocationmakki);
                ivLocationmadani = view.findViewById(R.id.ivLocationmadani);
                ivSurahIcon = view.findViewById(R.id.ivSurahIcon);
                tvVerses = view.findViewById(R.id.tvVerses);
                tvRukus = view.findViewById(R.id.tvRukus);
                tvSura = view.findViewById(R.id.tvSura);
                ivBismillah = view.findViewById(R.id.ivBismillah);

            } else {
                //     kathir_note = view.findViewById(R.id.kathir_note);
                kathir_translation = view.findViewById(R.id.katheer_textview);
                ivSummary = view.findViewById(R.id.ivSumarry);
                ivSummary.setTag("summary");
                ivSummary.setOnClickListener(this);
                jumpto = view.findViewById(R.id.jumpto);
                ivhelp = view.findViewById(R.id.ivHelp);
                ivoverflow = view.findViewById(R.id.ivActionOverflow);
                ivhelp.setOnClickListener(this);
                ivoverflow.setOnClickListener(this);
                ivoverflow.setTag("overflow_img");


                ivoverflow2 = view.findViewById(R.id.ivActionOverflowtwo);
                ivoverflow2.setOnClickListener(this);

                ivoverflow2.setTag("overflowbottom");






                makkimadaniicon = view.findViewById(R.id.makkimadaniicon);
                //    jumpto = view.findViewById(R.id.jumpto);
                bismilla = view.findViewById(R.id.bismillah);
                quran_transliterationnote = view.findViewById(R.id.quran_transliterationnote);
                quran_jalalaynnote = view.findViewById(R.id.quran_jalalaynnote);
                translate_textViewnote = view.findViewById(R.id.translate_textViewnote);
                erab_textViewnote = view.findViewById(R.id.erab_textViewnote);
                quran_transliteration = view.findViewById(R.id.quran_transliteration);
                quran_jalalayn = view.findViewById(R.id.quran_jalalayn);
                surah_info = view.findViewById(R.id.chaptername);
                //    verse_idTextView = view.findViewById(R.id.verse_id_textView);
                flow_word_by_word = view.findViewById(R.id.flow_word_by_word);
                translate_textView = view.findViewById(R.id.translate_textView);
                erab_textView = view.findViewById(R.id.erab_textView);
                //     erab_textView.setTextIsSelectable(true);
                quran_textView = view.findViewById(R.id.quran_textView);
                erab_notes = view.findViewById(R.id.erab_notes);
                //     bookmark = view.findViewById(R.id.bookmarkView);
                erabexpand = view.findViewById(R.id.erabexpand);
                erab_notes_expand = view.findViewById(R.id.erab_img);
                expandImageButton = view.findViewById(R.id.expandImageButton);
                quran_textView.setOnClickListener(this);
                quran_textView.setTag("qurantext");
                erab_notes_expand.setOnClickListener(this);
                erab_notes_expand.setTag("erab_notes");
             erabnotescardView = view.findViewById(R.id.base_cardview);

                mafoolatarow = view.findViewById(R.id.show);

                hiddenGroup = view.findViewById(R.id.card_group);

                mafoolatarow.setOnClickListener(this);
                mafoolbihi = view.findViewById(R.id.directobject);
            base_cardview = view.findViewById(R.id.base_cardview);


                fabmenu =  view. findViewById(R.id.expandfabs);
                tafsir =   view. findViewById(R.id.tafsirfb);
                jumptofb =   view. findViewById(R.id.jumptofb);
                bookmarfb =   view. findViewById(R.id.bookmarfb);
                summbaryfb=view.findViewById(R.id.summbaryfb);
                helpfb=view.findViewById(R.id.helpfb);
                sharescreenfb=view.findViewById(R.id.sharescreenfb);
                sharescreenfb.setOnClickListener(this);
                fabmenu.setOnClickListener(this);
                tafsir.setOnClickListener(this);
                jumptofb.setOnClickListener(this);
                bookmarfb.setOnClickListener(this);
                summbaryfb.setOnClickListener(this);
                helpfb.setOnClickListener(this);
                fabmenu.setTag("fabmenu");
                tafsir.setTag("tafsir");
                jumptofb.setTag("jumptofb");
                bookmarfb.setTag("bookmarfb");
                helpfb.setTag("help");
                summbaryfb.setTag("summaryfb");
                sharescreenfb.setTag("sharefb");

                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                SharedPreferences shared = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
                boolean colortag = shared.getBoolean("colortag", true);

                fabmenu.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        if (!isFABOpen) {
                            showFABMenu();
                        } else {
                            closeFABMenu();
                       //     HideFabMenu();
                        }
                    }

                    private void showFABMenu() {

                        isFABOpen = true;
                        fabmenu.animate().rotationBy(180);

                        tafsir.setVisibility(View.VISIBLE);
                        tafsir.animate().translationX(- getInstance().getResources().getDimension(R.dimen.standard_55));
                        tafsir.animate().rotationBy(360);
                        tafsir.animate().setDuration(1500);

                        jumptofb.setVisibility(View.VISIBLE);
                        jumptofb.animate().translationX(-getInstance().getResources().getDimension(R.dimen.standard_105));
                        jumptofb.animate().rotationBy(360);

                        bookmarfb.setVisibility(View.VISIBLE);
                        bookmarfb.animate().translationX(-getInstance().getResources().getDimension(R.dimen.standard_155));
                        bookmarfb.animate().rotationBy(360);
                        bookmarfb.animate().setDuration(600);

                        summbaryfb.setVisibility(View.VISIBLE);
                        summbaryfb.animate().translationX(-getInstance().getResources().getDimension(R.dimen.standard_205));
                        summbaryfb.animate().rotationBy(360);
                        helpfb.setVisibility(View.VISIBLE);
                        helpfb.animate().translationX(-getInstance().getResources().getDimension(R.dimen.standard_255));
                        helpfb.animate().rotationBy(360);

                        sharescreenfb.setVisibility(View.VISIBLE);
                        sharescreenfb.animate().translationX(-getInstance().getResources().getDimension(R.dimen.standard_305));
                        sharescreenfb.animate().rotationBy(360);
                        sharescreenfb.animate().setDuration(500);







                        tafsir.setOnClickListener(view12 -> {
                            closeFABMenu();
                            //HideFabMenu();
                            Intent readingintent = new Intent(context, TafsirFullscreenActivity.class);
                            //  flowAyahWordAdapter.getItem(position);
                            int chapter_no = ayahWord.getWord().get(0).getSurahId();
                            //   int verse = ayahWord.getWord().get(0).getVerseId();
                            int verse=        ayahWordArrayList.get(getPosition()-1).getWord().get(0).getVerseId();
                            //   String name = getSurahArabicName();
                            readingintent.putExtra(SURAH_ID, chapter_no);
                            readingintent.putExtra(AYAH_ID, verse);
                            readingintent.putExtra(SURAH_ARABIC_NAME, SurahName);
                            context.startActivity(readingintent);
                        });
                        summbaryfb.setOnClickListener(v -> {
                            closeFABMenu();
                          //  HideFabMenu();
                            int chapter_no = ayahWord.getWord().get(0).getSurahId();
                            //   int verse = ayahWord.getWord().get(0).getVerseId();
                            int verse=        ayahWordArrayList.get(getPosition()-1).getWord().get(0).getVerseId();

                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt(SURAH_ID, chapter_no);
                            SurahSummary item = new SurahSummary();
                            item.setArguments(dataBundle);
                            int data = (chapter_no);
                            SurahSummary.newInstance(data).show( ((AppCompatActivity) context).getSupportFragmentManager(), NamesDetail.TAG);
                        });

                        helpfb.setOnClickListener(v -> {
                            closeFABMenu();

                            ParticleColorScheme.newInstance().show(((AppCompatActivity) context).getSupportFragmentManager(), WordAnalysisBottomSheet.TAG);
                        });

                        sharescreenfb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                closeFABMenu();
                                //HideFabMenu();
                                takeScreenShot(((AppCompatActivity) context).getWindow().getDecorView());
                            }

                            private void takeScreenShot(View view) {
                                Date date = new Date();
                                CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);
                                try {
                                    File mainDir = new File(
                                            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
                                    if (!mainDir.exists()) {
                                        boolean mkdir = mainDir.mkdir();
                                    }

                                    String path = mainDir + "/" + "Mushafapplication" + "-" + format + ".jpeg";
                                    //    File zipfile = new File(getExternalFilesDir(null).getAbsolutePath() + getString(R.string.app_folder_path) + File.separator + DATABASEZIP);

                                    view.setDrawingCacheEnabled(true);
                                    int color = Color.RED;
                                    Bitmap bitmap =getBitmapFromView(view, color);

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

                                Uri uri = FileProvider.getUriForFile(context, getContext().getPackageName() + ".provider", imageFile);


                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_SEND);
                                intent.setType("image/*");
                                intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Application from Instagram");
                                intent.putExtra(Intent.EXTRA_STREAM, uri);


                                List<ResolveInfo> resInfoList =  context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                                for (ResolveInfo resolveInfo : resInfoList) {
                                    String packageName = resolveInfo.activityInfo.packageName;
                                    context.  grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                }
                                //  startActivity(Intent.createChooser(intent, "Share PDF using.."));
                                try {
                                    context.startActivity(Intent.createChooser(intent, "Share With"));
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }



                    private void bookMarkSelected(int position) {

                        int chapter_no = ayahWord.getWord().get(0).getSurahId();
                        //   int verse = ayahWord.getWord().get(0).getVerseId();
                        int verse=        ayahWordArrayList.get(getPosition()-1).getWord().get(0).getVerseId();
                        BookMarks en = new BookMarks();
                        en.setChapterno(String.valueOf(chapter_no));
                        en.setVerseno(String.valueOf(verse));
                        en.setSurahname(SurahName);
                        Utils utils=new Utils(getContext());
                        //     Utils utils = new Utils(ReadingSurahPartActivity.this);
                        utils.insertBookMark(en);

                        Snackbar snackbar = Snackbar
                                .make(view, "BookMark Created", Snackbar.LENGTH_LONG);
                        snackbar.setActionTextColor(Color.BLUE);
                        snackbar.setTextColor(Color.CYAN);
                        snackbar.setBackgroundTint(Color.BLACK);
                        snackbar.show();
                    }
                    private void closeFABMenu() {
                        isFABOpen = false;
                        fabmenu.animate().rotationBy(-180);

                        tafsir.animate().translationX(0);
                        tafsir.animate().rotationBy(0);

                        jumptofb.animate().translationX(0);

                        bookmarfb.animate().translationX(0);
                        bookmarfb.animate().rotationBy(360);

                        summbaryfb.animate().translationX(0);

                        helpfb.animate().translationX(0);
                        sharescreenfb.animate().translationX(0);
                        sharescreenfb.animate().rotationBy(360);


                    }
                });



                mafoolatarow.setOnClickListener(view1 -> {
                    TransitionManager.beginDelayedTransition(erabnotescardView, new AutoTransition());
                    if (hiddenGroup.getVisibility() == View.VISIBLE) {
                        hiddenGroup.setVisibility(View.GONE);
                        mafoolatarow.setImageResource(android.R.drawable.arrow_down_float);
                    } else {
                        //     colllayout.setLayoutParams(params);
                        hiddenGroup.setVisibility(View.VISIBLE);
                        mafoolatarow.setImageResource(android.R.drawable.arrow_up_float);
                    }
                });

                erabexpand.setOnClickListener(view1 -> {
                    if (erab_textView.getVisibility() == View.GONE) {
                        erab_textView.setVisibility(View.VISIBLE);
                        //  AnimationUtility.slide_down(context, erabexpand);
                        AnimationUtility.AnimateArrow(90.0f, erabexpand);
                    } else {
                        erab_textView.setVisibility(View.GONE);
                        AnimationUtility.AnimateArrow(0.0f, erabexpand);
                        //   Fader.slide_down(context,expandImageButton);
                    }

                });
                flow_word_by_word.setOnClickListener(view1 -> {
                    if (translate_textView.getVisibility() == View.GONE)
                        translate_textView.setVisibility(View.VISIBLE);
                    else
                        translate_textView.setVisibility(View.VISIBLE);

                });
                translate_textView.setOnClickListener(view1 -> {
                    if (translate_textView.getVisibility() == View.VISIBLE)
                        translate_textView.setVisibility(View.GONE);
                    else
                        translate_textView.setVisibility(View.VISIBLE);

                });
                erab_textView.setOnClickListener(view1 -> {
                    if (erab_textView.getVisibility() == View.VISIBLE)
                        erab_textView.setVisibility(View.GONE);
                    else
                        erab_textView.setVisibility(View.VISIBLE);

                });
            }

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //   mItemClickListener.onItemLongClick(getAdapterPosition(), v);
            mItemClickListener.onItemLongClick(getBindingAdapterPosition(), v);
            return true;

        }
    }

    //View.OnClickListener, View.OnLongClickListener

}























