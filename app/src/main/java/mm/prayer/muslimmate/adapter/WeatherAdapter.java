package mm.prayer.muslimmate.adapter;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

 

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mm.prayer.muslimmate.ui.NumbersLocal;
import mm.prayer.muslimmate.entity.WeatherApp;
import mm.prayer.muslimmate.ui.WeatherIcon;
import com.example.mushafconsolidated.R;

/**
 * Adapter for weather list
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<WeatherApp> weatherAppList;
    private Context context;

    public WeatherAdapter(List<WeatherApp> weatherAppList, Context context) {
        this.weatherAppList = weatherAppList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mm_row_weather, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherApp weatherApp = weatherAppList.get(position);
        String[] time = weatherApp.dayName.split(" ");
        String[] weatherTime = time[1].split(":");
        String[] date = time[0].split("-");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        d.setYear(Integer.parseInt(date[0]));
        d.setMonth(Integer.parseInt(date[1]) - 1);
        d.setDate(Integer.parseInt(date[2]) - 1);
        String dayOfTheWeek = sdf.format(d);
        Log.d("DAY", weatherApp.dayName + " : " + dayOfTheWeek);
        holder.dayName.setText(NumbersLocal.convertNumberType(context, weatherTime[0] + ":" + weatherTime[1] + ""));
        holder.weather.setText(NumbersLocal.convertNumberType(context, weatherApp.tempMini + "°"));
        holder.image.setImageResource(WeatherIcon.get_icon_id_white(weatherApp.image));
    }

    @Override
    public int getItemCount() {
        return weatherAppList.size();
    }

    /**
     * Class of view holder in the adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView weather, dayName;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            dayName = (TextView) itemView.findViewById(R.id.textView17);
            weather = (TextView) itemView.findViewById(R.id.textView18);
            image = (ImageView) itemView.findViewById(R.id.imageView3);
        }
    }

}
