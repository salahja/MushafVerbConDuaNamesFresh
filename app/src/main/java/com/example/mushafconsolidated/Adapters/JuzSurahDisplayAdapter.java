package com.example.mushafconsolidated.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.mushafconsolidated.model.Juz;
import com.example.utility.QuranGrammarApplication;
import com.example.utility.SharedPref;

import java.util.ArrayList;
import java.util.List;

//public class VerseDisplayAdapter extends RecyclerView.Adapter<VerseDisplayAdapter.ItemViewAdapter> {
//public class SurahPartAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
public class JuzSurahDisplayAdapter extends RecyclerView.Adapter<JuzSurahDisplayAdapter.ItemViewAdapter>  {
    //implements Filterable
    private static final String TAG = "SurahPartAdap ";
    OnItemClickListener mItemClickListener;
    private List<ChaptersAnaEntity> listonearray = new ArrayList<>();
    private List<ChaptersAnaEntity> listtwoarray = new ArrayList<>();
    private final Context context;
    private String surahname;
    private List<Juz> JuzArray;
    private boolean defaultfont;

    public JuzSurahDisplayAdapter(Context context, List<Juz> juzArray) {
        this.context = context;
        this.JuzArray = juzArray;


    }

    @NonNull
    @Override
    public ItemViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surarowlinear, parent, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.juz_hizb, parent, false);
        return new ItemViewAdapter(view, viewType);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull JuzSurahDisplayAdapter.ItemViewAdapter holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        final Juz juzitem = JuzArray.get(position);
        Context context = QuranGrammarApplication.getContext();
        SharedPreferences pref = context.getSharedPreferences("lastread", MODE_PRIVATE);
        int chapterno = pref.getInt("surah", 1);
        int verse_no = pref.getInt("ayah", 1);
        SharedPref sharedPref = new SharedPref(context);
        final String isNightmode = SharedPref.themePreferences();
        TypedArray imgs = this.context.getResources().obtainTypedArray(R.array.sura_imgs);
        TypedArray array = imgs;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPreferences.getString("themepref", "dark");

        String juzdetail="Juz".concat(" ").concat(String.valueOf(juzitem.getJuz()));
        String surahdetails=juzitem.getNamearabic().concat(" ").concat(String.valueOf(juzitem.getChapterid())).concat(":").concat(String.valueOf(juzitem.getAyah()));
        String text=juzitem.getQurantext();
        String[] split = text.split(" ");

        defaultfont = sharedPreferences.getBoolean("default_font", true);



        holder.tvnumber.setText(juzdetail);
        holder.tvJuz.setText(surahdetails);
        if(split.length>1) {
            holder.tvArabic.setText(split[0].concat(" ").concat(split[1]));
        }else if(split.length==1){
            holder.tvArabic.setText(split[0]);
        }
        holder.tvnumber.setTextSize(SharedPref.SeekarabicFontsize());
        holder.tvJuz.setTextSize(SharedPref.SeekarabicFontsize());
        holder.tvArabic.setTextSize(SharedPref.SeekarabicFontsize());
        if(!defaultfont){
            holder.tvArabic.setTextSize(SharedPref.SeekarabicFontsize());
        }




    }


    public Object getItem(int position) {
        return JuzArray.get(position).getChapterid()-1;
    }

    @Override
    public int getItemCount() {
        return JuzArray.size();

    }







    public class ItemViewAdapter extends RecyclerView.ViewHolder
            implements View.OnClickListener // current clickListerner
    {
        public TextView tvnumber, tvJuz, tvArabic, part;




        CardView surahcardview;

        ItemViewAdapter(View layout, int viewType) {
            super(layout);
            surahcardview = itemView.findViewById(R.id.surahcardview);
            tvnumber = itemView.findViewById(R.id.tvNumber);
            tvJuz=itemView.findViewById(R.id.tvJuz);
            tvArabic = itemView.findViewById(R.id.tvArabic);
     /*       makkimadaniIcon = itemView.findViewById(R.id.makkimadaniicon);
            overlayTypeChapterView = itemView.findViewById(R.id.overlayTypeChapterView);
            ivsurahicon = itemView.findViewById(R.id.surahicon);*/

            layout.setOnClickListener(this); // current clickListerner

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());

            }
        }
    }

}