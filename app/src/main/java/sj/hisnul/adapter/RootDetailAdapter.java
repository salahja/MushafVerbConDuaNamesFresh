package sj.hisnul.adapter;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.utility.QuranGrammarApplication;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import sj.hisnul.entity.hcategory;

public class RootDetailAdapter extends RecyclerView.Adapter<RootDetailAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<String> catTwoArrayList;
    OnItemClickListener mItemClickListener;

    public RootDetailAdapter(ArrayList<String> lists, Context context) {
        this.context = context;
        this.catTwoArrayList = lists;

    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarfkabeercolumn, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_arabicroot_detail, parent, false);
        //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thulathisarfsagheer, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        TypedArray imgs = this.context.getResources().obtainTypedArray(R.array.cat_img);
        String catOne = catTwoArrayList.get(position);
      //  Drawable icon = imgs.getDrawable(catOne.getId() - 1);
      //  imgs.recycle();
        //  CatTwo catOne= catTwoArrayList.get(position);
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QuranGrammarApplication.getContext());
        StringBuilder sb = new StringBuilder();
        holder.arabicroot_detail.setText(catOne);
       // sb.append(catOne.getId());
        //    holder.rulenumber.setTextSize(arabicFontsize);
     //   holder.title.setText(catOne.getTitle());
      //  holder.title.setTextSize(18);
        //  holder.id.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null);


    }

    @Override
    public long getItemId(int position) {
        //  Surah surah = surahArrayList.get(position);
        return catTwoArrayList.size();
    }

    public Object getItem(int position) {
        return catTwoArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return catTwoArrayList.size();

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener // current clickListerner
    {
       // public final ImageView id;
        public final Chip arabicroot_detail;
        public CardView cardview;

        public ViewHolder(View view) {
            super(view);
            view.setTag(this);
            itemView.setOnClickListener(this);
          //  id = view.findViewById(R.id.imgview);
            arabicroot_detail = view.findViewById(R.id.arabicroot_detail);
            arabicroot_detail.setTag("root");
            arabicroot_detail.setOnClickListener(this);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }





    }

}

