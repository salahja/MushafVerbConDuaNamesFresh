package sj.hisnul.adapter;

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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

public class origBookmarCrateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 2;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM =0;

    private final List<BookMarksPojo> collection;
    RelativeLayout frameLayout;

    OnItemClickListener mItemClickListener;
    int weaknesscolor;
    int wazancolor;
    ArrayList<String> subheaders;

    public origBookmarCrateAdapter(List<BookMarksPojo> collectionC) {

        this.collection=collectionC;
    }


    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            //Inflating header view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bheader, parent, false);
            return new  HeaderHolder(view);
        }else

        if (viewType == TYPE_FOOTER) {
            //Inflating header view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_bookmarks_collection, parent, false);
            return new ViewHolder(itemView);
        } else return null;

    }





    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BookMarksPojo collections;
       if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
         //   footerHolder.footerText.setText("footer");
          // footerHolder.footerText.setOnClickListener(view -> {
                //     Toast.makeText(activity, "You clicked at Footer View", Toast.LENGTH_SHORT).show();
          //  });
        } else if (holder instanceof ViewHolder) {



           try {
                collections = collection.get(position-1);
           } catch (IndexOutOfBoundsException e){
            collections=null;
           }


           ViewHolder itemholder=(ViewHolder)  holder;
           //    setHolderposition(position);
           //   setBookmarid(bookMark.getId());
           TypedArray imgs = QuranGrammarApplication.getContext().getResources().obtainTypedArray(R.array.sura_imgs);
           SharedPreferences shared = android.preference.PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
           String isNightmode = shared.getString("theme", "dark");


      /*    itemholder.surahicon.setImageDrawable(drawable);
           if (isNightmode.equals("dark") || isNightmode.equals("blue")) {
               itemholder.surahicon.setColorFilter(Color.CYAN);

           }*/
           String aya="(aya's";
           if(null!=collections){
               itemholder.collectiondetails.setText(collections.getHeader().concat(collections.getCount().concat("-").concat("aya's)")));
               itemholder.collectiondetails.setVisibility(View.VISIBLE);
               itemholder.collectionimage.setVisibility(View.VISIBLE);


           }else {
               itemholder.collectiondetails.setVisibility(View.GONE);
               itemholder.collectionimage.setVisibility(View.GONE);
           }

        //   itemholder.datestamp.setText(bookMark.getDatetime());
         //  itemholder.suraName.setText(bookMark.getSurahname());
         //  itemholder.chapterno.setText(bookMark.getChapterno());
          // itemholder.verseno.setText(bookMark.getVerseno() + "");
           int arabicFontSize = shared.getInt("pref_font_arabic_key", 18);
         //  itemholder.datestamp.setTextSize(arabicFontSize);
          // itemholder.suraName.setTextSize(arabicFontSize);
         //  itemholder.verseno.setTextSize(arabicFontSize);
          // itemholder.chapterno.setTextSize(arabicFontSize);
   /*        if (isNightmode.equals("dark")) {
               ContextCompat.getColor(QuranGrammarApplication.getContext(), R.color.color_background_overlay);
               itemholder.cardView.setCardBackgroundColor(ContextCompat.getColor(QuranGrammarApplication.getContext(), R.color.color_background_overlay));

           } else if (isNightmode.equals("blue")) {
               itemholder.cardView.setCardBackgroundColor(ContextCompat.getColor(QuranGrammarApplication.getContext(), R.color.solarizedBase02));

           }
*/


       }

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

    @Override
    public int getItemViewType(int position) {
        if (position == collection.size()) {
            // This is where we'll add footer.
            return TYPE_FOOTER;
        }else
        if(position==0){
            return TYPE_HEADER;
        } else
        if(position!=collection.size() && position!=0){
            return TYPE_ITEM;
        }


        return super.getItemViewType(position);
    }


    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }
    private  class FooterViewHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {
        final TextView footerText;
        MaterialButton newcollection,addtocollection;
        public FooterViewHolder(View view) {
            super(view);
            view.setTag(this);
            view.setOnClickListener(this);
            footerText = view.findViewById(R.id.txtDuaReference);
            newcollection=view.findViewById(R.id.newcollection);
            addtocollection=view.findViewById(R.id.addtocollection);

            addtocollection.setTag("add");
            addtocollection.setOnClickListener(this);



            addtocollection.setOnClickListener(this);
            addtocollection.setTag("addcollection");
         ;
            newcollection.setOnClickListener(this);
            newcollection.setTag("newcollection");



        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    private  class HeaderHolder extends RecyclerView.ViewHolder   implements View.OnClickListener {

        MaterialButton create,addpin,done;
        public HeaderHolder(View view) {
            super(view);
            view.setTag(this);
            view.setOnClickListener(this);




//            addpin.setTag("add");
        //    addpin.setOnClickListener(this);





        }



        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
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
