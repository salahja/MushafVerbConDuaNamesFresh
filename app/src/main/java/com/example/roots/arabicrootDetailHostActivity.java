package com.example.roots;

import static com.example.Constant.QURAN_VERB_ROOT;
import static com.example.Constant.WORDDETAILS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mushafconsolidated.Activity.ActivitySettings;
import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.databinding.ActivityArabicrootDetailBinding;
import com.example.mushafconsolidated.fragments.NewSurahDisplayFrag;
import com.example.mushafconsolidated.fragments.WbwTranslationListPrefrence;
import com.example.mushafconsolidated.fragments.WordAnalysisBottomSheet;

import java.util.Objects;


public class arabicrootDetailHostActivity extends BaseActivity {

    private String wordorverb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   ActivityArabicrootDetailBinding binding = ActivityArabicrootDetailBinding.inflate(getLayoutInflater());
   //     setContentView(binding.getRoot());
        setContentView(R.layout.activity_arabicroot_detail);
        Bundle bundle = getIntent().getExtras();
        wordorverb = bundle.getString(WORDDETAILS);
        Bundle arguments = new Bundle();
        arguments.putString(WORDDETAILS, wordorverb);
        arabicrootListFragment afrag=new arabicrootListFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        arabicrootListFragment newCustomFragment = arabicrootListFragment.newInstance(wordorverb);
        newCustomFragment.setArguments(bundle);
        transaction.replace(R.id.nav_host_fragment_arabicroot_detail, newCustomFragment);
        transaction.addToBackStack(null);
        transaction.commit();

 /*       NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_arabicroot_detail);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        setSupportActionBar(binding.toolbarLayout);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.root_title);*/


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_arabicroot_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}