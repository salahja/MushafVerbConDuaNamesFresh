package mm.prayer.muslimmate.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Locale;
import java.util.Objects;

import mm.prayer.muslimmate.Activity.CompassActivityPrayer;
import mm.prayer.muslimmate.Activity.SettingsActivity;
import mm.prayer.muslimmate.fragments.CalendarFragment;
import mm.prayer.muslimmate.fragments.IslamicEventsFragment;
import mm.prayer.muslimmate.fragments.PrayingFragment;
import mm.prayer.muslimmate.fragments.WeatherFragment;
import mm.prayer.muslimmate.ui.ConfigPreferences;
import mm.prayer.muslimmate.ui.DataConvertPopup;

public class MuslimMateBookMarkActivity extends BaseActivity {

    private static final int[] TAB_TITLES = new int[]{R.string.collection, R.string.pins };

    // private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager2 mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //load application language
        String languageToLoad = ConfigPreferences.getApplicationLanguage(this);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        /*addHelper = new Adds();*/
        setContentView(R.layout.mm_activity_main_location);
        final TabLayout tabLayout = findViewById(R.id.tabs);
      //  final ViewPager2 viewPager = findViewById(R.id.container);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        FragmentManager fm = getSupportFragmentManager();
        ViewStateAdapter mSectionsPagerAdapter = new ViewStateAdapter(fm, getLifecycle());




        //  mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        new TabLayoutMediator(tabLayout, mViewPager, (tab, position) -> tab.setText(TAB_TITLES[position])).attach();
 
        //clickable application title
        TextView applicationTitle = (TextView) findViewById(R.id.title);
        applicationTitle.setText(getString(R.string.main));
        applicationTitle.setOnClickListener(v -> mViewPager.setCurrentItem(0));






    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mm_menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

     if (id == R.id.action_convert_date) {
         //start date convert pop-up
          new DataConvertPopup(this);
     }else  if (id == R.id.action_settings) {
            //start compass activity
            startActivity(new Intent(this, CompassActivityPrayer.class));
            return true;


        }  else if (id == R.id.settings) {

               startActivityForResult(new Intent(this, SettingsActivity.class) , 16);
        }
     


        else if (id == R.id.action_share) {
            //share intent for the application
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "#" + getString(R.string.app_name) + "\n https://play.google.com/store/apps/details?id=com.fekracomputers.muslimmate");
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }

        return super.onOptionsItemSelected(item);
    }






    private static class ViewStateAdapter extends FragmentStateAdapter {
        public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:

                    return new PrayingFragment();
                case 1:
                    return new CalendarFragment();

                case 2:

                    return new IslamicEventsFragment();
                case 3:
                    return new WeatherFragment();



                default:
                    return new PrayingFragment();
            }


        }

        @Override
        public int getItemCount() {

            return 4;

        }

    }

}
