package com.example.mushafconsolidated.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mushafconsolidated.R;
import com.example.mushafconsolidated.databinding.ActivityGrammarruleDetailBinding;

public class GrammarRuleDetailHostActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityGrammarruleDetailBinding binding = ActivityGrammarruleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_grammarrule_detail);
        NavController navController = navHostFragment.getNavController();

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(toolbar, navController);

//        val navHostFragment: NavHostFragment = nav_host_fragment as NavHostFragment
//        NavigationUI.setupWithNavController(toolbar, navHostFragment.navController)








    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, QuranGrammarAct.class);
        startActivity(in);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_grammarrule_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}