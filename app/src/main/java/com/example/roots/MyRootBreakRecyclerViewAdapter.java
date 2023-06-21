package com.example.roots;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mushafconsolidated.Entities.RootWordDetails;
import com.example.mushafconsolidated.Entities.qurandictionary;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.roots.placeholder.PlaceholderContent.PlaceholderItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRootBreakRecyclerViewAdapter extends RecyclerView.Adapter<MyRootBreakRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<RootWordDetails> mValues;
    private OnItemClickListener mItemClickListener;

    public MyRootBreakRecyclerViewAdapter(ArrayList<RootWordDetails> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarfkabeercolumn, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_arabicroot_detail, parent, false);
        //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thulathisarfsagheer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RootWordDetails lughat = mValues.get(position);
        StringBuilder sb=new StringBuilder();
      //  sb.append(lughat.getSurah()).append("   ").append(lughat.getNamearabic()).append(lughat.getAyah()).append(" ").append(lughat.getArabic());
        sb.append(lughat.getArabic()).append("  ").append(lughat.getAyah()).append("  ").append(lughat.getNamearabic()).append("   ").append(lughat.getSurah());
     //   sb.append(lughat.getSurah()).append(":").append(lughat.getAyah()).append(":").append(lughat.getArabic()).append("-").append(lughat.getAbjadname());
       holder.arabicroot_detail.setText(sb.toString()    );

   //     holder.mIdView.setText(mValues.get(position).id);
    //    holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
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
            arabicroot_detail = view.findViewById(R.id.recarabicroot_detail);
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