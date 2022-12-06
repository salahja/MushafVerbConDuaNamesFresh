package mm.prayer.muslimmate.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

 

import java.util.List;

import mm.prayer.muslimmate.Activity.PrayShowActivityPrayer;
import mm.prayer.muslimmate.ui.AppEvent;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.entity.Dates;
import mm.prayer.muslimmate.ui.HGDate;
import mm.prayer.muslimmate.ui.NumbersLocal;
import com.example.mushafconsolidated.R;

/**
 * Adapter for islamic event list
 */
public class IslamicEventAdapter extends RecyclerView.Adapter<IslamicEventAdapter.ViewHolder> {
    private List<AppEvent> eventList;
    private Context context;

    public IslamicEventAdapter(Context context, List<AppEvent> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.mm_row_event, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AppEvent event = eventList.get(position);

        //convert from islamic to georgian
        String[] date = event.hejriDate.split("/");
        HGDate hgd = new HGDate();
        hgd.setHigri(Integer.valueOf(date[2]), Integer.valueOf(date[1]), Integer.valueOf(date[0]));


        //Show event name
        holder.eventName.setText(event.eventName.trim().equals(context.getString(R.string.milad_al_naby))? event.eventName+" "+"ﷺ" : event.eventName);

        //set event icon in right or left according to language
        if (ConfigPreferences.getApplicationLanguage(context).equals("en"))
            holder.eventName.setCompoundDrawablesWithIntrinsicBounds(event.icon, 0, 0, 0);
        else
            holder.eventName.setCompoundDrawablesWithIntrinsicBounds(0, 0, event.icon, 0);

        //Show dates
        holder.hejriDate.setText(NumbersLocal.convertNumberType(context, date[0]) + " " +
                Dates.islamicMonthName(context, Integer.valueOf(date[1])-1) + " " +
                NumbersLocal.convertNumberType(context, date[2]));
        hgd.toGregorian();
        holder.meladyDate.setText(NumbersLocal.convertNumberType(context, hgd.getDay() + "") +
                " " + Dates.gregorianMonthName(context, hgd.getMonth()-1) + " " +
                NumbersLocal.convertNumberType(context, hgd.getYear() + ""));

        //make layout is clickable to fo to show event prayer
        ((RelativeLayout) holder.eventName.getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, PrayShowActivityPrayer.class).putExtra("date", event.hejriDate));
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static final String decode(final String in)
    {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while(index > -1)
        {
            int length = working.length();
            if(index > (length-6))break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring,16);
            String stringStart = working.substring(0, index);
            String stringEnd   = working.substring(numFinish);
            working = stringStart + ((char)number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }

    /**
     * Class of view holder in the adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName, hejriDate, meladyDate;

        public ViewHolder(View itemView) {
            super(itemView);
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/AlQalam.ttf");
            eventName = (TextView) itemView.findViewById(R.id.textView15);
            eventName.setTypeface(tf);
            hejriDate = (TextView) itemView.findViewById(R.id.hejri);
            meladyDate = (TextView) itemView.findViewById(R.id.melady);
        }
    }

}
