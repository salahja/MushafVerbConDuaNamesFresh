package com.example.mushafconsolidated.Adapters;

import static com.example.utility.QuranGrammarApplication.getContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Config;
import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.Entities.QuranEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListenerOnLong;
import com.example.utility.CorpusUtilityorig;

import java.text.MessageFormat;
import java.util.ArrayList;

//import com.example.mushafconsolidated.Entities.JoinVersesTranslationDataTranslation;
//public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListenerOnLong {
public class PageMushaAudioAdapter extends RecyclerView.Adapter<PageMushaAudioAdapter.ItemViewAdapter>
        //implements OnItemClickListenerOnLong {
{

    private final ArrayList<Page> fullQuranPages;
    // --Commented out by Inspection (25/08/23, 7:28 pm):BroadcastReceiver broadcastReceiver;
    // --Commented out by Inspection (25/08/23, 7:28 pm):private final boolean isFABOpen = false;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    final long surah_id;

    private final ArrayList<String> header;
    private final int isMakkiMadani;
    public final Context context;
    // --Commented out by Inspection (25/08/23, 7:28 pm):public TextView arabic, rootword;
    final int arabicfontSize;
    final int translationfontsize;
    final OnItemClickListenerOnLong mItemClickListener;

    // --Commented out by Inspection (25/08/23, 7:28 pm):private CorpusAyahWord ayahWord;

    public PageMushaAudioAdapter(ArrayList<Page> fullQuranPages, Context context, OnItemClickListenerOnLong listener, long surah_id, String surahName, int ismakki, ArrayList<String> header) {
    this.fullQuranPages=fullQuranPages;
        this.context = context;
        this.mItemClickListener = listener;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.getBoolean(Config.SHOW_Erab, Config.defaultShowErab);
        //   this.header = header;
        arabicfontSize = sharedPreferences.getInt("pref_font_arabic_key", 18);
        translationfontsize = sharedPreferences.getInt("pref_font_englsh_key", 18);
        this.surah_id = surah_id;
        this.isMakkiMadani = ismakki;
        this.header = header;
    }

// --Commented out by Inspection START (25/08/23, 7:28 pm):
//    public void addContext(Context context) {
//        this.context = context;
//
//    }
// --Commented out by Inspection STOP (25/08/23, 7:28 pm)

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
        return fullQuranPages.size()+1;

        //     return  quran.size();
    }

    @NonNull
    @Override
    public ItemViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surah_header, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mushaf_row_ayah_word, parent, false);

        }
        return new ItemViewAdapter(view, viewType);

    }


    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ItemViewAdapter holder, int position) {


        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String isNightmode = sharedPreferences.getString("themepref", "dark");

        String islamicfont = "AlQalam.ttf";
        String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", "quranicfontregular.ttf");
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),
                islamicfont);
        final String FONTS_LOCATION_PATH = "fonts/DejaVuSans.ttf";

        boolean defaultfont = sharedPreferences.getBoolean("default_font", true);
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
            int headercolor;
            if (isNightmode.equals("dark") || isNightmode.equals("blue")  || isNightmode.equals("green")) {
                holder.ivLocationmakki.setColorFilter(Color.CYAN);
                holder.ivSurahIcon.setColorFilter(Color.CYAN);
                holder.ivLocationmadani.setColorFilter(Color.CYAN);

            } else {
                holder.ivLocationmakki.setColorFilter(Color.BLACK);
                holder.ivSurahIcon.setColorFilter(Color.BLACK);
                holder.ivLocationmadani.setColorFilter(Color.BLACK);
            }

        } else {
            Page page = fullQuranPages.get(position-1);
            //  String s=      passage.get(position);
            String pageNum = String.valueOf(page.getPageNum());
            holder.pagenum.setText((pageNum));
         String aya;
            StringBuilder builder = new StringBuilder();
            for (QuranEntity ayahItem : page.getAyahItemsquran()) {
                aya = ayahItem.getQurantext();




                builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", aya, ayahItem.getAyah()));
            }
            holder.quran_textView.setText(CorpusUtilityorig.getSpannable(builder.toString()), TextView.BufferType.SPANNABLE);
            holder.quran_textView.setTypeface(custom_font);


            if(!defaultfont) {
                 holder.quran_textView.setTextSize(arabicfontSize);
            }


        }

    }

// --Commented out by Inspection START (25/08/23, 7:28 pm):
//    public void displayAyats(boolean showrootkey, PageMushaAudioAdapter.ItemViewAdapter holder, int position, SharedPreferences sharedPreferences, Typeface custom_font, boolean showErab, boolean showWordColor, boolean showTransliteration, boolean showJalalayn, boolean showTranslation, boolean showWordByword, String whichtranslation, boolean showKathir) {
//        //   holder.flowwbw.setBackgroundColor(R.style.Theme_DarkBlue);
//        QuranEntity entity = null;
//
//        //   String wbw = sharedPreferences.getString("wordByWord", String.valueOf(Context.MODE_PRIVATE));
//
//
//
//
//    /*    if(entity.getPassage_no()!=0){
//            StringBuilder builder = new StringBuilder();
//            builder.append(entity.getQurantext());
//            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ {2}", aya, entity.getAyah(),R.drawable.ruku_new));
//            //     builder.append("۩");
//            holder.quran_textView.setText(entity.getQurantext());
//            holder.quran_textView.setTextSize(arabicfontSize);
//            holder.quran_textView.setTypeface(custom_font);
//
//          //  holder.sajdaverse.setImageResource(R.drawable.ruku_new);
//
//        }*/
//
//     /*   if(entity.getHas_prostration()==1) {
//
//            StringBuilder builders = new StringBuilder();
//            builder.append(entity.getQurantext());
//            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ۩", aya, entity.getAyah()));
//       //     builder.append("۩");
//            holder.quran_textView.setText(builder.toString());
//            holder.quran_textView.setTextSize(arabicfontSize);
//            holder.quran_textView.setTypeface(custom_font);
//        }else{
//            StringBuilder builders = new StringBuilder();
//            builder.append(entity.getQurantext());
//            builder.append(MessageFormat.format("{0} ﴿ {1} ﴾ ", aya, entity.getAyah()));
//
//            holder.quran_textView.setText(builder.toString());
//            holder.quran_textView.setTextSize(arabicfontSize);
//            holder.quran_textView.setTypeface(custom_font);
//        }
//*/
//
//        System.out.println("Position" + position);
//
//        if (showTranslation) {
//            if (whichtranslation.equals("en_arberry")) {
//                if (entity != null) {
//                    holder.translate_textView.setText(entity.getEn_arberry());
//                }
//                holder.translate_textViewnote.setText(R.string.arberry);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setVisibility(View.VISIBLE);
//                holder.translate_textViewnote.setVisibility(View.VISIBLE);
//            }
//            if (whichtranslation.equals("en_sahih")) {
//                if (entity != null) {
//                    holder.translate_textView.setText(entity.getTranslation());
//                }
//                holder.translate_textViewnote.setText(R.string.ensahih);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setVisibility(View.VISIBLE);
//                holder.translate_textViewnote.setVisibility(View.VISIBLE);
//            }
//            if (whichtranslation.equals("en_jalalayn")) {
//                if (entity != null) {
//                    holder.translate_textView.setText(entity.getEn_jalalayn());
//                }
//                holder.translate_textViewnote.setText(R.string.enjalalayn);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setVisibility(View.VISIBLE);
//                holder.translate_textViewnote.setVisibility(View.VISIBLE);
//            }
//            if (whichtranslation.equals("ur_jalalayn")) {
//                if (entity != null) {
//                    holder.translate_textView.setText(entity.getUr_jalalayn());
//                }
//                holder.translate_textViewnote.setText(R.string.enjalalayn);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setVisibility(View.VISIBLE);
//                holder.translate_textViewnote.setVisibility(View.VISIBLE);
//            }
//            if (whichtranslation.equals("ur_junagarhi")) {
//                if (entity != null) {
//                    holder.translate_textView.setText(entity.getUr_junagarhi());
//                }
//                holder.translate_textViewnote.setText(R.string.urjunagadi);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setTextSize(translationfontsize);
//                holder.translate_textView.setVisibility(View.VISIBLE);
//                holder.translate_textViewnote.setVisibility(View.VISIBLE);
//            }
//            holder.translate_textView.setTextSize(translationfontsize);
//            holder.translate_textView.setTextSize(translationfontsize);
//            holder.translate_textView.setVisibility(View.VISIBLE);
//            holder.translate_textViewnote.setVisibility(View.VISIBLE);
//
//        }
//
//    }
// --Commented out by Inspection STOP (25/08/23, 7:28 pm)

    public class ItemViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, OnLongClickListener {




        public TextView pagenum;





        public ImageView  ivBismillah ;
        TextView tvSura, tvRukus, tvVerses, quran_textView;



        ImageView ivSurahIcon;
        ImageView ivLocationmakki;
        ImageView ivLocationmadani;





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
                pagenum=view.findViewById(R.id.pagenum);





                //    verse_idTextView = view.findViewById(R.id.verse_id_textView);



                //     erab_textView.setTextIsSelectable(true);
                quran_textView = view.findViewById(R.id.quran_textView);


                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                quran_textView.setOnClickListener(this);
                quran_textView.setTag("verse");


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























