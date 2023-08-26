package com.example.mushafconsolidated.Adapters;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.BookMarksPojo;
import com.example.mushafconsolidated.R;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

public class BookmarkCreateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<BookMarksPojo> collection;
    RelativeLayout frameLayout;

    OnItemClickListener mItemClickListener;
    int weaknesscolor;
    int wazancolor;
    ArrayList<String> subheaders;

    public BookmarkCreateAdapter(List<BookMarksPojo> collectionC) {

        this.collection=collectionC;
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_bookmarks_collection, parent, false);
            return new ViewHolder(itemView);


    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookMarksPojo collections;




           try {
                collections = collection.get(position);
           } catch (IndexOutOfBoundsException e){
            collections=null;
           }


           ViewHolder itemholder=(ViewHolder)  holder;
           //    setHolderposition(position);
           //   setBookmarid(bookMark.getId());
       //    TypedArray imgs = QuranGrammarApplication.getContext().getResources().obtainTypedArray(R.array.sura_imgs);
           SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
           String isNightmode = shared.getString("theme", "dark");

           String aya="(aya's";
           if(null!=collections){
               itemholder.collectiondetails.setText(collections.getHeader().concat(collections.getCount().concat("-").concat("aya's)")));
               itemholder.collectiondetails.setVisibility(View.VISIBLE);
               itemholder.collectionimage.setVisibility(View.VISIBLE);


           }else {
               itemholder.collectiondetails.setVisibility(View.GONE);
               itemholder.collectionimage.setVisibility(View.GONE);
           }

           int arabicFontSize = shared.getInt("pref_font_arabic_key", 18);





    }
    @Override
    public int getItemCount() {
        if (collection == null) {
            return 0;
        }



        if (collection.size() == 0) {
            //Return 1 here to show nothing
            return 1;
        }

        // Add extra view to show the footer view
        return collection.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);
        return collection.size();
    }

    public Object getItem(int position) {
        return collection.get(position);
    }



    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            // current clickListerner
    {

        RadioButton purple,black,dark_blue, green,brown;
        public   TextView datestamp,suraName,verseno;
        public MaterialCheckBox collectiondetails;
      ;
      public  ImageView surahicon,collectionimage;
        CardView cardView;
        public TextView chapterno;

        public ViewHolder(View view) {
            super(view);
            view.setTag(this);
            collectionimage=view.findViewById(R.id.imgviewcol);
            frameLayout= itemView.findViewById(R.id.bottomSheet);
            itemView.setOnClickListener(this);
           collectiondetails=view.findViewById(R.id.checkbox);
       //     surahicon = itemView.findViewById(R.id.surahicon);
        //    cardView = itemView.findViewById(R.id.cardview);
        //    datestamp = itemView.findViewById(R.id.date);
         //   chapterno = itemView.findViewById(R.id.chapterno);
         //   suraName = (TextView) itemView.findViewById(R.id.surahname);
          //  verseno = itemView.findViewById(R.id.verseno);
          //  chapterno = itemView.findViewById(R.id.chapterno);
            itemView.setOnClickListener(this);
            collectiondetails.setOnClickListener(this);// current clickListerner
            collectiondetails.setTag("ck");;

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

}
