package com.example.mushafconsolidated.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.Page;
import com.example.mushafconsolidated.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {

    private static final String TAG = "PageAdapter";


    int ayahsColora, scrollColora;
    int ayahsColor, scrollColor;
    int vis = View.INVISIBLE;
    private List<Page> list = new ArrayList<>();
    private Typeface typeface;
    private IOnClick iOnClick;
    private PageShown pageShown;
    private IBookmark iBookmark;
    Context context;

    public PageAdapter(Typeface typeface, int ayahsColor, int scrollColor, Context context) {
        this.typeface = typeface;
        this.ayahsColora = ayahsColor;
        this.scrollColora = scrollColor;
        this.context=context;
       this. ayahsColor = context.getColor(R.color.ayas_color);
       this. scrollColor = context.getColor(R.color.bg_ayahs);

    }

    public PageAdapter(List<Page> pageList, Context context) {
        this.list=pageList;
        ayahsColor = context.getColor(R.color.ayas_color);
        scrollColor = context.getColor(R.color.bg_ayahs);
        this.context=context;

    }

    public void setPageShown(PageShown pageShown) {
        this.pageShown = pageShown;
    }

    public void setiBookmark(IBookmark iBookmark) {
        this.iBookmark = iBookmark;
    }


    public void setPageList(List<Page> newList) {
        list = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }


    public interface PageShown {
        void onDiplayed(int pos, ViewHolder holder);
    }
    public List<Page> getList() {
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.page_item, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int index) {
        Page item = list.get(index);
        SharedPreferences sharedPreferences =
                androidx.preference.PreferenceManager.getDefaultSharedPreferences(context);
        String arabic_font_selection = sharedPreferences.getString("Arabic_Font_Selection", "me_quran.ttf");
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),
                arabic_font_selection);
   //     holder.topLinear.setVisibility(View.VISIBLE);
    //    holder.BottomLinear.setVisibility(View.VISIBLE);

        // set Colors
        holder.tvAyahs.setTextColor(ayahsColor);
        holder.scAyahsText.setBackgroundColor(scrollColor);

        //<editor-fold desc="Create Text">
        StringBuilder builder = new StringBuilder();
        String aya;
        String suraName = "";
        suraName = getSuraNameFromIndex(item.getAyahItems().get(0).getSurahIndex());

        String tempSuraName;
        boolean isFirst = true;


        //</editor-fold>

      holder.tvAyahs.setText((CharSequence) item.getAyahItems().get(0).getCleantext());
        holder.tvAyahs.setTypeface(custom_font);

        // text justifivation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.tvAyahs.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_NONE);
        }

        // top - bottom details
      //  holder.detailsbutton
        StringBuilder sb=new StringBuilder();
        sb.append("Page:-").append(item.getPageNum()).append("       "   ).append(suraName).append("     ").append("Juz:-").append(item.getJuz());
        holder.detailsbutton.setText(sb.toString());
      /*  holder.tvSurahName.setText(suraName);
        holder.tvJuz.setText(String.valueOf(item.getJuz()));*/

        //<editor-fold desc="listeners">
 /*       holder.imBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iBookmark.onBookmarkClicked(item);
            }
        });
*/
        /*holder.ayahsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: frameLayout");
                flipState(holder);
            }
        });
        */

 /*       holder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClick.onClick(holder.getAdapterPosition());
            }
        });*/

    /*    holder.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iOnClick.onClick(holder.getAdapterPosition() - 2); // there will be update by one
            }
        });*/
        //</editor-fold>

        //<editor-fold desc="configure next/prev buttons">
    /*    if (index == 0 ){
            holder.btnNext.setVisibility(View.VISIBLE);
            holder.btnPrev.setVisibility(View.INVISIBLE);
        }else if (index == 603 ){
            holder.btnNext.setVisibility(View.INVISIBLE);
            holder.btnPrev.setVisibility(View.VISIBLE);
        }else{
            holder.btnNext.setVisibility(View.VISIBLE);
            holder.btnPrev.setVisibility(View.VISIBLE);
        }*/
        //</editor-fold>

        /*
         *//**
         * getAxisValueis more accurate it get axis inside textView not screen
         * view.getWidth == view.getMeasuredWidth()) ==>  width of textView
         *//*
        holder.tvAyahs.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d(TAG, "2 - onTouch: getRawX  " + motionEvent.getRawX());
                Log.d(TAG, "2 - onTouch: getAxisValue " + motionEvent.getAxisValue(MotionEvent.AXIS_X));
                Log.d(TAG, "onTouch: getWidth  " + view.getWidth());
                Log.d(TAG, "onTouch: getMeasuredWidth " + view.getMeasuredWidth());
                Log.d(TAG, "onTouch: getX" + view.getX());

                int width = view.getWidth() ;
                int xAxix = (int) motionEvent.getAxisValue(MotionEvent.AXIS_X);

                if (width / 2 >= xAxix) // means left part so perform nextAction
                {
                    iOnClick.onClick(holder.getAdapterPosition());
                }else{
                    iOnClick.onClick(holder.getAdapterPosition()-2);
                }

                return false;
            }
        });*/

        holder.tvAyahs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
            }
        });


    }

    private void flipState(ViewHolder holder) {
        vis = holder.topLinear.getVisibility();
        vis = vis == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
        holder.BottomLinear.setVisibility(vis);
        holder.topLinear.setVisibility(vis);
    }

    /**
     * @param surahIndex in quran
     * @return
     */
    private String getSuraNameFromIndex(int surahIndex) {
        return Data.SURA_NAMES[surahIndex - 1];
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //<editor-fold desc="timer to hide">
/*        new CountDownTimer(50, 100) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                vis = View.VISIBLE;
                holder.BottomLinear.setVisibility(vis);
                holder.topLinear.setVisibility(vis);
            }
        }.start();*/


        pageShown.onDiplayed(holder.getAdapterPosition(), holder);
    }

    public Page getPage(int pos) {
        return list.get(pos);
    }



    interface IBookmark {
        void onBookmarkClicked(Page item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {






        ImageView imBookmark;
        MaterialButton detailsbutton;
        public LinearLayout topLinear; ImageButton btnNext;

        TextView tvPageNumShowAyahs;ImageButton btnPrev; public LinearLayout BottomLinear;
        ScrollView scAyahsText;

        TextView tvJuz,tvSurahName,tvAyahs;
        FrameLayout ayahsLayout;
        public ViewHolder(@NonNull View view) {
            super(view);
            detailsbutton=view.findViewById(R.id.detailsbutton);
       //     BottomLinear=view.findViewById(R.id.BottomLinear);
       //     btnPrev=view.findViewById(R.id.btnPrev);
          //  tvPageNumShowAyahs=view.findViewById(R.id.tvPageNumShowAyahs);
         //   btnNext=view.findViewById(R.id.btnNext);
            topLinear=view.findViewById(R.id.topLinear);
         //   imBookmark=view.findViewById(R.id.imBookmark);
          //  tvJuz=view.findViewById(R.id.tvJuz);
          //  tvSurahName=view.findViewById(R.id.tvSurahName);
            tvAyahs=view.findViewById(R.id.tvAyahs);
            ayahsLayout=view.findViewById(R.id.ayahsLayout);
            scAyahsText=view.findViewById(R.id.sc_ayahs_text);
        }

        @Override
        public void onClick(View v) {

        }
    }



    }

