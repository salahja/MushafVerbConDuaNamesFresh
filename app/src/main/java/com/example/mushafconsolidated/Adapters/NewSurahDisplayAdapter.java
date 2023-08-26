package com.example.mushafconsolidated.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.ChaptersAnaEntity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.QuranGrammarApplication;
import com.example.utility.SharedPref;

import java.util.ArrayList;
import java.util.List;

//public class VerseDisplayAdapter extends RecyclerView.Adapter<VerseDisplayAdapter.ItemViewAdapter> {
//public class SurahPartAdap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
public class NewSurahDisplayAdapter extends RecyclerView.Adapter<NewSurahDisplayAdapter.ItemViewAdapter> implements Filterable {
    private static final String TAG = "SurahPartAdap ";
    OnItemClickListener mItemClickListener;
    private List<ChaptersAnaEntity> listonearray;
    private final Context context;
    private String surahname;
    private List<ChaptersAnaEntity> chapterfilered;

    public NewSurahDisplayAdapter(Context context, ArrayList<ChaptersAnaEntity> allAnaChapters) {
        this.context = context;
        this.listonearray = allAnaChapters;
        this.chapterfilered=allAnaChapters;

    }

    @NonNull
    @Override
    public ItemViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        //    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.surarowlinear, parent, false);
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orignalsurarowlinear, parent, false);
        return new ItemViewAdapter(view, viewType);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull NewSurahDisplayAdapter.ItemViewAdapter holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        final ChaptersAnaEntity surah = chapterfilered.get(position);
        Context context = QuranGrammarApplication.getContext();
        SharedPreferences pref = context.getSharedPreferences("lastread", MODE_PRIVATE);
        TypedArray imgs = this.context.getResources().obtainTypedArray(R.array.sura_imgs);


        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String theme = sharedPreferences.getString("themepref", "dark");
        boolean defaultfont = sharedPreferences.getBoolean("default_font", true);

        int surahIsmakki = surah.getIsmakki();
        int cno = surah.getChapterid();
      //  holder.tvsurahleft.setText(sb);
        holder.tvsurahleft.setText(surah.getNameenglish());
        if(!defaultfont){
            holder.tvsurahleft.setTextSize(SharedPref.SeekarabicFontsize());
        }
   //
        final Drawable drawable = imgs.getDrawable(cno - 1);
        imgs.recycle();
        holder.ivsurahicon.setImageDrawable(drawable);

        if (surahIsmakki == 1) {
            holder.makkimadaniIcon.setImageResource(R.drawable.ic_makkah_foreground);
        } else {
            holder.makkimadaniIcon.setImageResource(R.drawable.ic_madinah_foreground);
        }
        if (theme.equals("green")) {
            holder.surahcardview.setCardBackgroundColor(context.getResources().getColor(R.color.mdgreen_theme_dark_onPrimary));
        }

        if (theme.equals("dark") || theme.equals("blue")  ||theme.equals("green")) {
            holder.makkimadaniIcon.setColorFilter(Color.CYAN);
            holder.ivsurahicon.setColorFilter(Color.CYAN);
        } else {
            holder.makkimadaniIcon.setColorFilter(Color.BLUE);
            holder.ivsurahicon.setColorFilter(Color.BLACK);
        }
     //   holder.tvsurahleft.setTextSize(SharedPref.SeekarabicFontsize());

    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);
        return chapterfilered.size();
    }

    public Object getItem(int position) {
        return chapterfilered.get(position);
    }

    @Override
    public int getItemCount() {
        return chapterfilered.size();

    }




    public void setUp(List<ChaptersAnaEntity> listone, List<ChaptersAnaEntity> listtwo) {
        this.listonearray = listone;

    }

    public void setUp(ArrayList<ChaptersAnaEntity> allAnaChapters) {
        this.listonearray = allAnaChapters;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {


            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                boolean bool = false;
                char[] charArray = charString.toCharArray();
                if(!charString.isEmpty()) {
                    bool = Character.isDigit(charArray[0]);
                }

                if (charString.isEmpty()) {
                    chapterfilered = listonearray;

                } else {
                    if(!bool) {
                        List<ChaptersAnaEntity> filteredList = new ArrayList<>();
                        for (ChaptersAnaEntity details : listonearray) {
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (details.getNameenglish().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(details);
                            }
                        }
                        chapterfilered = filteredList;
                    }else {
                        List<ChaptersAnaEntity> filteredList = new ArrayList<>();
                        for (ChaptersAnaEntity details : listonearray) {
                            int str= Integer.parseInt(charString);
                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                           if(details.getChapterid()==str)

                                {
                                    filteredList.add(details);

                            }
                            chapterfilered = filteredList;

                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = chapterfilered;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                chapterfilered = (ArrayList<ChaptersAnaEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewAdapter extends RecyclerView.ViewHolder
            implements View.OnClickListener // current clickListerner
    {
        public TextView tvnumber;
        public TextView tvsurahright;
        public final TextView tvsurahleft;
        public TextView part;
        public TextView tvSurahlefttarabic;
        public TextView tvSurahrightarabic;
        public final TextView overlayTypeChapterView;
        public TextView overlayTypePartView;
        public TextView surah_name_arabic;
        public TextView referenceview;
        public RelativeLayout row_surah;
        //   public ConstraintLayout surah_row_table;///for rnew_surah_row
        public LinearLayout surah_row_table;
        final ImageView makkimadaniIcon;
        final ImageView ivsurahicon;
        ImageView tvarabicright;
        final CardView surahcardview;

        ItemViewAdapter(View layout, int viewType) {
            super(layout);
            surahcardview = itemView.findViewById(R.id.surahcardview);
          //  tvsurahright = itemView.findViewById(R.id.tvSuraright);
            tvsurahleft = itemView.findViewById(R.id.tvArabic);
            makkimadaniIcon = itemView.findViewById(R.id.makkimadaniicon);
            overlayTypeChapterView = itemView.findViewById(R.id.overlayTypeChapterView);
            ivsurahicon = itemView.findViewById(R.id.surahicon);
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