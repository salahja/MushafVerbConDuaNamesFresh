package com.example.roots;

import static com.example.Constant.WORDDETAILS;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mushafconsolidated.Activity.BaseActivity;
import com.example.mushafconsolidated.R;
import com.google.android.material.appbar.MaterialToolbar;


public class arabicrootDetailHostActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_arabicroot_detail);
        Bundle bundle = getIntent().getExtras();
        String wordorverb = bundle.getString(WORDDETAILS);
        Bundle arguments = new Bundle();
        arguments.putString(WORDDETAILS, wordorverb);
        MaterialToolbar toolbar=findViewById(R.id.toolbar_layout);
        toolbar.setTitle("Root List");
        if(wordorverb.equals("word")){
            toolbar.setTitle("Root List(Nouns/Verbs");
        }else {
            toolbar.setTitle("Verb Root List");
        }


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