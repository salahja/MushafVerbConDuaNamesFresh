package com.example.roots;

import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushafconsolidated.Entities.RootVerbDetails;
import com.example.mushafconsolidated.Entities.RootWordDetails;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.fragments.QuranMorphologyDetails;
import com.example.mushafconsolidated.intrface.OnItemClickListener;
import com.example.roots.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.utility.CorpusUtilityorig;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class VerbDetailsRecAdapter extends RecyclerView.Adapter<VerbDetailsRecAdapter.ViewHolder> {

    private final ArrayList<RootVerbDetails> mValues;
    private OnItemClickListener mItemClickListener;

    public VerbDetailsRecAdapter(ArrayList<RootVerbDetails> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sarfkabeercolumn, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verbdetails, parent, false);
        //    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thulathisarfsagheer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        RootVerbDetails lughat = mValues.get(position);
        StringBuilder sb=new StringBuilder();

        SpannableString spannableString = CorpusUtilityorig.NewSetWordSpan(lughat.getTagone(), lughat.getTagtwo(), lughat.getTagthree(), lughat.getTagfour(), lughat.getTagfive(),
                lughat.getAraone(), lughat.getAratwo(), lughat.getArathree(), lughat.getArafour(), lughat.getArafive());
        //  sb.append(lughat.getSurah()).append("   ").append(lughat.getNamearabic()).append(lughat.getAyah()).append(" ").append(lughat.getArabic());

        sb.append(lughat.getAyah()).append("  ").append(lughat.getNamearabic()).append("   ").append(lughat.getSurah()).append(" ").append(lughat.getEn());
        SpannableString sbs=new SpannableString(sb);
        CharSequence charSequence = TextUtils.concat(spannableString,sb);
       // charSequence=TextUtils.concat(sb);
     //   sb.append(lughat.getSurah()).append(":").append(lughat.getAyah()).append(":").append(lughat.getArabic()).append("-").append(lughat.getAbjadname());
       holder.arabicsurahname.setText(charSequence    );

       holder.arabicsurahname.setText(lughat.getNamearabic());
       StringBuilder sa=new StringBuilder();
       sa.append(lughat.getSurah()).append(":").append(lughat.getAyah()).append(":").append(lughat.getWordno());
       holder.verbsaw.setText(sa.toString());
       holder.arabicword.setText(spannableString);
       holder.tensevoicegendernumbermood.setText( QuranMorphologyDetails.getGenderNumberdetails(lughat.getGendernumber()));
       sa=new StringBuilder();
       sa.append(lughat.getTense()).append(":").append(lughat.getVoice()).append(":").append(lughat.getMood_kananumbers());
       holder.tensevoice.setText(sa.toString());

       if(lughat.getForm().equals("I")){
           if(lughat.getThulathibab().length()>1){
            String s=   lughat.getThulathibab().substring(0,1);
               holder.wazan.setText(  QuranMorphologyDetails.getThulathiName(s));

           }else{
               holder.wazan.setText(  QuranMorphologyDetails.getThulathiName(lughat.getThulathibab()));
           }


        //   QuranMorphologyDetails.getThulathiName(lughat.getThulathibab());

       }else {
           holder.wazan.setText(  QuranMorphologyDetails.getFormName(lughat.getForm()));
       }


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
        public final Chip conjugate;
        public CardView cardview;
        public TextView arabicsurahname,verbsaw,arabicword,wazan,tensevoicegendernumbermood,tensevoice;

        public ViewHolder(View view) {
            super(view);
            view.setTag(this);
            itemView.setOnClickListener(this);
            //  id = view.findViewById(R.id.imgview);
            arabicsurahname = view.findViewById(R.id.arabicsurahname);
            arabicsurahname.setTag("root");
            arabicsurahname.setOnClickListener(this);
            conjugate=view.findViewById(R.id.conjugate);
            arabicsurahname=view.findViewById(R.id.arabicsurahname);
            verbsaw=view.findViewById(R.id.verbsaw);
            arabicword=view.findViewById(R.id.arabicword);
            wazan=view.findViewById(R.id.wazan);
            tensevoicegendernumbermood=view.findViewById(R.id.tensevoicegendernumbermood);
            tensevoice=view.findViewById(R.id.tensevoice);
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