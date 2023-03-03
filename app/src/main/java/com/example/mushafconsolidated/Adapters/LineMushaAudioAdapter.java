package com.example.mushafconsolidated.Adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.Constant.AYAH_ID;
import static com.example.Constant.CHAPTER;
import static com.example.Constant.SURAH_ARABIC_NAME;
import static com.example.utility.QuranGrammarApplication.getContext;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Config;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.mushafconsolidated.model.CorpusAyahWord;
import com.example.mushafconsolidated.model.CorpusWbwWord;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;
//public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListenerOnLong {
public class LineMushaAudioAdapter extends RecyclerView.Adapter<LineMushaAudioAdapter.ItemViewAdapter>
        //implements OnItemClickListenerOnLong {
{

    private final boolean defaultfont;
    BroadcastReceiver broadcastReceiver;
    private boolean isFABOpen=false;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    final long surah_id;



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



    public LineMushaAudioAdapter(Context context, List<QuranEntity> quranbySurah, OnItemClickListenerOnLong listener, long surah_id, String surahName, int ismakki, ArrayList<String> header) {
        this.context=context;
        this.allofQuran=quranbySurah;
        this.mItemClickListener=listener;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.getBoolean(Config.SHOW_Erab, Config.defaultShowErab);
     //   this.header = header;
        arabicfontSize = sharedPreferences.getInt("pref_font_arabic_key", 18);
        translationfontsize = sharedPreferences.getInt("pref_font_englsh_key", 18);
        defaultfont = sharedPreferences.getBoolean("default_font", true);
        this.surah_id = surah_id;
        this.SurahName = surahName;
        this.isMakkiMadani = ismakki;
        this.header=header;
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
        return allofQuran.size();
        //     return  quran.size();
    }
    @Override
    public long getItemId(int position) {
        return allofQuran.get(position).getAyah();
    }



    @NonNull
    @Override
    public ItemViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surah_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_mushaf_row_ayah_word, parent, false);
          //  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_mushaf_row_ayah_word, parent, false);
            //   view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewer_aya_cardview, parent, false);
        }
        return new ItemViewAdapter(view, viewType);

    }

    public Object getItem(int position) {

            return allofQuran.get(position);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ItemViewAdapter holder, int position) {
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        isNightmode = sharedPreferences.getString("themepref", "dark");
        //  String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", String.valueOf(MODE_PRIVATE));
        String islamicfont="AlQalam.ttf";
        String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", "quranicfontregular.ttf");
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),
                islamicfont);
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
            if (isNightmode.equals("dark") || isNightmode.equals("blue") ||isNightmode.equals("green")) {
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


            displayAyats(showrootkey, holder, position   , sharedPreferences, custom_font, showErab, showWordColor, showTransliteration, showJalalayn, showTranslation, showWordByword, whichtranslation, showKathir);

        }

    }

    public void displayAyats(boolean showrootkey, LineMushaAudioAdapter.ItemViewAdapter holder, int position, SharedPreferences sharedPreferences, Typeface custom_font, boolean showErab, boolean showWordColor, boolean showTransliteration, boolean showJalalayn, boolean showTranslation, boolean showWordByword, String whichtranslation, boolean showKathir) {
        //   holder.flowwbw.setBackgroundColor(R.style.Theme_DarkBlue);
        QuranEntity entity = null;


        //   String wbw = sharedPreferences.getString("wordByWord", String.valueOf(Context.MODE_PRIVATE));
        try {
            entity = allofQuran.get(position-1);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("check");
        }
        String aya = "";

        if (null != entity) {
            storepreferences(entity);
        }
        ViewGroup.LayoutParams lp = holder.quran_textView.getLayoutParams();

        if(entity.getPassage_no()!=0){
            StringBuilder builder = new StringBuilder();
            builder.append(entity.getQurantext());
            String s="";
            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ {2}", aya, entity.getAyah(),R.drawable.ruku_new));
            if(entity.getPassage_no()>0){
                CorpusWbwWord words = new CorpusWbwWord();
                NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("AR"));
                 s = "\uFD3E" + "\0639" + "\uFD3F";
                s = "\uFD3F"+ "\u0639" + "\uFD3E";
            }
            //     builder.append("۩");

            holder.quran_textView.setText(entity.getQurantext().concat(s));
            holder.quran_textView.setTextSize(arabicfontSize);
            holder.quran_textView.setTypeface(custom_font);
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

          //  holder.sajdaverse.setImageResource(R.drawable.ruku_new);

        }
        if(entity.getPassage_no()>0) {
            holder.rukuview.setVisibility(View.VISIBLE);

        }


        if(entity.getHas_prostration()==1) {

            StringBuilder builder = new StringBuilder();
            builder.append(entity.getQurantext());
            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ۩", aya, entity.getAyah()));
       //     builder.append("۩");
            holder.quran_textView.setText(builder.toString());
            if(!defaultfont) {
                holder.quran_textView.setTextSize(arabicfontSize);
            }
            holder.quran_textView.setTypeface(custom_font);
        }else{
            StringBuilder builder = new StringBuilder();
            builder.append(entity.getQurantext());
            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", aya, entity.getAyah()));

            holder.quran_textView.setText(builder.toString());
            if(!defaultfont) {
                holder.quran_textView.setTextSize(arabicfontSize);
            }
            holder.quran_textView.setTypeface(custom_font);
        }

   /*     if(entity.getPassage_no()!=0){
           holder.sajdaverse.setVisibility(View.VISIBLE);
       }else {
            holder.sajdaverse.setVisibility(View.GONE);
        }
*/

        System.out.println("Position"+position);









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
      //  setTextSizes(holder);
    }

    private void setTextSizes(ItemViewAdapter holder) {
        if (!defaultfont) {
            holder.quran_transliteration.setTextSize(translationfontsize);
            holder.erab_textView.setTextSize(translationfontsize);
            holder.translate_textView.setTextSize(translationfontsize);
            holder.translate_textViewnote.setTextSize(translationfontsize);
            holder.quran_jalalayn.setTextSize(translationfontsize);

        }
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

    private void setChapterInfo(ItemViewAdapter holder, QuranEntity verse) {
        StringBuilder surahInfo = new StringBuilder();
//        surahInfo.append(surahName+".");

        surahInfo.append(verse.getSurah()).append(".");
        surahInfo.append(verse.getAyah()).append("-");
        surahInfo.append(SurahName);
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String isNightmode = sharedPreferences.getString("theme", "dark");
        if (isMakkiMadani == 1) {
            holder.surah_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_makkah_48, 0, 0, 0);

        } else {
            holder.surah_info.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_madinah_48, 0, 0, 0);
        }
        if (isNightmode.equals("dark") || isNightmode.equals("blue")||isNightmode.equals("green")) {
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

        public TextView quran_transliterationnote;
        public TextView quran_jalalaynnote;
        public TextView erab_textViewnote;
        public TextView translate_textViewnote;
        public ImageView bookmark, jumpto, makkimadaniicon, ivSummary;
        public ImageView expandImageButton, ivBismillah, erabexpand, erab_notes_expand;
        TextView tvSura, tvRukus, tvVerses;
        AppCompatTextView quran_textView;
        View rukuview;
        public  ImageView sajdaverse;
        ImageView ivSurahIcon, ivLocationmakki, ivLocationmadani, ivhelp, ivoverflow, ivoverflow2,arrowforward, arrowback;
        //  public   com.nex3z.flowlayout.FlowLayout  flow_word_by_word;
        com.example.utility.FlowLayout flow_word_by_word;
        //   RelativeLayout colllayout;
        CardView erabnotescardView, kahteercardview;
        ImageView mafoolatarow, showkatheer;
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






                rukuview=view.findViewById(R.id.rukuview);


               sajdaverse=view.findViewById(R.id.sajda);
                makkimadaniicon = view.findViewById(R.id.makkimadaniicon);

                translate_textViewnote = view.findViewById(R.id.translate_textViewnote);

                surah_info = view.findViewById(R.id.chaptername);
                //    verse_idTextView = view.findViewById(R.id.verse_id_textView);

                translate_textView = view.findViewById(R.id.translate_textView);

                //     erab_textView.setTextIsSelectable(true);
                quran_textView = view.findViewById(R.id.quran_textView);


            base_cardview = view.findViewById(R.id.base_cardview);



                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                quran_textView.setOnClickListener(this);
                quran_textView.setOnLongClickListener(this);
                quran_textView.setTag("verse");
                SharedPreferences shared = androidx.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
                boolean colortag = shared.getBoolean("colortag", true);








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























